package com.bunk3r.spanez.locators;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Part of SpanEZ
 * Created by joragu on 1/13/2017.
 */
public class WordTest {

    @Test
    public void find_first_occurrence_when_only_one_exist_at_start() {
        String fullContent = "This is a dummy text";
        String wordToFind = "This";
        Word wordLocator = Word.findFirst("This");
        List<TargetRange> results = wordLocator.locate(fullContent);
        assertTrue("There must be exactly 1 result", results.size() == 1);

        TargetRange result = results.get(0);
        final int startIndex = 0;
        final int endIndex = startIndex + wordToFind.length() - 1;
        TargetRange expectedResult = TargetRange.from(startIndex, endIndex);
        assertTrue("The range for this word was incorrect", expectedResult.equals(result));
    }

    @Test
    public void find_first_occurrence_when_only_one_exist_at_center() {
        String fullContent = "This is a dummy text";
        String wordToFind = "is a dummy";
        Word wordLocator = Word.findFirst(wordToFind);
        List<TargetRange> results = wordLocator.locate(fullContent);
        assertTrue("There must be exactly 1 result", results.size() == 1);

        TargetRange result = results.get(0);
        final int startIndex = 5;
        final int endIndex = startIndex + wordToFind.length() - 1;
        TargetRange expectedResult = TargetRange.from(startIndex, endIndex);
        assertTrue("The range for this word was incorrect", expectedResult.equals(result));
    }

    @Test
    public void find_first_occurrence_when_only_one_exist_at_end() {
        String fullContent = "This is a dummy text";
        String wordToFind = "text";
        Word wordLocator = Word.findFirst("text");
        List<TargetRange> results = wordLocator.locate(fullContent);
        assertTrue("There must be exactly 1 result", results.size() == 1);

        TargetRange result = results.get(0);
        final int startIndex = 16;
        final int endIndex = startIndex + wordToFind.length() - 1;
        TargetRange expectedResult = TargetRange.from(startIndex, endIndex);
        assertTrue("The range for this word was incorrect", expectedResult.equals(result));

        assertTrue("The range should't be outside the content", endIndex <= fullContent.length());
    }

    @Test
    public void find_first_occurrence_when_multiple_exist() {
        String fullContent = "start word word word word word end";
        String wordToFind = "word";
        Word wordLocator = Word.findFirst(wordToFind);
        List<TargetRange> results = wordLocator.locate(fullContent);
        assertTrue("There must be exactly 1 result", results.size() == 1);

        TargetRange result = results.get(0);
        final int startIndex = 6;
        final int endIndex = startIndex + wordToFind.length() - 1;
        TargetRange expectedResult = TargetRange.from(startIndex, endIndex);
        assertTrue("The range for this word was incorrect", expectedResult.equals(result));
    }

    @Test
    public void find_first_when_it_does_not_exist_in_content() {
        String fullContent = "word word word word word";
        String wordToFind = "random";
        Word wordLocator = Word.findFirst(wordToFind);
        List<TargetRange> results = wordLocator.locate(fullContent);
        assertTrue("There should not be any result", results.size() == 0);
    }

    @Test
    public void find_first_when_word_is_empty() {
        String fullContent = "word word word word word";
        String wordToFind = "";
        Word wordLocator = Word.findFirst(wordToFind);
        List<TargetRange> results = wordLocator.locate(fullContent);
        assertTrue("There should not be any result", results.size() == 0);
    }

    @Test
    public void find_all_occurrence_when_multiple_exist() {
        String fullContent = "start word word word word word end";
        String wordToFind = "word";
        Word wordLocator = Word.findAll(wordToFind);
        List<TargetRange> results = wordLocator.locate(fullContent);
        assertTrue("There must be exactly 5 result", results.size() == 5);

        int startIndex = 6;
        for (TargetRange result : results) {
            final int endIndex = startIndex + wordToFind.length() - 1;
            TargetRange expectedResult = TargetRange.from(startIndex, endIndex);
            assertTrue("The range for this word was incorrect", expectedResult.equals(result));
            startIndex = endIndex + 2;
        }
    }

    @Test
    public void find_all_when_it_does_not_exist_in_content() {
        String wordToFind = "random";
        Word wordLocator = Word.findAll(wordToFind);
        List<TargetRange> results = wordLocator.locate("word word word word word");
        assertTrue("There should not be any result", results.size() == 0);
    }

    @Test
    public void find_all_when_word_is_empty() {
        String fullContent = "word word word word word";
        String wordToFind = "";
        Word wordLocator = Word.findAll(wordToFind);
        List<TargetRange> results = wordLocator.locate(fullContent);
        assertTrue("There should not be any result", results.size() == 0);
    }

}