package com.bunk3r.spanez.api;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Part of SpanEZ
 * Created by Jorge Aguilar on 1/20/2017.
 */

@SuppressWarnings("WeakerAccess")
public final class EZ {
    public static final int BOLD = 1;
    public static final int ITALIC = 1 << 1;
    public static final int UNDERLINE = 1 << 2;
    public static final int STRIKETHROUGH = 1 << 3;
    public static final int SUBSCRIPT = 1 << 4;
    public static final int SUPERSCRIPT = 1 << 5;

    @Retention(SOURCE)
    @IntDef(flag = true, value = {BOLD,
                                  ITALIC,
                                  UNDERLINE,
                                  STRIKETHROUGH,
                                  SUBSCRIPT,
                                  SUPERSCRIPT})
    public @interface STYLE {}

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
    public @interface FontFamily {}
}
