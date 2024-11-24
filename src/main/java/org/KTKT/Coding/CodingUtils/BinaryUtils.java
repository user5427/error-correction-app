package org.KTKT.Coding.CodingUtils;

/**
 * All binary representations use int[] arrays. This is because matrix operations are done using int[] arrays.
 */
public class BinaryUtils {
    /**
     * Converts number to binary representation
     * Example: 1 -> [00000001]
     * @param number
     * @return
     */
    public static int[] covnertNumberToBinary(int number) {
        int[] result = new int[8];
        for (int i = 0; i < 8; i++) {
            result[7 - i] = (number >> i) & 1;
        }

        return result;
    }

    /**
     * Converts int array to binary representation
     * Example: [1, 2, 3] -> [000000010000001000000011]
     * @apiNote Each number is converted to 8 bits
     * @param numbers
     * @return
     */
    public static int[] convertNumberArrayToBinary(int[] numbers) {
        int[] result = new int[numbers.length * 8];

        for (int i = 0; i < numbers.length; i++) {
            int[] binary = covnertNumberToBinary(numbers[i]);
            System.arraycopy(binary, 0, result, i * 8, 8);
        }

        return result;
    }

    /**
     * Converts byte array to binary representation
     * @param bytes
     * @return
     */
    public static int[] convertByteArrayToBinary(byte[] bytes) {
        int[] result = new int[bytes.length * 8];
        for (int i = 0; i < bytes.length; i++) {
            int[] binary = covnertNumberToBinary(bytes[i]);
            System.arraycopy(binary, 0, result, i * 8, 8);
        }

        return result;
    }

    /**
     * Converts 8 bits (byte) to int
     * @param bits
     * @return
     */
    public static int convertBitsToNumber(int[] bits) {
        int result = 0;
        for (int i = 0; i < 8; i++) {
            result |= ((bits[i] & 1) << (7 - i));  // Shift each bit to its correct position
        }

        return result;
    }

    /**
     * Converts binary to byte array
     * @param binary
     * @return
     */
    public static byte[] convertBinaryToByteArray(int[] binary) {
        byte[] result = new byte[binary.length / 8];
        for (int i = 0; i < binary.length; i += 8) {
            int[] bits = new int[8];
            System.arraycopy(binary, i, bits, 0, 8);
            result[i / 8] = (byte) convertBitsToNumber(bits);
        }
        return result;
    }

    /**
     * Converts binary string to int array
     * Example: "1001" -> [1, 0, 0, 1]
     * @param message
     * @return
     */
    public static int[] convertBinaryStringToIntArray(String message) {
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

    /**
     * Converts int array to binary string
     * Example: [1, 0, 0, 1] -> "1001"
     * @param binaryMessage
     * @return
     */
    public static String convertIntArrayToBinaryString(int[] binaryMessage) {
        StringBuilder sb = new StringBuilder();
        for (int j : binaryMessage) {
            sb.append(j);
        }
        return sb.toString();
    }
}
