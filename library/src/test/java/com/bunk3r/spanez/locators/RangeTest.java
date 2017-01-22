package com.bunk3r.spanez.locators;

import com.bunk3r.spanez.models.TargetRange;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Part of SpanEZ
 * Created by joragu on 1/13/2017.
 */
public class RangeTest {
    private static final String CONTENT = "This is a text where the range fits";
    private static final int RANGE_START = 0;
    private static final int RANGE_END = 10;
    private static final int RANGE_OUTSIDE_CONTENT = 100;

    @Test
    public void valid_range() {
        Range range = Range.from(RANGE_START, RANGE_END);
        assertThat(range).isNotNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void exception_thrown_when_range_is_invalid() {
        Range.from(RANGE_END, RANGE_START);
    }

    @Test
    public void range_should_not_change() {
        Range range = Range.from(RANGE_START, RANGE_END);
        List<TargetRange> results = range.locate(CONTENT);

        TargetRange expectedResult = TargetRange.from(RANGE_START, RANGE_END);
        assertThat(results).hasSize(1)
                           .containsOnly(expectedResult);
    }

    @Test
    public void do_not_a_range_if_end_range_outside_content() {
        Range range = Range.from(RANGE_START, RANGE_OUTSIDE_CONTENT);
        List<TargetRange> results = range.locate(CONTENT);
        assertThat(results).isEmpty();
    }

    @Test
    public void do_not_a_range_if_start_range_outside_content() {
        Range range = Range.from(RANGE_OUTSIDE_CONTENT, RANGE_OUTSIDE_CONTENT);
        List<TargetRange> results = range.locate(CONTENT);
        assertThat(results).isEmpty();
    }
}