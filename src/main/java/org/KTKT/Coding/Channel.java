package org.KTKT.Coding;

public class Channel {
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
}
