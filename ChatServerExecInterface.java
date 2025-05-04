package application;
/**
 * Title: Chat Server Execution Interface
 * Lab 10
 * Author: G. Araya
 * Date: May 4, 2025
 *
 * Description:
 * Interface that provides method signature for launching the server.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public interface ChatServerExecInterface {
    void startServer(); 
}
