package com.example.gg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.gg.Config.FTP_HOST;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MyFTPClientFunctions ftpclient;
    public boolean status;
    MainActivity a = this;
    EditText login, password;
    Button enter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ftpclient = new MyFTPClientFunctions();
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
                if (isOnline(MainActivity.this)) {
                connectToFTPAddress();
                if (status) {
                    Toast.makeText(MainActivity.this,
                            "Ви підключилися",
                            Toast.LENGTH_LONG).show();
                    Intent mainmenu = new Intent(a, MainMenuActivity.class);
                    mainmenu.putExtra("USERNAME", login.getText().toString());
                    startActivity(mainmenu);
                } else {
                    Toast.makeText(MainActivity.this,
                            "Перевірте вхідні дані",
                            Toast.LENGTH_LONG).show();
                }
                }
                else {
				Toast.makeText(MainActivity.this,
						"Перевірте, будь-ласка, підключення до Інтернету",
						Toast.LENGTH_LONG).show();
			}
            }
        });
    }
    private void connectToFTPAddress() {

        final String username = login.getText().toString();
        final String pass = password.getText().toString();

         if (username.length() < 1) {
            Toast.makeText(MainActivity.this, "Введіть логін",
                    Toast.LENGTH_LONG).show();
        } else if (password.length() < 1) {
            Toast.makeText(MainActivity.this, "Введіть пароль",
                    Toast.LENGTH_LONG).show();
        } else {

          Thread b =  new Thread(new Runnable() {
                public void run() {
                    status = ftpclient.ftpConnect(FTP_HOST, username, pass, Config.FTP_PORT);

                    if (status) {
                        Log.d(TAG, "Connection Success");
                    } else {
                        Log.d(TAG, "Connection failed");
                    }
                }
            });b.start();
          while (b.isAlive()){

          }
        }
    }
    private boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
