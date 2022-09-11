package edu.northeastern.numad22fa_yaozhengwang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button aboutMe = findViewById(R.id.aboutMe);
        aboutMe.setOnClickListener(view -> Toast.makeText(
                MainActivity.this,
                "Name: Yaozheng Wang\n" +
                        "Email: wang.yaozh@northeastern.edu",
                Toast.LENGTH_LONG).show()
        );
    }


}