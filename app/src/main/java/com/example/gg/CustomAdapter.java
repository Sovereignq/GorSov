package com.example.gg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    ArrayList<String> fileList;
    Context context;
    public CustomAdapter(Context context, ArrayList<String> fileList) {
        super(context, R.layout.activity_file_list, fileList);
        this.fileList = fileList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.customdapter, null);
        TextView fileName = convertView.findViewById(R.id.fileView);
        fileName.setText(fileList.get(position));
        return convertView;
    }
}
