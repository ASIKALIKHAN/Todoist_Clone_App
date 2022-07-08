package com.bawp.todoister.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bawp.todoister.model.Task;
import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);

    @SuppressWarnings("AndroidUnresolvedRoomSqlReference")
    @Query("DELETE FROM task_table")
    void deleteAllTask();

    @SuppressWarnings("AndroidUnresolvedRoomSqlReference")
    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getTaskList();

    @SuppressWarnings("AndroidUnresolvedRoomSqlReference")
    @Query("SELECT * FROM task_table WHERE task_table.task_id ==:id")
    LiveData<Task> getTask(long id);

//    @SuppressWarnings("AndroidUnresolvedRoomSqlReference")
//    @Query("DELETE FROM task_table WHERE task_table.is_radio_selected==:iS")
//    void deleteRadioSelected(Task task,boolean iS);

}
