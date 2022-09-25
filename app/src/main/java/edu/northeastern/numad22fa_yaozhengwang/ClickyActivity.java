package edu.northeastern.numad22fa_yaozhengwang;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ClickyActivity extends AppCompatActivity {
    private TextView display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky_clicky);
        display = findViewById(R.id.textViewPressed);
    }

    public void onClick(View view) {
        String stringBuilder = "Pressed: " +
                ((Button) view).getText().toString();
        display.setText(stringBuilder);
    }
}
