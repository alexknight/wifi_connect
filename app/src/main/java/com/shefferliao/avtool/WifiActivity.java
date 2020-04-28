package com.shefferliao.avtool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class WifiActivity extends Activity {
    final static String TAG = "WifiActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate");
        Intent intent = getIntent();

        String target_wifi = intent.getStringExtra("wifi");
        if (target_wifi != null && !"".equals(target_wifi)) {
            changeWifi(target_wifi, false);
            Toast.makeText(this, "change wifi to : " + target_wifi, Toast.LENGTH_SHORT).show();
        }

        moveTaskToBack(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


    private void changeWifi(String ssid, boolean fuzzyMatch) {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration wifiConfiguration : list) {
            String wifiSSID = wifiConfiguration.SSID;
            wifiSSID = wifiSSID.replace("\"", "");
            boolean ssidMatch = fuzzyMatch ? wifiSSID.startsWith(ssid) : wifiSSID.equals(ssid);
            if (ssidMatch) {
                wifiManager.disconnect();
                wifiManager.enableNetwork(wifiConfiguration.networkId, true);
                wifiManager.reconnect();
                break;
            }
        }
    }
}