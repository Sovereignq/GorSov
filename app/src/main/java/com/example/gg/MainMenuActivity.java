package com.example.gg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

public class MainMenuActivity extends AppCompatActivity {
    private TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().hide();
        Thread myThread = null;
        Runnable runnable = new CountDownRunner();
        myThread= new Thread(runnable);
        myThread.start();

        username = findViewById(R.id.textUsername);
        Bundle loginmenu = getIntent().getExtras();
        username.setText("@" + loginmenu.getString("USERNAME"));
    }
    public void doWork() {
        runOnUiThread(new Runnable() {
            public void run() {
                try{
                    TextView txtCurrentTime = findViewById(R.id.textViewTime);
                    Date dt = new Date();
                    int hours = dt.getHours();
                    int minutes = dt.getMinutes();
                    String curTime = "";
                    if (minutes <= 10) {
                        curTime += hours + ":0" + minutes;
                    } else if (hours <= 10) {
                        curTime += "0" + hours + ":" + minutes;
                    } else if (minutes<= 10 && hours<=10) {
                        curTime += "0" + hours + ":0" + minutes;
                    } else {
                    curTime += hours + ":" + minutes;
                    }
                    txtCurrentTime.setText(curTime);
                }catch (Exception e) {}
            }
        });
    }
    class CountDownRunner implements Runnable{
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    doWork();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }catch(Exception e){
                }
            }
        }
    }
}
