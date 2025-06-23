package edu.seg2105.server.ui;

import java.util.Scanner;

import edu.seg2105.edu.server.backend.EchoServer;
import edu.seg2105.client.common.ChatIF;

public class ServerConsole implements ChatIF {
  
  final public static int DEFAULT_PORT = 5555;
  EchoServer server;
  Scanner fromConsole;

  public ServerConsole(int port) {
    server = new EchoServer(port, this);

    try {
      server.listen();  // Start listening for clients
    } catch (Exception ex) {
      System.out.println("ERROR - Could not listen for clients!");
    }

    fromConsole = new Scanner(System.in);
  }

  public void accept() {
    try {
      String message;

      while (true) {
        message = fromConsole.nextLine();
        server.handleMessageFromServerUI(message);
      }
    } catch (Exception ex) {
      System.out.println("Unexpected error while reading from console!");
    }
  }

  public void display(String message) {
    System.out.println("> " + message);
  }

  public static void main(String[] args) {
    int port = DEFAULT_PORT;

    try {
      port = Integer.parseInt(args[0]);
    } catch (Exception e) {
      port = DEFAULT_PORT;
    }

    ServerConsole sc = new ServerConsole(port);
    sc.accept();  // Accept input from console
  }
}
