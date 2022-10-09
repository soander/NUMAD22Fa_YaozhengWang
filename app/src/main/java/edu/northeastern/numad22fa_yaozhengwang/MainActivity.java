package edu.northeastern.numad22fa_yaozhengwang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.northeastern.numad22fa_yaozhengwang.aboutMe.AboutMeActivity;
import edu.northeastern.numad22fa_yaozhengwang.linkCollector.LinkCollectorActivity;
import edu.northeastern.numad22fa_yaozhengwang.primeDirective.PrimeDirectiveActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button aboutMe = findViewById(R.id.aboutMe);
        aboutMe.setOnClickListener(view -> openAboutMeActivity());

        Button clicky = findViewById(R.id.clicky);
        clicky.setOnClickListener(view -> openClickyActivity());

        Button linkCollector = findViewById(R.id.linkCollector);
        linkCollector.setOnClickListener(view -> openLinkCollectorActivity());

        Button primeDirective = findViewById(R.id.primeDirective);
        primeDirective.setOnClickListener(view -> openPrimeDirectiveActivity());
    }

    public void openClickyActivity() {
        Intent intent = new Intent(this, ClickyActivity.class);
        startActivity(intent);
    }

    public void openAboutMeActivity() {
        Intent intent = new Intent(this, AboutMeActivity.class);
        startActivity(intent);
    }

    public void openLinkCollectorActivity() {
        Intent intent = new Intent(this, LinkCollectorActivity.class);
        startActivity(intent);
    }

    public void openPrimeDirectiveActivity() {
        Intent intent = new Intent(this, PrimeDirectiveActivity.class);
        startActivity(intent);
    }
}