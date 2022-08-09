package com.bawp.todoister.broadcast;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;

import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bawp.todoister.BottomSheetFragment;
import com.bawp.todoister.MainActivity;
import com.bawp.todoister.R;
import com.bawp.todoister.util.SettingSavedPrefs;

public class TaskService extends Service {
    private static String title;
    private static String date;
    private static int taskKey;
  //  private static boolean alarmMode ;
    private static boolean notifyMode;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onHandleIntent(intent);
        return super.onStartCommand(intent, flags, startId);
    }
    protected void onHandleIntent(Intent intent) {
        SettingSavedPrefs settingSavedPrefs = new SettingSavedPrefs(getApplicationContext());
    //    alarmMode = settingSavedPrefs.getAlarmMode();
        notifyMode = settingSavedPrefs.getNotifyMode();
        taskKey = intent.getIntExtra(BottomSheetFragment.TASK_KEY,-1);
        title = intent.getStringExtra(BottomSheetFragment.TITLE_KEY);
        date = intent.getStringExtra(BottomSheetFragment.DATE_KEY);
       // if(alarmMode) { alarmOnScreen(context);}
        if (notifyMode) {
            notificationForTask();
        }
    }

    private void notificationForTask() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            NotificationChannel notificationChannel =
                    new NotificationChannel(BottomSheetFragment.NOTIFICATION_CHANNEL_ID_KEY,
                            "YOUR_CHANNEL_NAME",
                            NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("YOUR_NOTIFICATION_CHANNEL_DESCRIPTION");
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder  builder = new NotificationCompat.Builder(getApplicationContext(), BottomSheetFragment.NOTIFICATION_CHANNEL_ID_KEY)
                 .setSmallIcon(R.drawable.ic_baseline_work_24)
                 .setContentText("Remainder for your Task"+ " on " + date)
                 .setContentTitle(title)
                 .setPriority(NotificationCompat.PRIORITY_HIGH)
                 .setAutoCancel(true);
        Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent =
                PendingIntent.getActivity(this,taskKey,
                        intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        notificationManager.notify(taskKey, builder.build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    /**sampled code**/

         /*   NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                NotificationChannel notificationChannel =
                        new NotificationChannel(BottomSheetFragment.NOTIFICATION_CHANNEL_ID_KEY,
                        "YOUR_CHANNEL_NAME",
                        NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setDescription("YOUR_NOTIFICATION_CHANNEL_DESCRIPTION");
                mNotificationManager.createNotificationChannel(notificationChannel);
            }
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), BottomSheetFragment.NOTIFICATION_CHANNEL_ID_KEY)
                    .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                    .setContentTitle("Remainder for your Task") // title for notification
                    .setContentText(title)// message for notification
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true); // clear notification after click

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pi =
                    PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pi);
            mNotificationManager.notify(taskKey, mBuilder.build());

          */
    /**sampled code**/
}
