package com.example.gg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter {
    ArrayList<String> fileList;
    int download;
    Context context;
    public CustomAdapter(Context context, ArrayList<String> fileList, int download) {
        super(context, R.layout.activity_file_list, fileList);
        this.fileList = fileList;
        this.download = download;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.customdapter, null);
        TextView fileName = convertView.findViewById(R.id.fileView);
        ImageView imgDownload = convertView.findViewById(R.id.imageDownload);
        fileName.setText(fileList.get(position));
        imgDownload.setBackgroundResource(R.drawable.download);
        return convertView;
    }
}
