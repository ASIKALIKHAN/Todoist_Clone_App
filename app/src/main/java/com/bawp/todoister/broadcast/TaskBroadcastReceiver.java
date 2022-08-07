package com.bawp.todoister.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bawp.todoister.AlarmActivity;
import com.bawp.todoister.BottomSheetFragment;
import com.bawp.todoister.R;

public class TaskBroadcastReceiver extends BroadcastReceiver {
    private String title;
    private String priority;
    private String date;
    private int taskKey;
    @Override
    public void onReceive(Context context, Intent intent) {

        taskKey = intent.getIntExtra(BottomSheetFragment.TASK_KEY,-1);
        title = intent.getStringExtra(BottomSheetFragment.TITLE_KEY);
        priority = intent.getStringExtra(BottomSheetFragment.PRIORITY_KEY);
        date = intent.getStringExtra(BottomSheetFragment.DATE_KEY);
       // alarmOnScreen(context);
        notificationForTask(context);
    }

    private void notificationForTask(Context context) {
        NotificationCompat.Builder  builder = new NotificationCompat.Builder(context, BottomSheetFragment.NOTIFICATION_CHANNEL_ID_KEY)
                .setSmallIcon(R.drawable.ic_baseline_work_24)
                .setContentText("Remainder for your Task")
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(taskKey, builder.build());

    }

    private void alarmOnScreen(Context context) {
        Intent intentAlarm = new Intent(context, AlarmActivity.class);
        intentAlarm.putExtra(BottomSheetFragment.TITLE_KEY, title);
        intentAlarm.putExtra(BottomSheetFragment.PRIORITY_KEY, priority);
        intentAlarm.putExtra(BottomSheetFragment.DATE_KEY, date);
        intentAlarm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentAlarm);
    }

}
