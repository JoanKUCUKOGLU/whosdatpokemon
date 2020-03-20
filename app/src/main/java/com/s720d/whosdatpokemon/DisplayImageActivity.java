package com.s720d.whosdatpokemon;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class DisplayImageActivity extends AppCompatActivity {

    private ImageView image;

    private Button goBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        image = findViewById(R.id.spriteImageView);
        goBackButton = findViewById(R.id.goBackButton);

        int imageid = getIntent().getIntExtra("image", 0);

        image.setImageResource(imageid);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
