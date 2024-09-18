package client;

import java.util.Scanner;

public class NioClientRunnerFirst {

    public static void main(String[] args) {

        NioClient firstClient = new NioClient("First Client");
        firstClient.connect(
                8090,"localhost", new Scanner(System.in)
        );
    }
}
