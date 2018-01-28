package com.example.cheshta.todo.Adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheshta.todo.Models.Task;
import com.example.cheshta.todo.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by chesh on 12/28/2017.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private Context context;
    private ArrayList<Task> tasks;

    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.list_item_tasks,parent,false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, final int position) {
        final Task currentTask = tasks.get(position);
        if(currentTask!=null){
            holder.tvTask.setText(currentTask.getTaskName());
            holder.cbDone.setChecked(currentTask.isChecked());

            holder.ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Task task = tasks.get(position);
                    showUpdateDialog(task.getId(),task.getTaskName(),task.isChecked());
                }
            });

            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Task task = tasks.get(position);
                    deleteTask(task.getId());
                }
            });

            holder.cbDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("tasks").child(currentTask.getId());
                    if(b){
                        currentTask.setChecked(true);
                        Task task = new Task(currentTask.getId(),currentTask.getTaskName(),true);
                        databaseReference.setValue(task);
                    }
                    else {
                        currentTask.setChecked(false);
                        Task task = new Task(currentTask.getId(),currentTask.getTaskName(),true);
                        databaseReference.setValue(task);
                    }
                }
            });
        }
        else {
            holder.tvTask.setText("");
            holder.cbDone.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder{

        TextView tvTask;
        CheckBox cbDone;
        ImageView ivDelete;
        ImageView ivEdit;

        public TaskViewHolder(View itemView) {
            super(itemView);
            tvTask = itemView.findViewById(R.id.tvTask);
            cbDone = itemView.findViewById(R.id.cbDone);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            ivEdit = itemView.findViewById(R.id.ivEdit);
        }
    }

    private void showUpdateDialog(final String id, final String taskName, final boolean checked){
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = li.inflate(R.layout.edit_task,null);
        dialogBuilder.setView(dialogView);

        final EditText etTaskEdit = (EditText) dialogView.findViewById(R.id.etTaskEdit);
        final Button btnUpdate = (Button) dialogView.findViewById(R.id.btnUpdate);

        dialogBuilder.setTitle("Editing task: " + taskName);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etTaskEdit.getText().toString();
                if(name.isEmpty()){
                    etTaskEdit.setError("Task required");
                    return;
                }
                updateTask(id,name);
                alertDialog.dismiss();
            }
        });
    }

    private boolean updateTask(String id, String name){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("tasks").child(id);
        Task task = new Task(id,name);
        databaseReference.setValue(task);
        Toast.makeText(context, "Task updated successfully", Toast.LENGTH_SHORT).show();
        return true;
    }

    private void deleteTask(String id){
        DatabaseReference drTask = FirebaseDatabase.getInstance().getReference("tasks").child(id);
        drTask.removeValue();
        Toast.makeText(context, "Task is deleted", Toast.LENGTH_SHORT).show();
    }
}
