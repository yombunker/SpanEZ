package com.bunk3r.spanez.locators;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Part of SpanEZ
 * Created by joragu on 1/13/2017.
 */
public class TargetRangeTest {
    private static final int RANGE_ZERO = 0;
    private static final int RANGE_ONE = 1;
    private static final int RANGE_FIVE = 5;
    private static final int RANGE_TEN = 10;

    @Test
    public void valid_range() {
        TargetRange targetRange = TargetRange.from(RANGE_ZERO, RANGE_TEN);
        assertThat(targetRange).isNotNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void exception_thrown_when_range_is_invalid() {
        TargetRange.from(RANGE_TEN, RANGE_ZERO);
    }

    @Test
    public void hashcode_must_match() {
        TargetRange targetRange = TargetRange.from(RANGE_ONE, RANGE_FIVE);
        final int hashCode = targetRange.hashCode();

        int expectedResult = 36;
        assertThat(hashCode).isEqualTo(expectedResult);
    }

    @Test
    public void hashcode_must_not_match() {
        TargetRange targetRange = TargetRange.from(RANGE_ONE, RANGE_FIVE);
        final int hashCode = targetRange.hashCode();

        TargetRange otherRange = TargetRange.from(RANGE_ZERO, RANGE_TEN);
        final int otherHashCode = otherRange.hashCode();
        assertThat(hashCode).isNotEqualTo(otherHashCode);
    }

    @Test
    public void target_range_must_be_equal() {
        TargetRange targetRange = TargetRange.from(RANGE_ZERO, RANGE_TEN);

        TargetRange expectedResult = TargetRange.from(RANGE_ZERO, RANGE_TEN);
        assertThat(targetRange).isEqualTo(expectedResult);
    }


    @Test
    public void target_range_compare_same_instance() {
        TargetRange targetRange = TargetRange.from(RANGE_ZERO, RANGE_TEN);

        assertThat(targetRange).isSameAs(targetRange);
    }

    @Test
    public void target_range_must_not_be_equal() {
        TargetRange targetRange = TargetRange.from(RANGE_ZERO, RANGE_TEN);

        TargetRange notExpectedResult = TargetRange.from(RANGE_ONE, RANGE_FIVE);
        assertThat(targetRange).isNotEqualTo(notExpectedResult);
    }

    @Test
    public void target_range_start_must_not_be_equal() {
        TargetRange targetRange = TargetRange.from(RANGE_ZERO, RANGE_TEN);

        TargetRange notExpectedResult = TargetRange.from(RANGE_ONE, RANGE_TEN);
        assertThat(targetRange).isNotEqualTo(notExpectedResult);
    }

    @Test
    public void target_range_must_not_be_equal_they_are_different_types() {
        TargetRange targetRange = TargetRange.from(RANGE_ZERO, RANGE_TEN);
        assertThat(targetRange).isNotEqualTo("a different object type");
    }

    @Test
    public void target_range_end_must_not_be_equal() {
        TargetRange targetRange = TargetRange.from(RANGE_ZERO, RANGE_TEN);

        TargetRange notExpectedResult = TargetRange.from(RANGE_ZERO, RANGE_FIVE);
        assertThat(targetRange).isNotEqualTo(notExpectedResult);
    }

    @Test
    public void getters_are_just_for_read_only() {
        TargetRange targetRange = TargetRange.from(RANGE_ZERO, RANGE_TEN);

        assertThat(targetRange.getStart()).isEqualTo(RANGE_ZERO);
        assertThat(targetRange.getEnd()).isEqualTo(RANGE_TEN);
    }
}