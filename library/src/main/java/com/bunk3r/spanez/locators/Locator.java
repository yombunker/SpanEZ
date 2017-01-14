package com.bunk3r.spanez.locators;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Part of SpanEZ
 * Created by joragu on 1/12/2017.
 */

public interface Locator {
    /**
     * Returns a list of ranges where the target(s) were located
     *
     * @param content the content were to look for the target(s)
     * @return a list of ranges were the target was found, will be empty if not found
     */
    @NonNull
    List<TargetRange> locate(@NonNull String content);
}
