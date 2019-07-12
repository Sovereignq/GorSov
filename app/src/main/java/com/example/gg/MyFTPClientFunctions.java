package com.example.gg;

import java.io.FileOutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import android.util.Log;

public class MyFTPClientFunctions {

    private static final String TAG = "MyFTPClientFunctions";
    public FTPClient mFTPClient = null;

    public boolean ftpConnect(String host, String username, String password,
                              int port) {
        try {
            mFTPClient = new FTPClient();
            mFTPClient.connect(host, port);
            if (FTPReply.isPositiveCompletion(mFTPClient.getReplyCode())) {
                boolean status = mFTPClient.login(username, password);
                mFTPClient.setFileType(FTP.BINARY_FILE_TYPE);
                mFTPClient.enterLocalPassiveMode();

                return status;
            }
        } catch (Exception e) {
            Log.d(TAG, "Error: could not connect to host " + host);
        }

        return false;
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

    public String[] ftpPrintFilesList(String dir_path) {
        String[] fileList = null;
        try {
            FTPFile[] ftpFiles = mFTPClient.listFiles(dir_path);
            int length = ftpFiles.length;
            fileList = new String[length];
            for (int i = 0; i < length; i++) {
                String name = ftpFiles[i].getName();
                boolean isFile = ftpFiles[i].isFile();

                if (isFile) {
                    fileList[i] = "File :: " + name;
                    Log.i(TAG, "File : " + name);
                } else {
                    fileList[i] = "Directory :: " + name;
                    Log.i(TAG, "Directory : " + name);
                }
            }
            return fileList;
        } catch (Exception e) {
            e.printStackTrace();
            return fileList;
        }
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
