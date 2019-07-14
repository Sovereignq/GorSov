package com.example.gg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class ExitProfileActivity extends AppCompatActivity {
    ExitProfileActivity c = this;
    private TextView nick;
    private boolean stat;
    Bitmap back;
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
        back = BlurBuilder.blur(this, MainMenuActivity.bm);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(back);
        background.setBackgroundDrawable(bitmapDrawable);
        exitfromprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   
                    Intent exit = new Intent(c, MainActivity.class);
                    startActivity(exit);
                    
            }
        });
    }
}
