package com.bunk3r.spanez.locators;

import com.bunk3r.spanez.models.TargetRange;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Part of SpanEZ
 * Created by joragu on 1/13/2017.
 */
public class ParagraphTest {
    private static final int FIRST_PARAGRAPH = 1;
    private static final int MIDDLE_PARAGRAPH = 2;
    private static final int LAST_PARAGRAPH = 3;
    private static final int NOT_EXISTENT_PARAGRAPH = 4;

    private static final String EMPTY_EXCERPT = "";
    private static final String PARAGRAPH_EXCERPT = "paragraph";
    private static final String NOT_EXISTENT_EXCERPT = "random";
    private static final String FIRST_PARAGRAPH_CONTENT = "First paragraph";
    private static final String SECOND_PARAGRAPH_CONTENT = "Second paragraph";
    private static final String THIRD_PARAGRAPH_CONTENT = "Third paragraph";
    private static final String EMPTY_CONTENT = "";
    private static final String NEW_LINES_CONTENT = "\n\n\n\n";
    private static final String SINGLE_PARAGRAPH_CONTENT = NEW_LINES_CONTENT + FIRST_PARAGRAPH_CONTENT + NEW_LINES_CONTENT;
    private static final String MULTIPLE_PARAGRAPHS_CONTENT = FIRST_PARAGRAPH_CONTENT + "\n" + SECOND_PARAGRAPH_CONTENT + "\n" + THIRD_PARAGRAPH_CONTENT;

    private static final TargetRange FIRST_PARAGRAPH_RANGE = TargetRange.from(0, 15);
    private static final TargetRange SECOND_PARAGRAPH_RANGE = TargetRange.from(16, 32);
    private static final TargetRange THIRD_PARAGRAPH_RANGE = TargetRange.from(33, 47);

    @Test
    public void find_by_number_when_content_is_empty() {
        Paragraph paragraph = Paragraph.number(FIRST_PARAGRAPH);
        List<TargetRange> results = paragraph.locate(EMPTY_CONTENT);
        assertThat(results).isEmpty();
    }

    @Test
    public void find_by_number_when_content_is_new_lines() {
        Paragraph paragraph = Paragraph.number(FIRST_PARAGRAPH);
        List<TargetRange> results = paragraph.locate(NEW_LINES_CONTENT);
        assertThat(results).isEmpty();
    }

    @Test
    public void find_by_number_when_empty_paragraphs_exist() {
        Paragraph paragraph = Paragraph.number(FIRST_PARAGRAPH);
        List<TargetRange> results = paragraph.locate(SINGLE_PARAGRAPH_CONTENT);

        int startIndex = 4;
        int endIndex = startIndex + FIRST_PARAGRAPH_CONTENT.length();
        TargetRange expectedResult = TargetRange.from(startIndex, endIndex);
        assertThat(results).hasSize(1)
                           .containsOnly(expectedResult);
    }

    @Test
    public void find_by_number_when_paragraph_does_exist_at_start() {
        Paragraph paragraph = Paragraph.number(FIRST_PARAGRAPH);
        List<TargetRange> results = paragraph.locate(MULTIPLE_PARAGRAPHS_CONTENT);

        assertThat(results).hasSize(1)
                           .containsOnly(FIRST_PARAGRAPH_RANGE);
    }

    @Test
    public void find_by_number_when_paragraph_does_exist_at_center() {
        Paragraph paragraph = Paragraph.number(MIDDLE_PARAGRAPH);
        List<TargetRange> results = paragraph.locate(MULTIPLE_PARAGRAPHS_CONTENT);

        assertThat(results).hasSize(1)
                           .containsOnly(SECOND_PARAGRAPH_RANGE);
    }

    @Test
    public void find_by_number_when_paragraph_does_exist_at_end() {
        Paragraph paragraph = Paragraph.number(LAST_PARAGRAPH);
        List<TargetRange> results = paragraph.locate(MULTIPLE_PARAGRAPHS_CONTENT);

        assertThat(results).hasSize(1)
                           .containsOnly(THIRD_PARAGRAPH_RANGE);
    }

