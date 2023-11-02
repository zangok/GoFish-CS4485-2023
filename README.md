# GoldFish
GameActivity: game-related functionality
MainActivity: entry point of your app, responsible for general app navigation
NetworkClass: for network connectivity
to start, WifiDirect net = new WifiDirect(this,handler);
make a new handler to receive data.
net.requestPeers(); gets you devices you can connect to, send one to 
net.connect(device) to connect to it. 
if other device accepts connection, socket connection will then be made in new network thread.
net.sendMessage(String message) to send messages.
To test, connect with a android api 34 device to an emulators AndroidWifi. Will connect & launch network thread easily with 1 other peer on network.
