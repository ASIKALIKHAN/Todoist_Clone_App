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
import com.bawp.todoister.util.SettingSavedPrefs;

public class TaskBroadcastReceiver extends BroadcastReceiver {
    private static String title;
    private static String priority;
    private static String date;
    private static int taskKey;
    private static boolean alarmMode ;
 //   private static boolean notifyMode;
    @Override
    public void onReceive(Context context, Intent intent) {
        SettingSavedPrefs settingSavedPrefs = new SettingSavedPrefs(context);
        alarmMode = settingSavedPrefs.getAlarmMode();
     //   notifyMode = settingSavedPrefs.getNotifyMode();
        taskKey = intent.getIntExtra(BottomSheetFragment.TASK_KEY,-1);
        title = intent.getStringExtra(BottomSheetFragment.TITLE_KEY);
        priority = intent.getStringExtra(BottomSheetFragment.PRIORITY_KEY);
        date = intent.getStringExtra(BottomSheetFragment.DATE_KEY);
        if(alarmMode) { alarmOnScreen(context);}
       // if (notifyMode) { notificationForTask(context);}
    }
    private void alarmOnScreen(Context context) {
        Intent intentAlarm = new Intent(context, AlarmActivity.class);
        intentAlarm.putExtra(BottomSheetFragment.TITLE_KEY, title);
        intentAlarm.putExtra(BottomSheetFragment.PRIORITY_KEY, priority);
        intentAlarm.putExtra(BottomSheetFragment.DATE_KEY, date);
        intentAlarm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentAlarm);
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


}
