package com.example.goldfish.network;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class NetworkThread extends Thread {
    //private Handler handler;
    //private WifiDirect manager;
    //private MessageListener listener;
    //private static final int MSG_TYPE_SEND_DATA = 1;
    //private static final int MSG_TYPE_RECEIVE_DATA = 2;
    //private List<WifiDirectListener> networkMessageListeners;
    private Socket socket;
    private BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
    public NetworkThread() {
        //networkMessageListeners = new ArrayList<>();
        //manager = wifiDirect;
        //manager.discoverPeers();
    }

    @Override
    public void run() {
        Log.d("network","network thread made");


    }


    //network operations

    public void sendMessage(String message) {
        if (socket != null) {
            try {
                OutputStream outputStream = socket.getOutputStream();
                byte[] messageBytes = message.getBytes();
                outputStream.write(messageBytes);
            } catch (IOException e) {
                // Handle exceptions, such as socket or I/O errors
            }
        }
    }

    public void receiveMessage(String message) {
        // Add the incoming message to the message queue
        messageQueue.offer(message);
    }
}
