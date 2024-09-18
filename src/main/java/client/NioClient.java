package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NioClient {

    private String name;

    public NioClient(String name) {
        this.name = name;
    }

    public void connect(int port, String address, Scanner scanner) {

        try (
                SocketChannel channel = SocketChannel.open()
        ){
            channel.connect(
                    new InetSocketAddress(address, port)
            );
            channel.configureBlocking(false);

            System.out.println(
                    "Connection to the server with port " + port
            );

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            while (true) {
                String message = name + ": " + scanner.nextLine();
                if ((name + ": quit").equalsIgnoreCase(message)) {
                    break;
                }
                message += System.lineSeparator();

                byteBuffer.clear()
                        .put(message.getBytes())
                        .flip();

                while (byteBuffer.hasRemaining()) {
                    channel.write(byteBuffer);
                }

                int read = channel.read(byteBuffer);
                if (read != -1) {
                    byteBuffer.flip();

                    while (byteBuffer.position() < byteBuffer.limit()) {
                        System.out.print(
                                (char) byteBuffer.get()
                        );
                    }
                }
                byteBuffer.clear();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.name = name;
    }
}
