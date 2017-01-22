package com.bunk3r.spanez.locators;

import com.bunk3r.spanez.models.TargetRange;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Part of SpanEZ
 * Created by joragu on 1/13/2017.
 */
public class WordTest {
    private static final String EMPTY_WORD = "";
    private static final String FIRST_WORD = "start";
    private static final String MIDDLE_WORD = "middle";
    private static final String LAST_WORD = "end";
    private static final String WORD_NOT_IN_CONTENT = "blah blah";
    private static final String UNIQUE_CONTENT = FIRST_WORD + MIDDLE_WORD + LAST_WORD;
    private static final String REPEATED_CONTENT = FIRST_WORD + MIDDLE_WORD + MIDDLE_WORD + MIDDLE_WORD + LAST_WORD;

    private static final TargetRange FIRST_WORD_RANGE = TargetRange.from(0, 4);
    private static final TargetRange MIDDLE_WORD_RANGE = TargetRange.from(5, 10);
    private static final TargetRange LAST_WORD_RANGE = TargetRange.from(11, 13);
    private static final TargetRange REPEATED_MIDDLE_WORD_RANGE_1 = TargetRange.from(5, 10);
    private static final TargetRange REPEATED_MIDDLE_WORD_RANGE_2 = TargetRange.from(11, 16);
    private static final TargetRange REPEATED_MIDDLE_WORD_RANGE_3 = TargetRange.from(17, 22);

    @Test
    public void find_first_occurrence_when_only_one_exist_at_start() {
        Word wordLocator = Word.findFirst(FIRST_WORD);
        List<TargetRange> results = wordLocator.locate(UNIQUE_CONTENT);

        assertThat(results).hasSize(1)
                           .containsOnly(FIRST_WORD_RANGE);
    }

    @Test
    public void find_first_occurrence_when_only_one_exist_at_center() {
        Word wordLocator = Word.findFirst(MIDDLE_WORD);
        List<TargetRange> results = wordLocator.locate(UNIQUE_CONTENT);

        assertThat(results).hasSize(1)
                           .containsOnly(MIDDLE_WORD_RANGE);
    }

    @Test
    public void find_first_occurrence_when_only_one_exist_at_end() {
        Word wordLocator = Word.findFirst(LAST_WORD);
        List<TargetRange> results = wordLocator.locate(UNIQUE_CONTENT);

        assertThat(results).hasSize(1)
                           .containsOnly(LAST_WORD_RANGE);
    }

    @Test
    public void find_first_occurrence_when_multiple_exist() {
        Word wordLocator = Word.findFirst(MIDDLE_WORD);
        List<TargetRange> results = wordLocator.locate(REPEATED_CONTENT);

        assertThat(results).hasSize(1)
                           .containsOnly(REPEATED_MIDDLE_WORD_RANGE_1);
    }

    @Test
    public void find_first_when_it_does_not_exist_in_content() {
        Word wordLocator = Word.findFirst(WORD_NOT_IN_CONTENT);
        List<TargetRange> results = wordLocator.locate(UNIQUE_CONTENT);
        assertThat(results).isEmpty();
    }

    @Test
    public void find_first_when_word_is_empty() {
        Word wordLocator = Word.findFirst(EMPTY_WORD);
        List<TargetRange> results = wordLocator.locate(UNIQUE_CONTENT);
        assertThat(results).isEmpty();
    }

    @Test
    public void find_all_occurrence_when_only_one_exist_at_start() {
        Word wordLocator = Word.findAll(FIRST_WORD);
        List<TargetRange> results = wordLocator.locate(UNIQUE_CONTENT);

        assertThat(results).hasSize(1)
                           .containsOnly(FIRST_WORD_RANGE);
    }

    @Test
    public void find_all_occurrence_when_only_one_exist_at_end() {
        Word wordLocator = Word.findAll(LAST_WORD);
        List<TargetRange> results = wordLocator.locate(UNIQUE_CONTENT);

        assertThat(results).hasSize(1)
                           .containsOnly(LAST_WORD_RANGE);
    }

    @Test
    public void find_all_occurrence_when_multiple_exist() {
        Word wordLocator = Word.findAll(MIDDLE_WORD);
        List<TargetRange> results = wordLocator.locate(REPEATED_CONTENT);

        assertThat(results).hasSize(3)
                           .containsOnly(REPEATED_MIDDLE_WORD_RANGE_1,
                                         REPEATED_MIDDLE_WORD_RANGE_2,
                                         REPEATED_MIDDLE_WORD_RANGE_3);
    }

    @Test
    public void find_all_when_it_does_not_exist_in_content() {
        Word wordLocator = Word.findAll(WORD_NOT_IN_CONTENT);
        List<TargetRange> results = wordLocator.locate(UNIQUE_CONTENT);
        assertThat(results).isEmpty();
    }

    @Test
    public void find_all_when_word_is_empty() {
        Word wordLocator = Word.findAll(EMPTY_WORD);
        List<TargetRange> results = wordLocator.locate(UNIQUE_CONTENT);
        assertThat(results).isEmpty();
    }
}