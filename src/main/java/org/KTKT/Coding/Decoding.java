package org.KTKT.Coding;

import org.KTKT.Constants.ErrorConstants;
import org.KTKT.Data.CosetSyndromWeightTable.CosetSyndromWeight;
import org.KTKT.Data.Matrix.Matrix;
import org.KTKT.Data.Matrix.MatrixInt;

import java.util.Arrays;
import java.util.List;

public class Decoding {

    /**
     * Does not use the n-k bits to reconstruct the message.
     * In other words, it does not use the x*A part from (x|x*A)
     * @param H_matrix
     * @param message
     * @return
     */
    public static int[] decodeWithoutReconstruciton (MatrixInt H_matrix, int[] message ) {
        int k = H_matrix.getColumns() - H_matrix.getRows();
        int[] result = new int[k];
        System.arraycopy(message, 0, result, 0, k);
        return result;
    }

    /**
     * Decodes the message using the H matrix and the coset syndrom weights
     * @param H_matrix
     * @param cosetSyndromWeights
     * @param message
     * @return
     * @throws RuntimeException
     */
    public static int[] decode(MatrixInt H_matrix, List<CosetSyndromWeight> cosetSyndromWeights, int[] message) throws RuntimeException {
        int[] res = new int[message.length];
        System.arraycopy(message, 0, res, 0, message.length);

        int messageSyndromeWeight = intFindWeightOfCoset(res, H_matrix, cosetSyndromWeights);
        if (messageSyndromeWeight == -1) {
            throw new RuntimeException(ErrorConstants.MESSAGE_SYNDROME_WEIGHT_NOT_FOUND);
        }

        int changePosition = 0;
        while (messageSyndromeWeight != 0 && changePosition < res.length) {
            res[changePosition] = (res[changePosition] + 1) % 2;
            int tempWeight = intFindWeightOfCoset(res, H_matrix, cosetSyndromWeights);
            if (tempWeight < messageSyndromeWeight) {
                messageSyndromeWeight = tempWeight;
            } else {
                res[changePosition] = (res[changePosition] + 1) % 2;
            }
            changePosition++;
        };

        if (messageSyndromeWeight != 0) {
            throw new RuntimeException(ErrorConstants.SYNDROME_WEIGHT_ZERO_NOT_FOUND);
        }

        int k = H_matrix.getColumns() - H_matrix.getRows();
        int[] result = new int[k];
        System.arraycopy(res, 0, result, 0, k);
        return result;
    }

    /**
     * Finds the weight of coset for message
     * @param message
     * @param H_Matrix
     * @param cosetSyndromWeights
     * @return
     */
    private static int intFindWeightOfCoset(int[] message, MatrixInt H_Matrix, List<CosetSyndromWeight> cosetSyndromWeights){
        var res = new Matrix(H_Matrix).multiply(new Matrix(message).transpose()).transpose().toVector();
        // find same syndrome from list
        for (CosetSyndromWeight cosetSyndromWeight : cosetSyndromWeights) {
            if (Arrays.equals(cosetSyndromWeight.getSyndrom(), res)) {
                return cosetSyndromWeight.getWeight();
            }
        }

        throw new RuntimeException(ErrorConstants.SYNDROME_WEIGHT_NOT_FOUND);
    }


}
