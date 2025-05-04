/**
 * CMSC204 Chat Room Lab 10
 * Author: G. Araya
 * Date: May 4, 2025
 *
 * Description:
 * Connects to the server, sends/receives messages, and manages its own GUI.
 */
package application;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class ChatClient implements Runnable, ChatClientInterface {
	private static int CHAT_ROOM_PORT = 0;
	boolean local = true;
	BufferedReader in;
	PrintWriter out = null;
	BorderPane frame = new BorderPane();
	TextField textField = new TextField();
	TextArea messageArea = new TextArea();
	Stage stage;
	String myScreenName = "";

	public ChatClient(int port) {
		CHAT_ROOM_PORT = port;

		// Layout GUI
		stage = new Stage();
		stage.setScene(new Scene(frame, 500, 200));
		stage.setX(ChatClientExec.getClientX());
		stage.setY(ChatClientExec.getClientY());
		stage.setTitle("Chat Client");
		stage.show();

		textField.setEditable(false);
		messageArea.setEditable(false);
		frame.setTop(textField);
		frame.setCenter(messageArea);
		frame.setVisible(true);

		// Responds to pressing Enter in the textfield
		textField.setOnAction(event -> {
			try {
				out.println(textField.getText());
				textField.setText("");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private String getServerAddress() {
		return "localhost";
	}

	@Override
	public String getScreenName() {
		return JOptionPane.showInputDialog(
			null,
			"Choose a screen name:",
			"Screen name selection",
			JOptionPane.PLAIN_MESSAGE
		);
	}

	@Override
	public int getServerPort() {
		return CHAT_ROOM_PORT;
	}

	@Override
	public void run() {
		try {
			Socket socket = new Socket(getServerAddress(), getServerPort());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			while (true) {
				String line = in.readLine();
				if (line == null) break;

				if (line.startsWith("SUBMITNAME")) {
					System.out.println("client received SUBMITNAME");
					myScreenName = getScreenName();
					out.println(myScreenName);
				} else if (line.startsWith("NAMEACCEPTED")) {
					System.out.println("client received NAMEACCEPTED");
					textField.setEditable(true);
				} else if (line.startsWith("WRONGNAME")) {
					System.out.println("client received WRONGNAME");
					JOptionPane.showMessageDialog(null, "Screen Name " + myScreenName + " is already in use.");
				} else if (line.startsWith("MESSAGE")) {
					System.out.println("client received MESSAGE");
					String message = line.substring(8);
					String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

					if (message.startsWith(myScreenName + ":")) {
						messageArea.appendText("[" + time + "] [You]" + message.substring(myScreenName.length()) + "\n");
					} else {
						messageArea.appendText("[" + time + "] " + message + "\n");
					}
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
