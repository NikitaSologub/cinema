package by.itacademy.final_task_nikita_sologub.encoder;

import java.util.Base64;

public abstract class Encoder {
    private static Base64.Encoder encoder = null;

    public static String getHashCode(String password) {
        initEncoder();
        byte[] hashedBytes = encoder.encode(password.getBytes());
        char[] hashedChars = new char[hashedBytes.length];
        for (int i = 0; i < hashedBytes.length; i++) {
            hashedChars[i] = (char) hashedBytes[i];
        }
        return new String(hashedChars);
    }

    private static void initEncoder() {
        if (encoder == null) {
            encoder = Base64.getEncoder();
        }
    }
}