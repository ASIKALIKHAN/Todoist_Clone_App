package com.bawp.todoister;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
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

    public static AlarmActivity getINSTANCE(){
        return INSTANCE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        title = findViewById(R.id.textview_todo_title_alarm);
        chipPriority = findViewById(R.id.chip_priority_alarm);
        chipDatenTime = findViewById(R.id.chip_date_time_alarm);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), Settings.System.DEFAULT_ALARM_ALERT_URI );
        mediaPlayer.start();
       // mediaPlayer.setLooping(true);

        if(getIntent().getExtras() != null) {
            title.setText(getIntent().getStringExtra("TITLE"));
            chipPriority.setText(getIntent().getStringExtra("PRIORITY"));
            chipDatenTime.setText(getIntent().getStringExtra("DATE"));
        }
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