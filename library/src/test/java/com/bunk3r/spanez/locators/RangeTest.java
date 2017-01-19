package com.bunk3r.spanez.locators;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Part of SpanEZ
 * Created by joragu on 1/13/2017.
 */
public class RangeTest {
    private static final String CONTENT_DOES_NOT_MATTER = "";
    private static final int RANGE_START = 0;
    private static final int RANGE_END = 10;

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
        List<TargetRange> results = range.locate(CONTENT_DOES_NOT_MATTER);

        TargetRange expectedResult = TargetRange.from(RANGE_START, RANGE_END);
        assertThat(results).hasSize(1)
                           .containsOnly(expectedResult);
    }
}