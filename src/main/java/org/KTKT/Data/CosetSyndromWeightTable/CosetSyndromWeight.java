package org.KTKT.Data.CosetSyndromWeightTable;

public class CosetSyndromWeight {
    private String coset;
    private String syndrom;
    private int weight;

    public CosetSyndromWeight() {
    }

    public CosetSyndromWeight(String coset, String syndrom, int weight) {
        this.coset = coset;
        this.syndrom = syndrom;
        this.weight = weight;
    }

    public String getCoset() {
        return coset;
    }

    public String getSyndrom() {
        return syndrom;
    }

    public int getWeight() {
        return weight;
    }

    public void setCoset(String coset) {
        this.coset = coset;
    }

    public void setSyndrom(String syndrom) {
        this.syndrom = syndrom;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


}
