package encode;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncoderDecoderTest {

    public static void main(String[] args)

            throws NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException
    {
        String string = "Hello World";

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();

        Cipher cipher = Cipher.getInstance("AES");

        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encodedBytes = cipher.doFinal(string.getBytes());
        System.out.println(
                new String(encodedBytes)
        );

        String encoded = Base64.getEncoder().encodeToString(string.getBytes());
        System.out.println(encoded);

        byte[] decode = Base64.getDecoder().decode(encoded);
        System.out.println(
                new String(decode)
        );
    }
}
