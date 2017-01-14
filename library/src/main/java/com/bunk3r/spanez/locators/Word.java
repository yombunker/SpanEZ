package com.bunk3r.spanez.locators;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Part of SpanEZ
 * Created by joragu on 1/12/2017.
 */

@SuppressWarnings("WeakerAccess")
public class Word implements Locator {
    private static final int NOT_FOUND = -1;

    public static Word findFirst(@NonNull String word) {
        return new Word(word, false);
    }

    public static Word findAll(@NonNull String word) {
        return new Word(word, true);
    }

    private final String word;
    private final boolean findAll;

    private Word(@NonNull String word, boolean findAll) {
        this.word = word;
        this.findAll = findAll;
    }

    @NonNull
    @Override
    public List<TargetRange> locate(@NonNull String content) {
        List<TargetRange> ranges = new ArrayList<>();
        if (word.isEmpty()) {
            return ranges;
        }

        int startingIndex = 0;
        int wordLength = word.length() - 1;

        do {
            int wordIndex = content.indexOf(word, startingIndex);
            if (wordIndex != NOT_FOUND) {
                ranges.add(TargetRange.from(wordIndex, wordIndex + wordLength));
            } else {
                break;
            }
            startingIndex = wordIndex + 1;
        } while (findAll);

        return ranges;
    }
}
