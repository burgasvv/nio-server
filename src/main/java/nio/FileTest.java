package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class FileTest {

    public static void main(String[] args) {

        try (
                FileChannel channel = FileChannel.open(
                        Path.of("src/main/resources/files/testFile.txt"),
                        StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.READ
                );
                Scanner scanner = new Scanner(System.in)
        ){
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            while (true) {

                String line = scanner.nextLine();

                if ("quit".equalsIgnoreCase(line)) {
                    break;
                }
                line += System.lineSeparator();

                byteBuffer.clear()
                        .put(line.getBytes())
                        .flip();

                while (byteBuffer.hasRemaining()) {
                    channel.write(byteBuffer);
                }

                channel.read(byteBuffer);
                byteBuffer.flip();
                while (byteBuffer.position() != byteBuffer.limit()) {
                    System.out.print(
                            (char) byteBuffer.get()
                    );
                }
                byteBuffer.clear();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
