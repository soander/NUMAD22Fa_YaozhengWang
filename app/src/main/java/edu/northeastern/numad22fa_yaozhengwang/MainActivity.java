package edu.northeastern.numad22fa_yaozhengwang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import edu.northeastern.numad22fa_yaozhengwang.aboutMe.AboutMeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button aboutMe = findViewById(R.id.aboutMe);
        aboutMe.setOnClickListener(view -> openAboutMeActivity());

        Button clicky = findViewById(R.id.clicky);
        clicky.setOnClickListener(view -> openClickyActivity());
    }

    public void openClickyActivity() {
        Intent intent = new Intent(this, ClickyActivity.class);
        startActivity(intent);
    }

    public void openAboutMeActivity() {
        Intent intent = new Intent(this, AboutMeActivity.class);
        startActivity(intent);
    }
}