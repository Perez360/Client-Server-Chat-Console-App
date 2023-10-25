package non_blocking.client;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Locale;


public class AsyncClientChat {
    private final Socket socket;

    AsyncClientChat(int port) throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
        SocketChannel socketChannel = SocketChannel.open(inetSocketAddress);

        socket = socketChannel.socket();


        if (socket.isConnected()) {
            InetAddress inetAddress = socket.getInetAddress();
            System.out.println("Connected to server on (" + inetAddress.getHostAddress() + ":" + port + ")");
        }
    }

    public void startChat() {
        try (
                InputStreamReader inputStreamReaderFromConsole = new InputStreamReader(System.in);
                BufferedReader bufferedReaderFromConsole = new BufferedReader(inputStreamReaderFromConsole);
                OutputStream outputStream = socket.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
                BufferedWriter bufferedWriterToServer = new BufferedWriter(outputStreamWriter);
                InputStreamReader inputStreamReaderFromClient = new InputStreamReader(socket.getInputStream());
                BufferedReader bufferedReaderFromServer = new BufferedReader(inputStreamReaderFromClient)

        ) {
            String clientName;

            System.out.print("Please enter your name: ");
            clientName = bufferedReaderFromConsole.readLine();
            while (true) {
                System.out.print("Type your message: ");
                //Getting message from console to be sent to blocking.server.
                String serverMessage = bufferedReaderFromConsole.readLine();
                bufferedWriterToServer.write(String.format(Locale.ENGLISH, "%s-> %s", clientName, serverMessage));
                bufferedWriterToServer.newLine();
                bufferedWriterToServer.flush();

                String messageFromClient = bufferedReaderFromServer.readLine();
                System.out.println("Server-> " + messageFromClient);
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
        int port = 3000;
        AsyncClientChat asyncClientChat = new AsyncClientChat(port);
        asyncClientChat.startChat();
    }
}