    @Test
    public void find_by_number_when_paragraph_does_not_exist() {
        Paragraph paragraph = Paragraph.number(NOT_EXISTENT_PARAGRAPH);
        List<TargetRange> results = paragraph.locate(MULTIPLE_PARAGRAPHS_CONTENT);
        assertThat(results).isEmpty();
    }

    @Test
    public void find_by_content_when_content_is_empty() {
        Paragraph paragraph = Paragraph.containing(PARAGRAPH_EXCERPT);
        List<TargetRange> results = paragraph.locate(EMPTY_CONTENT);
        assertThat(results).isEmpty();
    }

    @Test
    public void find_by_content_when_content_is_new_lines() {
        Paragraph paragraph = Paragraph.containing(PARAGRAPH_EXCERPT);
        List<TargetRange> results = paragraph.locate(NEW_LINES_CONTENT);
        assertThat(results).isEmpty();
    }

    @Test
    public void find_by_content_when_excerpt_is_empty() {
        Paragraph paragraph = Paragraph.containing(EMPTY_EXCERPT);
        List<TargetRange> results = paragraph.locate(MULTIPLE_PARAGRAPHS_CONTENT);
        assertThat(results).isEmpty();
    }

    @Test
    public void find_by_content_when_empty_paragraphs_exist() {
        Paragraph paragraph = Paragraph.containing(PARAGRAPH_EXCERPT);
        List<TargetRange> results = paragraph.locate(SINGLE_PARAGRAPH_CONTENT);

        int startIndex = 4;
        int endIndex = startIndex + FIRST_PARAGRAPH_CONTENT.length();
        TargetRange expectedResult = TargetRange.from(startIndex, endIndex);
        assertThat(results).hasSize(1)
                           .containsOnly(expectedResult);
    }

    @Test
    public void find_by_content_when_excerpt_not_present_in_content() {
        Paragraph paragraph = Paragraph.containing(NOT_EXISTENT_EXCERPT);
        List<TargetRange> results = paragraph.locate(MULTIPLE_PARAGRAPHS_CONTENT);
        assertThat(results).isEmpty();
    }

    @Test
    public void find_by_content_when_paragraph_does_exist_at_start() {
        Paragraph paragraph = Paragraph.containing(FIRST_PARAGRAPH_CONTENT);
        List<TargetRange> results = paragraph.locate(MULTIPLE_PARAGRAPHS_CONTENT);

        assertThat(results).hasSize(1)
                           .containsOnly(FIRST_PARAGRAPH_RANGE);
    }

    @Test
    public void find_by_content_when_paragraph_does_exist_at_center() {
        Paragraph paragraph = Paragraph.containing(SECOND_PARAGRAPH_CONTENT);
        List<TargetRange> results = paragraph.locate(MULTIPLE_PARAGRAPHS_CONTENT);

        assertThat(results).hasSize(1)
                           .containsOnly(SECOND_PARAGRAPH_RANGE);
    }

    @Test
    public void find_by_content_when_paragraph_does_exist_at_end() {
        Paragraph paragraph = Paragraph.containing(THIRD_PARAGRAPH_CONTENT);
        List<TargetRange> results = paragraph.locate(MULTIPLE_PARAGRAPHS_CONTENT);

        assertThat(results).hasSize(1)
                           .containsOnly(THIRD_PARAGRAPH_RANGE);
    }

    @Test
    public void find_by_content_when_multiple_paragraphs_exist() {
        Paragraph paragraph = Paragraph.containing(PARAGRAPH_EXCERPT);
        List<TargetRange> results = paragraph.locate(MULTIPLE_PARAGRAPHS_CONTENT);

        assertThat(results).hasSize(3)
                           .containsOnly(FIRST_PARAGRAPH_RANGE,
                                         SECOND_PARAGRAPH_RANGE,
                                         THIRD_PARAGRAPH_RANGE);
    }
}