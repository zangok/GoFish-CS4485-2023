package com.example.gofish.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class NetworkThread extends Thread {
    private Handler networkHandler;
    //private WifiDirect manager;
    //private MessageListener listener;
    private static final int MSG_TYPE_SEND_DATA = 1;
    private static final int MSG_TYPE_RECEIVE_DATA = 2;
    private Socket socket;
    private ServerSocket serverSocket;
    private String ipAddress;
    private boolean isHost;
    private int serverPort = 8080;
    private BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
    public NetworkThread(String ipAddress, Handler networkHandler) {
        //try {
        //    socket = new Socket("10.0.2.2",8080);
        this.networkHandler  = networkHandler;
        this.ipAddress = ipAddress;
        //} catch (IOException e) {
        //    throw new RuntimeException(e);
        //}
        isHost  = false;
    }
    public NetworkThread(Handler networkHandler) {
        //try {
        //    serverSocket = new ServerSocket(8080);
        this.networkHandler  = networkHandler;
        //} catch (IOException e) {
        //    throw new RuntimeException(e);
        //}
        isHost  = true;
    }
    //todo:split network class into client and server improve coupling and cohesion
    @Override
    public void run() {
        Log.d("network","network thread made");
        try {
            if(isHost) {

                InetSocketAddress serverSocketAddress = new InetSocketAddress("10.0.2.15", serverPort);
                serverSocket = new ServerSocket(serverPort);
                //serverSocket.bind(serverSocketAddress);
                Log.d("network", "server listening");
                socket = serverSocket.accept();
                Log.d("network", "server connected");
                handleMessage(socket.getOutputStream(), socket.getInputStream());
            } else {
                Log.d("network", "client connecting");
                socket = new Socket(ipAddress,8080);
                Log.d("network", "client connected");
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
        Log.d("network","Message Queued");
    }
    //todo:not use busy wait, create thread to read and a thread to write + blocking read / write
    //should work however, so low priority
    private void handleMessage(OutputStream outputStream, InputStream inputStream) {
        Log.d("network", "connected");
        while (true) {
            if (!messageQueue.isEmpty()) {
                String message = messageQueue.poll();
                try {
                    outputStream.write(message.getBytes());
                    outputStream.flush();
                    Log.d("network","Message Sent to Peer");
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
                        message.what = MSG_TYPE_RECEIVE_DATA;
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
