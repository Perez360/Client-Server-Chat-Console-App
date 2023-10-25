package blocking.client;

import java.io.IOException;
import java.net.InetAddress;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        int port = 3000;
        String address = InetAddress.getLocalHost().getHostAddress();
        ClientChat clientChat = new ClientChat(address, port);
        clientChat.startChat();
    }
}