package com.bunk3r.spanez.spans;

import android.support.annotation.NonNull;
import android.text.style.ClickableSpan;
import android.view.View;

import com.bunk3r.spanez.callbacks.OnSpanClickListener;

/**
 * Part of SpanEZ
 * Created by joragu on 1/1/2017.
 */

public final class ClickableSpanEZ extends ClickableSpan {
    private final OnSpanClickListener spanClickListener;
    private final String text;

    @NonNull
    public static ClickableSpan from(@NonNull OnSpanClickListener onSpanClickListener, @NonNull String content) {
        return new ClickableSpanEZ(onSpanClickListener, content);
    }

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