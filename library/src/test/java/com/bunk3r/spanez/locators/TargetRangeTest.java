package com.bunk3r.spanez.locators;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Part of SpanEZ
 * Created by joragu on 1/13/2017.
 */
public class TargetRangeTest {

    @Test
    public void valid_range() {
        final int rangeStart = 0;
        final int rangeEnd = 10;

        TargetRange targetRange = TargetRange.from(rangeStart, rangeEnd);
        assertNotNull("Failed to create range", targetRange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void exception_thrown_when_range_is_invalid() {
        final int rangeStart = 10;
        final int rangeEnd = 9;

        TargetRange.from(rangeStart, rangeEnd);
    }

    @Test
    public void hashcode_must_match() {
        final int HASH_CODE_RESULT = 36;
        final int rangeStart = 1;
        final int rangeEnd = 5;

        TargetRange targetRange = TargetRange.from(rangeStart, rangeEnd);
        final int hashCode = targetRange.hashCode();

        assertTrue("The hashcode function has changed", hashCode == HASH_CODE_RESULT);
    }

    @Test
    public void hashcode_must_not_match() {
        final int rangeStart = 1;
        final int rangeEnd = 5;

        TargetRange targetRange = TargetRange.from(rangeStart, rangeEnd);
        TargetRange otherRange = TargetRange.from(rangeStart, rangeStart);
        final int hashCode = targetRange.hashCode();
        final int otherHashCode = otherRange.hashCode();

        assertFalse("The hashcode function has changed", hashCode == otherHashCode);
    }

    @Test
    public void target_range_must_be_equal() {
        final int rangeStart = 1;
        final int rangeEnd = 5;

        TargetRange targetRange = TargetRange.from(rangeStart, rangeEnd);
        TargetRange expectedResult = TargetRange.from(rangeStart, rangeEnd);

        assertTrue("The two objects should be equal", expectedResult.equals(targetRange));
    }


    @SuppressWarnings("UnnecessaryLocalVariable")
    @Test
    public void target_range_compare_same_instance() {
        final int rangeStart = 1;
        final int rangeEnd = 5;

        TargetRange targetRange = TargetRange.from(rangeStart, rangeEnd);
        TargetRange expectedResult = targetRange;

        assertTrue("The two objects should be equal", expectedResult.equals(targetRange));
    }

    @Test
    public void target_range_must_not_be_equal() {
        final int rangeStart = 1;
        final int rangeEnd = 5;

        TargetRange targetRange = TargetRange.from(rangeStart, rangeEnd);
        TargetRange notExpectedResult = TargetRange.from(0, 0);

        assertFalse("The two objects should be different", notExpectedResult.equals(targetRange));
    }

    @Test
    public void target_range_start_must_not_be_equal() {
        final int rangeStart = 1;
        final int rangeEnd = 5;

        TargetRange targetRange = TargetRange.from(rangeStart, rangeEnd);
        TargetRange notExpectedResult = TargetRange.from(rangeEnd, rangeEnd);

        assertFalse("The two objects should be different", notExpectedResult.equals(targetRange));
    }

//    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void target_range_must_not_be_equal_they_are_different_types() {
        final int rangeStart = 1;
        final int rangeEnd = 5;

        TargetRange targetRange = TargetRange.from(rangeStart, rangeEnd);
        Object notExpectedResult = new Object();

        assertFalse("The two objects should be different", notExpectedResult.equals(targetRange));
    }

    @Test
    public void target_range_end_must_not_be_equal() {
        final int rangeStart = 1;
        final int rangeEnd = 5;

        TargetRange targetRange = TargetRange.from(rangeStart, rangeEnd);
        TargetRange notExpectedResult = TargetRange.from(rangeStart, rangeStart);

        assertFalse("The two objects should be different", notExpectedResult.equals(targetRange));
    }

    @Test
    public void getters_are_just_for_read_only() {
        final int rangeStart = 1;
        final int rangeEnd = 5;

        TargetRange targetRange = TargetRange.from(rangeStart, rangeEnd);

        assertTrue("The start range has changed", targetRange.getStart() == rangeStart);
        assertTrue("The end range has changed", targetRange.getEnd() == rangeEnd);
    }

}