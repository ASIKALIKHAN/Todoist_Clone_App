package com.bawp.todoister;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingActivity extends AppCompatActivity  {
  //  implements AdapterView.OnItemSelectedListener

    private Spinner spinner;
    private SwitchMaterial notificationSwitch;
    private SwitchMaterial alarmSwitch;
    private Button saveButton;
    private final String[] wallpaper = new String[]{"Buttery wall-a","abstract milky-b","fantasy opaque-c","abstract smooth-d"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        spinner = findViewById(R.id.wallpaper_spinner);
        notificationSwitch = findViewById(R.id.switch_notification);
        alarmSwitch = findViewById(R.id.switch_alarm);
        saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> restartApp());
        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

        });
        alarmSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

        });

        ArrayAdapter<String> adapter = new ArrayAdapter(SettingActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                wallpaper);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

     //   spinner.setOnItemClickListener((AdapterView.OnItemClickListener) SettingActivity.this);






    }

    public void restartApp(){
        Intent restart = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(restart);

        Intent jumpTo = new Intent(getApplicationContext(), SettingActivity.class);
        startActivity(jumpTo);
        finish();
    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
}