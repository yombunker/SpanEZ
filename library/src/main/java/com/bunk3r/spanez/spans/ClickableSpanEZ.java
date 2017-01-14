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
    public static ClickableSpan from(OnSpanClickListener onSpanClickListener, @NonNull String content) {
        return new ClickableSpanEZ(onSpanClickListener, content);
    }

    private OnSpanClickListener spanClickListener;
    private String text;

    private ClickableSpanEZ(@NonNull OnSpanClickListener onSpanClickListener, @NonNull String content) {
        spanClickListener = onSpanClickListener;
        text = content;
    }

    @Override
    public void onClick(View widget) {
        spanClickListener.onSpanClick(text);
    }
}