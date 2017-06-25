package io.keweishang.network.tcp.connection;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kshang on 25/06/2017.
 *
 * Inspired by a question about port and socket on stackoverflow.
 * https://stackoverflow.com/questions/152457/what-is-the-difference-between-a-port-and-a-socket
 */
public class Client {
  public static void main(String[] args) throws IOException {
    String remoteHostName = "localhost";
    int remotePort = 56789;
    int totalConnections = 5;

    List<Socket> connections = new ArrayList<>();
    System.out.println("client process starts requesting new connections");
    for (int i = 0; i < totalConnections; i++) {
      Socket connection = new Socket(remoteHostName, remotePort);
      // each TCP connection in the same client process has different port
      System.out.printf("The %d-th connection local socket address = %s\n", i, connection.getLocalSocketAddress());
      connections.add(connection);
    }

    // closing TCP connections
    for (Socket con : connections) {
      con.close();
    }

  }
}
