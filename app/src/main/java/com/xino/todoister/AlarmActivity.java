package com.xino.todoister;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.google.android.material.chip.Chip;

public class AlarmActivity extends AppCompatActivity {
    private static AlarmActivity INSTANCE;
    private  MediaPlayer mediaPlayer;
    private String taskName,taskPriority,taskDate;
    private LottieAnimationView lottieAnimationView;


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
        TextView title = findViewById(R.id.textview_todo_title_alarm);
      //  Chip chipPriority = findViewById(R.id.chip_priority_alarm);
        Chip chipDateNTime = findViewById(R.id.chip_date_time_alarm);
        Button offButton = findViewById(R.id.button_off_alarm);
        lottieAnimationView = findViewById(R.id.lotteViewAnim);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), Settings.System.DEFAULT_ALARM_ALERT_URI);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

        if(getIntent().getExtras() != null) {
            taskName = getIntent().getStringExtra(BottomSheetFragment.TITLE_KEY);
            taskPriority = getIntent().getStringExtra(BottomSheetFragment.PRIORITY_KEY) +" "+ getString(R.string.alarm_aux_prioritytag);
            taskDate = getIntent().getStringExtra(BottomSheetFragment.DATE_KEY);
            title.setText(taskName);
         //   chipPriority.setText(taskPriority);
            chipDateNTime.setText(taskDate);
        }
        new Handler().postDelayed(() -> {
            mediaPlayer.release();

            finish();
        }, 120000);

        offButton.setOnClickListener(this::onClick);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer!=null){mediaPlayer.release();}
        finish();
    }

    private void onClick(View v) {
       // lottieAnimationView.clearAnimation();
        finish();
    }
}