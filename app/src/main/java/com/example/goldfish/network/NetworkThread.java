package com.example.goldfish.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class NetworkThread extends Thread {
    private Handler networkHandler;
    //private WifiDirect manager;
    //private MessageListener listener;
    //private static final int MSG_TYPE_SEND_DATA = 1;
    //private static final int MSG_TYPE_RECEIVE_DATA = 2;
    private List<WifiDirectListener> networkMessageListeners;
    private Socket socket;
    private ServerSocket serverSocket;
    private String ipAddress;
    private boolean isHost;
    private BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
    public NetworkThread(String ipAddress, Handler networkHandler) {
        try {
            socket = new Socket(ipAddress,8080);
            this.networkHandler  = networkHandler;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        isHost  = false;
    }
    public NetworkThread(Handler networkHandler) {
        try {
            serverSocket = new ServerSocket(8080);
            this.networkHandler  = networkHandler;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        isHost  = true;
    }

    @Override
    public void run() {
        Log.d("network","network thread made");
        try {
            if(isHost) {
                    socket = serverSocket.accept();
                    handleMessage(socket.getOutputStream(), socket.getInputStream());
            } else {
                handleMessage(socket.getOutputStream(), socket.getInputStream());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //network operations
    //todo: change to use handler instead
    public void sendMessage(String message) {
        messageQueue.add(message);
    }
    //todo:not use busy wait, create thread to read and a thread to write + blocking read / write
    private void handleMessage(OutputStream outputStream, InputStream inputStream) {
        while (true) {
            if (!messageQueue.isEmpty()) {
                String message = messageQueue.poll();
                try {
                    outputStream.write(message.getBytes());
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                if (inputStream.available() > 0) {
                    byte[] buffer = new byte[1024];
                    int bytesRead = inputStream.read(buffer);
                    if (bytesRead > 0) {
                        String receivedMessage = new String(buffer, 0, bytesRead);
                        Message message = Message.obtain();
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("key", receivedMessage);
                        message.setData(bundle);
                        networkHandler.sendMessage(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
