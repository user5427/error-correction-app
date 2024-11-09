package org.KTKT.Coding.TextManagers;

public class TextSender {
    public static String toBinaryString(String text) {
        byte[] bytes = text.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        StringBuilder binary = new StringBuilder();

        for (byte b : bytes) {
            binary.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }

        return binary.toString();
    }
}
