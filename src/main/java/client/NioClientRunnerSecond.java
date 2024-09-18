package client;

import java.util.Scanner;

public class NioClientRunnerSecond {

    public static void main(String[] args) {

        NioClient secondClient = new NioClient("Second Client");
        secondClient.connect(
                8090, "localhost", new Scanner(System.in)
        );
    }
}
