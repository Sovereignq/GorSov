package com.example.gg;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import android.util.Log;

public class MyFTPClientFunctions {

    private static final String TAG = "MyFTPClientFunctions";
    public static MyFTPClientFunctions ftpclient;
    public FTPClient mFTPClient;
    public int code;
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
            Log.d(TAG, "Error: could not connect to host " + host);
        }

        return 404;
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

    public String[] ftpPrintFilesList(final String dir_path) {

        final String[][] fileList = {null};

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
                    fileList[0] = new String[length];
                    for (int i = 0; i < length; i++) {
                        String name = ftpFiles[i].getName();
                        boolean isFile = ftpFiles[i].isFile();

                        if (isFile) {
                            fileList[0][i] = "File :: " + name;
                            Log.i(TAG, "File : " + name);
                        } else {
                            fileList[0][i] = "Directory :: " + name;
                            Log.i(TAG, "Directory : " + name);
                        }
                    }
                }
            });
             t.start();
        try {
            t.join();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        return fileList[0];

    }

    public boolean ftpDownload(String srcFilePath, String desFilePath) {
        boolean status = false;
        try {
            FileOutputStream desFileStream = new FileOutputStream(desFilePath);
            status = mFTPClient.retrieveFile(srcFilePath, desFileStream);
            desFileStream.close();

            return status;
        } catch (Exception e) {
            Log.d(TAG, "download failed");
        }

        return status;
    }

}
