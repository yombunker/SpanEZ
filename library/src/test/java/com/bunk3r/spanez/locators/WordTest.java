package com.bunk3r.spanez.locators;

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

    @Test
    public void find_first_occurrence_when_only_one_exist_at_start() {
        Word wordLocator = Word.findFirst(FIRST_WORD);
        List<TargetRange> results = wordLocator.locate(UNIQUE_CONTENT);
        assertThat(results).hasSize(1);

        TargetRange result = results.get(0);
        int startIndex = 0;
        int endIndex = startIndex + FIRST_WORD.length() - 1;
        TargetRange expectedResult = TargetRange.from(startIndex, endIndex);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void find_first_occurrence_when_only_one_exist_at_center() {
        Word wordLocator = Word.findFirst(MIDDLE_WORD);
        List<TargetRange> results = wordLocator.locate(UNIQUE_CONTENT);
        assertThat(results).hasSize(1);

        TargetRange result = results.get(0);
        int startIndex = 5;
        int endIndex = startIndex + MIDDLE_WORD.length() - 1;
        TargetRange expectedResult = TargetRange.from(startIndex, endIndex);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void find_first_occurrence_when_only_one_exist_at_end() {
        Word wordLocator = Word.findFirst(LAST_WORD);
        List<TargetRange> results = wordLocator.locate(UNIQUE_CONTENT);
        assertThat(results).hasSize(1);

        TargetRange result = results.get(0);
        int startIndex = 11;
        int endIndex = startIndex + LAST_WORD.length() - 1;
        TargetRange expectedResult = TargetRange.from(startIndex, endIndex);
        assertThat(result).isEqualTo(expectedResult);
        assertThat(endIndex).isLessThan(UNIQUE_CONTENT.length());
    }

    @Test
    public void find_first_occurrence_when_multiple_exist() {
        Word wordLocator = Word.findFirst(MIDDLE_WORD);
        List<TargetRange> results = wordLocator.locate(REPEATED_CONTENT);
        assertThat(results).hasSize(1);

        TargetRange result = results.get(0);
        int startIndex = 5;
        int endIndex = startIndex + MIDDLE_WORD.length() - 1;
        TargetRange expectedResult = TargetRange.from(startIndex, endIndex);
        assertThat(result).isEqualTo(expectedResult);
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
    public void find_all_occurrence_when_multiple_exist() {
        Word wordLocator = Word.findAll(MIDDLE_WORD);
        List<TargetRange> results = wordLocator.locate(REPEATED_CONTENT);
        assertThat(results).hasSize(3);

        int startIndex = 5;
        for (TargetRange result : results) {
            int endIndex = startIndex + MIDDLE_WORD.length() - 1;
            TargetRange expectedResult = TargetRange.from(startIndex, endIndex);
            assertThat(result).isEqualTo(expectedResult);
            startIndex = endIndex + 1;
        }
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