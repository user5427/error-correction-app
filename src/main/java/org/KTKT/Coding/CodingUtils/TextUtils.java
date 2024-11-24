package org.KTKT.Coding.CodingUtils;

import java.nio.charset.StandardCharsets;

public class TextUtils {
    public static String textFromBinary(int[] binaryArray) {
        byte[] byteArray = BinaryUtils.convertBinaryToByteArray(binaryArray);
        return new String(byteArray, StandardCharsets.UTF_8);
    }

    public static int[] textToBinary(String text) {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);

        return BinaryUtils.convertByteArrayToBinary(bytes);
    }


}
