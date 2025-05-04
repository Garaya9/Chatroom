package application;
/**
 * Title: Chat Client Executor
 * Lab 10
 * Author: G. Araya
 * Date: May 4, 2025
 *
 * Description:
 * Starts the client in a new thread for concurrent execution.
 */
public class ChatClientExec implements ChatClientExecInterface {
    private int CHAT_ROOM_PORT;
    private static double clientX = 30.0;
    private static double clientY = 10.0;

    public ChatClientExec(int port) {
        CHAT_ROOM_PORT = port;
    }

    public void startClient() throws Exception {
        setClientY(getClientY() + 50.0);
        setClientX(getClientX() + 50.0);
        ChatClient client = new ChatClient(CHAT_ROOM_PORT);
        Thread t = new Thread(client);
        t.start();
    }

    public static double getClientX() {
        return clientX;
    }

    public static void setClientX(double clientX) {
        ChatClientExec.clientX = clientX;
    }

    public static double getClientY() {
        return clientY;
    }

    public static void setClientY(double clientY) {
        ChatClientExec.clientY = clientY;
    }
}
