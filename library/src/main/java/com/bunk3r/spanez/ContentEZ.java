package com.bunk3r.spanez;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * Part of SpanEZ
 * Created by joragu on 1/13/2017.
 */

@SuppressWarnings({"WeakerAccess",
                   "unused"})
public interface ContentEZ {
    /**
     * If you want to keep the content of the {@code target}
     * NOTE: Currently it only works if the content is a SpannableString, otherwise it will be taken
     * as a normal String.
     *
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ withCurrentContent();

    /**
     * If you want to set the starting content of the {@code target} from a {@code String}
     *
     * @param text the text that will be set as the content of the (@code target)
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ withContent(String text);

    /**
     * If you want to set the starting content of the {@code target} from a String resource id
     *
     * @param textResId the {@code R.string.*} id of the string that will be used
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ withContent(@StringRes int textResId);

    /**
     * If you want to set the starting content of the {@code target} from a String resource id, that
     * has format, and you need to pass down the arguments for that format
     *
     * @param textResId the {@code R.string.*} id of the string that will be used
     * @param args      the arguments that will be pass to the format of the {@code String}
     * @return the same instance of the object so that chaining is possible
     */
    StyleEZ withFormattedContent(@StringRes int textResId, @NonNull Object... args);
}
