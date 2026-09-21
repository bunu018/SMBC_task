package com.smbc.drivingsim.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CarResultTest {

    @Test
    void success_createsCorrectResult() {
        CarResult result = CarResult.success("A", new Position(1, 2), Direction.N);

        assertThat(result.carName()).isEqualTo("A");
        assertThat(result.finalPosition()).isEqualTo(new Position(1, 2));
        assertThat(result.finalDirection()).isEqualTo(Direction.N);
        assertThat(result.collided()).isFalse();
        assertThat(result.collisionDetails()).isNull();
    }

    @Test
    void collision_createsCorrectResult() {
        CarResult result = CarResult.collision("A", new Position(5, 4), "B at (5,4) at step 7");

        assertThat(result.carName()).isEqualTo("A");
        assertThat(result.finalPosition()).isEqualTo(new Position(5, 4));
        assertThat(result.finalDirection()).isNull();
        assertThat(result.collided()).isTrue();
        assertThat(result.collisionDetails()).isEqualTo("B at (5,4) at step 7");
    }

    @Test
    void toString_success_returnsPositionAndDirection() {
        CarResult result = CarResult.success("A", new Position(5, 4), Direction.S);
        assertThat(result.toString()).isEqualTo("A, (5,4) S");
    }

    @Test
    void toString_collision_returnsCollisionDetails() {
        CarResult result = CarResult.collision("A", new Position(5, 4), "B at (5,4) at step 7");
        assertThat(result.toString()).isEqualTo("A, collides with B at (5,4) at step 7");
    }
}