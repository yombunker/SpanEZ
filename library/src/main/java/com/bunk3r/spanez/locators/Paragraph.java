package com.bunk3r.spanez.locators;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Part of SpanEZ
 * Created by joragu on 1/12/2017.
 */
@SuppressWarnings({"WeakerAccess",
                   "unused"})
public final class Paragraph implements Locator {
    private static final int NOT_FOUND = -1;

    public static Paragraph number(@IntRange(from = 1) int paragraphNumber) {
        return new Paragraph(paragraphNumber);
    }

    public static Paragraph containing(@NonNull String excerpt) {
        return new Paragraph(excerpt);
    }

    private final boolean byNumber;
    private int paragraphNumber;
    private String excerpt;

    private Paragraph(@IntRange(from = 1) int paragraphNumber) {
        this.byNumber = true;
        this.paragraphNumber = paragraphNumber;
    }

    private Paragraph(@NonNull String excerpt) {
        this.byNumber = false;
        this.excerpt = excerpt;
    }

    @NonNull
    @Override
    public List<TargetRange> locate(@NonNull String content) {
        List<P> paragraphs = splitParagraphs(content);
        return byNumber ? locateByNumber(paragraphs) : locateByContent(paragraphs);
    }

    @NonNull
    private List<TargetRange> locateByNumber(@NonNull List<P> paragraphs) {
        List<TargetRange> ranges = new ArrayList<>(1);
        if (paragraphs.size() >= paragraphNumber) {
            P paragraph = paragraphs.get(paragraphNumber - 1);
            ranges.add(TargetRange.from(paragraph.startIndex, paragraph.endIndex));
        }
        return ranges;
    }

    @NonNull
    private List<TargetRange> locateByContent(@NonNull List<P> paragraphs) {
        List<TargetRange> ranges = new ArrayList<>();
        if (excerpt.isEmpty()) {
            return ranges;
        }

        for (P paragraph : paragraphs) {
            if (paragraph.content.contains(excerpt)) {
                ranges.add(TargetRange.from(paragraph.startIndex, paragraph.endIndex));
            }
        }
        return ranges;
    }

    /**
     * Splits the content by new line character (\n), any empty paragraph is ignored
     *
     * @param content the full content to split into paragraphs
     * @return a list of {@code P} that contains all the paragraphs
     */
    @NonNull
    private List<P> splitParagraphs(@NonNull String content) {
        List<P> paragraphs = new ArrayList<>();
        int startPosition = 0;
        int newLinePosition;

        do {
            newLinePosition = content.indexOf('\n', startPosition);
            int paragraphEnd = newLinePosition != NOT_FOUND ? newLinePosition : content.length();
            if (paragraphEnd - startPosition > 0) {
                P paragraph = new P(content, startPosition, paragraphEnd);
                paragraphs.add(paragraph);
            }
            startPosition = newLinePosition + 1;
        } while (newLinePosition != NOT_FOUND);

        return paragraphs;
    }

    private static class P {
        final String content;
        final int startIndex;
        final int endIndex;

        P(String fullContent, int startIndex, int endIndex) {
            this.content = fullContent.substring(startIndex, endIndex);
            this.startIndex = startIndex;
            this.endIndex = fullContent.length() != endIndex ? endIndex : endIndex - 1;
        }
    }
}
