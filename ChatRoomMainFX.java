package application;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Title: Chat Room Main Control Panel
 * Lab 10
 * Author: G. Araya
 * Date: May 4, 2025
 *
 * Description:
 * JavaFX control panel with buttons to start the server and clients.
 */
public class ChatRoomMainFX extends BorderPane {

    private static final int CHAT_ROOM_PORT = 5001;
    private Button startClientButton, exitButton, startServerButton;
    private Label titleLabel, instructionsLabel1, instructionsLabel2, instructionsLabel3, instructionsLabel4, instructionsLabel5;

    private boolean serverStarted = false;

    public ChatRoomMainFX() {

        // Setup title and instructions
        titleLabel = new Label("\tChat Room Controller");
        titleLabel.setStyle("-fx-font-size: 20;");

        instructionsLabel1 = new Label("\t\t1.  Start the server.");
        instructionsLabel2 = new Label("\t\t2.  Start a client.");
        instructionsLabel3 = new Label("\t\t3.  Enter a screen name in the client's GUI.");
        instructionsLabel4 = new Label("\t\t4.  Start more clients.");
        instructionsLabel5 = new Label("\t\t5.  Enter a message in a client's GUI.");

        VBox centerPane = new VBox();
        centerPane.setAlignment(Pos.CENTER_LEFT);
        centerPane.setPadding(new Insets(10));
        centerPane.getChildren().addAll(titleLabel, instructionsLabel1, instructionsLabel2,
                instructionsLabel3, instructionsLabel4, instructionsLabel5);
        centerPane.setStyle("-fx-border-color: gray;");
        setCenter(centerPane);

        // Exit button
        exitButton = new Button("_Exit");
        exitButton.setMnemonicParsing(true);
        exitButton.setTooltip(new Tooltip("Select to close the server, all clients, and the application"));
        exitButton.setOnAction(event -> {
            System.out.println("Closing the server, all clients, and the application");
            Platform.exit();
            System.exit(0);
        });

        // Start Client button
        startClientButton = new Button("Start each _Client");
        startClientButton.setMnemonicParsing(true);
        startClientButton.setTooltip(new Tooltip("Select this to start a Chat Room client. Can be selected multiple times."));
        startClientButton.setOnAction(event -> {
            if (serverStarted) {
                System.out.println("Starting a new client at port: " + CHAT_ROOM_PORT);
                ChatClientExec chatClientExec = new ChatClientExec(CHAT_ROOM_PORT);
                try {
                    chatClientExec.startClient();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Start the server first.");
            }
        });

        // Start Server button
        startServerButton = new Button("Start the _Server");
        startServerButton.setMnemonicParsing(true);
        startServerButton.setTooltip(new Tooltip("Select this to start the Chat Room server - do this once only"));
        startServerButton.setOnAction(event -> {
            if (serverStarted) {
                JOptionPane.showMessageDialog(null, "Server is already running - not restarted");
            } else {
                serverStarted = true;
                System.out.println("Starting the server at hostname localhost at port: " + CHAT_ROOM_PORT);
                ChatServerExec chatServerExec = new ChatServerExec(CHAT_ROOM_PORT);
                chatServerExec.startServer();
            }
        });

        // Bottom button layout
        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER);
        HBox.setMargin(startClientButton, new Insets(10));
        HBox.setMargin(startServerButton, new Insets(10));
        HBox.setMargin(exitButton, new Insets(10));
        bottomBox.getChildren().addAll(startServerButton, startClientButton, exitButton);
        setBottom(bottomBox);
    }
}
