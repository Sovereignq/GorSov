package com.example.gg;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


public class FileListActivity extends AppCompatActivity {
    ListView fileListView;
    String[] fileList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);
        getSupportActionBar().hide();
        fileListView = findViewById(R.id.listFiles);
        Bundle fileslist = getIntent().getExtras();
        fileList = fileslist.getStringArray("FILELIST");
        CustomAdapter customadapter = new CustomAdapter(this, fileList, R.drawable.download);
        fileListView.setAdapter(customadapter);
    }
}
