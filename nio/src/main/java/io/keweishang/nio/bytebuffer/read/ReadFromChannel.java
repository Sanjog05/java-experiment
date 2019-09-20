package io.keweishang.nio.bytebuffer.read;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Example of using ByteBuffer to read data from Channel
 */
public class ReadFromChannel {
    public static void main(String[] args) throws IOException {
        Path path = createTmpFile();
        String res = readFromFileChannel(path);
        System.out.println(res);
    }

    private static Path createTmpFile() throws IOException {
        Path tempFile = Files.createTempFile("my-file", ".txt");
        Files.write(tempFile, "hello world!".getBytes());
        return tempFile;
    }

    private static String readFromFileChannel(Path path) throws IOException {
        RandomAccessFile aFile = new RandomAccessFile(path.toAbsolutePath().toString(), "rw");
        FileChannel inChannel = aFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(4);
        StringBuilder sb = new StringBuilder();

        int bytesRead = inChannel.read(buf); //read into buffer.
        while (bytesRead != -1) {

            buf.flip();  //make buffer ready for read

            while (buf.hasRemaining()) {
                sb.append((char) buf.get());
            }

            buf.clear(); //make buffer ready for writing
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
        return sb.toString();
    }

}
