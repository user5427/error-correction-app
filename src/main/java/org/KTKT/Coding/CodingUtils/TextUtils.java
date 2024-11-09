package org.KTKT.Coding.CodingUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TextUtils {
    public static String textFromBinary(String binaryString) {
        List<Byte> byteList = new ArrayList<>();

        // Iterate through the binary string in chunks of 8
        for (int i = 0; i < binaryString.length(); i += 8) {
            try {
                // Extract 8-bit chunk and convert to a byte
                String byteString = binaryString.substring(i, Math.min(i + 8, binaryString.length()));
                byte b = (byte) Integer.parseInt(byteString, 2);
                byteList.add(b);
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                // Handle any errors in binary parsing or boundary issues
                System.out.println("Error decoding byte: " + binaryString.substring(i, Math.min(i + 8, binaryString.length())));
                byteList.add((byte) 0xEF); // Add replacement byte (often U+FFFD or 0xEF 0xBF 0xBD for �)
            }
        }

        // Convert list of bytes to byte array
        byte[] byteArray = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            byteArray[i] = byteList.get(i);
        }

        // Convert byte array to UTF-8 string
        return new String(byteArray, StandardCharsets.UTF_8);
    }

    public static String textToBinary(String text) {
        byte[] bytes = text.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        StringBuilder binary = new StringBuilder();

        for (byte b : bytes) {
            binary.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }

        return binary.toString();
    }

    public static int[] convertStringToIntArray(String message) {
        char[] charArray = message.toCharArray();
        int[] binaryMessage = new int[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == '0') {
                binaryMessage[i] = 0;
            } else if (charArray[i] == '1') {
                binaryMessage[i] = 1;
            }
        }
        return binaryMessage;
    }

    public static String convertIntArrayToString(int[] binaryMessage) {
        StringBuilder sb = new StringBuilder();
        for (int j : binaryMessage) {
            sb.append(j);
        }
        return sb.toString();
    }
}
