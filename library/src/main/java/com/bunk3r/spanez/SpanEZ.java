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
@SuppressWarnings(value = {"unused",
                           "WeakerAccess"})
public final class SpanEZ {
    private static final int RESOURCE_NOT_SET = -1;

    /**
     * This is the starting place, once the target has been selected you can apply all the changes
     * to that target.
     *
     * @param target The View were the string is located
     * @return an SpanEZ instance were you can apply all the different span styles
     */
    public static SpanEZ from(TextView target) {
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

    /**
     * If you want to set the starting content of the {@code target} from a {@code String}
     *
     * @param text the text that will be set as the content of the (@code target)
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ withContent(String text) {
        content = text;
        spannableContent = new SpannableString(content);
        return this;
    }

    /**
     * If you want to set the starting content of the {@code target} from a String resource id
     *
     * @param textResId the {@code R.string.*} id of the string that will be used
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ withContent(@StringRes int textResId) {
        String text = resources.getString(textResId);
        return withContent(text);
    }

    /**
     * If you want to set the starting content of the {@code target} from a String resource id, that
     * has format, and you need to pass down the arguments for that format
     *
     * @param textResId the {@code R.string.*} id of the string that will be used
     * @param args      the arguments that will be pass to the format of the {@code String}
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ withFormattedContent(@StringRes int textResId, @NonNull Object... args) {
        String text = resources.getString(textResId, args);
        return withContent(text);
    }

    /**
     * Every call after this will set the {@code Span} to be {@code Inclusive} in both sides, this
     * means that if text is added within that range the new text will keep the same format.
     *
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ inclusive() {
        spanFlags = Spanned.SPAN_INCLUSIVE_INCLUSIVE;
        return this;
    }

    /**
     * Every call after this will set the {@code Span} to be {@code Exclusive} in both sides, this
     * means that if text is added within that range, excluding the last character the new
     * text will keep the same format.
     *
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ inclusiveExclusive() {
        spanFlags = Spanned.SPAN_INCLUSIVE_EXCLUSIVE;
        return this;
    }

    /**
     * Every call after this will set the {@code Span} to be {@code Exclusive} in both sides, this
     * means that if text is added within that range, excluding the first character the new
     * text will keep the same format.
     *
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ exclusiveInclusive() {
        spanFlags = Spanned.SPAN_EXCLUSIVE_INCLUSIVE;
        return this;
    }

    /**
     * Every call after this will set the {@code Span} to be {@code Exclusive} in both sides, this
     * means that if text is added within that range, excluding the first and last character the new
     * text will keep the same format.
     *
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ exclusive() {
        spanFlags = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
        return this;
    }

    /**
     * Applies a {@code Bold} style to all the {@code String} in {@code targetTexts} that exist in
     * the {@code target} content.
     *
     * @param targetTexts all the {@code String} that are to be modified
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ bold(@NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                bold(position, position + text.length());
            }
        }
        return this;
    }

    /**
     * Applies a {@code Bold} style to all the {@code Character} within the given range. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param start the position of the first character in the range
     * @param end   the position of the last character in the range
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ bold(@IntRange(from = 0) int start, @IntRange(from = 0) int end) {
        StyleSpan bold = new StyleSpan(Typeface.BOLD);
        return addSpan(start, end, bold);
    }

    /**
     * Applies an {@code Italic} style to all the {@code String} in {@code targetTexts} that exist in
     * the {@code target} content.
     *
     * @param targetTexts all the {@code String} that are to be modified
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ italic(@NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                italic(position, position + text.length());
            }
        }
        return this;
    }

    /**
     * Applies an {@code Italic} style to all the {@code Character} within the given range. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param start the position of the first character in the range
     * @param end   the position of the last character in the range
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ italic(@IntRange(from = 0) int start, @IntRange(from = 0) int end) {
        StyleSpan italic = new StyleSpan(Typeface.ITALIC);
        return addSpan(start, end, italic);
    }

    /**
     * Applies an {@code Underline} style to all the {@code String} in {@code targetTexts} that exist in
     * the {@code target} content.
     *
     * @param targetTexts all the {@code String} that are to be modified
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ underline(@NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                underline(position, position + text.length());
            }
        }
        return this;
    }

    /**
     * Applies a {@code Underline} style to all the {@code Character} within the given range. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param start the position of the first character in the range
     * @param end   the position of the last character in the range
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ underline(@IntRange(from = 0) int start, @IntRange(from = 0) int end) {
        UnderlineSpan underline = new UnderlineSpan();
        return addSpan(start, end, underline);
    }

    /**
     * Changes the {@code ForegroundColor} of the {@code Char} to all the {@code String} in {@code targetTexts}
     * that exist in the {@code target} content.
     *
     * @param fgColorResId the color to use for the {@code Char}
     * @param targetTexts  all the {@code String} that are to be modified
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ foregroundColor(@ColorRes int fgColorResId, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                foregroundColor(position, position + text.length(), fgColorResId);
            }
        }
        return this;
    }

    /**
     * Changes the {@code ForegroundColor} of all the {@code Character} within the given range. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param start        the position of the first character in the range
     * @param end          the position of the last character in the range
     * @param fgColorResId the color to use for the {@code Char}
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ foregroundColor(@IntRange(from = 0) int start, @IntRange(from = 0) int end, @ColorRes int fgColorResId) {
        final int fgColor = ContextCompat.getColor(context, fgColorResId);
        ForegroundColorSpan fgColorSpan = new ForegroundColorSpan(fgColor);
        return addSpan(start, end, fgColorSpan);
    }

    /**
     * Changes the {@code BackgroundColor} to all the {@code String} in {@code targetTexts}
     * that exist in the {@code target} content.
     *
     * @param bgColorResId the color to use for the {@code BackgroundColor}
     * @param targetTexts  all the {@code String} that are to be modified
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ backgroundColor(@ColorRes int bgColorResId, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                backgroundColor(position, position + text.length(), bgColorResId);
            }
        }
        return this;
    }

    /**
     * Changes the {@code BackgroundColor} of all the {@code Character} within the given range. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param start        the position of the first character in the range
     * @param end          the position of the last character in the range
     * @param bgColorResId the color to use for the {@code BackgroundColor}
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ backgroundColor(@IntRange(from = 0) int start, @IntRange(from = 0) int end, @ColorRes int bgColorResId) {
        final int bgColor = ContextCompat.getColor(context, bgColorResId);
        BackgroundColorSpan bgColorSpan = new BackgroundColorSpan(bgColor);
        return addSpan(start, end, bgColorSpan);
    }

    /**
     * Adds an {@code OnClickListener} to all the {@code String} in {@code targetTexts}
     * that exist in the {@code target} content.
     *
     * @param spanClick   the callback to call whenever the span it is clicked
     * @param targetTexts all the {@code String} that are to be modified
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ clickable(@NonNull OnSpanClickListener spanClick, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                clickable(position, position + text.length(), spanClick);
            }
        }
        return this;
    }

    /**
     * Adds an {@code OnClickListener} to all the {@code Character} within the given range. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param start     the position of the first character in the range
     * @param end       the position of the last character in the range
     * @param spanClick the callback to call whenever the span it is clicked
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ clickable(@IntRange(from = 0) int start, @IntRange(from = 0) int end, @NonNull OnSpanClickListener spanClick) {
        ClickableSpan clickSpan = new ClickableSpanEZ(spanClick, content.substring(start, end));
        target.setMovementMethod(new LinkMovementMethod());
        return addSpan(start, end, clickSpan);
    }

    /**
     * Styles the paragraph where all the {@code String} in {@code targetTexts} that exist in the
     * {@code target} content. (Commonly this adds a vertical line to the left of the paragraph, but
     * is something dependant on the OS)
     *
     * @param targetTexts all the {@code String} that are to be modified
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ quote(@NonNull String... targetTexts) {
        return quote(RESOURCE_NOT_SET, targetTexts);
    }


    /**
     * Styles the paragraph (with the selected {@code quoteColorResId}) where all the {@code String}
     * in {@code targetTexts} that exist in the {@code target} content.(Commonly this adds a vertical
     * line to the left of the paragraph, but is something dependant on the OS)
     *
     * @param quoteColorResId the color to use for the paragraph marker
     * @param targetTexts     all the {@code String} that are to be modified
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ quote(@ColorRes int quoteColorResId, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                quote(position, position + text.length(), quoteColorResId);
            }
        }
        return this;
    }

    /**
     * Styles the paragraph where all the {@code Character} within the given range exist. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param start the position of the first character in the range
     * @param end   the position of the last character in the range
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ quote(@IntRange(from = 0) int start, @IntRange(from = 0) int end) {
        return quote(start, end, RESOURCE_NOT_SET);
    }

    /**
     * Styles the paragraph (with the selected {@code quoteColorResId}) where all the {@code Character} within the given range exist. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param start           the position of the first character in the range
     * @param end             the position of the last character in the range
     * @param quoteColorResId the color to use for the paragraph marker
     * @return the same instance of the object so that chaining is possible
     */
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


    /**
     * Applies an {@code Strikethrough} style (Meaning that it crosses all text with a horizontal line)
     * to all the {@code String} in {@code targetTexts} that exist in the {@code target} content.
     *
     * @param targetTexts all the {@code String} that are to be modified
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ strikethrough(@NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                strikethrough(position, position + text.length());
            }
        }
        return this;
    }

    /**
     * Applies an {@code Strikethrough} style (Meaning that it crosses all text with a horizontal line)
     * to all the {@code Character} within the given range. Or throws and {@code IllegalArgumentException}
     * if the range provided is outside the content bounds.
     *
     * @param start the position of the first character in the range
     * @param end   the position of the last character in the range
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ strikethrough(@IntRange(from = 0) int start, @IntRange(from = 0) int end) {
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        return addSpan(start, end, strikethroughSpan);
    }

    /**
     * Modifies the gravity of the paragraph to be Center align, this is applied to the location of
     * where the {@code String} in {@code targetTexts} that exist in the {@code target} content are.
     *
     * @param targetTexts all the {@code String} that are to be modified
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ alignCenter(@NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                alignCenter(position, position + text.length());
            }
        }
        return this;
    }

    /**
     * Modifies the gravity of the paragraph to be Center align, this is applied to the location of
     * where the {@code Character} within the given range are located. Or throws and
     * {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param start the position of the first character in the range
     * @param end   the position of the last character in the range
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ alignCenter(@IntRange(from = 0) int start, @IntRange(from = 0) int end) {
        AlignmentSpan centerSpan = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER);
        return addSpan(start, end, centerSpan);
    }

    /**
     * Modifies the gravity of the paragraph to be Right align for LTR locales, and to be Left align
     * for RTL locales, this is applied to the location of where the {@code String} in
     * {@code targetTexts} that exist in the {@code target} content are.
     *
     * @param targetTexts all the {@code String} that are to be modified
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ alignEnd(@NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                alignEnd(position, position + text.length());
            }
        }
        return this;
    }

    /**
     * Modifies the gravity of the paragraph to be Right align for LTR locales, and to be Left align
     * for RTL locales, this is applied to the location of where the {@code Character} within the
     * given range are located. Or throws and {@code IllegalArgumentException} if the range provided
     * is outside the content bounds.
     *
     * @param start the position of the first character in the range
     * @param end   the position of the last character in the range
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ alignEnd(@IntRange(from = 0) int start, @IntRange(from = 0) int end) {
        AlignmentSpan oppositeSpan = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE);
        return addSpan(start, end, oppositeSpan);
    }

    /**
     * Modifies the gravity of the paragraph to be Left align for LTR locales, and to be Right align
     * for RTL locales, this is applied to the location of where the {@code String} in
     * {@code targetTexts} that exist in the {@code target} content are.
     *
     * @param targetTexts all the {@code String} that are to be modified
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ alignStart(@NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                alignStart(position, position + text.length());
            }
        }
        return this;
    }

    /**
     * Modifies the gravity of the paragraph to be Left align for LTR locales, and to be Right align
     * for RTL locales, this is applied to the location of where the {@code Character} within the
     * given range are located. Or throws and {@code IllegalArgumentException} if the range provided
     * is outside the content bounds.
     *
     * @param start the position of the first character in the range
     * @param end   the position of the last character in the range
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ alignStart(@IntRange(from = 0) int start, @IntRange(from = 0) int end) {
        AlignmentSpan oppositeSpan = new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL);
        return addSpan(start, end, oppositeSpan);
    }

    /**
     * Applies a {@code Subscript} style to all the {@code String} in {@code targetTexts} that exist in
     * the {@code target} content.
     *
     * @param targetTexts all the {@code String} that are to be modified
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ subscript(@NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                subscript(position, position + text.length());
            }
        }
        return this;
    }

    /**
     * Applies a {@code Subscript} style to all the {@code Character} within the given range. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param start the position of the first character in the range
     * @param end   the position of the last character in the range
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ subscript(@IntRange(from = 0) int start, @IntRange(from = 0) int end) {
        SubscriptSpan subscriptSpan = new SubscriptSpan();
        return addSpan(start, end, subscriptSpan);
    }

    /**
     * Applies a {@code Superscript} style to all the {@code String} in {@code targetTexts} that exist in
     * the {@code target} content.
     *
     * @param targetTexts all the {@code String} that are to be modified
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ superscript(@NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                superscript(position, position + text.length());
            }
        }
        return this;
    }

    /**
     * Applies a {@code Superscript} style to all the {@code Character} within the given range. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param start the position of the first character in the range
     * @param end   the position of the last character in the range
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ superscript(@IntRange(from = 0) int start, @IntRange(from = 0) int end) {
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        return addSpan(start, end, superscriptSpan);
    }

    /**
     * Applies a {@code Link} style to all the {@code String} in {@code targetTexts} that exist in
     * the {@code target} content, If clicked an Intent to open the link will be fired.
     *
     * @param url         the Url that will be applied to the {@code String}
     * @param targetTexts all the {@code String} that are to be modified
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ link(@NonNull String url, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                link(position, position + text.length(), url);
            }
        }
        return this;
    }

    /**
     * Applies a {@code Link} style to all the {@code Character} within the given range. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds. If
     * clicked an Intent to open the link will be fired.
     *
     * @param start the position of the first character in the range
     * @param end   the position of the last character in the range
     * @param url   the Url that will be applied to the {@code Char}
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ link(@IntRange(from = 0) int start, @IntRange(from = 0) int end, @NonNull String url) {
        URLSpan urlSpan = new URLSpan(url);
        return addSpan(start, end, urlSpan);
    }

    /**
     * Changes the font family to be used for the {@code String} in {@code targetTexts} that exist in
     * the {@code target} content.
     *
     * @param fontFamily  the font family to be use
     * @param targetTexts all the {@code String} that are to be modified
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ font(@NonNull @Font.Family String fontFamily, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                font(position, position + text.length(), fontFamily);
            }
        }
        return this;
    }

    /**
     * Changes the font family to be used for the {@code Character} within the given range. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param start      the position of the first character in the range
     * @param end        the position of the last character in the range
     * @param fontFamily the font family to be use
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ font(@IntRange(from = 0) int start, @IntRange(from = 0) int end, @NonNull @Font.Family String fontFamily) {
        TypefaceSpan urlSpan = new TypefaceSpan(fontFamily);
        return addSpan(start, end, urlSpan);
    }

    /**
     * Applies the defined style to the {@code String} in {@code targetTexts} that exist in
     * the {@code target} content.
     *
     * @param appearance  the style resource to be used
     * @param targetTexts all the {@code String} that are to be modified
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ style(@StyleRes int appearance, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                style(position, position + text.length(), appearance);
            }
        }
        return this;
    }

    /**
     * Applies the defined style to the {@code Character} within the given range. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param start      the position of the first character in the range
     * @param end        the position of the last character in the range
     * @param appearance the style resource to be used
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ style(@IntRange(from = 0) int start, @IntRange(from = 0) int end, @StyleRes int appearance) {
        TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(context, appearance);
        return addSpan(start, end, textAppearanceSpan);
    }

    /**
     * Applies a different {@code locale} to the {@code String} in {@code targetTexts} that exist in
     * the {@code target} content.
     *
     * @param locale      the locale to be use
     * @param targetTexts all the {@code String} that are to be modified
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ locale(@NonNull Locale locale, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                locale(position, position + text.length(), locale);
            }
        }
        return this;
    }

    /**
     * Applies a different {@code locale} to the {@code Character} within the given range. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param start  the position of the first character in the range
     * @param end    the position of the last character in the range
     * @param locale the locale to be use
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ locale(@IntRange(from = 0) int start, @IntRange(from = 0) int end, @NonNull Locale locale) {
        LocaleSpan localeSpan = new LocaleSpan(locale);
        return addSpan(start, end, localeSpan);
    }

    /**
     * Applies a scale factor to the width of the {@code String} in {@code targetTexts} that exist in
     * the {@code target} content.
     *
     * @param proportion  the factor to be used by the scaling
     * @param targetTexts all the {@code String} that are to be modified
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ scaleX(@FloatRange(from = 0.f) float proportion, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                scaleX(position, position + text.length(), proportion);
            }
        }
        return this;
    }

    /**
     * Applies a scale factor to the width of the {@code Character} within the given range. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param start      the position of the first character in the range
     * @param end        the position of the last character in the range
     * @param proportion the factor to be used by the scaling
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ scaleX(@IntRange(from = 0) int start, @IntRange(from = 0) int end, @FloatRange(from = 0.f) float proportion) {
        ScaleXSpan scaleXSpan = new ScaleXSpan(proportion);
        return addSpan(start, end, scaleXSpan);
    }

    /**
     * Changes the size of the {@code String} relative to the {@code TextSize} applied to the {@code target}
     *
     * @param proportion  the factor to be used by the scaling
     * @param targetTexts all the {@code String} that are to be modified
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ relativeSize(@FloatRange(from = 0.f) float proportion, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                relativeSize(position, position + text.length(), proportion);
            }
        }
        return this;
    }

    /**
     * Changes the size of the {@code String} relative to the {@code TextSize} applied to the
     * {@code Character} within the given range. Or throws and {@code IllegalArgumentException} if
     * the range provided is outside the content bounds.
     *
     * @param start      the position of the first character in the range
     * @param end        the position of the last character in the range
     * @param proportion the factor to be used by the scaling
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ relativeSize(@IntRange(from = 0) int start, @IntRange(from = 0) int end, @FloatRange(from = 0.f) float proportion) {
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(proportion);
        return addSpan(start, end, relativeSizeSpan);
    }

    /**
     * Changes the size of the {@code String} to be exactly {@code pixels}Px.
     *
     * @param pixels      the size in pixels that will be applied to the {@code String}
     * @param targetTexts all the {@code String} that are to be modified
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ absoluteSize(@IntRange(from = 1) int pixels, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                absoluteSize(position, position + text.length(), pixels);
            }
        }
        return this;
    }

    /**
     * Changes the size of the {@code Character} within the given range to be exactly {@code pixels}Px.
     * Or throws and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param start  the position of the first character in the range
     * @param end    the position of the last character in the range
     * @param pixels the size in pixels that will be applied to the {@code String}
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ absoluteSize(@IntRange(from = 0) int start, @IntRange(from = 0) int end, @IntRange(from = 1) int pixels) {
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(pixels);
        return addSpan(start, end, absoluteSizeSpan);
    }

    /**
     * Changes the size of the {@code String} to be exactly {@code dips}DP.
     *
     * @param dips        the size in density independent pixels that will be applied to the {@code String}
     * @param targetTexts all the {@code String} that are to be modified
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ absoluteSizeDP(@IntRange(from = 1) int dips, @NonNull String... targetTexts) {
        for (String text : targetTexts) {
            final int position = content.indexOf(text);
            if (position != -1) {
                absoluteSize(position, position + text.length(), dips);
            }
        }
        return this;
    }

    /**
     * Changes the size of the {@code Character} within the given range to be exactly {@code dips}DP.
     * Or throws and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param start the position of the first character in the range
     * @param end   the position of the last character in the range
     * @param dips  the size in density independent pixels that will be applied to the {@code Char}
     * @return the same instance of the object so that chaining is possible
     */
    public SpanEZ absoluteSizeDP(@IntRange(from = 0) int start, @IntRange(from = 0) int end, @IntRange(from = 1) int dips) {
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(dips, true);
        return addSpan(start, end, absoluteSizeSpan);
    }

    /**
     * Applies the given {@code span} to the specified range from {@code start} to {@code end}
     *
     * @param start starting position
     * @param end   ending position
     * @param span  the span to be applied
     * @return the same instance of the object so that chaining is possible
     */
    private SpanEZ addSpan(@IntRange(from = 0) int start, int end, @NonNull Object span) {
        validateRange(start, end);
        spannableContent.setSpan(span, start, end, spanFlags);
        return this;
    }

    /**
     * Validates that a starting and ending position are valid within the content of the {@code target}
     *
     * @param start starting position
     * @param end   ending position
     */
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

    /**
     * Applies all the previous modifications to the {@code target}
     */
    public void apply() {
        target.setText(spannableContent);
    }

}
