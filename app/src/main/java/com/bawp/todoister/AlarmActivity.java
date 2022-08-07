package com.bawp.todoister;


import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.chip.Chip;

public class AlarmActivity extends AppCompatActivity {
    private static AlarmActivity INSTANCE;
    private TextView title;
    private Chip chipPriority;
    private Chip chipDatenTime;
    private Button offButton;
    private  MediaPlayer mediaPlayer;
    private String taskName,taskPriority,taskDate;

    public static AlarmActivity getINSTANCE(){
        return INSTANCE;
    }

    @Override
    protected void onStart() {
        super.onStart();
        INSTANCE = this;
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        title = findViewById(R.id.textview_todo_title_alarm);
        chipPriority = findViewById(R.id.chip_priority_alarm);
        chipDatenTime = findViewById(R.id.chip_date_time_alarm);
        offButton = findViewById(R.id.button_off_alarm);


        mediaPlayer = MediaPlayer.create(getApplicationContext(), Settings.System.DEFAULT_ALARM_ALERT_URI);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

        if(getIntent().getExtras() != null) {
            taskName = getIntent().getStringExtra(BottomSheetFragment.TITLE_KEY);
            taskPriority = getIntent().getStringExtra(BottomSheetFragment.PRIORITY_KEY) + getString(R.string.alarm_aux_prioritytag);
            taskDate = getIntent().getStringExtra(BottomSheetFragment.DATE_KEY);
            title.setText(taskName);
            chipPriority.setText(taskPriority);
            chipDatenTime.setText(taskDate);

        }
        Log.d(BottomSheetFragment.TAG, "createAlarmForTodo: "+taskName);
        Log.d(BottomSheetFragment.TAG, "createAlarmForTodo: "+ taskPriority);
        Log.d(BottomSheetFragment.TAG, "createAlarmForTodo: "+ taskDate);
       // Glide.with(getApplicationContext()).load(R.drawable.alert).into(imageView);
        offButton.setOnClickListener(this::onClick);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

    private void onClick(View v) {
        finish();
    }
}