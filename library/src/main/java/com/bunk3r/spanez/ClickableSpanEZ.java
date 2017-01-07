package com.bunk3r.spanez;

import android.support.annotation.NonNull;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Part of SpanEZ
 * Created by joragu on 1/1/2017.
 */

class ClickableSpanEZ extends ClickableSpan {
    private OnSpanClickListener spanClickListener;
    private String text;

    ClickableSpanEZ(@NonNull OnSpanClickListener onSpanClickListener, @NonNull String content) {
        spanClickListener = onSpanClickListener;
        text = content;
    }

    @Override
    public void onClick(View widget) {
        spanClickListener.onSpanClick(text);
    }
}