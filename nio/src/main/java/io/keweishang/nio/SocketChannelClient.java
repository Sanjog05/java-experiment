package io.keweishang.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Test how the following works:
 * 
 * - How to read to ByteBuffer from Channel
 * 
 * - How to write to Channel from ByteBuffer
 * 
 * - Read() from channel is blocked until resource (another socket's writing) is available
 * 
 * - Read() from channel returns -1 when it's the end of resource (another socket closing connection)
 * 
 * - Write() to channel is not blocked
 * 
 * @author kewei
 *
 */
public class SocketChannelClient {

    public static void main(String[] args) throws IOException {
        // Create socket channel
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress(9999));

        ByteBuffer buffer = ByteBuffer.allocate(8);
        byte[] dst = new byte[8];
        while (true) {
            // Read from socket channel is blocked until sb (server) writes to
            // the channel.
            // ByteBuffer is ready to be written to when it's first created or
            // when
            // it's cleared
            int num = channel.read(buffer);
            if (num == -1) {
                // When the number of bytes read from channel is -1, the server
                // has closed its socket.
                System.out.println("Server closed socket and ended conversation.");
                break;
            }
            // When you are done writing to ByteBuffer and want to read from it,
            // do flip.
            buffer.flip();
            buffer.get(dst);
            System.out.printf("read %d bytes\n", num);
            // When you are done reading from ByteBuffer and want to write to
            // it, do clear or compact. Compact moves unread bytes to the head
            // and starts writing at position after head.
            buffer.clear();
        }

    }

}
