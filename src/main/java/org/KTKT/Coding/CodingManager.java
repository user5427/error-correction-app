package org.KTKT.Coding;

import org.KTKT.Coding.CodingUtils.*;
import org.KTKT.Coding.ESDResultRecords.ImageESDResult;
import org.KTKT.Coding.ESDResultRecords.MessageESDResult;
import org.KTKT.CodingPages.ESDStatus;
import org.KTKT.CodingPages.SendImagePage;
import org.KTKT.CodingPages.SendTextPage;
import org.KTKT.Data.CosetSyndromWeightTable.CosetSyndromWeight;
import org.KTKT.Data.DataManager;
import org.KTKT.Data.Matrix.Matrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class CodingManager {
    private static CodingManager instance = null;

    /**
     * Singleton
     * @return
     */
    public static CodingManager getInstance() {
        if (instance == null) {
            instance = new CodingManager();
        }
        return instance;
    }

    /**
     * Encodes, sends and decodes the text
     * @param message
     * @param probability
     * @param textPageController
     */
    public void ESDText(String message, float probability, SendTextPage textPageController) {
        new Thread(() -> {
            if (!ESDactive && FinishedExecuting) {
                ESDactive = true;
                FinishedExecuting = false;
            } else {
                return;
            }
            try {
                int[] binaryMessage = TextUtils.textToBinary(message);
                ESDBinaryRecord results = encodeSendDecodeBinary(probability, binaryMessage, textPageController);

                if (results != null) {
                    String resultString = TextUtils.textFromBinary(results.result());
                    String noDecString = TextUtils.textFromBinary(results.noDecResult());

                    textPageController.receiveResult(new MessageESDResult(resultString, noDecString));
                }
            } finally {
                ESDactive = false;
                FinishedExecuting = true;
            }
        }).start();
    }

    /**
     * Used for stopping the ESD process from UI
     */
    public static void stopESD() {
        ESDactive = false;
    }

    /**
     * Used for checking if ESD is active
     * @return
     */
    public static boolean isESDactive() {
        return ESDactive;
    }

    private static boolean ESDactive = false;
    private static boolean FinishedExecuting = true;

    /**
     * Encodes, sends and decodes the image
     * @param imageFile
     * @param probability
     * @param imagePageController
     * @throws IOException
     */
    public void ESDImage(File imageFile, float probability, SendImagePage imagePageController) throws IOException {
        new Thread(() -> {
            if (!ESDactive && FinishedExecuting) {
                ESDactive = true;
                FinishedExecuting = false;
            } else {
                return;
            }
            try {
                BufferedImage originalImage = ImageIO.read(imageFile);
                int[] binaryImage = ImageUtils.convertImageToIntArray(originalImage);
                ESDBinaryRecord results = encodeSendDecodeBinary(probability, binaryImage, imagePageController);

                if (results != null) {
                    BufferedImage processedImage = ImageUtils.convertIntArrayToImage(results.result(), originalImage.getWidth(), originalImage.getHeight());
                    BufferedImage noCodeImage = ImageUtils.convertIntArrayToImage(results.noDecResult(), originalImage.getWidth(), originalImage.getHeight());
                    imagePageController.receiveResult(new ImageESDResult(noCodeImage, processedImage));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                ESDactive = false;
                FinishedExecuting = true;
            }
        }).start();
    }

    /**
     * Encodes, sends and decodes the message
     * @param probability
     * @param binaryMessage
     * @param status
     * @return
     */
    private ESDBinaryRecord encodeSendDecodeBinary(float probability, int[] binaryMessage, ESDStatus status) {
        int k = DataManager.getInstance().getRows_k();
        int additionalBits;

        // start time
        long startTime = System.currentTimeMillis();

        /**
         * Add additional bits to the message if needed
         */
        if (binaryMessage.length > k) {
            additionalBits = k - binaryMessage.length % k;
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

        /**
         * Encode, send and decode the message
         */
        int[][] resultBlocks = new int[blocks.length][];
        int[][] noDecodedBlocks = new int[blocks.length][];
        double statusNow = 0;
        for (int i = 0; i < blocks.length; i++) {
            // return if thread is instructed to stop
            if (!ESDactive){
                return null;
            }
            int[] encodedBlock = encodeMessage(blocks[i]);
            int[] noisedBlock = sendBinaryMessageToChannel(encodedBlock, probability);
            int[] decodedBlock = decodeMessage(noisedBlock);
            int[] noDecodedBlock = decodeMessageWithoutReconstruction(noisedBlock);
            resultBlocks[i] = decodedBlock;
            noDecodedBlocks[i] = noDecodedBlock;

            double calculateStatus = (double) i / blocks.length;
            if (calculateStatus - statusNow > 0.01){
                statusNow = calculateStatus;
                long timeTaken = System.currentTimeMillis() - startTime;
                status.setESDStatus(statusNow, timeTaken);
            }
        }

        long timeTaken = System.currentTimeMillis() - startTime;
        status.setESDStatus(1.0, timeTaken);

        /**
         * Convert message blocks to one array
         */
        int[] result = getResult(resultBlocks, k, additionalBits);
        int[] noDecResult = getResult(noDecodedBlocks, k, additionalBits);
        ESDBinaryRecord results = new ESDBinaryRecord(result, noDecResult);

        return results;
    }

    /**
     * Record for the ESD result
     * @param result
     * @param noDecResult
     */
    private record ESDBinaryRecord(int[] result, int[] noDecResult) {
    }

    /**
     * Convert message blocks to one array
     * Removes additional bits from the message
     * @param resultBlocks
     * @param k
     * @param additionalBits
     * @return
     */
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

    /**
     * Adds noise to the message with given probability
     * @param message
     * @param probability
     * @return
     */
    public int[] sendBinaryMessageToChannel(int[] message, float probability) {
        return Channel.addNoise(message, probability);
    }

    /**
     * Encodes the message using the G matrix
     * @param message
     * @return
     */
    public int[] encodeMessage(int[] message) {
        Matrix G_matrix = DataManager.getInstance().getG_matrix();
        return Encoding.encode(G_matrix, message);
    }

    /**
     * Decodes the message using the H matrix and the coset syndrom weights
     * @param message
     * @return
     */
    public int[] decodeMessage(int[] message) {
        Matrix H_matrix = DataManager.getInstance().getH_matrix();
        List<CosetSyndromWeight> cosetSyndromWeights = DataManager.getInstance().getCosetSyndromWeights();
        return Decoding.decode(H_matrix, cosetSyndromWeights, message);
    }

    /**
     * Decodes the message without reconstruction
     * Uses H matrix to receive k parameter
     * @param message
     * @return
     */
    public int[] decodeMessageWithoutReconstruction(int[] message) {
        Matrix H_matrix = DataManager.getInstance().getH_matrix();
        return Decoding.decodeWithoutReconstruction(H_matrix, message);
    }
}
