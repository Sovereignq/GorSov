package com.example.gg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainMenuActivity extends AppCompatActivity {
    private TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().hide();
        username = findViewById(R.id.textUsername);
        Bundle loginmenu = getIntent().getExtras();
        username.setText("@" + loginmenu.getString("USERNAME"));
    }
}
