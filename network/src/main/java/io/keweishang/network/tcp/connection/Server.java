package io.keweishang.network.tcp.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kshang on 25/06/2017.
 *
 * Inspired by a question about port and socket on stackoverflow.
 * https://stackoverflow.com/questions/152457/what-is-the-difference-between-a-port-and-a-socket
 */
public class Server {
  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(56789);

    List<Socket> connections = new ArrayList<>();
    int numConnections = 0;
    System.out.println("Server starts accepting connection requests.");
    do {
      Socket connection = serverSocket.accept();
      // each TCP connection in the same server process has same port.
      System.out.printf("The %d-th connection local socket address = %s\n", ++numConnections, connection.getLocalSocketAddress());
      connections.add(connection);
    } while (numConnections < 5);
    System.out.println("Simple server can only handle at most 5 connections.");

    // close TCP connections
    for (Socket con : connections) {
      con.close();
    }
    // close the listening TCP connection
    serverSocket.close();

  }
}
