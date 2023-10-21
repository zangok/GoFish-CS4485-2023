package com.example.gofish.network;

public interface WifiDirectListener {
    void onDeviceDiscovered();
    void onConnectionEstablished();
    void onMessageReceived(String data);
}