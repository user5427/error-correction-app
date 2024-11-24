package org.KTKT.Data.CosetSyndromWeightTable;

import java.util.Arrays;

public class CosetSyndromWeight {
    private int[] coset;
    private int[] syndrom;
    private int weight;

    private String stringCosset;
    private String stringSyndrom;
    private String stringWeight;

    public CosetSyndromWeight() {
    }

    /**
     * Constructor
     * @param coset
     * @param syndrom
     */
    public CosetSyndromWeight(int[] coset, int[] syndrom) {
        this.coset = coset;
        this.syndrom = syndrom;
        int digCount = 0;
        for (int j : coset) {
            if (j == 1) {
                digCount++;
            }
        }
        this.weight = digCount;
    }

    public int[] getCoset() {
        return coset;
    }

    public int[] getSyndrom() {
        return syndrom;
    }

    public int getWeight() {
        return weight;
    }


    public String getStringCosset() {
        StringBuilder stringified = new StringBuilder();
        for (int i = 0; i < coset.length; i++) {
            stringified.append(coset[i]);
        }
        return stringCosset = stringified.toString();
    }

    public String getStringSyndrom() {
        StringBuilder stringified = new StringBuilder();
        for (int i = 0; i < syndrom.length; i++) {
            stringified.append(syndrom[i]);
        }
        return stringSyndrom = stringified.toString();
    }

    public String getStringWeight() {
        return stringWeight = String.valueOf(weight);
    }


}
