package com.bunk3r.spanez.locators;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Part of SpanEZ
 * Created by joragu on 1/12/2017.
 */

@SuppressWarnings("WeakerAccess")
public class Range implements Locator {
    public static Range from(@IntRange(from = 0) int start, @IntRange(from = 0) int end) {
        if (end < start) {
            throw new IllegalArgumentException("The end of the range shouldn't be before the start");
        }

        return new Range(start, end);
    }

    private final List<TargetRange> ranges = new ArrayList<>(1);

    private Range(int start, int end) {
        TargetRange range = TargetRange.from(start, end);
        ranges.add(range);
    }

    @NonNull
    @Override
    public List<TargetRange> locate(@NonNull String content) {
        return ranges;
    }
}
