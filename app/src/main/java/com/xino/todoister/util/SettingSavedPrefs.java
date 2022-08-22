package com.xino.todoister.util;


import android.content.Context;
import android.content.SharedPreferences;

import com.xino.todoister.MainActivity;

public class SettingSavedPrefs {
  private static  SharedPreferences sharedPrefs;

    public SettingSavedPrefs(Context context) {
        sharedPrefs = context.getSharedPreferences(MainActivity.FILE_NAME,Context.MODE_PRIVATE);
    }
    public void setAlarmMode(Boolean mode){
         SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(MainActivity.ALARM_MODE,mode);
        editor.apply();
    }
    public boolean getAlarmMode(){
       return sharedPrefs.getBoolean(MainActivity.ALARM_MODE,false);

    }

    public void setNotifyMode(Boolean mode){
         SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(MainActivity.NOTIFY_MODE,mode);
        editor.apply();
    }
    public boolean getNotifyMode(){
       return sharedPrefs.getBoolean(MainActivity.NOTIFY_MODE,false);
    }
    public void setWallpaper(int wallpaper){
        SharedPreferences.Editor editor = sharedPrefs.edit();
       editor.putInt(MainActivity.WALLPAPER_MODE,wallpaper);
       editor.apply();
    }
    public int getWallpaper(){
      return sharedPrefs.getInt(MainActivity.WALLPAPER_MODE,0);
    }

}
