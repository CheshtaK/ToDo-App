package com.example.cheshta.todo.Models;

/**
 * Created by chesh on 12/28/2017.
 */
public class Task {

    String id;
    String taskName;
    boolean checked;

    public Task() {
    }

    public Task(String id, String taskName) {
        this.id = id;
        this.taskName = taskName;
    }

    public Task(String id, String taskName, boolean checked) {
        this.id = id;
        this.taskName = taskName;
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

}
