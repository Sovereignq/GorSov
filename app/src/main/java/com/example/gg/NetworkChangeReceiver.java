package com.example.gg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {
    int i = 0;
    @Override
    public void onReceive(final Context context, final Intent intent) {

        int status = NetworkUtil.getConnectivityStatusString(context);
        Log.e("Sulodsanetworkreciever", "Sulod sa network reciever");
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
           if (status == NetworkUtil.NETWORK_STATUS_WIFI){


                WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ssid = wifiInfo.getSSID();

                if(ssid.equals('"' + Config.WIFI_SSID + '"'))
                {

                    Config.FTP_ENABLED = true;
                }
                else
                {
                    Toast.makeText(context, "FTP-сервер не доступний у цій мережі",Toast.LENGTH_SHORT).show();
                    Config.FTP_ENABLED = false;
                }



            }
        }
    }
}
