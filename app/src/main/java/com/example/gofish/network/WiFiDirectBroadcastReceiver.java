package com.example.gofish.network;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

/**
 * A BroadcastReceiver that notifies of important Wi-Fi p2p events.
 */
public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private WifiDirect activity;
    private WifiP2pManager.PeerListListener peerListListener;
    private WifiP2pManager.ConnectionInfoListener connectionListener;

    public WiFiDirectBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel,
                                       WifiDirect activity, WifiP2pManager.PeerListListener peerListListener,
                                       WifiP2pManager.ConnectionInfoListener connectionListener) {
        super();
        this.manager = manager;
        this.channel = channel;
        this.activity = activity;
        this.peerListListener = peerListListener;
        this.connectionListener = connectionListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("onReceive", action);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context, Manifest.permission.NEARBY_WIFI_DEVICES) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("network", "onReceive: lacking permissions for p2p peers changed");
            //return;
        }
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            manager.requestPeers(channel, peerListListener);
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // Call WifiP2pManager.requestPeers() to get a list of current peers
            if (manager != null) {

                manager.requestPeers(channel, peerListListener);
            }
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            if (manager == null) {
                return;
            }

            NetworkInfo networkInfo = (NetworkInfo) intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

            if (networkInfo != null && networkInfo.isConnected()) {

                // We are connected with the other device, request connection
                // info to find group owner IP

                manager.requestConnectionInfo(channel, connectionListener);
            }
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // Respond to this device's wifi state changing
        }
        if (WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION.equals(action)) {
            // Discovery state has changed, handle it here
            int discoveryState = intent.getIntExtra(WifiP2pManager.EXTRA_DISCOVERY_STATE, -1);

            if (discoveryState == WifiP2pManager.WIFI_P2P_DISCOVERY_STARTED) {
                // Wi-Fi Direct discovery has started
                Log.d("network", "Discovery started");
            } else if (discoveryState == WifiP2pManager.WIFI_P2P_DISCOVERY_STOPPED) {
                // Wi-Fi Direct discovery has stopped
                Log.d("network", "Discovery stopped");
            }
        }
    }


}