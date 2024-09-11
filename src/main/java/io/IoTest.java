package io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class IoTest {

    public static void main(String[] args) throws URISyntaxException, IOException {

        BufferedInputStream bufferedInputStream = new BufferedInputStream(
                new URI("https://kolesa-uploads.ru/-/99209907-5392-4002-9d33-030ced8f3bdb/pagani-huayra-codalunga-40.jpg.webp")
                        .toURL().openStream()
        );

        byte[] bytes = bufferedInputStream.readAllBytes();

        try (
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                        new FileOutputStream(
                                "src/main/resources/images/pagani-huayra.jpg"
                        )
                )
        ) {
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.flush();
        }
    }
}
