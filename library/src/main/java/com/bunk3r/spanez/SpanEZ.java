package com.bunk3r.spanez;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LocaleSpan;
import android.text.style.QuoteSpan;
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

import com.bunk3r.spanez.listeners.OnSpanClickListener;
import com.bunk3r.spanez.locators.Locator;
import com.bunk3r.spanez.locators.Paragraph;
import com.bunk3r.spanez.locators.TargetRange;
import com.bunk3r.spanez.spans.ClickableSpanEZ;

import java.util.Locale;

/**
 * Part of SpanEZ
 * Created by joragu on 1/12/2017.
 */

public class SpanEZ implements ContentEZ, StyleEZ {
    private static final int RESOURCE_NOT_SET = -1;

    /**
     * This is the starting place, once the target has been selected you can apply all the changes
     * to that target.
     *
     * @param target The View were the string is located
     * @return an SpanEZ instance were you can apply all the different span styles
     */
    public static ContentEZ from(TextView target) {
        return new SpanEZ(target);
    }

    private final TextView target;
    private final Context context;
    private final Resources resources;

    private String content;
    private SpannableString spannableContent;
    private int spanFlags = Spanned.SPAN_INCLUSIVE_INCLUSIVE;

    private SpanEZ(@NonNull TextView targetView) {
        target = targetView;
        context = target.getContext();
        resources = target.getResources();
    }

    @Override
    public StyleEZ withCurrentContent() {
        CharSequence currentText = target.getText();
        if (currentText instanceof SpannableString) {
            spannableContent = (SpannableString) currentText;
            content = spannableContent.toString();
        } else {
            content = currentText.toString();
            spannableContent = new SpannableString(content);
        }
        return this;
    }

    @Override
    public StyleEZ withContent(String text) {
        content = text;
        spannableContent = new SpannableString(content);
        return this;
    }

    @Override
    public StyleEZ withContent(@StringRes int textResId) {
        String text = resources.getString(textResId);
        return withContent(text);
    }

    @Override
    public StyleEZ withFormattedContent(@StringRes int textResId, @NonNull Object... args) {
        String text = resources.getString(textResId, args);
        return withContent(text);
    }

    @Override
    public StyleEZ inclusive() {
        spanFlags = Spanned.SPAN_INCLUSIVE_INCLUSIVE;
        return this;
    }

    @Override
    public StyleEZ inclusiveExclusive() {
        spanFlags = Spanned.SPAN_INCLUSIVE_EXCLUSIVE;
        return this;
    }

    @Override
    public StyleEZ exclusiveInclusive() {
        spanFlags = Spanned.SPAN_EXCLUSIVE_INCLUSIVE;
        return this;
    }

    @Override
    public StyleEZ exclusive() {
        spanFlags = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
        return this;
    }

    @Override
    public StyleEZ style(@NonNull Locator locator, @STYLE int styles) {
        if (isFlagSet(styles, BOLD)) {
            bold(locator);
        }

        if (isFlagSet(styles, ITALIC)) {
            italic(locator);
        }

        if (isFlagSet(styles, UNDERLINE)) {
            underline(locator);
        }

        if (isFlagSet(styles, STRIKETHROUGH)) {
            strikethrough(locator);
        }

        if (isFlagSet(styles, SUBSCRIPT)) {
            subscript(locator);
        }

        if (isFlagSet(styles, SUPERSCRIPT)) {
            superscript(locator);
        }
        return this;
    }

    /**
     * Applies a {@code Bold} style to all the {@code Character} within the given range. Or throws
     * and {@code IndexOutOfBoundsException} if the range provided is outside the content bounds.
     *
     * @param locator range were the span will be applied to
     */
    private void bold(@NonNull Locator locator) {
        StyleSpan bold = new StyleSpan(Typeface.BOLD);
        addMultipleSpan(locator, bold);
    }

    /**
     * Applies an {@code Italic} style to all the {@code Character} within the given range. Or throws
     * and {@code IndexOutOfBoundsException} if the range provided is outside the content bounds.
     *
     * @param locator range were the span will be applied to
     */
    private void italic(@NonNull Locator locator) {
        StyleSpan italic = new StyleSpan(Typeface.ITALIC);
        addMultipleSpan(locator, italic);
    }

    /**
     * Applies a {@code Underline} style to all the {@code Character} within the given range. Or throws
     * and {@code IndexOutOfBoundsException} if the range provided is outside the content bounds.
     *
     * @param locator range were the span will be applied to
     */
    private void underline(@NonNull Locator locator) {
        UnderlineSpan underline = new UnderlineSpan();
        addMultipleSpan(locator, underline);
    }

    /**
     * Applies an {@code Strikethrough} style (Meaning that it crosses all text with a horizontal line)
     * to all the {@code Character} within the given range. Or throws and {@code IndexOutOfBoundsException}
     * if the range provided is outside the content bounds.
     *
     * @param locator range were the span will be applied to
     */
    private void strikethrough(@NonNull Locator locator) {
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        addMultipleSpan(locator, strikethroughSpan);
    }

    /**
     * Applies a {@code Subscript} style to all the {@code Character} within the given range. Or throws
     * and {@code IndexOutOfBoundsException} if the range provided is outside the content bounds.
     *
     * @param locator range were the span will be applied to
     */
    private void subscript(@NonNull Locator locator) {
        SubscriptSpan subscriptSpan = new SubscriptSpan();
        addMultipleSpan(locator, subscriptSpan);
    }

    /**
     * Applies a {@code Superscript} style to all the {@code Character} within the given range. Or throws
     * and {@code IndexOutOfBoundsException} if the range provided is outside the content bounds.
     *
     * @param locator range were the span will be applied to
     */
    private void superscript(@NonNull Locator locator) {
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        addMultipleSpan(locator, superscriptSpan);
    }

