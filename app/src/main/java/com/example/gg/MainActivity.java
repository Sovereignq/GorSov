package com.example.gg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.gg.Config.FTP_HOST;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String PREFS_NAME = "MyPrefsFile";
    public static Context context;
    public int status;
    MainActivity a = this;
    EditText login, password;
    Button enter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        SharedPreferences prefs = this.getSharedPreferences("prefs", MODE_PRIVATE);
        String ssid = prefs.getString("SSID",null);
        if(ssid!=null)
            Config.WIFI_SSID = ssid;
        else
        {
            Intent intent = new Intent(this,SetSSID.class);
            startActivityForResult(intent, 1);
        }


        MyFTPClientFunctions.ftpclient = new MyFTPClientFunctions();
        login = findViewById(R.id.editTextLogin);
        password = findViewById(R.id.editTextPassword);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        login.setText(settings.getString("LOGIN", ""));
        password.setText(settings.getString("PASSWORD", ""));
        enter = findViewById(R.id.btnEnter);
        enter.setTextColor(Color.rgb(255,255,255));
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnline(MainActivity.this)) {
                    connectToFTPAddress();
                    System.out.println(MyFTPClientFunctions.ftpclient.code);
                    if (MyFTPClientFunctions.ftpclient.code==200) {
                        Toast.makeText(MainActivity.this,
                                "Ви підключилися",
                                Toast.LENGTH_LONG).show();

                        Intent mainmenu = new Intent(a, MainMenuActivity.class);
                        mainmenu.putExtra("USERNAME", login.getText().toString());
                        startActivity(mainmenu);

                    } else if(MyFTPClientFunctions.ftpclient.code==530) {
                        Toast.makeText(MainActivity.this,
                                "Перевірте вхідні дані",
                                Toast.LENGTH_LONG).show();
                    }
                    else if(MyFTPClientFunctions.ftpclient.code==404) {
                        Toast.makeText(MainActivity.this,
                                "Сервер недоступний",
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Config.WIFI_SSID = data.getStringExtra("SSID");
            }

        }
    }//onActivityResult
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
                    status = MyFTPClientFunctions.ftpclient.ftpConnect(FTP_HOST, username, pass, Config.FTP_PORT);

                }
            });
            b.start();
            try {
                b.join(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
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

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("LOGIN", login.getText().toString());
        editor.putString("PASSWORD", password.getText().toString());
        editor.commit();
    }

}