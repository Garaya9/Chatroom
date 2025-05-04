package application;
/**
 * Title: Chat Room server
 * Lab 10
 * Author: G. Araya
 * Date: May 4, 2025
 *
 * Description:
 * JavaFX control panel with buttons to start the server and clients.
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class ChatServer implements Runnable {

    private static int CHAT_ROOM_PORT;
    private String name;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    private static HashSet<String> names = new HashSet<>();
    private static HashSet<PrintWriter> writers = new HashSet<>();

    public ChatServer(int port) {
        CHAT_ROOM_PORT = port;
    }

    @Override
    public void run() {
        ServerSocket listener = null;
        try {
            // Create server socket
            listener = new ServerSocket(CHAT_ROOM_PORT);
            System.out.println("The chat server is running on port " + CHAT_ROOM_PORT);

            while (true) {
                // Accept a new client
                clientSocket = listener.accept();
                System.out.println("New client connected: " + clientSocket);

                // Setup input and output streams
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Loop until a unique name is submitted
                name = null;
                while (name == null) {
                    out.println("SUBMITNAME");
                    name = in.readLine();

                    if (name == null || name.trim().isEmpty() || name.equals("null") || names.contains(name)) {
                        out.println("WRONGNAME");
                        name = null;
                    } else {
                        names.add(name);
                    }
                }

                out.println("NAMEACCEPTED");
                writers.add(out);

                // Start a thread to handle this client
                Thread t = new Thread(new ServerThreadForClient(in, out, name));
                t.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (listener != null) listener.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Inner class to handle communication with one client.
     */
    private class ServerThreadForClient implements Runnable {
        BufferedReader in;
        PrintWriter out;
        String name;

        ServerThreadForClient(BufferedReader in, PrintWriter out, String name) {
            this.in = in;
            this.out = out;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                String input;
                while ((input = in.readLine()) != null) {
                    for (PrintWriter writer : writers) {
                        writer.println("MESSAGE " + name + ": " + input);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Cleanup
                if (name != null) {
                    names.remove(name);
                }
                if (out != null) {
                    writers.remove(out);
                }
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
