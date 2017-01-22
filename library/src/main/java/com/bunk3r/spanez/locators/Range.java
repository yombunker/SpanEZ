package com.bunk3r.spanez.locators;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.bunk3r.spanez.api.Locator;
import com.bunk3r.spanez.models.TargetRange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Part of SpanEZ
 * Created by joragu on 1/12/2017.
 */

@SuppressWarnings("WeakerAccess")
public final class Range implements Locator {
    private final TargetRange targetRange;
    private final List<TargetRange> ranges = new ArrayList<>(1);
    private final List<TargetRange> empty = Collections.emptyList();

    public static Range from(@IntRange(from = 0) int start, @IntRange(from = 0) int end) {
        if (end < start) {
            throw new IllegalArgumentException("The end of the targetRange shouldn't be before the start");
        }

        return new Range(start, end);
    }

    private Range(int start, int end) {
        targetRange = TargetRange.from(start, end);
        ranges.add(targetRange);
    }

    @NonNull
    @Override
    public List<TargetRange> locate(@NonNull String content) {
        final int contentLength = content.length();
        return (targetRange.getStart() < contentLength && targetRange.getEnd() < contentLength)
                ? ranges
                : empty;
    }
}
