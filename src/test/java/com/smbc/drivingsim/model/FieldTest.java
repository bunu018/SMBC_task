package com.smbc.drivingsim.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FieldTest {

    @Test
    void constructor_validDimensions_createsField() {
        Field field = new Field(10, 10);
        assertThat(field.width()).isEqualTo(10);
        assertThat(field.height()).isEqualTo(10);
    }

    @Test
    void constructor_zeroWidth_throwsException() {
        assertThatThrownBy(() -> new Field(0, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("positive");
    }

    @Test
    void constructor_negativeHeight_throwsException() {
        assertThatThrownBy(() -> new Field(10, -5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("positive");
    }

    @Test
    void isValidPosition_withinBounds_returnsTrue() {
        Field field = new Field(10, 10);
        assertThat(field.isValidPosition(new Position(0, 0))).isTrue();
        assertThat(field.isValidPosition(new Position(9, 9))).isTrue();
        assertThat(field.isValidPosition(new Position(5, 5))).isTrue();
    }

    @Test
    void isValidPosition_outOfBounds_returnsFalse() {
        Field field = new Field(10, 10);
        assertThat(field.isValidPosition(new Position(-1, 0))).isFalse();
        assertThat(field.isValidPosition(new Position(0, -1))).isFalse();
        assertThat(field.isValidPosition(new Position(10, 0))).isFalse();
        assertThat(field.isValidPosition(new Position(0, 10))).isFalse();
        assertThat(field.isValidPosition(new Position(10, 10))).isFalse();
    }

    @Test
    void toString_returnsDimensions() {
        Field field = new Field(10, 10);
        assertThat(field.toString()).isEqualTo("10 x 10");
    }
}