package org.KTKT.Data.SeamlessWeightVectors;

import java.util.ArrayList;
import java.util.List;

public class SeamlessWeightVectors {

    private static int[] mapToSeamlessWeightVector(int[] vector) {
        int[] myVector = new int[vector.length];
        System.arraycopy(vector, 0, myVector, 0, vector.length);

        int nonZeroCount = 0;
        for (int i = 0; i < myVector.length; i++) {
            if (myVector[i] != 0) {
                if (myVector[i] != 1) {
                    throw new IllegalArgumentException("Vector must contain only 0 and 1");
                }
                nonZeroCount++;
            }
        }

        if (nonZeroCount == 0) {
            throw new IllegalArgumentException("Vector must contain at least one 1");
        }

        for (int i = 0; i < myVector.length; i++) {
            if (myVector[i] != 0){
                myVector[i] = nonZeroCount;
                nonZeroCount--;
            }
        }

        return myVector;
    }

    private static int[] mapToBinaryVector(int[] vector) {
        int[] binaryVector = new int[vector.length];
        for (int i = 0; i < vector.length; i++) {
            if (vector[i] == 0) {
                binaryVector[i] = 0;
            } else {
                binaryVector[i] = 1;
            }
        }

        return binaryVector;
    }

    /**
     * my algorithm:
     * we have a vector of length k
     * each digit has its special number n which is written in the array as number n
     * each digit can move to k-n+1 place
     * rule below is applied from left to right of the array:
        * check if array is at maximum. if it is, throw exception
        * when digit is at k-n+1 place:
             * it increments n+1 digits place if it has n+1 neighbour digit
             * if a digit does not have n+1 neighbour digit, it creates the neighbour digit at start position of 0
            * the digit moves to the place of its neighbour digit n+1 right side and its children are moved to the right
        * if there are no digits at position k-n+1 then increment n = 1 if possible
     */

    public static int[] generateSeamlessWeightVector(int[] vector) {
        if (vector.length < 2) {
            throw new IllegalArgumentException("Vector length must be at least 2");
        }

        int[] myVector = mapToSeamlessWeightVector(vector);
        boolean changed = false;

        for (int i = 1; i < myVector.length; i++) {
            int n = myVector[i];
            if (myVector.length - n == i) {
                // try to find n+1 neighbour
                int neighbourIndex = -1;
                for (int j = 0; j < i; j++) {
                    if (myVector[j] == n + 1) {
                        neighbourIndex = j;
                        break;
                    }
                }

                if (neighbourIndex == -1) {
                    // create neighbour
                    myVector[0] = n+1;
                    neighbourIndex = 0;
                } else {
                    // increment neighbour
                    if (myVector[neighbourIndex+1] != 0){
                        break;
                    }

                    int temp = myVector[neighbourIndex];
                    myVector[neighbourIndex] = 0;
                    myVector[neighbourIndex+1] = temp;
                    neighbourIndex++;
                }

                // move to neighbour
                // get all numbers higher than n+1
                List<Integer> lowerNumbers = new ArrayList<>();
                for (int j = neighbourIndex+1; j < myVector.length; j++) {
                    if (myVector[j] < n+1 && myVector[j] != 0) {
                        lowerNumbers.add(myVector[j]);
                    }
                }

                // clear everything after neighbourIndex
                for (int j = neighbourIndex+1; j < myVector.length; j++) {
                    myVector[j] = 0;
                }
                // move to neighbour
                for (int j = 0; j < lowerNumbers.size(); j++) {
                    myVector[neighbourIndex+j+1] = lowerNumbers.get(j);
                }

                changed = true;
                break;
            }
        }

        // increment
        if (!changed) {
            for (int i = 0; i < myVector.length; i++) {
                if (myVector[i] == 1 && i != myVector.length-1) {
                    myVector[i] = 0;
                    myVector[i+1] = 1;
                    changed = true;
                    break;
                }
            }
        }

        if (!changed) {
            throw new IllegalArgumentException("Vector is already at maximum");
        }

        return mapToBinaryVector(myVector);
    }
}
