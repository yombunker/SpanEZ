package com.bunk3r.spanez;

import android.support.annotation.ColorRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.annotation.StyleRes;

import com.bunk3r.spanez.listeners.OnSpanClickListener;
import com.bunk3r.spanez.locators.Locator;
import com.bunk3r.spanez.locators.Paragraph;

import java.lang.annotation.Retention;
import java.util.Locale;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Part of SpanEZ
 * Created by joragu on 1/13/2017.
 */
@SuppressWarnings({"unused",
                   "WeakerAccess"})
public interface StyleEZ {
    int BOLD = 1;
    int ITALIC = 1 << 1;
    int UNDERLINE = 1 << 2;
    int STRIKETHROUGH = 1 << 3;
    int SUBSCRIPT = 1 << 4;
    int SUPERSCRIPT = 1 << 5;

    @Retention(SOURCE)
    @IntDef(flag = true, value = {BOLD,
                                  ITALIC,
                                  UNDERLINE,
                                  STRIKETHROUGH,
                                  SUBSCRIPT,
                                  SUPERSCRIPT})
    @interface STYLE {}

    String MONOSPACE = "monospace";
    String SERIF = "serif";
    String SANS_SERIF = "sans-serif";
    String SANS_SERIF_LIGHT = "sans-serif-light";
    String SANS_SERIF_CONDENSED = "sans-serif-condensed";


    @Retention(SOURCE)
    @StringDef(value = {MONOSPACE,
                        SERIF,
                        SANS_SERIF,
                        SANS_SERIF_LIGHT,
                        SANS_SERIF_CONDENSED})
    @interface FontFamily {}

