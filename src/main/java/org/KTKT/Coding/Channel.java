package org.KTKT.Coding;

public class Channel {
    /**
     * Adds noise to the message with given probability
     * @param message
     * @param probability
     * @return
     */
    public static int[] addNoise(int[] message, double probability) {
        int[] res = new int[message.length];
        for (int i = 0; i < message.length; i++) {
            if (Math.random() < probability) {
                res[i] = (message[i] + 1) % 2;
            } else {
                res[i] = message[i];
            }
        }
        return res;
    }

    /**
     * Generates a noise vector (e) with given length and probability
     * @param length
     * @param probability
     * @return
     */
    public static int[] generateNoiseVector(int length, double probability) {
        int[] res = new int[length];
        for (int i = 0; i < length; i++) {
            if (Math.random() < probability) {
                res[i] = 1;
            } else {
                res[i] = 0;
            }
        }
        return res;
    }

    /**
     * Applies noise vector (e) to the message
     * @param message
     * @param noise
     * @return
     */
    public static int[] applyNoise(int[] message, int[] noise) {
        int[] res = new int[message.length];
        for (int i = 0; i < message.length; i++) {
            res[i] = (message[i] + noise[i]) % 2;
        }
        return res;
    }
}
