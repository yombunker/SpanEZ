package com.bunk3r.spanez.locators;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Part of SpanEZ
 * Created by joragu on 1/13/2017.
 */
public class ParagraphTest {

    @Test
    public void find_by_number_when_content_is_empty() {
        final String CONTENT = "";
        final int paragraphNumber = 1;
        Paragraph paragraph = Paragraph.number(paragraphNumber);
        List<TargetRange> results = paragraph.locate(CONTENT);
        assertTrue("There must not be any result", results.size() == 0);
    }

    @Test
    public void find_by_number_when_content_is_new_lines() {
        final String CONTENT = "\n\n\n\n";
        final int paragraphNumber = 1;
        Paragraph paragraph = Paragraph.number(paragraphNumber);
        List<TargetRange> results = paragraph.locate(CONTENT);
        assertTrue("There must not be any result", results.size() == 0);
    }

    @Test
    public void find_by_number_when_empty_paragraphs_exist() {
        final String CONTENT = "\n\nThird paragraph\n\n";
        final int paragraphNumber = 1;
        Paragraph paragraph = Paragraph.number(paragraphNumber);
        List<TargetRange> results = paragraph.locate(CONTENT);
        assertTrue("There must be exactly 1 result", results.size() == 1);

        TargetRange result = results.get(0);
        final int startIndex = 2;
        final int endIndex = startIndex + "Third paragraph".length();
        TargetRange expectedResult = TargetRange.from(startIndex, endIndex);
        assertTrue("The range for this paragraph was incorrect", expectedResult.equals(result));
    }

    @Test
    public void find_by_number_when_paragraph_does_exist_at_start() {
        final String CONTENT = "First paragraph\nSecond paragraph\nThird paragraph\nFourth paragraph";
        final int paragraphNumber = 1;
        Paragraph paragraph = Paragraph.number(paragraphNumber);
        List<TargetRange> results = paragraph.locate(CONTENT);
        assertTrue("There must be exactly 1 result", results.size() == 1);

        TargetRange result = results.get(0);
        final int startIndex = 0;
        final int endIndex = startIndex + "First paragraph".length();
        TargetRange expectedResult = TargetRange.from(startIndex, endIndex);
        assertTrue("The range for this paragraph was incorrect", expectedResult.equals(result));
    }

    @Test
    public void find_by_number_when_paragraph_does_exist_at_center() {
        final String CONTENT = "First paragraph\nSecond paragraph\nThird paragraph\nFourth paragraph";
        final int paragraphNumber = 3;
        Paragraph paragraph = Paragraph.number(paragraphNumber);
        List<TargetRange> results = paragraph.locate(CONTENT);
        assertTrue("There must be exactly 1 result", results.size() == 1);

        TargetRange result = results.get(0);
        final int startIndex = 33;
        final int endIndex = startIndex + "Third paragraph".length();
        TargetRange expectedResult = TargetRange.from(startIndex, endIndex);
        assertTrue("The range for this paragraph was incorrect", expectedResult.equals(result));
    }

    @Test
    public void find_by_number_when_paragraph_does_exist_at_end() {
        final String CONTENT = "First paragraph\nSecond paragraph\nThird paragraph\nFourth paragraph";
        final int paragraphNumber = 4;
        Paragraph paragraph = Paragraph.number(paragraphNumber);
        List<TargetRange> results = paragraph.locate(CONTENT);
        assertTrue("There must be exactly 1 result", results.size() == 1);

        TargetRange result = results.get(0);
        final int startIndex = 49;
        final int endIndex = startIndex + "Fourth paragraph".length() - 1;
        TargetRange expectedResult = TargetRange.from(startIndex, endIndex);
        assertTrue("The range for this paragraph was incorrect", expectedResult.equals(result));

        assertTrue("The range should't be outside the content", endIndex < CONTENT.length());
    }

    @Test
    public void find_by_number_when_paragraph_does_not_exist() {
        final String CONTENT = "First paragraph\nSecond paragraph\nThird paragraph\nFourth paragraph";
        final int paragraphNumber = 5;
        Paragraph paragraph = Paragraph.number(paragraphNumber);
        List<TargetRange> results = paragraph.locate(CONTENT);
        assertTrue("There must not be any result", results.size() == 0);
    }

    @Test
    public void find_by_content_when_content_is_empty() {
        final String CONTENT = "";
        final String paragraphExcerpt = "not empty";
        Paragraph paragraph = Paragraph.containing(paragraphExcerpt);
        List<TargetRange> results = paragraph.locate(CONTENT);
        assertTrue("There must not be any result", results.size() == 0);
    }

