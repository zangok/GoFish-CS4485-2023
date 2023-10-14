package com.example.goldfish.network;

import android.net.wifi.p2p.WifiP2pDevice;

public interface WifiDirectListener {
    void onDeviceDiscovered();
    void onConnectionEstablished();
    void onMessageReceived(String data);
}