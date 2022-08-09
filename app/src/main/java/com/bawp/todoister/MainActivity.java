package com.bawp.todoister;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import com.bawp.todoister.adapter.OnTodoClickListener;
import com.bawp.todoister.adapter.RecyclerViewTaskAdapter;
import com.bawp.todoister.broadcast.TaskBroadcastReceiver;
import com.bawp.todoister.model.SharedViewModel;
import com.bawp.todoister.model.Task;
import com.bawp.todoister.model.TaskViewModel;
import com.bawp.todoister.util.SettingSavedPrefs;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    public static final String FILE_NAME = "SAVED CHANGES FILE";
    public static final String ALARM_MODE ="ALARM_KEY";
    public static final String NOTIFY_MODE = "NOTIFY_KEY";
    public static final String WALLPAPER_MODE ="WALLPAPER_KEY";

    private CoordinatorLayout coordinatorLayout;
    private BottomSheetFragment bottomSheetFragment;
    private RecyclerViewTaskAdapter recyclerViewTaskAdapter;
    private TaskViewModel taskViewModel;
    private SharedViewModel sharedViewModel;
    private ConstraintLayout noTodoLayout;
    private static boolean deleteReadyForMenu = false;
    private static int wallpaperIndex =0;
//    private AlarmManager alarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /**FOR **/
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ComponentName receiver = new ComponentName(this, TaskBroadcastReceiver.class);
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        noTodoLayout = findViewById(R.id.status_no_todo);
        SettingSavedPrefs settingSavedPrefs = new SettingSavedPrefs(MainActivity.this);
        wallpaperIndex = settingSavedPrefs.getWallpaper();
        switch (settingSavedPrefs.getWallpaper()) {
            case 1:
                wallpaperIndex = R.drawable.picture_b;
                break;
            case 2:
                wallpaperIndex = R.drawable.picture_c;
                break;
            case 3:
                wallpaperIndex = R.drawable.picture_d;
                break;
            default:
                wallpaperIndex = R.drawable.picture_a;
        }
        
        coordinatorLayout = findViewById(R.id.main_constraint_layout);
        coordinatorLayout.setBackground(AppCompatResources.getDrawable(MainActivity.this,wallpaperIndex));

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bottomSheetFragment = new BottomSheetFragment();
        ConstraintLayout constraintLayout = findViewById(R.id.bottomSheet);
     //   //todO note this for future use bottoMsheet animation//
    //   BottomSheetBehavior<ConstraintLayout> bottomSheetBehaviour = BottomSheetBehavior.from(findViewById(R.id.bottomSheet));
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehaviour = BottomSheetBehavior.from(constraintLayout);

        bottomSheetBehaviour.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);

        // implements OnTodoClickListener
      taskViewModel = new ViewModelProvider.AndroidViewModelFactory(
                MainActivity.this.getApplication())
                .create(TaskViewModel.class);

        sharedViewModel = new ViewModelProvider(this)
                .get(SharedViewModel.class);

        recyclerViewTaskAdapter = new RecyclerViewTaskAdapter();
        recyclerView.setAdapter(recyclerViewTaskAdapter);
        taskViewModel.getAllTask().observe(this,
                tasks -> recyclerViewTaskAdapter.setUpdatedTaskList(tasks));

        recyclerViewTaskAdapter.setOnTaskClickListener( new OnTodoClickListener() {
            @Override
            public void onTodoClick(Task task) {
                sharedViewModel.setSelectedItem(task);
                sharedViewModel.setIsEdit(true);
                showBottomSheetDialog();
            }

            @Override
            public void onTodoLongClick(Task task, int position) {
                Dialog dialogCompat = new Dialog(MainActivity.this);
                dialogCompat.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogCompat.setContentView(R.layout.delete_todo_dialogue);
                Button buttonYes= dialogCompat.findViewById(R.id.buttonYes);
                Button buttonNo= dialogCompat.findViewById(R.id.buttonNo);
                TextView warning = dialogCompat.findViewById(R.id.warning_msg);
                warning.setText(R.string.single_todo_warning);
                buttonYes.setOnClickListener(v -> {
                    TaskViewModel.delete(task);
                    recyclerViewTaskAdapter.notifyItemRemoved(position);
                    dialogCompat.dismiss();


                });
                buttonNo.setOnClickListener(v -> {
                    Toast.makeText(MainActivity.this, "DELETE OPERATION CANCELED", Toast.LENGTH_SHORT).show();
                    dialogCompat.dismiss();
                });
                dialogCompat.show();

            }
            @Override
            public void onTodoRadioButtonClick(Task task, int position) {
                deleteReadyForMenu = true;
                task.setRadioSelected(!task.isRadioSelected); // with each click the state(isRadioSelected) is toggled
                TaskViewModel.update(task); //setting the radiobutn as true
            //    Toast.makeText(MainActivity.this, "LOOK"+ task.getTask() +" :"+task.isRadioSelected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNoTodoEvent(boolean emptyTodo) {
                if(emptyTodo){
             noTodoLayout.setVisibility(View.VISIBLE);}
                else{

                    noTodoLayout.setVisibility(View.GONE);
                }
            }
        });


        // if(taskViewModel.getAllTask())
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> showBottomSheetDialog());
    }


    private void showBottomSheetDialog() {
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            startActivity(new Intent(this,AboutActivity.class));

        }
        else if (id == R.id.action_delete_selected) {
            if(deleteReadyForMenu) {
                Dialog dialogCompat = new Dialog(MainActivity.this);
                dialogCompat.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogCompat.setContentView(R.layout.delete_todo_dialogue);
                Button buttonYes = dialogCompat.findViewById(R.id.buttonYes);
                Button buttonNo = dialogCompat.findViewById(R.id.buttonNo);
                TextView warning = dialogCompat.findViewById(R.id.warning_msg);
                warning.setText(R.string.todo_delete_select_msg);

                List<Task> tasks = taskViewModel.getAllTask().getValue();
                buttonYes.setOnClickListener(v -> {
                    for (int i = 0; i < Objects.requireNonNull(tasks).size(); i++) {
                        Task taskMe = tasks.get(i);
                        if (taskMe.isRadioSelected) {
                          /**cancelAlarmNNotification**/
                           // cancelAlertification(taskMe);
                            TaskViewModel.delete(taskMe);
                        }
                    }
                    recyclerViewTaskAdapter.notifyDataSetChanged();
                    dialogCompat.dismiss();
                });
                buttonNo.setOnClickListener(v -> {
                    Toast.makeText(MainActivity.this, "DELETE OPERATION CANCELED", Toast.LENGTH_SHORT).show();
                    dialogCompat.dismiss();
                });
                dialogCompat.show();
                deleteReadyForMenu = false;
            }
            else{
                Toast.makeText(MainActivity.this, "PLEASE SELECT SOME TODO", Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.action_delete_all) {
            Dialog dialogCompat = new Dialog(MainActivity.this);
            dialogCompat.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogCompat.setContentView(R.layout.delete_todo_dialogue);
            Button buttonYes= dialogCompat.findViewById(R.id.buttonYes);
            Button buttonNo= dialogCompat.findViewById(R.id.buttonNo);
            TextView warning = dialogCompat.findViewById(R.id.warning_msg);
            warning.setText(R.string.todo_delete_msg);

            buttonYes.setOnClickListener(v -> {
                TaskViewModel.deleteAll();
                recyclerViewTaskAdapter.notifyDataSetChanged();
                dialogCompat.dismiss();


            });
            buttonNo.setOnClickListener(v -> {
                Toast.makeText(MainActivity.this,
                        R.string.toastmsg_on_cancel,
                        Toast.LENGTH_SHORT).show();
                dialogCompat.dismiss();
            });
            dialogCompat.show();
        }
        else if(id == R.id.action_settings){
            //todo add settings features
            Intent intentTake = new Intent(MainActivity.this,SettingActivity.class);
            startActivity(intentTake);
         //  new Handler().postDelayed(() -> Toast.makeText(getApplicationContext(), "Designed & Developed by: ❤️AIZU", Toast.LENGTH_LONG).show(),3000);
        }
        return super.onOptionsItemSelected(item);
    }

 /*   private void cancelAlertification(Task taskMe) {
        Intent alarmIntent = new Intent(MainActivity.this, TaskBroadcastReceiver.class);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent
                .getBroadcast
                        (MainActivity.this, (int) taskMe.getTaskId(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if(alarmManager ==null){
         alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);}
        alarmManager.cancel(pendingIntent);
    }

  */

}