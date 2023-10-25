package blocking.server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerChat extends Thread {
    private final Socket socket;

    public ServerChat(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port, 10);
        serverSocket.setSoTimeout(20000); // Set the timeout to 20 seconds (for example)

        System.out.println("Waiting for client...");
        socket = serverSocket.accept();


        System.out.println("Server started. Waiting for clients...");
        InetAddress clientAddress = socket.getInetAddress();
        System.out.println(clientAddress.getHostAddress() + " is connected on port " + port);
    }

    @Override
    public void start() {
        try (
                InputStreamReader inputStreamReaderFromConsole = new InputStreamReader(System.in);
                BufferedReader bufferedReaderFromConsole = new BufferedReader(inputStreamReaderFromConsole);
                OutputStream outputStream = socket.getOutputStream();
                OutputStreamWriter outputStreamWriterToClient = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
                BufferedWriter bufferedWriterToClient = new BufferedWriter(outputStreamWriterToClient);
                InputStreamReader inputStreamReaderFromClient = new InputStreamReader(socket.getInputStream());
                BufferedReader bufferedReaderFromClient = new BufferedReader(inputStreamReaderFromClient)

        ) {
            while (true) {

                String messageFromClient = bufferedReaderFromClient.readLine();
                System.out.println(messageFromClient);

                System.out.print("Type your message: ");
                //Getting message from console to be sent to clients.
                String clientMessage = bufferedReaderFromConsole.readLine();
                bufferedWriterToClient.write(clientMessage);
                bufferedWriterToClient.newLine();
                bufferedWriterToClient.flush();


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
}
