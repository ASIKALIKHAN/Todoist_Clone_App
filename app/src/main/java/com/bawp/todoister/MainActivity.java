package com.bawp.todoister;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import com.bawp.todoister.adapter.OnTodoClickListener;
import com.bawp.todoister.adapter.RecyclerViewTaskAdapter;
import com.bawp.todoister.model.SharedViewModel;
import com.bawp.todoister.model.Task;
import com.bawp.todoister.model.TaskViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private BottomSheetFragment bottomSheetFragment;
//    private  OnTodoClickListener listener;
    private RecyclerViewTaskAdapter recyclerViewTaskAdapter;
    private TaskViewModel taskViewModel;
    private SharedViewModel sharedViewModel;
    private static boolean deleteReadyForMenu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                deleteReadyForMenu= true;
                task.setRadioSelected(!task.isRadioSelected);
                TaskViewModel.update(task);
            //    Toast.makeText(MainActivity.this, "LOOK"+ task.getTask() +" :"+task.isRadioSelected, Toast.LENGTH_SHORT).show();
            }
        });
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
            Toast.makeText(MainActivity.this, R.string.toast_msg_for_underdevelopment, Toast.LENGTH_LONG).show();
           new Handler().postDelayed(() -> Toast.makeText(getApplicationContext(), "Designed & Developed by: ❤️AIZU", Toast.LENGTH_LONG).show(),3000);
        }
        return super.onOptionsItemSelected(item);
    }

}