package com.xino.todoister;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;


public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        if (getSupportActionBar()!=null){
        getSupportActionBar().setTitle("About");
            ConstraintLayout constraintLayout = findViewById(R.id.ConstraintAbout);
            constraintLayout.setBackground(AppCompatResources.getDrawable(AboutActivity.this,R.drawable.my_gradient));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}