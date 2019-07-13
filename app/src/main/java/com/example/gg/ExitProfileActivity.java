package com.example.gg;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ExitProfileActivity extends AppCompatActivity {
    private TextView nick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_profile);
        getSupportActionBar().hide();
        nick = findViewById(R.id.textViewProfile);
        nick.bringToFront();
        Button exitfromprofile = findViewById(R.id.buttonExit);
        exitfromprofile.bringToFront();
        ImageView imgprofile = findViewById(R.id.imageViewProfile);
        imgprofile.bringToFront();
        ImageView exittobegin = findViewById(R.id.imageViewClose);
        exittobegin.bringToFront();
        Bundle frommainmenu = getIntent().getExtras();
        nick.setText(frommainmenu.getString("NICK"));
        ImageView background = findViewById(R.id.imagePrintScreen);
        background.setImageResource(0);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(MainMenuActivity.bm);
        background.setBackgroundDrawable(bitmapDrawable);
    }
}
