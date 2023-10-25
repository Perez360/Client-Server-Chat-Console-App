package non_blocking.server;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.StandardCharsets;


public class AsyncServerChat extends Thread {
    private final Socket socket;

    AsyncServerChat(int port) throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
        ServerSocketChannel serversocketChannel = ServerSocketChannel.open().bind(inetSocketAddress);
        ServerSocket serverSocket = serversocketChannel.socket();
        serverSocket.setSoTimeout(20000);// Set the timeout to 20 seconds (for example)

        System.out.println("Waiting for client...");
        socket = serverSocket.accept();

        System.out.println("Server started. Waiting for clients...");
        InetAddress clientAddress = serverSocket.getInetAddress();
        System.out.println(clientAddress.getHostAddress() + " is connected on port " + port);

    }

    @Override
    public void start() {
        try (
                InputStreamReader inputStreamReaderFromConsole = new InputStreamReader(System.in);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReaderFromConsole);
                OutputStream outputStream = socket.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                InputStreamReader inputStreamReaderFromClient = new InputStreamReader(socket.getInputStream());
                BufferedReader bufferedReaderFromClient = new BufferedReader(inputStreamReaderFromClient)

        ) {
            while (true) {
                if (!bufferedReaderFromClient.ready()) {
                    System.out.print("Type your message: ");
                    //Getting message from console to be sent to clients.
                    String clientMessage = bufferedReader.readLine();
                    bufferedWriter.write(clientMessage);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
                String messageFromClient = bufferedReaderFromClient.readLine();
                if (messageFromClient != null) {
                    System.out.println("Client-> " + messageFromClient);
                }
            }
        } catch (IOException ex) {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) throws IOException {
        int port = 3001;
        AsyncServerChat asyncServerChat = new AsyncServerChat(port);
        asyncServerChat.start();
    }
}