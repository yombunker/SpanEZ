package com.bunk3r.spanez.models;

/**
 * Part of SpanEZ
 * Created by Jorge Aguilar on 1/21/2017.
 *
 * This defines the content of a Paragraph
 */
public class P {
    private final String content;
    private final int startIndex;
    private final int endIndex;

    P(String fullContent, int startIndex, int endIndex) {
        this.content = fullContent.substring(startIndex, endIndex);
        this.startIndex = startIndex;
        this.endIndex = fullContent.length() != endIndex ? endIndex : endIndex - 1;
    }

    public String getContent() {
        return content;
    }

    public int getStart() {
        return startIndex;
    }

    public int getEnd() {
        return endIndex;
    }
}
