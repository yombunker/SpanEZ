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

    private TextView target;
    private String content;
    private SpannableString spannableContent;
    private Context context;
    private Resources resources;
    private int spanFlags = Spanned.SPAN_INCLUSIVE_INCLUSIVE;

    private SpanEZ(@NonNull TextView targetView) {
        target = targetView;
        context = target.getContext();
        resources = target.getResources();
        content = target.getText().toString();
        spannableContent = new SpannableString(content);
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
        // Iterate trough all the target(s) found and check which flags are set
        for (TargetRange targetRange : locator.locate(content)) {
            if (isFlagSet(styles, BOLD)) {
                bold(targetRange);
            }

            if (isFlagSet(styles, ITALIC)) {
                italic(targetRange);
            }

            if (isFlagSet(styles, UNDERLINE)) {
                underline(targetRange);
            }

            if (isFlagSet(styles, STRIKETHROUGH)) {
                strikethrough(targetRange);
            }

            if (isFlagSet(styles, SUBSCRIPT)) {
                subscript(targetRange);
            }

            if (isFlagSet(styles, SUPERSCRIPT)) {
                superscript(targetRange);
            }
        }
        return this;
    }

    /**
     * Applies a {@code Bold} style to all the {@code Character} within the given range. Or throws
     * and {@code IndexOutOfBoundsException} if the range provided is outside the content bounds.
     *
     * @param targetRange range were the span will be applied to
     */
    private void bold(@NonNull TargetRange targetRange) {
        StyleSpan bold = new StyleSpan(Typeface.BOLD);
        addSpan(targetRange, bold);
    }

    /**
     * Applies an {@code Italic} style to all the {@code Character} within the given range. Or throws
     * and {@code IndexOutOfBoundsException} if the range provided is outside the content bounds.
     *
     * @param targetRange range were the span will be applied to
     */
    private void italic(@NonNull TargetRange targetRange) {
        StyleSpan italic = new StyleSpan(Typeface.ITALIC);
        addSpan(targetRange, italic);
    }

    /**
     * Applies a {@code Underline} style to all the {@code Character} within the given range. Or throws
     * and {@code IndexOutOfBoundsException} if the range provided is outside the content bounds.
     *
     * @param targetRange range were the span will be applied to
     */
    private void underline(@NonNull TargetRange targetRange) {
        UnderlineSpan underline = new UnderlineSpan();
        addSpan(targetRange, underline);
    }

    /**
     * Applies an {@code Strikethrough} style (Meaning that it crosses all text with a horizontal line)
     * to all the {@code Character} within the given range. Or throws and {@code IndexOutOfBoundsException}
     * if the range provided is outside the content bounds.
     *
     * @param targetRange range were the span will be applied to
     */
    private void strikethrough(@NonNull TargetRange targetRange) {
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        addSpan(targetRange, strikethroughSpan);
    }

    /**
     * Applies a {@code Subscript} style to all the {@code Character} within the given range. Or throws
     * and {@code IndexOutOfBoundsException} if the range provided is outside the content bounds.
     *
     * @param targetRange range were the span will be applied to
     */
    private void subscript(@NonNull TargetRange targetRange) {
        SubscriptSpan subscriptSpan = new SubscriptSpan();
        addSpan(targetRange, subscriptSpan);
    }

    /**
     * Applies a {@code Superscript} style to all the {@code Character} within the given range. Or throws
     * and {@code IndexOutOfBoundsException} if the range provided is outside the content bounds.
     *
     * @param targetRange range were the span will be applied to
     */
    private void superscript(@NonNull TargetRange targetRange) {
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        addSpan(targetRange, superscriptSpan);
    }

    @Override
    public StyleEZ foregroundColor(@NonNull Locator locator, @ColorRes int fgColorResId) {
        for (TargetRange targetRange : locator.locate(content)) {
            final int fgColor = ContextCompat.getColor(context, fgColorResId);
            ForegroundColorSpan fgColorSpan = new ForegroundColorSpan(fgColor);
            addSpan(targetRange, fgColorSpan);
        }
        return this;
    }

    @Override
    public StyleEZ backgroundColor(@NonNull Locator locator, @ColorRes int bgColorResId) {
        for (TargetRange targetRange : locator.locate(content)) {
            final int bgColor = ContextCompat.getColor(context, bgColorResId);
            BackgroundColorSpan bgColorSpan = new BackgroundColorSpan(bgColor);
            addSpan(targetRange, bgColorSpan);
        }

        return this;
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
    public StyleEZ quote(@NonNull Locator locator) {
        return quote(locator, RESOURCE_NOT_SET);
    }

    @Override
    public StyleEZ quote(@NonNull Locator locator, @ColorRes int quoteColorResId) {
        final QuoteSpan quoteSpan;
        if (quoteColorResId == RESOURCE_NOT_SET) {
            quoteSpan = new QuoteSpan();
        } else {
            final int quoteColor = ContextCompat.getColor(context, quoteColorResId);
            quoteSpan = new QuoteSpan(quoteColor);
        }

        for (TargetRange targetRange : locator.locate(content)) {
            addSpan(targetRange, quoteSpan);
        }

        return this;
    }

    @Override
    public StyleEZ alignCenter(@NonNull Locator locator) {
        for (TargetRange targetRange : locator.locate(content)) {
            AlignmentSpan centerSpan = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER);
            addSpan(targetRange, centerSpan);
        }

        return this;
    }

    @Override
    public StyleEZ alignEnd(@NonNull Locator locator) {
        for (TargetRange targetRange : locator.locate(content)) {
            AlignmentSpan oppositeSpan = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE);
            addSpan(targetRange, oppositeSpan);
        }

        return this;
    }

    @Override
    public StyleEZ alignStart(@NonNull Locator locator) {
        for (TargetRange targetRange : locator.locate(content)) {
            AlignmentSpan oppositeSpan = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL);
            addSpan(targetRange, oppositeSpan);
        }

        return this;
    }

    @Override
    public StyleEZ link(@NonNull Locator locator, @NonNull String url) {
        for (TargetRange targetRange : locator.locate(content)) {
            URLSpan urlSpan = new URLSpan(url);
            addSpan(targetRange, urlSpan);
        }

        return this;
    }

    @Override
    public StyleEZ font(@NonNull Locator locator, @NonNull @FontFamily String fontFamily) {
        for (TargetRange targetRange : locator.locate(content)) {
            TypefaceSpan urlSpan = new TypefaceSpan(fontFamily);
            addSpan(targetRange, urlSpan);
        }

        return this;
    }

    @Override
    public StyleEZ appearance(@NonNull Locator locator, @StyleRes int appearance) {
        for (TargetRange targetRange : locator.locate(content)) {
            TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(context, appearance);
            addSpan(targetRange, textAppearanceSpan);
        }

        return this;
    }

    @Override
    public StyleEZ locale(@NonNull Locator locator, @NonNull Locale locale) {
        for (TargetRange targetRange : locator.locate(content)) {
            LocaleSpan localeSpan = new LocaleSpan(locale);
            addSpan(targetRange, localeSpan);
        }

        return this;
    }

    @Override
    public StyleEZ scaleX(@NonNull Locator locator, @FloatRange(from = 0.f) float proportion) {
        for (TargetRange targetRange : locator.locate(content)) {
            ScaleXSpan scaleXSpan = new ScaleXSpan(proportion);
            addSpan(targetRange, scaleXSpan);
        }

        return this;
    }

    @Override
    public StyleEZ relativeSize(@NonNull Locator locator, @FloatRange(from = 0.f) float proportion) {
        for (TargetRange targetRange : locator.locate(content)) {
            RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(proportion);
            addSpan(targetRange, relativeSizeSpan);
        }

        return this;
    }

    @Override
    public StyleEZ absoluteSize(@NonNull Locator locator, @IntRange(from = 1) int pixels) {
        for (TargetRange targetRange : locator.locate(content)) {
            AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(pixels);
            addSpan(targetRange, absoluteSizeSpan);
        }

        return this;
    }

    @Override
    public StyleEZ absoluteSizeDP(@NonNull Locator locator, @IntRange(from = 1) int dips) {
        for (TargetRange targetRange : locator.locate(content)) {
            AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(dips, true);
            addSpan(targetRange, absoluteSizeSpan);
        }

        return this;
    }

    @Override
    public void apply() {
        target.setText(spannableContent);
    }

    /**
     * Applies the given {@code span} to the specified range from {@code start} to {@code end}
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
