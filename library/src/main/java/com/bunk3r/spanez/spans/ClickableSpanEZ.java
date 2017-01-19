package com.bunk3r.spanez.spans;

import android.support.annotation.NonNull;
import android.text.style.ClickableSpan;
import android.view.View;

import com.bunk3r.spanez.listeners.OnSpanClickListener;

/**
 * Part of SpanEZ
 * Created by joragu on 1/1/2017.
 */

public class ClickableSpanEZ extends ClickableSpan {
    @NonNull
    public static ClickableSpan from(@NonNull OnSpanClickListener onSpanClickListener, @NonNull String content) {
        return new ClickableSpanEZ(onSpanClickListener, content);
    }

    private final OnSpanClickListener spanClickListener;
    private final String text;

    private ClickableSpanEZ(@NonNull OnSpanClickListener onSpanClickListener, @NonNull String content) {
        super();
        spanClickListener = onSpanClickListener;
        text = content;
    }

    @Override
    public void onClick(View widget) {
        spanClickListener.onSpanClick(text);
    }
}