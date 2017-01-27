package com.bunk3r.spanez;

import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.ClickableSpan;
import android.text.style.LocaleSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.bunk3r.spanez.api.EZ;
import com.bunk3r.spanez.api.StyleEZ;
import com.bunk3r.spanez.callbacks.OnSpanClickListener;
import com.bunk3r.spanez.locators.Paragraph;
import com.bunk3r.spanez.locators.Range;
import com.bunk3r.spanez.models.TargetRange;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Part of SpanEZ
 * Created by Jorge Aguilar on 1/21/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class SpanEZTest {
    private static final int INTEGER_ARG = 1;
    @Mock
    private TextView mockTextView;

    @Mock
    private OnSpanClickListener spanClickListener;

    private Paragraph paragraph;
    private Range range;
    private StyleEZ spanBuilder;

    @Before
    public void setUp() throws Exception {
        spanBuilder = SpanEZ.setTextView(mockTextView)
                            .withContent("This is a dummy content");
        spanBuilder = spy(spanBuilder);

        int start = 1;
        int end = 5;
        range = Range.from(start, end);
        paragraph = Paragraph.number(1);
    }

    @Test
    public void bold_should_add_only_one_span() {
        spanBuilder.style(range, EZ.BOLD)
                   .apply();

        verify((SpanEZ) spanBuilder, times(1))
                .addSpan(isA(TargetRange.class), isA(StyleSpan.class));
    }

    @Test
    public void italic_should_add_only_one_span() {
        spanBuilder.style(range, EZ.ITALIC)
                   .apply();

        verify((SpanEZ) spanBuilder, times(1))
                .addSpan(isA(TargetRange.class), isA(StyleSpan.class));
    }

    @Test
    public void underline_should_add_only_one_span() {
        spanBuilder.style(range, EZ.UNDERLINE)
                   .apply();

        verify((SpanEZ) spanBuilder, times(1))
                .addSpan(isA(TargetRange.class), isA(UnderlineSpan.class));
    }

    @Test
    public void strikethrough_should_add_only_one_span() {
        spanBuilder.style(range, EZ.STRIKETHROUGH)
                   .apply();

        verify((SpanEZ) spanBuilder, times(1))
                .addSpan(isA(TargetRange.class), isA(StrikethroughSpan.class));
    }

    @Test
    public void subscript_should_add_only_one_span() {
        spanBuilder.style(range, EZ.SUBSCRIPT)
                   .apply();

        verify((SpanEZ) spanBuilder, times(1))
                .addSpan(isA(TargetRange.class), isA(SubscriptSpan.class));
    }

    @Test
    public void superscript_should_add_only_one_span() {
        spanBuilder.style(range, EZ.SUPERSCRIPT)
                   .apply();

        verify((SpanEZ) spanBuilder, times(1))
                .addSpan(isA(TargetRange.class), isA(SuperscriptSpan.class));
    }

    @Test
    public void link_should_add_only_one_span() {
        spanBuilder.link(range, "")
                   .apply();

        verify((SpanEZ) spanBuilder, times(1))
                .addSpan(isA(TargetRange.class), isA(URLSpan.class));
    }

    @Test
    public void font_should_add_only_one_span() {
        spanBuilder.font(range, EZ.MONOSPACE)
                   .apply();

        verify((SpanEZ) spanBuilder, times(1))
                .addSpan(isA(TargetRange.class), isA(TypefaceSpan.class));
    }

    @SuppressWarnings("ResourceType")
    @Test
    public void appearance_should_add_only_one_span() {
        spanBuilder.appearance(range, INTEGER_ARG)
                   .apply();

        verify((SpanEZ) spanBuilder, times(1))
                .addSpan(isA(TargetRange.class), isA(TextAppearanceSpan.class));
    }

    @Test
    public void locale_should_add_only_one_span() {
        spanBuilder.locale(range, Locale.US)
                   .apply();

        verify((SpanEZ) spanBuilder, times(1))
                .addSpan(isA(TargetRange.class), isA(LocaleSpan.class));
    }

    @Test
    public void scale_x_should_add_only_one_span() {
        spanBuilder.scaleX(range, INTEGER_ARG)
                   .apply();

        verify((SpanEZ) spanBuilder, times(1))
                .addSpan(isA(TargetRange.class), isA(ScaleXSpan.class));
    }

    @Test
    public void relative_size_should_add_only_one_span() {
        spanBuilder.relativeSize(range, INTEGER_ARG)
                   .apply();

        verify((SpanEZ) spanBuilder, times(1))
                .addSpan(isA(TargetRange.class), isA(RelativeSizeSpan.class));
    }

    @Test
    public void absolute_size_should_add_only_one_span() {
        spanBuilder.absoluteSize(range, INTEGER_ARG)
                   .apply();

        verify((SpanEZ) spanBuilder, times(1))
                .addSpan(isA(TargetRange.class), isA(AbsoluteSizeSpan.class));
    }

    @Test
    public void absolute_size_pixel_independent_should_add_only_one_span() {
        spanBuilder.absoluteSizeDP(range, INTEGER_ARG)
                   .apply();

        verify((SpanEZ) spanBuilder, times(1))
                .addSpan(isA(TargetRange.class), isA(AbsoluteSizeSpan.class));
    }

    @Test
    public void click_should_add_only_one_span() {
        spanBuilder.clickable(range, spanClickListener)
                   .apply();

        verify(mockTextView, times(1))
                .setMovementMethod(isA(LinkMovementMethod.class));

        verify((SpanEZ) spanBuilder, times(1))
                .addSpan(isA(TargetRange.class), isA(ClickableSpan.class));
    }

    @Test
    public void clicking_on_a_click_span_triggers_callback() {
        spanBuilder.clickable(range, spanClickListener)
                   .apply();

        ArgumentCaptor<ClickableSpan> captor = ArgumentCaptor.forClass(ClickableSpan.class);
        verify((SpanEZ) spanBuilder, times(1))
                .addSpan(isA(TargetRange.class), captor.capture());

        captor.getValue().onClick(mockTextView);

        verify(spanClickListener, only())
                .onSpanClick(anyString());
    }

    @Test
    public void align_start_should_add_only_one_span() {
        spanBuilder.alignStart(paragraph)
                   .apply();

        verify((SpanEZ) spanBuilder, times(1))
                .addSpan(isA(TargetRange.class), isA(AlignmentSpan.class));
    }

    @Test
    public void align_center_should_add_only_one_span() {
        spanBuilder.alignCenter(paragraph)
                   .apply();

        verify((SpanEZ) spanBuilder, times(1))
                .addSpan(isA(TargetRange.class), isA(AlignmentSpan.class));
    }

    @Test
    public void align_end_should_add_only_one_span() {
        spanBuilder.alignEnd(paragraph)
                   .apply();

        verify((SpanEZ) spanBuilder, times(1))
                .addSpan(isA(TargetRange.class), isA(AlignmentSpan.class));
    }
}