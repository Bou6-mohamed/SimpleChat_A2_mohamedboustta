package edu.seg2105.client.backend;

import ocsf.client.*;

import java.io.*;

import edu.seg2105.client.common.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 */
public class ChatClient extends AbstractClient {

  // Instance variables **********************************************

  /** The interface type variable to communicate with console UI */
  private ChatIF clientUI;

  /** The login ID of this client (provided from command-line) */
  private String loginID;

  // Constructors ****************************************************

  /**
   * Constructs an instance of the chat client with login ID support
   * @param host     the server host to connect to
   * @param port     the port number on the server
   * @param clientUI the user interface object
   * @param loginID  the login identifier of the client
   */
  public ChatClient(String host, int port, ChatIF clientUI, String loginID) throws IOException {
    super(host, port);
    this.clientUI = clientUI;
    this.loginID = loginID;
    openConnection(); // Automatically connect and send login
  }

  // Called when the connection is successfully established
  @Override
  protected void connectionEstablished() {
    try {
      sendToServer("#login " + loginID);
    } catch (IOException e) {
      clientUI.display("ERROR - Failed to send login ID to server.");
      quit();
    }
  }

  // Handles all messages received from the server
  public void handleMessageFromServer(Object msg) {
    clientUI.display(msg.toString());
  }

  // Handles user input from the console
  public void handleMessageFromClientUI(String message) {
    if (message.startsWith("#")) {
      handleCommand(message); // interpret as client command
    } else {
      try {
        sendToServer(message); // forward regular messages to server
      } catch (IOException e) {
        clientUI.display("Could not send message to server. Terminating client.");
        quit();
      }
    }
  }

  // Processes all commands from the user that start with '#'
  private void handleCommand(String command) {
    String[] parts = command.split(" ");

    switch (parts[0]) {
      case "#quit":
        clientUI.display("Quitting...");
        quit();
        break;

      case "#logoff":
        try {
          closeConnection();
          clientUI.display("Disconnected from server.");
        } catch (IOException e) {
          clientUI.display("Error disconnecting from server.");
        }
        break;

      case "#login":
        if (isConnected()) {
          clientUI.display("Already connected.");
        } else {
          try {
            openConnection();
            clientUI.display("Reconnected to server.");
          } catch (IOException e) {
            clientUI.display("Could not reconnect to server.");
          }
        }
        break;

      case "#sethost":
        if (isConnected()) {
          clientUI.display("Cannot change host while connected. Use #logoff first.");
        } else if (parts.length < 2) {
          clientUI.display("Usage: #sethost <hostname>");
        } else {
          setHost(parts[1]);
          clientUI.display("Host set to: " + getHost());
        }
        break;

      case "#setport":
        if (isConnected()) {
          clientUI.display("Cannot change port while connected. Use #logoff first.");
        } else if (parts.length < 2) {
          clientUI.display("Usage: #setport <port>");
        } else {
          try {
            int newPort = Integer.parseInt(parts[1]);
            setPort(newPort);
            clientUI.display("Port set to: " + getPort());
          } catch (NumberFormatException e) {
            clientUI.display("Invalid port number.");
          }
        }
        break;

      case "#gethost":
        clientUI.display("Current host: " + getHost());
        break;

      case "#getport":
        clientUI.display("Current port: " + getPort());
        break;

      default:
        clientUI.display("Unknown command: " + command);
        break;
    }
  }

  // Gracefully shut down the client
  public void quit() {
    try {
      closeConnection();
    } catch (IOException e) {
      // Ignore
    }
    System.exit(0);
  }

  // Called when server closes the connection
  @Override
  protected void connectionClosed() {
    clientUI.display("Connection closed.");
  }

  // Called when an unexpected error happens during connection
  @Override
  protected void connectionException(Exception e) {
    clientUI.display("The server has shut down.");
    quit();
  }
}
// End of ChatClient class
