package application;
/**
 * Title: Chat Client Interface
 * Lab 10
 * Author: G. Araya
 * Date: May 4, 2025
 *
 * Description:
 * Defines the method signatures required for a chat client,
 * including screen name and server port access.
 */
public interface ChatClientInterface extends Runnable {

    /**
     * Prompt for and return the desired screen name.
     */
    String getScreenName();

    /**
     * Return the port number of the chat server.
     */
    int getServerPort();
}
