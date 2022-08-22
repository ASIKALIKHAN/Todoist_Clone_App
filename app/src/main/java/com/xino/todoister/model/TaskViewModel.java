package com.xino.todoister.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.xino.todoister.data.DoisterRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    public static  DoisterRepository doisterRepository;
    public final LiveData<List<Task>> allTask;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        doisterRepository = new DoisterRepository(application);
        allTask = doisterRepository.getAllTask();
    }

    public LiveData<List<Task>> getAllTask() {
        return allTask;
    }
    public  LiveData<Task> getTask(long id) {
        return doisterRepository.getTask(id);
    }
    public static void insert(Task task){ doisterRepository.insert(task);}
    public static void update(Task task){ doisterRepository.update(task);}
    public static void delete(Task task){ doisterRepository.delete(task);}
    public static void deleteAll(){doisterRepository.deleteAll();}


}