    @Override
    public StyleEZ clickable(@NonNull Locator locator, @NonNull OnSpanClickListener spanClick) {
        for (TargetRange targetRange : locator.locate(content)) {
            ClickableSpan clickSpan = ClickableSpanEZ.from(spanClick,
                                                           content.substring(targetRange.getStart(),
                                                                             targetRange.getEnd()));
            target.setMovementMethod(new LinkMovementMethod());
            addSpan(targetRange, clickSpan);
        }
        return this;
    }

    @Override
    public StyleEZ foregroundColor(@NonNull Locator locator, @ColorRes int fgColorResId) {
        final int fgColor = ContextCompat.getColor(context, fgColorResId);
        ForegroundColorSpan fgColorSpan = new ForegroundColorSpan(fgColor);
        addMultipleSpan(locator, fgColorSpan);
        return this;
    }

    @Override
    public StyleEZ backgroundColor(@NonNull Locator locator, @ColorRes int bgColorResId) {
        final int bgColor = ContextCompat.getColor(context, bgColorResId);
        BackgroundColorSpan bgColorSpan = new BackgroundColorSpan(bgColor);
        addMultipleSpan(locator, bgColorSpan);
        return this;
    }

    @Override
    public StyleEZ quote(@NonNull Paragraph paragraph) {
        return quote(paragraph, RESOURCE_NOT_SET);
    }

    @Override
    public StyleEZ quote(@NonNull Paragraph paragraph, @ColorRes int quoteColorResId) {
        final QuoteSpan quoteSpan;
        if (quoteColorResId == RESOURCE_NOT_SET) {
            quoteSpan = new QuoteSpan();
        } else {
            final int quoteColor = ContextCompat.getColor(context, quoteColorResId);
            quoteSpan = new QuoteSpan(quoteColor);
        }

        addMultipleSpan(paragraph, quoteSpan);
        return this;
    }

    @Override
    public StyleEZ alignCenter(@NonNull Paragraph paragraph) {
        AlignmentSpan centerSpan = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER);
        addMultipleSpan(paragraph, centerSpan);
        return this;
    }

    @Override
    public StyleEZ alignEnd(@NonNull Paragraph paragraph) {
        AlignmentSpan oppositeSpan = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE);
        addMultipleSpan(paragraph, oppositeSpan);
        return this;
    }

    @Override
    public StyleEZ alignStart(@NonNull Paragraph paragraph) {
        AlignmentSpan normalSpan = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL);
        addMultipleSpan(paragraph, normalSpan);
        return this;
    }

    @Override
    public StyleEZ link(@NonNull Locator locator, @NonNull String url) {
        URLSpan urlSpan = new URLSpan(url);
        addMultipleSpan(locator, urlSpan);
        return this;
    }

    @Override
    public StyleEZ font(@NonNull Locator locator, @NonNull @FontFamily String fontFamily) {
        TypefaceSpan typefaceSpan = new TypefaceSpan(fontFamily);
        addMultipleSpan(locator, typefaceSpan);
        return this;
    }

    @Override
    public StyleEZ appearance(@NonNull Locator locator, @StyleRes int appearance) {
        TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(context, appearance);
        addMultipleSpan(locator, textAppearanceSpan);
        return this;
    }

    @Override
    public StyleEZ locale(@NonNull Locator locator, @NonNull Locale locale) {
        LocaleSpan localeSpan = new LocaleSpan(locale);
        addMultipleSpan(locator, localeSpan);
        return this;
    }

    @Override
    public StyleEZ scaleX(@NonNull Locator locator, @FloatRange(from = 0.f) float proportion) {
        ScaleXSpan scaleXSpan = new ScaleXSpan(proportion);
        addMultipleSpan(locator, scaleXSpan);
        return this;
    }

    @Override
    public StyleEZ relativeSize(@NonNull Locator locator, @FloatRange(from = 0.f) float proportion) {
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(proportion);
        addMultipleSpan(locator, relativeSizeSpan);
        return this;
    }

    @Override
    public StyleEZ absoluteSize(@NonNull Locator locator, @IntRange(from = 1) int pixels) {
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(pixels);
        addMultipleSpan(locator, absoluteSizeSpan);
        return this;
    }

    @Override
    public StyleEZ absoluteSizeDP(@NonNull Locator locator, @IntRange(from = 1) int dips) {
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(dips, true);
        addMultipleSpan(locator, absoluteSizeSpan);
        return this;
    }

    @Override
    @UiThread
    public void apply() {
        target.setText(spannableContent);
    }

    /**
     * Applies the given {@code span} to the specified ranges
     *
     * @param locator the locator to be used to decide were to apply this span
     * @param span    the span to be applied
     */
    private void addMultipleSpan(@NonNull Locator locator, @NonNull Object span) {
        for (TargetRange targetRange : locator.locate(content)) {
            addSpan(targetRange, span);
        }
    }

    /**
     * Applies the given {@code span} to the specified range from
     *
     * @param targetRange the range were the span will be applied to
     * @param span        the span to be applied
     */
    private void addSpan(@NonNull TargetRange targetRange, @NonNull Object span) {
        final int start = targetRange.getStart();
        final int end = targetRange.getEnd();
        // Added 1 to the span, because it seems that internally it does exclusive range
        spannableContent.setSpan(span, start, end + 1, spanFlags);
    }


    private boolean isFlagSet(@STYLE int flag, int flagToVerify) {
        return (flag & flagToVerify) == flagToVerify;
    }

}
