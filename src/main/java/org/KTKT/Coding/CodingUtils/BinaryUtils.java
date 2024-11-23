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
    public static int[] covnertNumberToBinaryRepresentation(int number) {
        int[] result = new int[8];
        for (int i = 0; i < 8; i++) {
            result[7 - i] = (number >> i) & 1;
        }

        return result;
    }

    /**
     * Converts int[] array to binary representation
     * Example: [1, 2, 3] -> [000000010000001000000011]
     * @param numbers
     * @return
     */
    public static int[] convertNumberArrayToBinaryRepresentation(int[] numbers) {

//        StringBuilder binaryString = new StringBuilder();
//        for (int b : numbers) {
//            String binary = String.format("%8s", Integer.toBinaryString(b & 0xFF));
//            binaryString.append(binary);
//        }
//
//        int[] result = new int[binaryString.length()];
//        for (int i = 0; i < binaryString.length(); i++) {
//            result[i] = binaryString.charAt(i) == '0' ? 0 : 1;
//        }
        int[] result = new int[numbers.length * 8];

        for (int i = 0; i < numbers.length; i++) {
            int[] binary = covnertNumberToBinaryRepresentation(numbers[i]);
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
}
