package com.example.goldfish.network;


import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.goldfish.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WifiDirect  {
    private Context context;
    private final WifiP2pManager manager;
    private final WifiP2pManager.Channel channel;
    //private List<WifiDirectListener> networkMessageListeners;
    private BroadcastReceiver receiver;
    private final IntentFilter intentFilter = new IntentFilter();
    private List<WifiP2pDevice> peers = new ArrayList<>();
    private Handler networkHandler;
    private NetworkThread networkThread;
    private WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {

            Log.d("network","onPeersAvailable");
            List<WifiP2pDevice> refreshedPeers = new ArrayList<>( peerList.getDeviceList());
            WifiP2pDevice fakeDevice = new WifiP2pDevice();
            fakeDevice.deviceName = "Fake Device";
            fakeDevice.deviceAddress = "00:00:00:00:00:00";
            refreshedPeers.add(fakeDevice);
            if (!refreshedPeers.equals(peers)) {
                peers.clear();
                peers.addAll(refreshedPeers);
                Log.d("network","new peers added");
            }
            for (WifiP2pDevice device : peers) {
                Log.d("network", "Device: " + device.deviceName);
            }
            if (peers.size() == 0) {
                Log.d("WiFiDirect", "No devices found");
                return;
            }
        }
    };
    private WifiP2pManager.ConnectionInfoListener connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo info) {
            if (info.groupFormed && info.isGroupOwner) {
                networkThread = new NetworkThread(networkHandler);
                networkThread.start();
            } else if (info.groupFormed) {
                networkThread = new NetworkThread(info.groupOwnerAddress.getHostAddress(),networkHandler);
                networkThread.start();
            }
        }
    };
    public WifiDirect(Context context, Handler nethandler) {
        this.context = context;

        // Indicates a change in the Wi-Fi Direct status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi Direct connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        //networkMessageListeners = new ArrayList<WifiDirectListener>();
        manager = (WifiP2pManager) context.getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(context, context.getMainLooper(), null);
        receiver = new WiFiDirectBroadcastReceiver(manager, channel, this, peerListListener,connectionInfoListener);
        context.registerReceiver(receiver, intentFilter);

        networkHandler = nethandler;
        discoverPeers();



    }
    //wifi direct handles messages for now, might want to split for better coupling later...
    public void sendMessage(String message) {
        networkThread.sendMessage(message);
    }
    private void handleReceivedMessage(String message) {
    }
    /*
    public void registerMessageListener(WifiDirectListener listener) {
        networkMessageListeners.add(listener);
    }

    public void unregisterMessageListener(WifiDirectListener listener) {
        networkMessageListeners.remove(listener);
    }

    // Method for receiving a message and notifying listeners
    private void receiveMessage(String message) {
        for (WifiDirectListener listener : networkMessageListeners) {
            listener.onMessageReceived(message);
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }
    // unregister the broadcast receiver

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
    */
    //automatically updates peer list when changes occur

    //
    public void discoverPeers() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context, Manifest.permission.NEARBY_WIFI_DEVICES) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("network","peers discovered lacking permissions");
            //return;
        }
        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d("network","peers discovered");
                receiver.onReceive(context, new Intent(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION));
            }


            @Override
            public void onFailure(int reasonCode) {
                Log.d("network","peers failed discovered::" + reasonCode);
            }
        });
    }
    //get peers.
    public List<WifiP2pDevice> requestPeers() {
        return Collections.unmodifiableList(peers);
    }
    //connects to device given
    public void connect(WifiP2pDevice device) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context, Manifest.permission.NEARBY_WIFI_DEVICES) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        manager.connect(channel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d("network","Connection successful");
            }

            @Override
            public void onFailure(int reasonCode) {
                Log.d("network","Connection failed::" + reasonCode);
            }
        });
    }

    public void disconnect() {
        manager.removeGroup(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                // Disconnected successfully
            }

            @Override
            public void onFailure(int reason) {
                // Disconnection failed
            }
        });
    }


}

