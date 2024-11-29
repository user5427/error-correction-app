package org.KTKT.CodingPages;

public interface ESDStatus {
    /**
     * Used for status sending from ESD Thread to UI Thread
     * @param status
     */
    void setESDStatus(Double status, Long time);
}
