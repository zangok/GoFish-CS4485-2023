# GoldFish
GameActivity: game-related functionality
MainActivity: entry point of your app, responsible for general app navigation
NetworkClass: for network connectivity
public class OtherThreadComponent implements WifiDirectListener {
//creates a listener to listen to changes
    public void initNetworkThread(NetworkThread networkThread) {
        networkThread.registerNetworkMessageListener(this);
    }
    
//override a method in wifidirectlistener to receive changes in network
    @Override
    public void methodName(String message) {
        // Handle the received message
    }
}