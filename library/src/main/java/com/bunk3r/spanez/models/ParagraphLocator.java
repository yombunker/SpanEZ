package com.bunk3r.spanez.models;

import android.support.annotation.NonNull;

import com.bunk3r.spanez.api.Locator;

import java.util.ArrayList;
import java.util.List;

/**
 * Part of SpanEZ
 * Created by joragu on 1/12/2017.
 * <p>
 * This is just to specify that this locator should return a paragraph
 */
@SuppressWarnings("WeakerAccess")
public abstract class ParagraphLocator implements Locator {
    private static final int NOT_FOUND = -1;

    /**
     * Splits the content by new line character (\n), any empty paragraph is ignored
     *
     * @param content the full content to split into paragraphs
     * @return a list of {@code P} that contains all the paragraphs
     */
    @NonNull
    protected List<P> splitIntoParagraphs(@NonNull String content) {
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
}
