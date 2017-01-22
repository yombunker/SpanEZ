package com.bunk3r.spanez.locators;

import android.support.annotation.NonNull;

import com.bunk3r.spanez.api.Locator;
import com.bunk3r.spanez.models.TargetRange;

import java.util.ArrayList;
import java.util.List;

/**
 * Part of SpanEZ
 * Created by joragu on 1/12/2017.
 */

@SuppressWarnings("WeakerAccess")
public final class Word implements Locator {
    private static final int NOT_FOUND = -1;

    private final String snippet;
    private final boolean findAllOccurrences;

    public static Word findFirst(@NonNull String word) {
        return new Word(word, false);
    }

    public static Word findAll(@NonNull String word) {
        return new Word(word, true);
    }

    private Word(@NonNull String snippet, boolean findAllOccurrences) {
        this.snippet = snippet;
        this.findAllOccurrences = findAllOccurrences;
    }

    @NonNull
    @Override
    public List<TargetRange> locate(@NonNull String content) {
        List<TargetRange> ranges = new ArrayList<>();
        if (snippet.isEmpty()) {
            return ranges;
        }

        int startingIndex = 0;
        int wordLength = snippet.length() - 1;

        do {
            int wordIndex = content.indexOf(snippet, startingIndex);
            if (wordIndex != NOT_FOUND) {
                ranges.add(TargetRange.from(wordIndex, wordIndex + wordLength));
            } else {
                break;
            }
            startingIndex = wordIndex + 1;
        } while (findAllOccurrences);

        return ranges;
    }
}
