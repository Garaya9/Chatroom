package application;

/**
 * A multithreaded chat room server launcher.
 * Starts a ChatServer in a separate thread to keep GUI responsive.
 */
public class ChatServerExec {

    private static int CHAT_ROOM_PORT;

    public ChatServerExec(int port) {
        CHAT_ROOM_PORT = port;
    }

    /**
     * Starts an instance of the server in its own thread.
     */
    public void startServer() {
        ChatServer server = new ChatServer(CHAT_ROOM_PORT);
        Thread serverThread = new Thread(server);  
        serverThread.start();
    }
}
