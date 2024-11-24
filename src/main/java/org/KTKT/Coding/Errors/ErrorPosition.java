package org.KTKT.Coding.Errors;

public class ErrorPosition {
    int position;

    /**
     * Error position in the vector
     * Used for displaying errors in table
     * @param position
     */
    public ErrorPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
