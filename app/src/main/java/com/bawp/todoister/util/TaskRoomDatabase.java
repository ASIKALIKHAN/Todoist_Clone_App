package com.bawp.todoister.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.bawp.todoister.data.TaskDao;
import com.bawp.todoister.model.Priority;
import com.bawp.todoister.model.Task;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = Task.class,exportSchema = false,version = 1)
@TypeConverters({Converters.class})
public abstract class TaskRoomDatabase extends RoomDatabase {
    public static final int NUMBER_OF_THREADS = 4;
    public static final String DATABASE_NAME = "todoister_database";
    private static volatile TaskRoomDatabase INSTANCE;
    public static final ExecutorService service = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static final RoomDatabase.Callback sRoomDatabaseCallback =new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            service.execute(() -> {
                // invoke Dao and write to database
                TaskDao taskDao = INSTANCE.taskDao();
                taskDao.insertTask(new Task("write something todo", Priority.HIGH, Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),false ));
            });
        }
    };
    public static TaskRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null){
            synchronized (TaskRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),TaskRoomDatabase.class,DATABASE_NAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    public  abstract TaskDao taskDao();


}
