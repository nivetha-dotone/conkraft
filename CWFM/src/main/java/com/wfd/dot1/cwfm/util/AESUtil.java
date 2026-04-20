package com.wfd.dot1.cwfm.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {

    private static final String SECRET = "1234567890123456"; // 16 chars

    public static String encrypt(String data) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getUrlEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            throw new RuntimeException("Error encrypting", e);
        }
    }

    public static String decrypt(String encryptedData) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decoded = Base64.getUrlDecoder().decode(encryptedData);
            return new String(cipher.doFinal(decoded));

        } catch (Exception e) {
            throw new RuntimeException("Error decrypting", e);
        }
    }
}
