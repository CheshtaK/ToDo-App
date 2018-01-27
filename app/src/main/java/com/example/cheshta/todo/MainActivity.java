package com.example.cheshta.todo;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheshta.todo.Adapter.TaskAdapter;

import com.example.cheshta.todo.Models.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TODO";
    RecyclerView rvTasks;
    EditText etNew;
    CheckBox cbDone;
    Button btnAddNew;
    ArrayList<Task> tasks = new ArrayList<>();
    TaskAdapter taskAdapter;

    String data;

    private DatabaseReference mDatabase;

    @Override
    protected void onStart() {
        super.onStart();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tasks.clear();
                for(DataSnapshot taskSnapshot: dataSnapshot.getChildren()){
                    Task task = taskSnapshot.getValue(Task.class);
                    tasks.add(task);
                }

                taskAdapter = new TaskAdapter(MainActivity.this, tasks);
                rvTasks.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                rvTasks.setAdapter(taskAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference("tasks");

        rvTasks = findViewById(R.id.rvTasks);
        etNew = findViewById(R.id.etNew);
        cbDone = findViewById(R.id.cbDone);
        btnAddNew = findViewById(R.id.btnAddNew);

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              addTask();
            }
        });
    }

    private void addTask(){
        data = etNew.getText().toString();
        if(!data.isEmpty()){
            String id = mDatabase.push().getKey();
            Task t = new Task(id, data, false);
            mDatabase.child(id).setValue(t);
            etNew.setText("");
            Toast.makeText(MainActivity.this, "Task Added", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(MainActivity.this, "Add some task", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(MainActivity.this, DateTimeActivity.class);
                intent.putExtra("task",data);
                startActivity(intent);*/
    }
}
