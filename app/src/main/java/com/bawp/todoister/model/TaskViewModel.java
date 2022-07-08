package com.bawp.todoister.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.bawp.todoister.data.DoisterRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    public static  DoisterRepository doisterRepository;
    public final LiveData<List<Task>> allTask;
 //   public final MutableLiveData<List<Task>> radioAllTask = new MutableLiveData<>();

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

//    public void setRadioAllTask(List<Task> taskList){
//        this.radioAllTask.setValue(taskList);
//    }

//    public void deleteSelected(){
//        if (getRadioAllTask() == null) {
//            return;
//        }
//        else {
//            List<Task> tasks = (List<Task>) this.getRadioAllTask();
//            for (int i = 0; i < tasks.size(); i++) {
//                doisterRepository.delete(tasks.get(i));
//            }  }
//    }
//
//
//    public LiveData<List<Task>> getRadioAllTask() {
//        return radioAllTask;
//    }
}
