package client;

import java.util.Scanner;

public class NioClientRunner {

    public static void main(String[] args) {

        NioClient nioClient = new NioClient();
        nioClient.connect(8090, new Scanner(System.in));
    }
}
