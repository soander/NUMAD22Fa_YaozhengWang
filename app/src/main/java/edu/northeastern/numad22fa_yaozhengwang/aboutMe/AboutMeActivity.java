package edu.northeastern.numad22fa_yaozhengwang.aboutMe;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import edu.northeastern.numad22fa_yaozhengwang.R;

public class AboutMeActivity extends AppCompatActivity {

    private TextView aboutMe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        aboutMe = findViewById(R.id.about);
    }

    public TextView getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(TextView aboutMe) {
        this.aboutMe = aboutMe;
    }
}
