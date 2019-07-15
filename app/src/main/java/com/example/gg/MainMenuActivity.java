package com.example.gg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainMenuActivity extends AppCompatActivity {
    private TextView username;
    MainMenuActivity b = this;
    public static Bitmap bm;
    LinearLayout recentApps;
    ImageView reader;
    ImageView chrome;
    ImageButton delAll;
    ArrayList<Boolean> posilToDel = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().hide();
        recentApps = findViewById(R.id.recentApps);
        reader = findViewById(R.id.imageViewReader);
        delAll = findViewById(R.id.buttonDelAll);
        delAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < recentApps.getChildCount(); i++) {
                    if (posilToDel.get(i)) {
                        recentApps.removeViewAt(i);
                        posilToDel.remove(i);
                        i--;
                    }
                }
            }
        });
        reader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                PackageManager manager = getPackageManager();
                i = manager.getLaunchIntentForPackage("com.adobe.reader");
                addRecentApp("pdfReader", R.drawable.reader, "com.adobe.reader");
                startActivity(i);
            }
        });

        chrome = findViewById(R.id.imageViewChrome);
        chrome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                addRecentApp("google", R.drawable.chrome, "http://www.google.com");
                startActivity(browserIntent);

            }
        });
        ImageView profileIcon = findViewById(R.id.imageProfile);
        Thread myThread;
        Runnable runnable = new CountDownRunner();
        myThread= new Thread(runnable);
        myThread.start();
        ImageView materials = findViewById(R.id.imageViewMaterials);
        materials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent filelist = new Intent(b, FileListActivity.class);
                startActivity(filelist);
            }
        });
        username = findViewById(R.id.textUsername);
        Bundle loginmenu = getIntent().getExtras();
        username.setText("@" + loginmenu.getString("USERNAME"));
        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeLayout rlmain = findViewById(R.id.relMain);
                rlmain.setDrawingCacheEnabled(true);
                bm = rlmain.getDrawingCache();
                Bundle extrasExit = new Bundle();
                extrasExit.putString("NICK", username.getText().toString());
                Intent exitprofile = new Intent(b, ExitProfileActivity.class);
                exitprofile.putExtras(extrasExit);
                startActivity(exitprofile);
            }
        });
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
                    if (minutes < 10) {
                        curTime += hours + ":0" + minutes;
                    } else if (hours < 10) {
                        curTime += "0" + hours + ":" + minutes;
                    } else if (minutes< 10 && hours<10) {
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
    void addRecentApp(final String name, int id, final String pacName) {
        LayoutInflater li = getLayoutInflater();
        final View v = li.inflate(R.layout.recapp, null, false);
        registerForContextMenu(v);
        ((TextView) v.findViewById(R.id.textView)).setText(name);
        ((ImageView) v.findViewById(R.id.imageView)).setImageResource(id);
        recentApps.addView(v, 0);
        posilToDel.add(0, true);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (name.equals("pdfReader")) {
                    intent = getPackageManager().getLaunchIntentForPackage("com.adobe.reader");
                } else
                    intent.setData(Uri.parse(pacName));
                startActivity(intent);
            }
        });

        v.setOnLongClickListener(new View.OnLongClickListener() {
            LayoutInflater k = getLayoutInflater();
            View jj = k.inflate(R.layout.delete, null, false);

            @Override
            public boolean onLongClick(final View view) {
                for (int i = 0; i < recentApps.getChildCount(); i++) {
                    if (recentApps.getChildAt(i) == view) {
                        ((LinearLayout) recentApps.getChildAt(i).findViewById(R.id.ll)).addView(jj);
                        ((ImageView) jj.findViewById(R.id.imageDelete)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view1) {
                                posilToDel.remove(recentApps.indexOfChild(view));
                                recentApps.removeView(view);
                                System.out.println(recentApps.getTouchables().size() + " " +
                                        recentApps.getTouchables().indexOf(view));

                                for (int i = 0; i < recentApps.getChildCount(); i++) {
                                    recentApps.getChildAt(i).setAlpha(1);
                                }
                            }
                        });
                        final int finalI = i;
                        ((ImageView) jj.findViewById(R.id.imageLock)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view1) {
                                System.out.println(recentApps.getTouchables().size() + " " +
                                        recentApps.getTouchables().indexOf(view));
                                posilToDel.set(recentApps.getTouchables().indexOf(view), false);
                                for (int i = 0; i < recentApps.getChildCount(); i++) {
                                    recentApps.getChildAt(i).setAlpha(1);
                                }
                                ((LinearLayout) recentApps.getChildAt(finalI).findViewById(R.id.ll)).removeView(jj);

                            }
                        });

                    } else recentApps.getChildAt(i).setAlpha(0.15f);
                }
                return true;
            }
        });
    }
}
