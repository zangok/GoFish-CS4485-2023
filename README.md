# GoldFish
GameActivity: game-related functionality
MainActivity: entry point of your app, responsible for general app navigation
NetworkClass: for network connectivity
to start, WifiDirect net = new WifiDirect(this,handler);
make a new handler to receive data.
net.requestPeers(); gets you devices you can connect to, send one to 
net.connect(device) to connect to it. Both devices must connect to each other
net.sendMessage(String message) to send messages.