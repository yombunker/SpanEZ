package com.bunk3r.spanez.locators;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Part of SpanEZ
 * Created by joragu on 1/13/2017.
 */
public class RangeTest {

    @Test
    public void valid_range() {
        final int rangeStart = 0;
        final int rangeEnd = 10;

        Range range = Range.from(rangeStart, rangeEnd);
        assertNotNull("Failed to create range", range);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exception_thrown_when_range_is_invalid() {
        final int rangeStart = 10;
        final int rangeEnd = 9;

        Range.from(rangeStart, rangeEnd);
    }

    @Test
    public void range_should_not_change() {
        final int rangeStart = 0;
        final int rangeEnd = 10;

        Range range = Range.from(rangeStart, rangeEnd);
        List<TargetRange> results = range.locate("");
        assertTrue("There should only be one result", results.size() == 1);

        TargetRange result = results.get(0);
        TargetRange expectedResult = TargetRange.from(rangeStart, rangeEnd);
        assertTrue("The  range should not change", expectedResult.equals(result));
    }

}