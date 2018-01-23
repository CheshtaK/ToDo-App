package com.example.cheshta.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.example.cheshta.todo.Adapter.TaskAdapter;

import com.example.cheshta.todo.Models.Task;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    RecyclerView rvTasks;
    EditText etNew;
    CheckBox cbDone;
    Button btnAddNew;
    ArrayList<Task> tasks = new ArrayList<>();
    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvTasks = findViewById(R.id.rvTasks);
        etNew = findViewById(R.id.etNew);
        cbDone = findViewById(R.id.cbDone);
        btnAddNew = findViewById(R.id.btnAddNew);

        taskAdapter = new TaskAdapter(this, tasks);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        rvTasks.setAdapter(taskAdapter);

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = etNew.getText().toString();
                etNew.setText("");
                Intent intent = new Intent(MainActivity.this, DateTimeActivity.class);
                intent.putExtra("task",data);
                startActivity(intent);
            }
        });

    }
}
