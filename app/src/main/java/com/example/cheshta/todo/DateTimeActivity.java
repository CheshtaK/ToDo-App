package com.example.cheshta.todo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.cheshta.todo.Adapter.TaskAdapter;
import com.example.cheshta.todo.Models.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DateTimeActivity extends AppCompatActivity {

    EditText etDate, etTime;
    Button btnAdd;
    Calendar myCalendar = Calendar.getInstance();
    Calendar myTime = Calendar.getInstance();

    String data;
    ArrayList<Task> tasks = new ArrayList<>();
    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);

        Intent intent = getIntent();
        data = intent.getStringExtra("task");

        Toast.makeText(this, "Set the date and time for your task", Toast.LENGTH_SHORT).show();

        etDate = findViewById(R.id.etDate);
        etTime = findViewById(R.id.etTime);
        btnAdd = findViewById(R.id.btnAdd);

        taskAdapter = new TaskAdapter(this, tasks);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                myCalendar.set(Calendar.YEAR, i);
                myCalendar.set(Calendar.MONTH, i1);
                myCalendar.set(Calendar.DAY_OF_MONTH, i2);
                updateLabel();
            }
        };

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(
                        DateTimeActivity.this,
                        date,
                        2018,
                        1,
                        6)
                        .show();
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = myTime.get(Calendar.HOUR_OF_DAY);
                int minute = myTime.get(Calendar.MINUTE);
                final int sec = myTime.get(Calendar.SECOND);
                new TimePickerDialog(
                        DateTimeActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                etTime.setText(i + ":" + i1 + ":" + sec);
                            }
                        },
                        hour,
                        minute,
                        false).show();;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!data.isEmpty()){
                    Task t = new Task();
                    t.setTaskName(data);
                    t.setChecked(false);
                    tasks.add(t);
                    taskAdapter.notifyDataSetChanged();
                    Toast.makeText(DateTimeActivity.this, "Task Added", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(DateTimeActivity.this, "Add some task", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateLabel(){
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etDate.setText(sdf.format(myCalendar.getTime()));
    }
}

