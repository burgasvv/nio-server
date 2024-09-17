package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Set;

public class NioServer {

    private final Set<SocketChannel>clients = new HashSet<>();

    public void start(int port) {

        try (
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                Selector selector = Selector.open()
        ){
            serverSocketChannel.bind(
                    new InetSocketAddress(port)
            );
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println(
                    "Server is started on port: " + port
            );

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            while (serverSocketChannel.isOpen()) {

                if (selector.select() == 0) {
                    continue;
                }

                for (SelectionKey key : selector.selectedKeys()) {

                    if (key.isAcceptable()) {

                        if (key.channel() instanceof ServerSocketChannel channel) {
                            SocketChannel client = channel.accept();

                            System.out.println(
                                    "Client " + client.socket().getInetAddress().getHostAddress() +
                                    ": " + client.socket().getPort() + " is connected"
                            );

                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);
                            clients.add(client);

                        } else {
                            throw new RuntimeException("Unknown connection");
                        }

                    } else if (key.isReadable()) {

                        if (key.channel() instanceof SocketChannel client) {

                            int read = client.read(byteBuffer);
                            if (read == -1) {

                                System.out.println(
                                        "Disconnect client: " + client.socket().getInetAddress().getHostAddress() +
                                        ": " + client.socket().getPort()
                                );

                                client.close();
                                clients.remove(client);
                            }

                            byteBuffer.flip();
                            System.out.println(
                                    new String(byteBuffer.array(), byteBuffer.position(), read)
                            );
                            byteBuffer.clear();

                        } else {
                            throw new RuntimeException("Unknown channel");
                        }
                    }
                }

                selector.selectedKeys().clear();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unused")
    public Set<SocketChannel> getClients() {
        return clients;
    }
}
