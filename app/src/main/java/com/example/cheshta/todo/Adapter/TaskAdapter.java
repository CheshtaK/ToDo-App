package com.example.cheshta.todo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cheshta.todo.Models.Task;
import com.example.cheshta.todo.R;

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
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        final Task currentTask = tasks.get(position);
        if(currentTask!=null){
            holder.tvTask.setText(currentTask.getTaskName());
            holder.cbDone.setText(currentTask.isCheckedAsString());

            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tasks.remove(currentTask);
                    tasks.trimToSize();
                    notifyDataSetChanged();
                }
            });

            holder.cbDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        currentTask.setChecked(true);
                    }
                    else {
                        currentTask.setChecked(false);
                    }
                }
            });
        }
        else {
            holder.tvTask.setText("");
            holder.cbDone.setText(String.valueOf(false));
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

        public TaskViewHolder(View itemView) {
            super(itemView);
            tvTask = itemView.findViewById(R.id.tvTask);
            cbDone = itemView.findViewById(R.id.cbDone);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }
}
