package client.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * Created by cj on 2017-11-22.
 */
public class AES {
    private static final String AES = "AES";
    private byte[] key;

    public AES(byte[] key) {
        this.key = key;
    }


    public byte[] encrypt(String plainText) throws Exception {

        byte[] plainTextBytes = plainText.getBytes(StandardCharsets.UTF_8);

        SecretKeySpec secretKey = new SecretKeySpec(key, AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        return cipher.doFinal(plainTextBytes);
    }

    /**
     * Decrypts the given byte array
     *
     * @param cipherText The data to decrypt
     */
    public String decrypt(byte[] cipherText) throws Exception {

        SecretKeySpec secretKey = new SecretKeySpec(key, AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        return new String(cipher.doFinal(cipherText));
    }
}