    @Test
    public void find_by_content_when_content_is_new_lines() {
        final String CONTENT = "\n\n\n\n";
        final String paragraphExcerpt = "not empty";
        Paragraph paragraph = Paragraph.containing(paragraphExcerpt);
        List<TargetRange> results = paragraph.locate(CONTENT);
        assertTrue("There must not be any result", results.size() == 0);
    }

    @Test
    public void find_by_content_when_excerpt_is_empty() {
        final String CONTENT = "First paragraph\nSecond paragraph\nThird paragraph\nFourth paragraph";
        final String paragraphExcerpt = "";
        Paragraph paragraph = Paragraph.containing(paragraphExcerpt);
        List<TargetRange> results = paragraph.locate(CONTENT);
        assertTrue("There must not be any result", results.size() == 0);
    }

    @Test
    public void find_by_content_when_empty_paragraphs_exist() {
        final String CONTENT = "\n\nThird paragraph\nFourth paragraph\n";
        final String paragraphExcerpt = "paragraph";
        Paragraph paragraph = Paragraph.containing(paragraphExcerpt);
        List<TargetRange> results = paragraph.locate(CONTENT);
        assertTrue("There must be exactly 2 result", results.size() == 2);

        TargetRange result = results.get(0);
        int startIndex = 2;
        int endIndex = startIndex + "Third paragraph".length();
        TargetRange expectedResult = TargetRange.from(startIndex, endIndex);
        assertTrue("The range for this paragraph was incorrect", expectedResult.equals(result));

        result = results.get(1);
        startIndex = 18;
        endIndex = startIndex + "Fourth paragraph".length();
        expectedResult = TargetRange.from(startIndex, endIndex);
        assertTrue("The range for this paragraph was incorrect", expectedResult.equals(result));
    }

    @Test
    public void find_by_content_when_excerpt_not_present_in_content() {
        final String CONTENT = "First paragraph\nSecond paragraph\nThird paragraph\nFourth paragraph";
        final String paragraphExcerpt = "random";
        Paragraph paragraph = Paragraph.containing(paragraphExcerpt);
        List<TargetRange> results = paragraph.locate(CONTENT);
        assertTrue("There must not be any result", results.size() == 0);
    }

    @Test
    public void find_by_content_when_paragraph_does_exist_at_start() {
        final String CONTENT = "First paragraph\nSecond paragraph\nThird paragraph\nFourth paragraph";
        final String paragraphExcerpt = "First paragraph";
        Paragraph paragraph = Paragraph.containing(paragraphExcerpt);
        List<TargetRange> results = paragraph.locate(CONTENT);
        assertTrue("There must be exactly 1 result", results.size() == 1);

        TargetRange result = results.get(0);
        final int startIndex = 0;
        final int endIndex = startIndex + "First paragraph".length();
        TargetRange expectedResult = TargetRange.from(startIndex, endIndex);
        assertTrue("The range for this paragraph was incorrect", expectedResult.equals(result));
    }

    @Test
    public void find_by_content_when_paragraph_does_exist_at_center() {
        final String CONTENT = "First paragraph\nSecond paragraph\nThird paragraph\nFourth paragraph";
        final String paragraphExcerpt = "Third paragraph";
        Paragraph paragraph = Paragraph.containing(paragraphExcerpt);
        List<TargetRange> results = paragraph.locate(CONTENT);
        assertTrue("There must be exactly 1 result", results.size() == 1);

        TargetRange result = results.get(0);
        final int startIndex = 33;
        final int endIndex = startIndex + "Third paragraph".length();
        TargetRange expectedResult = TargetRange.from(startIndex, endIndex);
        assertTrue("The range for this paragraph was incorrect", expectedResult.equals(result));
    }

    @Test
    public void find_by_content_when_paragraph_does_exist_at_end() {
        final String CONTENT = "First paragraph\nSecond paragraph\nThird paragraph\nFourth paragraph";
        final String paragraphExcerpt = "Fourth paragraph";
        Paragraph paragraph = Paragraph.containing(paragraphExcerpt);
        List<TargetRange> results = paragraph.locate(CONTENT);
        assertTrue("There must be exactly 1 result", results.size() == 1);

        TargetRange result = results.get(0);
        final int startIndex = 49;
        final int endIndex = startIndex + "Fourth paragraph".length() - 1;
        TargetRange expectedResult = TargetRange.from(startIndex, endIndex);
        assertTrue("The range for this paragraph was incorrect", expectedResult.equals(result));

        assertTrue("The range should't be outside the content", endIndex < CONTENT.length());
    }

}