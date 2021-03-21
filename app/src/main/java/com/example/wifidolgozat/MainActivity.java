package com.example.wifidolgozat;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.Formatter;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {

    private TextView viewInfo;
    private BottomNavigationView bottomNavigationView;
    private WifiManager wifiManager;
    private WifiInfo wifiInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();



        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.wifiOn:
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                                    viewInfo.setText("Nincs jogosultság!");
                                    Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
                                    startActivityForResult(panelIntent, 0);
                                    break;
                                }
                                wifiManager.setWifiEnabled(true);
                                break;
                            case R.id.wifiOff:
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                                    viewInfo.setText("Nincs jogosultság!");
                                    Intent panelIntent = new Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY);
                                    startActivityForResult(panelIntent, 0);
                                    break;
                                }
                                wifiManager.setWifiEnabled(false);
                                break;
                            case R.id.wifiInfo:
                                ConnectivityManager conManager =
                                        (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                                NetworkInfo netInfo = conManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                                if (netInfo.isConnected()){
                                    int ip_number = wifiInfo.getIpAddress();
                                    String ip = Formatter.formatIpAddress(ip_number);
                                    viewInfo.setText("IP: "+ip);
                                } else {
                                    viewInfo.setText("Sikertelen csatlakozás");
                                }
                                break;
                        }
                        return true;
                    }
                });
    }





    public void init() {
        textViewInfo = findViewById(R.id.textViewInfo);
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();
    }
}