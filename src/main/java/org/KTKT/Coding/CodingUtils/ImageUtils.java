package org.KTKT.Coding.CodingUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * ImageUtils class contains methods for converting images to int[] arrays and other way.
 * All binary representations use int[] arrays. This is because matrix operations are done using int[] arrays.
 */
public class ImageUtils {

    /**
     * Converts image to int[] array of 0 and 1. Does not include the header of the image.
     * @param image
     * @return int[] array of 0 and 1
     */
    public static int[] convertImageToIntArray(BufferedImage image) {
        int[] tempRes = new int[image.getWidth() * image.getHeight() * 3];
        int index = 0;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                tempRes[index++] = color.getRed();
                tempRes[index++] = color.getGreen();
                tempRes[index++] = color.getBlue();
            }
        }

        return BinaryUtils.convertNumberArrayToBinary(tempRes);
    }

    /**
     * Converts int[] array of 0 and 1 to BufferedImage
     * @param array
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage convertIntArrayToImage(int[] array, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int index = 0;
        // 8 bits per color
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int[] redBits = new int[8];
                int[] greenBits = new int[8];
                int[] blueBits = new int[8];
                for (int i = 0; i < 8; i++) {
                    redBits[i] = array[index++];
                }
                for (int i = 0; i < 8; i++) {
                    greenBits[i] = array[index++];
                }
                for (int i = 0; i < 8; i++) {
                    blueBits[i] = array[index++];
                }
                int red = BinaryUtils.convertBitsToNumber(redBits);
                int green = BinaryUtils.convertBitsToNumber(greenBits);
                int blue = BinaryUtils.convertBitsToNumber(blueBits);
                Color color = new Color(red, green, blue);
                image.setRGB(x, y, color.getRGB());
            }
        }
        return image;
    }
}
