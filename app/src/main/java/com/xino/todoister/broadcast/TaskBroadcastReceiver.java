package com.xino.todoister.broadcast;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xino.todoister.AlarmActivity;
import com.xino.todoister.BottomSheetFragment;
import com.xino.todoister.MainActivity;
import com.xino.todoister.util.SettingSavedPrefs;

public class TaskBroadcastReceiver extends BroadcastReceiver {
    private static String title;
    private static String priority;
    private static String date;
    private static int taskKey;
    private static boolean alarmMode ;
    private static boolean notifyMode;
    private  NotificationHelper notificationHelper;
    @Override
    public void onReceive(Context context, Intent intent) {
        notificationHelper = new NotificationHelper(context);
        getSavedSettings(context, intent);
        if(alarmMode) { alarmOnScreen(context);}
        if (notifyMode) {  notificationHelper.sendHighPriorityNotification(title,date,MainActivity.class);}
    }

    private void getSavedSettings(Context context, Intent intent) {
        SettingSavedPrefs settingSavedPrefs = new SettingSavedPrefs(context);
        alarmMode = settingSavedPrefs.getAlarmMode();
        notifyMode = settingSavedPrefs.getNotifyMode();
        taskKey = intent.getIntExtra(BottomSheetFragment.TASK_KEY,-1);
        title = intent.getStringExtra(BottomSheetFragment.TITLE_KEY);
        priority = intent.getStringExtra(BottomSheetFragment.PRIORITY_KEY);
        date = intent.getStringExtra(BottomSheetFragment.DATE_KEY);
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
