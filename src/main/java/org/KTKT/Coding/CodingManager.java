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

    public MessageEncodingResult encodeSendDecodeText(String message, float probability) {
        String binaryStringMessage = TextUtils.textToBinary(message);
        int[] binaryMessage = TextUtils.convertStringToBinary(binaryStringMessage);
        int k = DataManager.getInstance().getRows_k();
        int additionalBits;

        if (binaryMessage.length > k) {
            additionalBits = binaryMessage.length % k;
        } else {
            additionalBits = k - binaryMessage.length;
        }

        if (additionalBits != 0) {
            int[] newBinaryMessage = new int[binaryMessage.length + additionalBits];
            System.arraycopy(binaryMessage, 0, newBinaryMessage, 0, binaryMessage.length);
            binaryMessage = newBinaryMessage;
        }

        // split message into blocks
        int[][] blocks = new int[binaryMessage.length / k][k];
        for (int i = 0; i < binaryMessage.length / k; i++) {
            System.arraycopy(binaryMessage, i * k, blocks[i], 0, k);
        }

        int[][] resultBlocks = new int[blocks.length][];
        int[][] noDecodedBlocks = new int[blocks.length][];
        for (int i = 0; i < blocks.length; i++) {
            int[] encodedBlock = encodeMessage(blocks[i]);
            int[] noisedBlock = sendBinaryMessageToChannel(encodedBlock, probability);
            int[] decodedBlock = decodeMessage(noisedBlock);
            int[] noDecodedBlock = decodeMessageWithoutReconstruction(noisedBlock);
            resultBlocks[i] = decodedBlock;
            noDecodedBlocks[i] = noDecodedBlock;
        }

        int[] result = getResult(resultBlocks, k, additionalBits);
        int[] noDecResult = getResult(noDecodedBlocks, k, additionalBits);

        String resultString = TextUtils.convertBinaryToString(result);
        String noDecString = TextUtils.convertBinaryToString(noDecResult);

        return new MessageEncodingResult(TextUtils.textFromBinary(resultString), TextUtils.textFromBinary(noDecString));
    }

    private static int[] getResult(int[][] resultBlocks, int k, int additionalBits) {
        // convert message to one array
        int[] result = new int[resultBlocks.length * k];
        for (int i = 0; i < resultBlocks.length; i++) {
            System.arraycopy(resultBlocks[i], 0, result, i * k, k);
        }

        // remove additional bits
        if (additionalBits != 0) {
            int[] newResult = new int[result.length - additionalBits];
            System.arraycopy(result, 0, newResult, 0, newResult.length);
            result = newResult;
        }
        return result;
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
