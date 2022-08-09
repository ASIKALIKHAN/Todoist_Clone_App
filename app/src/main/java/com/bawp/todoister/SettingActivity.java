package com.bawp.todoister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.bawp.todoister.util.SettingSavedPrefs;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingActivity extends AppCompatActivity  {
  //  implements AdapterView.OnItemSelectedListener
    private  ConstraintLayout constraintLayout;
    private static SettingSavedPrefs settingSavedPrefs;
    private Spinner spinner;
    private SwitchMaterial notificationSwitch;
    private SwitchMaterial alarmSwitch;
    private Button saveButton;
    private final String[] wallpaperList = new String[]{"Buttery wall-a","Abstract Milky-b","Fantasy Opaque-c","Abstract Smooth-d"};
    private static int wallpaperIndex ;

    /**STATE_VARIABLE for saving changes fields **/
    private  static int  forSavingSelectedItemOfSpinner;
    private static int forSetSelectionItemFromPrefs;
    private static boolean forAlarmSaveState;
    private static boolean forNotificationSaveState;
    /** STATE_VARIABLE **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        settingSavedPrefs = new SettingSavedPrefs(getApplicationContext());

        /** FETCHING_SAVED_WALLPAPER -to set in background**/
        wallpaperIndex = settingSavedPrefs.getWallpaper();
        switch (settingSavedPrefs.getWallpaper()){
            case 1: wallpaperIndex = R.drawable.picture_b;
                break;
            case 2: wallpaperIndex = R.drawable.picture_c;
                break;
            case 3: wallpaperIndex = R.drawable.picture_d;
                break;
            default: wallpaperIndex = R.drawable.picture_a;
        }
        constraintLayout = findViewById(R.id.constrainlayout);
        constraintLayout.setBackground(AppCompatResources.getDrawable(getApplicationContext(),wallpaperIndex));
        /** FETCHING_SAVED_WALLPAPER **/

        spinner = findViewById(R.id.wallpaper_spinner);

        notificationSwitch = findViewById(R.id.switch_notification);
        /** FETCHING_SAVED_MODE -for notification **/
        if (settingSavedPrefs.getNotifyMode()==true){
            notificationSwitch.setChecked(true);
        }
        else {
            notificationSwitch.setChecked(false);
        }
        /** FETCHING_SAVED_MODE**/


         /** FETCHING_SAVED_MODE- for alarm **/
        alarmSwitch = findViewById(R.id.switch_alarm);
        if (settingSavedPrefs.getAlarmMode()==true){
            alarmSwitch.setChecked(true);
        }
        else {
            alarmSwitch.setChecked(false);
        }
          /** FETCHING_SAVED_MODE**/


        /** SAVE_CHANGES BUTTON **/
        saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(v -> SettingActivity.this.restartApp());

        /** SET_SWITCH STATE **/
        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            notifySaveChanges(isChecked);
        }) ;

        /** SET_SWITCH STATE **/
        alarmSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            alarmSaveChanges(isChecked);
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(SettingActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                wallpaperList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        /**SPINNER ITEM_ for getting the saved wallpaper as int and set the item of spinner selected  **/
        switch (settingSavedPrefs.getWallpaper()){
            case 1: { forSetSelectionItemFromPrefs=1;}
            break;
            case 2: { forSetSelectionItemFromPrefs=2;}
            break;
            case 3: { forSetSelectionItemFromPrefs=3;}
            break;
            default:{ forSetSelectionItemFromPrefs=0;}
            break;
        }
        spinner.setSelection(forSetSelectionItemFromPrefs);
        /** SPINNER ITEM     **/


        /** WALLPAPER_PREVIEW_ FOR CHECKIN OUT DIFFERENT WALLPAPER **/
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1: {
                        constraintLayout.setBackground(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.picture_b));}
                    break;
                    case 2:  {
                        constraintLayout.setBackground(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.picture_c)); }
                    break;
                    case 3: {
                        constraintLayout.setBackground(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.picture_d)); }
                    break;
                    default: {
                        constraintLayout.setBackground(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.picture_a));
                       }
                    break;
                }
                forSavingSelectedItemOfSpinner = position;

            }
            /**WALLPAPER_PREVIEW_ **/

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void alarmSaveChanges(boolean isChecked) {
        if (isChecked) {forAlarmSaveState = true;}
        else { forAlarmSaveState = false;}
    }

    private void notifySaveChanges(boolean isChecked) {
        if (isChecked) {forNotificationSaveState = true;}
        else {forNotificationSaveState = false;}
    }

  /*  private void goToHome() {
        Intent restart = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(restart);
        finish();
    }

   */

    /**SAVE_CHANGES_ For saving the changed setting ei,. alarm, notification, wallpaper**/
    public void restartApp(){
        settingSavedPrefs.setNotifyMode(forNotificationSaveState);
        settingSavedPrefs.setAlarmMode(forAlarmSaveState);
        settingSavedPrefs.setWallpaper(forSavingSelectedItemOfSpinner);
        Intent restart = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(restart);
        finish();
    }
    /** SAVE_CHANGES_ **/

}