    /**
     * Every call after this will set the {@code Span} to be {@code Inclusive} in both sides, this
     * means that if text is added within that range the new text will keep the same format.
     *
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ inclusive();

    /**
     * Every call after this will set the {@code Span} to be {@code Exclusive} in both sides, this
     * means that if text is added within that range, excluding the last character the new
     * text will keep the same format.
     *
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ inclusiveExclusive();

    /**
     * Every call after this will set the {@code Span} to be {@code Exclusive} in both sides, this
     * means that if text is added within that range, excluding the first character the new
     * text will keep the same format.
     *
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ exclusiveInclusive();

    /**
     * Every call after this will set the {@code Span} to be {@code Exclusive} in both sides, this
     * means that if text is added within that range, excluding the first and last character the new
     * text will keep the same format.
     *
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ exclusive();

    /**
     * Applies all the {@code styles} to the {@code Character} within the given range. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param locator the locator to be used to decide were to apply this style
     * @param styles  all the styles to be applied, this are in form of a flag
     *                ex. BOLD | ITALIC | UNDERLINE
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ style(@NonNull Locator locator, @STYLE int styles);

    /**
     * Changes the {@code ForegroundColor} of all the {@code Character} within the given range. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param locator      the locator to be used to decide were to apply this style
     * @param fgColorResId the color to use for the {@code Char}
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ foregroundColor(@NonNull Locator locator, @ColorRes int fgColorResId);

    /**
     * Changes the {@code BackgroundColor} of all the {@code Character} within the given range. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param locator      the locator to be used to decide were to apply this style
     * @param bgColorResId the color to use for the {@code BackgroundColor}
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ backgroundColor(@NonNull Locator locator, @ColorRes int bgColorResId);

    /**
     * Adds an {@code OnClickListener} to all the {@code Character} within the given range. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param locator   the locator to be used to decide were to apply this style
     * @param spanClick the callback to call whenever the span it is clicked
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ clickable(@NonNull Locator locator, @NonNull OnSpanClickListener spanClick);

    /**
     * Styles the paragraph where all the {@code Character} within the given range exist. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param paragraph the locator to be used to decide were to apply this style
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ quote(@NonNull Paragraph paragraph);

    /**
     * Styles the paragraph (with the selected {@code quoteColorResId}) where all the {@code Character}
     * within the given range exist. Or throws and {@code IllegalArgumentException} if the range
     * provided is outside the content bounds.
     *
     * @param paragraph       the locator to be used to decide were to apply this style
     * @param quoteColorResId the color to use for the paragraph marker
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ quote(@NonNull Paragraph paragraph, @ColorRes int quoteColorResId);

    /**
     * Modifies the gravity of the paragraph to be Center align, this is applied to the location of
     * where the {@code Character} within the given range are located. Or throws and
     * {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param paragraph the locator to be used to decide were to apply this style
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ alignCenter(@NonNull Paragraph paragraph);

    /**
     * Modifies the gravity of the paragraph to be Right align for LTR locales, and to be Left align
     * for RTL locales, this is applied to the location of where the {@code Character} within the
     * given range are located. Or throws and {@code IllegalArgumentException} if the range provided
     * is outside the content bounds.
     *
     * @param paragraph the locator to be used to decide were to apply this style
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ alignEnd(@NonNull Paragraph paragraph);

    /**
     * Modifies the gravity of the paragraph to be Left align for LTR locales, and to be Right align
     * for RTL locales, this is applied to the location of where the {@code Character} within the
     * given range are located. Or throws and {@code IllegalArgumentException} if the range provided
     * is outside the content bounds.
     *
     * @param paragraph the locator to be used to decide were to apply this style
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ alignStart(@NonNull Paragraph paragraph);

    /**
     * Applies a {@code Link} style to all the {@code Character} within the given range. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds. If
     * clicked an Intent to open the link will be fired.
     *
     * @param locator the locator to be used to decide were to apply this style
     * @param url     the Url that will be applied to the {@code Char}
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ link(@NonNull Locator locator, @NonNull String url);

    /**
     * Changes the font family to be used for the {@code Character} within the given range. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param locator    the locator to be used to decide were to apply this style
     * @param fontFamily the font family to be use
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ font(@NonNull Locator locator, @NonNull @FontFamily String fontFamily);

    /**
     * Applies the defined style to the {@code Character} within the given range. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param locator    the locator to be used to decide were to apply this style
     * @param appearance the style resource to be used
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ appearance(@NonNull Locator locator, @StyleRes int appearance);

    /**
     * Applies a different {@code locale} to the {@code Character} within the given range. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param locator the locator to be used to decide were to apply this style
     * @param locale  the locale to be use
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ locale(@NonNull Locator locator, @NonNull Locale locale);

    /**
     * Applies a scale factor to the width of the {@code Character} within the given range. Or throws
     * and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param locator    the locator to be used to decide were to apply this style
     * @param proportion the factor to be used by the scaling
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ scaleX(@NonNull Locator locator, @FloatRange(from = 0.f) float proportion);

    /**
     * Changes the size of the {@code String} relative to the {@code TextSize} applied to the
     * {@code Character} within the given range. Or throws and {@code IllegalArgumentException} if
     * the range provided is outside the content bounds.
     *
     * @param locator    the locator to be used to decide were to apply this style
     * @param proportion the factor to be used by the scaling
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ relativeSize(@NonNull Locator locator, @FloatRange(from = 0.f) float proportion);

    /**
     * Changes the size of the {@code Character} within the given range to be exactly {@code pixels}Px.
     * Or throws and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param locator the locator to be used to decide were to apply this style
     * @param pixels  the size in pixels that will be applied to the {@code String}
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ absoluteSize(@NonNull Locator locator, @IntRange(from = 1) int pixels);

    /**
     * Changes the size of the {@code Character} within the given range to be exactly {@code dips}DP.
     * Or throws and {@code IllegalArgumentException} if the range provided is outside the content bounds.
     *
     * @param locator the locator to be used to decide were to apply this style
     * @param dips    the size in density independent pixels that will be applied to the {@code Char}
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ absoluteSizeDP(@NonNull Locator locator, @IntRange(from = 1) int dips);

    /**
     * Applies all the previous modifications to the {@code target}
     */
    void apply();
}
