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

import java.util.Locale;

/**
 * Part of SpanEZ
 * Created by joragu on 1/1/2017.
 */
@SuppressWarnings("WeakerAccess")
public final class SpanEZ {
    private static final int RESOURCE_NOT_SET = -1;

    public static SpanEZ from(TextView target) {
        return new SpanEZ(target);
    }

    private TextView target;
    private String content;
    private SpannableString spannableContent;
    private Context context;
    private Resources resources;
    private int spanFlags = Spanned.SPAN_INCLUSIVE_INCLUSIVE;

    private SpanEZ(TextView target) {
        this.target = target;
        this.context = target.getContext();
        this.resources = target.getResources();
    }

    public SpanEZ withContent(String text) {
        content = text;
        spannableContent = new SpannableString(content);
        return this;
    }

    public SpanEZ withContent(@StringRes int textResId) {
        String text = resources.getString(textResId);
        return withContent(text);
    }

    public SpanEZ withFormattedContent(@StringRes int textResId, @NonNull Object... args) {
        String text = resources.getString(textResId, args);
        return withContent(text);
    }

    public SpanEZ inclusive() {
        spanFlags = Spanned.SPAN_INCLUSIVE_INCLUSIVE;
        return this;
    }

    public SpanEZ exclusive() {
        spanFlags = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
        return this;
    }

    public SpanEZ inclusiveExclusive() {
        spanFlags = Spanned.SPAN_INCLUSIVE_EXCLUSIVE;
        return this;
    }

    public SpanEZ exclusiveInclusive() {
        spanFlags = Spanned.SPAN_EXCLUSIVE_INCLUSIVE;
        return this;
    }

