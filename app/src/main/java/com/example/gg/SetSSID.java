package com.example.gg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SetSSID extends AppCompatActivity {

    EditText ssid;
    Button btnSetSSID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_ssid);
        ssid = findViewById(R.id.inpSSID);
        btnSetSSID = findViewById(R.id.btnSetSSID);

        btnSetSSID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtSSID = ssid.getText().toString();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("SSID",txtSSID);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }
}
