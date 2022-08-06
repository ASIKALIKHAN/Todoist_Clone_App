package com.bawp.todoister.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bawp.todoister.AlarmActivity;

public class TaskBroadcastReceiver extends BroadcastReceiver {
    private String title;
    private String priority;
    private String date;
    @Override
    public void onReceive(Context context, Intent intent) {
        title = intent.getStringExtra("TITLE");
        priority = intent.getStringExtra("PRIORITY");
        date = intent.getStringExtra("DATE");
        Intent i = new Intent(context, AlarmActivity.class);
        i.putExtra("TITLE", title);
        i.putExtra("PRIORITY", priority);
        i.putExtra("DATE", date);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }

}
