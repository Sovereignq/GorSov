package com.example.sov;

import android.widget.Toast;

public class FileReader {
    public void read(String filePath)
    {
        String ext = filePath.substring(filePath.lastIndexOf("."));

        switch (ext)
        {
            default:
                Toast.makeText(MainActivity.context, "Неизвестный формат", Toast.LENGTH_SHORT).show();
        }
    }
}
