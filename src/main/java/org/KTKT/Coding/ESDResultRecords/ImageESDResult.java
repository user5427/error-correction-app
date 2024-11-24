package org.KTKT.Coding.ESDResultRecords;

import java.awt.image.BufferedImage;

/**
 * Used for passing data to the UI
 * @param noCodeImage
 * @param decodedImage
 */
public record ImageESDResult(BufferedImage noCodeImage, BufferedImage decodedImage) {
}