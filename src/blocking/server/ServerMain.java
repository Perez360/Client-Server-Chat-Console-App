package blocking.server;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        int port = 3000;
        ServerChat serverChat = new ServerChat(port);
        serverChat.start();
    }
}