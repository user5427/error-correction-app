package org.KTKT.Coding.CodingUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtils {

    // Convert .BMP image to int[] array of 0 and 1. Do not include the header of the image.
    public static int[] convertImageToIntArray(BufferedImage image) {
        int[] tempRes = new int[image.getWidth() * image.getHeight() * 3];
        int index = 0;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                byte red = (byte) color.getRed();
                byte green = (byte) color.getGreen();
                byte blue = (byte) color.getBlue();

                tempRes[index++] = red;
                tempRes[index++] = green;
                tempRes[index++] = blue;
            }
        }

        StringBuilder binaryString = new StringBuilder();
        for (int b : tempRes) {
            String binary = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            binaryString.append(binary);
        }


        int[] result = new int[binaryString.length()];
        for (int i = 0; i < binaryString.length(); i++) {
            result[i] = binaryString.charAt(i) == '0' ? 0 : 1;
        }

        return result;
    }

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
                int red = convertBitsToByte(redBits);
                int green = convertBitsToByte(greenBits);
                int blue = convertBitsToByte(blueBits);
                Color color = new Color(red, green, blue);
                image.setRGB(x, y, color.getRGB());
            }
        }
        return image;
    }

    private static int convertBitsToByte(int[] redBits) {
        int result = 0;
        for (int i = 0; i < 8; i++) {
            result |= ((redBits[i] & 1) << (7 - i));  // Shift each bit to its correct position
        }

        return result;
    }
}