    public SpanEZ bold(@NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                bold(position, position + text.length());
            }
        }
        return this;
    }

    public SpanEZ bold(@IntRange(from = 0) int start, @IntRange(from = 0) int end) {
        StyleSpan bold = new StyleSpan(Typeface.BOLD);
        return addSpan(start, end, bold);
    }

    public SpanEZ italic(@NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                italic(position, position + text.length());
            }
        }
        return this;
    }

    public SpanEZ italic(@IntRange(from = 0) int start, @IntRange(from = 0) int end) {
        StyleSpan italic = new StyleSpan(Typeface.ITALIC);
        return addSpan(start, end, italic);
    }

    public SpanEZ underline(@NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                underline(position, position + text.length());
            }
        }
        return this;
    }

    public SpanEZ underline(@IntRange(from = 0) int start, @IntRange(from = 0) int end) {
        UnderlineSpan underline = new UnderlineSpan();
        return addSpan(start, end, underline);
    }

    public SpanEZ foregroundColor(@ColorRes int fgColorResId, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                foregroundColor(position, position + text.length(), fgColorResId);
            }
        }
        return this;
    }

    public SpanEZ foregroundColor(@IntRange(from = 0) int start, @IntRange(from = 0) int end, @ColorRes int fgColorResId) {
        final int fgColor = ContextCompat.getColor(context, fgColorResId);
        ForegroundColorSpan fgColorSpan = new ForegroundColorSpan(fgColor);
        return addSpan(start, end, fgColorSpan);
    }

    public SpanEZ backgroundColor(@ColorRes int bgColorResId, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                backgroundColor(position, position + text.length(), bgColorResId);
            }
        }
        return this;
    }

    public SpanEZ backgroundColor(@IntRange(from = 0) int start, @IntRange(from = 0) int end, @ColorRes int bgColorResId) {
        final int bgColor = ContextCompat.getColor(context, bgColorResId);
        BackgroundColorSpan bgColorSpan = new BackgroundColorSpan(bgColor);
        return addSpan(start, end, bgColorSpan);
    }

    public SpanEZ clickable(@NonNull OnSpanClickListener spanClick, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                clickable(position, position + text.length(), spanClick);
            }
        }
        return this;
    }

    public SpanEZ clickable(@IntRange(from = 0) int start, @IntRange(from = 0) int end, @NonNull OnSpanClickListener spanClick) {
        ClickableSpan clickSpan = new ClickableSpanEZ(spanClick, content.substring(start, end));
        target.setMovementMethod(new LinkMovementMethod());
        return addSpan(start, end, clickSpan);
    }

    public SpanEZ quote(@NonNull String... targetTexts) {
        return quote(RESOURCE_NOT_SET, targetTexts);
    }

    public SpanEZ quote(@ColorRes int quoteColorResId, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                quote(position, position + text.length(), quoteColorResId);
            }
        }
        return this;
    }

    public SpanEZ quote(@IntRange(from = 0) int start, @IntRange(from = 0) int end) {
        return quote(start, end, RESOURCE_NOT_SET);
    }

    public SpanEZ quote(@IntRange(from = 0) int start, @IntRange(from = 0) int end, @ColorRes int quoteColorResId) {
        QuoteSpan quoteSpan;
        if (quoteColorResId == RESOURCE_NOT_SET) {
            quoteSpan = new QuoteSpan();
        } else {
            final int quoteColor = ContextCompat.getColor(context, quoteColorResId);
            quoteSpan = new QuoteSpan(quoteColor);
        }
        return addSpan(start, end, quoteSpan);
    }

    public SpanEZ strikethrough(@NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                strikethrough(position, position + text.length());
            }
        }
        return this;
    }

    public SpanEZ strikethrough(@IntRange(from = 0) int start, @IntRange(from = 0) int end) {
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        return addSpan(start, end, strikethroughSpan);
    }

    public SpanEZ center(@NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                center(position, position + text.length());
            }
        }
        return this;
    }

    public SpanEZ center(@IntRange(from = 0) int start, @IntRange(from = 0) int end) {
        AlignmentSpan centerSpan = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER);
        return addSpan(start, end, centerSpan);
    }

    public SpanEZ opposite(@NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                opposite(position, position + text.length());
            }
        }
        return this;
    }

    public SpanEZ opposite(@IntRange(from = 0) int start, @IntRange(from = 0) int end) {
        AlignmentSpan oppositeSpan = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE);
        return addSpan(start, end, oppositeSpan);
    }

    public SpanEZ subscript(@NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                subscript(position, position + text.length());
            }
        }
        return this;
    }

    public SpanEZ subscript(@IntRange(from = 0) int start, @IntRange(from = 0) int end) {
        SubscriptSpan subscriptSpan = new SubscriptSpan();
        return addSpan(start, end, subscriptSpan);
    }

    public SpanEZ superscript(@NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                superscript(position, position + text.length());
            }
        }
        return this;
    }

    public SpanEZ superscript(@IntRange(from = 0) int start, @IntRange(from = 0) int end) {
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        return addSpan(start, end, superscriptSpan);
    }

    public SpanEZ link(@NonNull String url, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                link(position, position + text.length(), url);
            }
        }
        return this;
    }

    public SpanEZ link(@IntRange(from = 0) int start, @IntRange(from = 0) int end, @NonNull String url) {
        URLSpan urlSpan = new URLSpan(url);
        return addSpan(start, end, urlSpan);
    }

    public SpanEZ font(@NonNull @Font.Familiy String fontFamily, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                font(position, position + text.length(), fontFamily);
            }
        }
        return this;
    }

    public SpanEZ font(@IntRange(from = 0) int start, @IntRange(from = 0) int end, @NonNull @Font.Familiy String fontFamily) {
        TypefaceSpan urlSpan = new TypefaceSpan(fontFamily);
        return addSpan(start, end, urlSpan);
    }

    public SpanEZ style(@StyleRes int appearance, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                style(position, position + text.length(), appearance);
            }
        }
        return this;
    }

    public SpanEZ style(@IntRange(from = 0) int start, @IntRange(from = 0) int end, @StyleRes int appearance) {
        TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(context, appearance);
        return addSpan(start, end, textAppearanceSpan);
    }

    public SpanEZ locale(@NonNull Locale locale, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                locale(position, position + text.length(), locale);
            }
        }
        return this;
    }

    public SpanEZ locale(@IntRange(from = 0) int start, @IntRange(from = 0) int end, @NonNull Locale locale) {
        LocaleSpan localeSpan = new LocaleSpan(locale);
        return addSpan(start, end, localeSpan);
    }

    public SpanEZ scaleX(@FloatRange(from = 0.f) float proportion, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                scaleX(position, position + text.length(), proportion);
            }
        }
        return this;
    }

    public SpanEZ scaleX(@IntRange(from = 0) int start, @IntRange(from = 0) int end, @FloatRange(from = 0.f) float proportion) {
        ScaleXSpan scaleXSpan = new ScaleXSpan(proportion);
        return addSpan(start, end, scaleXSpan);
    }

    public SpanEZ relativeSize(@FloatRange(from = 0.f) float proportion, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                relativeSize(position, position + text.length(), proportion);
            }
        }
        return this;
    }

    public SpanEZ relativeSize(@IntRange(from = 0) int start, @IntRange(from = 0) int end, @FloatRange(from = 0.f) float proportion) {
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(proportion);
        return addSpan(start, end, relativeSizeSpan);
    }

    public SpanEZ absoluteSize(@IntRange(from = 1) int pixels, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                absoluteSize(position, position + text.length(), pixels);
            }
        }
        return this;
    }

    public SpanEZ absoluteSize(@IntRange(from = 0) int start, @IntRange(from = 0) int end, @IntRange(from = 1) int pixels) {
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(pixels);
        return addSpan(start, end, absoluteSizeSpan);
    }

    public SpanEZ absoluteSizeDP(@IntRange(from = 1) int dips, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                absoluteSize(position, position + text.length(), dips);
            }
        }
        return this;
    }

    public SpanEZ absoluteSizeDP(@IntRange(from = 0) int start, @IntRange(from = 0) int end, @IntRange(from = 1) int dips) {
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(dips, true);
        return addSpan(start, end, absoluteSizeSpan);
    }

    private SpanEZ addSpan(@IntRange(from = 0) int start, int end, @NonNull Object span) {
        validateRange(start, end);
        spannableContent.setSpan(span, start, end, spanFlags);
        return this;
    }

    private void validateRange(int start, int end) {
        if (start < 0) {
            throw new IllegalArgumentException("The start of the Span can't be less than 0");
        } else if (start > spannableContent.length()) {
            throw new IllegalArgumentException(
                    "The start of the Span can't be after the end of the content");
        } else if (end < start) {
            throw new IllegalArgumentException(
                    "The end of the Span can't be before the start of the Span");
        } else if (end > spannableContent.length()) {
            throw new IllegalArgumentException(
                    "The end of the Span can't be after the end of the content");
        }
    }

    public void apply() {
        target.setText(spannableContent);
    }

}
