package com.smbc.drivingsim.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DirectionTest {

    @Test
    void turnLeft_fromNorth_returnsWest() {
        assertThat(Direction.N.turnLeft()).isEqualTo(Direction.W);
    }

    @Test
    void turnLeft_fromEast_returnsNorth() {
        assertThat(Direction.E.turnLeft()).isEqualTo(Direction.N);
    }

    @Test
    void turnLeft_fromSouth_returnsEast() {
        assertThat(Direction.S.turnLeft()).isEqualTo(Direction.E);
    }

    @Test
    void turnLeft_fromWest_returnsSouth() {
        assertThat(Direction.W.turnLeft()).isEqualTo(Direction.S);
    }

    @Test
    void turnRight_fromNorth_returnsEast() {
        assertThat(Direction.N.turnRight()).isEqualTo(Direction.E);
    }

    @Test
    void turnRight_fromEast_returnsSouth() {
        assertThat(Direction.E.turnRight()).isEqualTo(Direction.S);
    }

    @Test
    void turnRight_fromSouth_returnsWest() {
        assertThat(Direction.S.turnRight()).isEqualTo(Direction.W);
    }

    @Test
    void turnRight_fromWest_returnsNorth() {
        assertThat(Direction.W.turnRight()).isEqualTo(Direction.N);
    }

    @ParameterizedTest
    @ValueSource(chars = {'N', 'n', 'E', 'e', 'S', 's', 'W', 'w'})
    void fromChar_validChar_returnsDirection(char c) {
        Direction direction = Direction.fromChar(c);
        assertThat(direction).isNotNull();
    }

    @Test
    void fromChar_invalidChar_throwsException() {
        assertThatThrownBy(() -> Direction.fromChar('X'))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid direction");
    }

    @Test
    void getDx_and_getDy_correctValues() {
        assertThat(Direction.N.getDx()).isEqualTo(0);
        assertThat(Direction.N.getDy()).isEqualTo(1);
        assertThat(Direction.E.getDx()).isEqualTo(1);
        assertThat(Direction.E.getDy()).isEqualTo(0);
        assertThat(Direction.S.getDx()).isEqualTo(0);
        assertThat(Direction.S.getDy()).isEqualTo(-1);
        assertThat(Direction.W.getDx()).isEqualTo(-1);
        assertThat(Direction.W.getDy()).isEqualTo(0);
    }

    @Test
    void toString_returnsName() {
        assertThat(Direction.N.toString()).isEqualTo("N");
        assertThat(Direction.E.toString()).isEqualTo("E");
        assertThat(Direction.S.toString()).isEqualTo("S");
        assertThat(Direction.W.toString()).isEqualTo("W");
    }
}