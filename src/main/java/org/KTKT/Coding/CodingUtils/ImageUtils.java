package org.KTKT.Coding.CodingUtils;

import java.awt.image.BufferedImage;

public class ImageUtils {

    // Convert .BMP image to int[] array of 0 and 1. Do not include the header of the image.
    public static int[] convertImageToIntArray(BufferedImage image) {
        int[] result = new int[image.getWidth() * image.getHeight()];
        int index = 0;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                result[index++] = image.getRGB(x, y) == -1 ? 0 : 1;
            }
        }
        return result;
    }

    public static BufferedImage convertIntArrayToImage(int[] array, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                image.setRGB(x, y, array[index++] == 0 ? -1 : 0);
            }
        }
        return image;
    }
}
