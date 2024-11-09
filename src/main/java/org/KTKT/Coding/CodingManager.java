package org.KTKT.Coding;

import org.KTKT.Data.CosetSyndromWeightTable.CosetSyndromWeight;
import org.KTKT.Data.DataManager;
import org.KTKT.Data.Matrix.Matrix;

import java.util.List;

public class CodingManager {
    private static CodingManager instance = null;
    public static CodingManager getInstance() {
        if (instance == null) {
            instance = new CodingManager();
        }
        return instance;
    }

    public int[] convertStringToBinary(String message) {
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

    public String convertBinaryToString(int[] binaryMessage) {
        StringBuilder sb = new StringBuilder();
        for (int j : binaryMessage) {
            sb.append(j);
        }
        return sb.toString();
    }

    public int[] sendBinaryMessageToChannel(int[] message, float probability) {
        return Channel.addNoise(message, probability);
    }

    public int[] encodeMessage(int[] message) {
        Matrix G_matrix = DataManager.getInstance().getG_matrix();
        return Encoding.encode(G_matrix, message);
    }

    public int[] decodeMessage(int[] message) {
        Matrix H_matrix = DataManager.getInstance().getH_matrix();
        List<CosetSyndromWeight> cosetSyndromWeights = DataManager.getInstance().getCosetSyndromWeights();
        return Decoding.decode(H_matrix, cosetSyndromWeights, message);
    }

    public int[] decodeMessageWithoutReconstruction(int[] message) {
        Matrix H_matrix = DataManager.getInstance().getH_matrix();
        return Decoding.decodeWithoutReconstruciton(H_matrix, message);
    }
}
