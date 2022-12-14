package com.xino.todoister.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "task_table")
public class Task {

    @ColumnInfo(name = "task_id")
    @PrimaryKey(autoGenerate = true)
    public long taskId;
    @ColumnInfo(name = "task")
    public String task;
    @ColumnInfo(name = "priority")
    public Priority priority;

    @ColumnInfo(name = "due_date")
    public Date dueDate;

    @ColumnInfo(name = "created_at")
    public Date dateCreated;

    @ColumnInfo(name = "is_done")
    public boolean isDone;

    @ColumnInfo(name = "is_radio_selected")
    // for the radio button state//
    public boolean isRadioSelected ;

    @ColumnInfo(name = "task_description")
    public String taskDescription;

    public boolean isRadioSelected() {
        return this.isRadioSelected;
    }

    public void setRadioSelected(boolean radioSelected) {
        isRadioSelected = radioSelected;
    }
    // for the radio button state//

    public Task(long taskId, String task, Priority priority, Date dueDate, Date dateCreated, boolean isDone) {
        this.taskId = taskId;
        this.task = task;
        this.priority = priority;
        this.dueDate = dueDate;
        this.dateCreated = dateCreated;
        this.isDone = isDone;
    }



    @Ignore
    public Task(String task, Priority priority, Date dueDate, Date dateCreated, boolean isDone) {
        this.task = task;
        this.priority = priority;
        this.dueDate = dueDate;
        this.dateCreated = dateCreated;
        this.isDone = isDone;
    }
    @Ignore
    public Task(String task, Priority priority, Date dueDate, Date dateCreated, boolean isDone, boolean isRadioSelected) {
        this.task = task;
        this.priority = priority;
        this.dueDate = dueDate;
        this.dateCreated = dateCreated;
        this.isDone = isDone;
        this.isRadioSelected = isRadioSelected;
    }
    @Ignore
    public Task(String task, Priority priority, Date dueDate, Date dateCreated, boolean isDone,  String taskDescription) {
        this.task = task;
        this.priority = priority;
        this.dueDate = dueDate;
        this.dateCreated = dateCreated;
        this.isDone = isDone;
        this.taskDescription = taskDescription;
    }


    @NonNull
    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", task='" + task + '\'' +
                ", priority=" + priority +
                ", dueDate=" + dueDate +
                ", dateCreated=" + dateCreated +
                ", isDone=" + isDone +
                ", isRadioSelected=" + isRadioSelected +
                '}';
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
}
