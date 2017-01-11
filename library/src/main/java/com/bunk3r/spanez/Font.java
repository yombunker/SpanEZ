package com.bunk3r.spanez;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Part of SpanEZ
 * Created by joragu on 1/2/2017.
 */

@SuppressWarnings("WeakerAccess")
public final class Font {
    public static final String MONOSPACE = "monospace";
    public static final String SERIF = "serif";
    public static final String SANS_SERIF = "sans-serif";
    public static final String SANS_SERIF_LIGHT = "sans-serif-light";
    public static final String SANS_SERIF_CONDENSED = "sans-serif-condensed";


    @Retention(SOURCE)
    @StringDef(value = {MONOSPACE,
                        SERIF,
                        SANS_SERIF,
                        SANS_SERIF_LIGHT,
                        SANS_SERIF_CONDENSED})
    public @interface Family {}

}
