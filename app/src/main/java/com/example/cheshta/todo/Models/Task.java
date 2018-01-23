package com.example.cheshta.todo.Models;

/**
 * Created by chesh on 12/28/2017.
 */
public class Task {

    String taskName;
    boolean checked;

    public String getTaskName() {
        return taskName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setChecked(boolean checked) { this.checked = checked; }

    public String isCheckedAsString(){
        return String.valueOf(checked);
    }

}
