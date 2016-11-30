package com.kewei.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class SocketChannelServer {

    public static void main(String[] args) throws IOException {
        // Create server socket channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        
        // Create socket channel
        SocketChannel socketChannel = serverSocketChannel.accept();

        ByteBuffer buffer = ByteBuffer.allocate(8);
        // Write 3 messages to client and close the connection
        for (int i = 3; i > 0; i--) {
            buffer.clear();
            buffer.put("abcdefgh".getBytes());
            buffer.flip();
            // write to channel is not blocked
            int num = socketChannel.write(buffer);
            System.out.printf("write %d bytes\n", num);
        }
        
        // Close server socket channel
        serverSocketChannel.close();
        // Socket channel is still open even if server socket channel
        socketChannel.close();
    }
}
