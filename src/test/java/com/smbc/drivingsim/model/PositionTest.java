package com.smbc.drivingsim.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class PositionTest {

    @Test
    void constructor_createsPosition() {
        Position pos = new Position(3, 5);
        assertThat(pos.x()).isEqualTo(3);
        assertThat(pos.y()).isEqualTo(5);
    }

    @Test
    void move_north_increasesY() {
        Position pos = new Position(2, 2);
        Position moved = pos.move(Direction.N);
        assertThat(moved.x()).isEqualTo(2);
        assertThat(moved.y()).isEqualTo(3);
    }

    @Test
    void move_east_increasesX() {
        Position pos = new Position(2, 2);
        Position moved = pos.move(Direction.E);
        assertThat(moved.x()).isEqualTo(3);
        assertThat(moved.y()).isEqualTo(2);
    }

    @Test
    void move_south_decreasesY() {
        Position pos = new Position(2, 2);
        Position moved = pos.move(Direction.S);
        assertThat(moved.x()).isEqualTo(2);
        assertThat(moved.y()).isEqualTo(1);
    }

    @Test
    void move_west_decreasesX() {
        Position pos = new Position(2, 2);
        Position moved = pos.move(Direction.W);
        assertThat(moved.x()).isEqualTo(1);
        assertThat(moved.y()).isEqualTo(2);
    }

    @ParameterizedTest
    @CsvSource({
        "0, 0, 10, 10, true",
        "9, 9, 10, 10, true",
        "5, 5, 10, 10, true",
        "-1, 0, 10, 10, false",
        "0, -1, 10, 10, false",
        "10, 0, 10, 10, false",
        "0, 10, 10, 10, false",
        "10, 10, 10, 10, false"
    })
    void isWithinBounds_variousPositions(int x, int y, int width, int height, boolean expected) {
        Position pos = new Position(x, y);
        assertThat(pos.isWithinBounds(width, height)).isEqualTo(expected);
    }

    @Test
    void equals_sameCoordinates_returnsTrue() {
        Position pos1 = new Position(3, 5);
        Position pos2 = new Position(3, 5);
        assertThat(pos1).isEqualTo(pos2);
        assertThat(pos1.hashCode()).isEqualTo(pos2.hashCode());
    }

    @Test
    void equals_differentCoordinates_returnsFalse() {
        Position pos1 = new Position(3, 5);
        Position pos2 = new Position(5, 3);
        assertThat(pos1).isNotEqualTo(pos2);
    }

    @Test
    void toString_returnsCorrectFormat() {
        Position pos = new Position(3, 5);
        assertThat(pos.toString()).isEqualTo("(3,5)");
    }
}