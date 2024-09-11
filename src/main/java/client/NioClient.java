package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NioClient {

    public void connect(int port, Scanner scanner) {

        try (
                SocketChannel channel = SocketChannel.open()
        ){
            channel.connect(
                    new InetSocketAddress(port)
            );
            channel.configureBlocking(false);

            System.out.println(
                    "Connection to the server with port " + port
            );

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            while (true) {
                String message = scanner.nextLine();
                if ("quit".equalsIgnoreCase(message)) {
                    break;
                }
                message += System.lineSeparator();

                byteBuffer.clear()
                        .put(message.getBytes())
                        .flip();

                while (byteBuffer.hasRemaining()) {
                    channel.write(byteBuffer);
                }
                byteBuffer.clear();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
