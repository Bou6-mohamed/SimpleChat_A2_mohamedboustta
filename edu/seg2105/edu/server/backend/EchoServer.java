package edu.seg2105.edu.server.backend;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import java.io.IOException;
import edu.seg2105.client.common.ChatIF;  // Required for serverUI

public class EchoServer extends AbstractServer {
  // Class variables *************************************************
  final public static int DEFAULT_PORT = 5555;

  // Instance variables **********************************************
  private ChatIF serverUI;

  // Constructors ****************************************************
  /**
   * Default constructor with no UI (used in Phase 0)
   */
  public EchoServer(int port) {
    super(port);
  }

  /**
   * Constructor that accepts a server UI (used in Exercise 2)
   */
  public EchoServer(int port, ChatIF serverUI) {
    super(port);
    this.serverUI = serverUI;
  }

  // Instance methods ************************************************
  public void handleMessageFromClient(Object msg, ConnectionToClient client) {
	  String message = msg.toString();

	  // Handle login command
	  if (message.startsWith("#login")) {
	    String[] parts = message.split(" ");
	    if (parts.length < 2) {
	      try {
	        client.sendToClient("ERROR - No login ID provided.");
	        client.close();
	      } catch (IOException e) {}
	      return;
	    }

	    if (client.getInfo("loginID") != null) {
	      try {
	        client.sendToClient("ERROR - Already logged in.");
	        client.close();
	      } catch (IOException e) {}
	      return;
	    }

	    String loginID = parts[1];
	    client.setInfo("loginID", loginID);
	    System.out.println(loginID + " has logged on.");
	    return;
	  }

	  // Block non-logged in clients from sending messages
	  Object idObj = client.getInfo("loginID");
	  if (idObj == null) {
	    try {
	      client.sendToClient("ERROR - You must log in first.");
	      client.close();
	    } catch (IOException e) {}
	    return;
	  }

	  // Otherwise, broadcast with loginID prefix
	  String loginID = idObj.toString();
	  System.out.println("Message received: " + message + " from " + loginID);
	  sendToAllClients(loginID + " > " + message);
	}

  /**
   * Handles messages typed into the server console (via ServerConsole)
   */
  public void handleMessageFromServerUI(String message) {
    if (message.startsWith("#")) {
      if (message.equals("#quit")) {
        try {
          close();
        } catch (Exception e) {
          // Ignore
        }
        System.exit(0);
      } else if (message.equals("#stop")) {
        stopListening();
      } else if (message.equals("#close")) {
        try {
          close();
        } catch (Exception e) {
          serverUI.display("Error closing the server.");
        }
      } else if (message.startsWith("#setport")) {
        if (!isListening()) {
          try {
            int newPort = Integer.parseInt(message.split(" ")[1]);
            setPort(newPort);
            serverUI.display("Port set to " + newPort);
          } catch (Exception e) {
            serverUI.display("Usage: #setport <port>");
          }
        } else {
          serverUI.display("Cannot change port while server is listening.");
        }
      } else if (message.equals("#start")) {
        try {
          listen();
        } catch (Exception e) {
          serverUI.display("Could not start server.");
        }
      } else if (message.equals("#getport")) {
        serverUI.display("Current port: " + getPort());
      } else {
        serverUI.display("Unknown command.");
      }
    } else {
      String formatted = "SERVER MSG> " + message;
      System.out.println(formatted);
      sendToAllClients(formatted);
    }
  }

  @Override
  protected void clientConnected(ConnectionToClient client) {
    System.out.println("A new client has connected: " + client);
  }

  @Override
  synchronized protected void clientDisconnected(ConnectionToClient client) {
    System.out.println("A client has disconnected: " + client);
  }

  @Override
  protected void serverStarted() {
    System.out.println("Server listening for connections on port " + getPort());
  }

  @Override
  protected void serverStopped() {
    System.out.println("Server has stopped listening for connections.");
  }

  // Main method for phase 0 or testing without ServerConsole
  public static void main(String[] args) {
    int port = 0;

    try {
      port = Integer.parseInt(args[0]);
    } catch (Throwable t) {
      port = DEFAULT_PORT;
    }

    EchoServer sv = new EchoServer(port);

    try {
      sv.listen();
    } catch (Exception ex) {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
}
// End of EchoServer class
