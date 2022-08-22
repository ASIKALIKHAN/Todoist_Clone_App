package com.xino.todoister.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.xino.todoister.model.Task;
import com.xino.todoister.util.TaskRoomDatabase;

import java.util.List;

public class DoisterRepository {
    private final TaskDao taskDao;
    private final LiveData<List<Task>> allTask;

    public DoisterRepository(Application application) {
        TaskRoomDatabase database = TaskRoomDatabase.getDatabase(application);
        this.taskDao = database.taskDao();
        this.allTask = taskDao.getTaskList();
    }

    public LiveData<List<Task>> getAllTask() {
        return allTask;
    }
    public void insert(Task task){
        TaskRoomDatabase.service.execute(() -> taskDao.insertTask(task));
    }
    public LiveData<Task> getTask(long id){
        return taskDao.getTask(id);
    }
    public void update(Task task){
        TaskRoomDatabase.service.execute(() -> taskDao.updateTask(task));
    }
    public void delete(Task task){
        TaskRoomDatabase.service.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.deleteTask(task);
            }
        });
    }

    public void deleteAll(){
        TaskRoomDatabase.service.execute(taskDao::deleteAllTask);
    }
}
