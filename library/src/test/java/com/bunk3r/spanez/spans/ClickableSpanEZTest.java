package com.bunk3r.spanez.spans;

import android.text.style.ClickableSpan;

import com.bunk3r.spanez.listeners.OnSpanClickListener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Part of SpanEZ
 * Created by joragu on 1/17/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ClickableSpanEZTest {
    private static final String FAKE_CONTENT = "Dummy Content";

    @Mock
    private OnSpanClickListener mockListener;

    @Test
    public void clicking_the_span_trigger_the_callback() {
        ClickableSpan clickableSpanEZ = ClickableSpanEZ.from(mockListener, FAKE_CONTENT);

        clickableSpanEZ.onClick(null);
        verify(mockListener, times(1)).onSpanClick(FAKE_CONTENT);
    }
}