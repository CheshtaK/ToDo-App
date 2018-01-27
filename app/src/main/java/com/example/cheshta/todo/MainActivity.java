package com.example.cheshta.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference("tasks");
/*
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Task task = dataSnapshot.getValue(Task.class);
                task.setTaskName(task.getTaskName());
                Log.d(TAG, "onDataChange: " + task.getTaskName());
                task.setChecked(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                Toast.makeText(MainActivity.this, "Failed to load post.", Toast.LENGTH_SHORT).show();
            }
        };*/

//        mDatabase.addValueEventListener(postListener);

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
              addTask();
            }
        });
    }

    private void addTask(){
        data = etNew.getText().toString();
        if(!data.isEmpty()){
            String id = mDatabase.push().getKey();
            Task t = new Task(id, data, false);
            tasks.add(t);
            taskAdapter.notifyDataSetChanged();
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
