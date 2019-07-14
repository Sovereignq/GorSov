package com.example.gg;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import android.util.Log;

public class MyFTPClientFunctions {

    private static final String TAG = "MyFTPClientFunctions";
    public static MyFTPClientFunctions ftpclient;
    public FTPClient mFTPClient;
    public int code = 404;
    public int ftpConnect(String host, String username, String password,
                          int port) {
        try {
            mFTPClient = new FTPClient();
            mFTPClient.connect(host, port);
            if (FTPReply.isPositiveCompletion(mFTPClient.getReplyCode())) {
                mFTPClient.login(username, password);

                mFTPClient.setFileType(FTP.BINARY_FILE_TYPE);
                mFTPClient.enterLocalPassiveMode();
                code = mFTPClient.getReplyCode();
                return code;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return code;
    }

    public boolean ftpDisconnect() {
        try {
            mFTPClient.logout();
            mFTPClient.disconnect();
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error occurred while disconnecting from ftp server.");
        }

        return false;
    }

    public String ftpGetCurrentWorkingDirectory() {
        try {
            String workingDir = mFTPClient.printWorkingDirectory();
            return workingDir;
        } catch (Exception e) {
            Log.d(TAG, "Error: could not get current working directory.");
        }

        return null;
    }

    public boolean ftpChangeDirectory(String directory_path) {
        try {
            mFTPClient.changeWorkingDirectory(directory_path);
        } catch (Exception e) {
            Log.d(TAG, "Error: could not change directory to " + directory_path);
        }

        return false;
    }

    public ArrayList<String> ftpPrintFilesList(final String dir_path) {

        final ArrayList<String> fileList = new ArrayList<>();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                FTPFile[] ftpFiles = new FTPFile[0];
                try {
                    ftpFiles = mFTPClient.listFiles(dir_path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int length = ftpFiles.length;
                for (int i = 0; i < length; i++) {
                    String name = ftpFiles[i].getName();
                    System.out.println("SOMEFILE"+name);
                    fileList.add(name);

                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        return fileList;

    }

    public boolean ftpDownload(final String srcFilePath, final String desFilePath) {
       final boolean[] status = {false};
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File file = new File(desFilePath, srcFilePath);
                    System.out.println(file.getAbsolutePath());
                    file.createNewFile();
                    FileOutputStream desFileStream = new FileOutputStream(file);
                    status[0] = mFTPClient.retrieveFile(srcFilePath, desFileStream);
                    desFileStream.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return status[0];
    }

}
