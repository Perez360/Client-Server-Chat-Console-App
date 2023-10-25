package blocking.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class ClientChat {
    private final Socket socket;

    public ClientChat(String address, int port) throws IOException {
        socket = new Socket(address, port);

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
                OutputStreamWriter outputStreamWriterToServer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
                BufferedWriter bufferedWriterToServer = new BufferedWriter(outputStreamWriterToServer);
                InputStreamReader inputStreamReaderFromServer = new InputStreamReader(socket.getInputStream());
                BufferedReader bufferedReaderFromServer = new BufferedReader(inputStreamReaderFromServer)
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
        } catch (IOException ioEx) {
            try {
                socket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(ioEx);
        }

    }
}