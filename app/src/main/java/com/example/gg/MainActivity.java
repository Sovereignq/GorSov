package com.example.gg;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    MainActivity a = this;
    EditText login, password;
    Button enter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        login = findViewById(R.id.editTextLogin);
        password = findViewById(R.id.editTextPassword);
        enter = findViewById(R.id.btnEnter);
        enter.setEnabled(false);
        login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!login.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    enter.setTextColor(Color.rgb(255,255,255));
                    enter.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!login.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    enter.setTextColor(Color.rgb(255,255,255));
                    enter.setEnabled(true);
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!login.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    enter.setTextColor(Color.rgb(255,255,255));
                    enter.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!login.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    enter.setTextColor(Color.rgb(255,255,255));
                    enter.setEnabled(true);
                }
            }
        });
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainmenu = new Intent(a, MainMenuActivity.class);
                mainmenu.putExtra("USERNAME", login.getText().toString());
                startActivity(mainmenu);
            }
        });
    }
}
