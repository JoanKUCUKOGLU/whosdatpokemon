package com.s720d.whosdatpokemon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StatsActivity extends AppCompatActivity {

    private TextView difficultyTextView;

    private TextView scoreTextView;

    private Button goHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        difficultyTextView = findViewById(R.id.difficultyTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        goHomeButton = findViewById(R.id.goHomeButton);

        difficultyTextView.setText("Difficulty : " + getIntent().getStringExtra("difficulty").toUpperCase());
        int score = getIntent().getIntExtra("score", 0);
        if(score < 6) {
            scoreTextView.setText(score + "/10, ok t'es naze un peu");
        } else if(score > 5 && score < 10) {
            scoreTextView.setText(score + "/10, bon ça va");
        } else if(score == 10) {
            scoreTextView.setText(score + "/10, ok bb t'es fort");
        }

        goHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
