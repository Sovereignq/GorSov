package com.example.gg;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;


public class FileListActivity extends AppCompatActivity {
    private static AppCompatActivity context;
    ListView fileListView;
    ArrayList<String> fileList;
    public static String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);
        getSupportActionBar().hide();
        fileListView = findViewById(R.id.listFiles);
        fileList = getFiles();
        context = this;
        CustomAdapter customadapter = new CustomAdapter(this, fileList);
        fileListView.setAdapter(customadapter);

        fileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {


                    if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    } else {

                        ActivityCompat.requestPermissions(context,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                1);
                    }
                } else {

                    fileName = fileList.get(i);
                    File file = new File(Config.DOWNLOAD_DIR, fileName);
                    if (!file.exists()) {
                        MyFTPClientFunctions.ftpclient.ftpDownload(fileName, Config.DOWNLOAD_DIR);
                    }
                    finish();
                }

            }
        });
    }

    private ArrayList<String> getFiles() {
        ArrayList<String> files = new ArrayList<>();


        File directory = new File(Config.DOWNLOAD_DIR);
        File[] fls = directory.listFiles();

        if (fls != null)
            for (File f : fls) {
                files.add(f.getName());
            }

        if (Config.FTP_ENABLED)
            files.addAll(MyFTPClientFunctions.ftpclient.ftpPrintFilesList("/"));

        HashSet<String> tmp = new HashSet<>(files);
        files = new ArrayList<>(tmp);
        return files;
    }

}
