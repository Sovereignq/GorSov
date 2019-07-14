package com.example.gg;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;


public class FileListActivity extends AppCompatActivity {
    ListView fileListView;
    ArrayList<String> fileList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);
        getSupportActionBar().hide();
        fileListView = findViewById(R.id.listFiles);
        fileList = getFiles();


        CustomAdapter customadapter = new CustomAdapter(this, fileList, R.drawable.download);
        fileListView.setAdapter(customadapter);

        fileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String fileName = fileList.get(i);
                MyFTPClientFunctions.ftpclient.ftpDownload(fileName,"/storage/emulated/sdcard0/Download/");

            }
        });
    }

    private ArrayList<String> getFiles() {
        ArrayList<String> files = new ArrayList<>();


        File directory = new File("/storage/emulated/sdcard0/Download/");
        File[] fls = directory.listFiles();

        if (fls != null)
            for(File f : fls)
            {
                files.add(f.getName());
            }

        if(Config.FTP_ENABLED)
            files.addAll(MyFTPClientFunctions.ftpclient.ftpPrintFilesList("/"));
        return files;
    }
}

