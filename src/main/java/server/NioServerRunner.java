package server;

public class NioServerRunner {

    public static void main(String[] args) {

        NioServer nioServer = new NioServer();
        nioServer.start(8090);
    }
}
