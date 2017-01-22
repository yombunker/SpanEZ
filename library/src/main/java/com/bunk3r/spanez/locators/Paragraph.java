package com.bunk3r.spanez.locators;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.bunk3r.spanez.models.P;
import com.bunk3r.spanez.models.ParagraphLocator;
import com.bunk3r.spanez.models.TargetRange;

import java.util.ArrayList;
import java.util.List;

/**
 * Part of SpanEZ
 * Created by joragu on 1/12/2017.
 */
@SuppressWarnings({"WeakerAccess",
                   "unused"})
public final class Paragraph extends ParagraphLocator {
    private final boolean byNumber;
    private int paragraphNumber;
    private String excerpt;

    public static Paragraph number(@IntRange(from = 1) int paragraphNumber) {
        return new Paragraph(paragraphNumber);
    }

    public static Paragraph containing(@NonNull String excerpt) {
        return new Paragraph(excerpt);
    }

    private Paragraph(@IntRange(from = 1) int paragraphNumber) {
        this.byNumber = true;
        this.paragraphNumber = paragraphNumber;
    }

    private Paragraph(@NonNull String excerpt) {
        this.byNumber = false;
        this.excerpt = excerpt;
    }

    @NonNull
    @Override
    public List<TargetRange> locate(@NonNull String content) {
        List<P> paragraphs = splitIntoParagraphs(content);
        return byNumber ? locateByNumber(paragraphs) : locateByContent(paragraphs);
    }

    @NonNull
    private List<TargetRange> locateByNumber(@NonNull List<P> paragraphs) {
        List<TargetRange> ranges = new ArrayList<>(1);
        if (paragraphs.size() >= paragraphNumber) {
            P paragraph = paragraphs.get(paragraphNumber - 1);
            ranges.add(TargetRange.from(paragraph.getStart(), paragraph.getEnd()));
        }
        return ranges;
    }

    @NonNull
    private List<TargetRange> locateByContent(@NonNull List<P> paragraphs) {
        List<TargetRange> ranges = new ArrayList<>();
        if (excerpt.isEmpty()) {
            return ranges;
        }

        for (P paragraph : paragraphs) {
            if (paragraph.getContent().contains(excerpt)) {
                ranges.add(TargetRange.from(paragraph.getStart(), paragraph.getEnd()));
            }
        }
        return ranges;
    }
}
