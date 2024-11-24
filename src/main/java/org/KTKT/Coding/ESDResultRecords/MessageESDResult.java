package org.KTKT.Coding.ESDResultRecords;

/**
 * Used for passing data to the UI
 */
public class MessageESDResult {
    public String withDecoding;
    public String withoutDecoding;

    public MessageESDResult(String s, String s1) {
        withDecoding = s;
        withoutDecoding = s1;
    }
}
