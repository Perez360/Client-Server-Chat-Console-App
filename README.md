# Client-Server-Chat--Console-App

This is a simple client-server chat application implemented in Java. The application demonstrates two communication modes: blocking IO using traditional Java sockets and non-blocking IO using Java SocketChannels.
Features

Client-Server Communication: The application allows multiple clients to connect to a server and send messages.
Blocking IO: The server uses blocking IO for communication with clients.
Non-blocking IO: The server uses Java SocketChannels for non-blocking communication with clients, demonstrating asynchronous communication.
Simple Console Interface: Both client and server have a simple console interface for user interaction.

# Prerequisites
Java Development Kit (JDK) installed on your system.

Communication Modes
Blocking IO (Traditional Sockets)

In this mode, the server uses blocking IO for communication with clients. Each client connection is handled in a separate thread, allowing multiple clients to communicate with the server concurrently.
Server (Blocking IO)

The Server class creates a ServerSocket and accepts client connections.
Each client connection is managed by a separate thread (ClientHandler class), which handles sending and receiving messages using traditional sockets (Socket).

# Client (Blocking IO)

The Client class creates a Socket to connect to the server.
The client communicates with the server using traditional input/output streams (InputStream and OutputStream).

# Non-blocking IO (SocketChannels)

In this mode, the server uses non-blocking IO with Java SocketChannel and Selector for asynchronous communication with clients. The server can handle multiple client connections in a single thread using non-blocking channels.
Server (Non-blocking IO)

The NonBlockingServer class creates a ServerSocketChannel and registers it with a Selector.
The server uses non-blocking SocketChannels for client connections and handles read/write operations asynchronously.
Clients are managed using a Map where each client has a corresponding SocketChannel.

# Client (Blocking IO)

The Client class creates a SocketChannel to connect to the server.
The client communicates with the server using traditional input/output streams (InputStream and OutputStream).

Contributions

Contributions are welcome! If you find any issues or have suggestions for improvements, please feel free to open an issue or create a pull request. are welcome!
If you find any issues or have suggestions for improvements, please feel free to open an issue or create a pull request.
