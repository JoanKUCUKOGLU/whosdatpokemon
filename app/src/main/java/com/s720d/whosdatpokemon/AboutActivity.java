package com.s720d.whosdatpokemon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    private TextView versionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        versionTextView = findViewById(R.id.versiontextView);

        versionTextView.setText("V" + BuildConfig.VERSION_NAME);
    }
}
