package com.smbc.drivingsim.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SimulationResultTest {

    @Test
    void constructor_createsResult() {
        CarResult result1 = CarResult.success("A", new Position(1, 2), Direction.N);
        CarResult result2 = CarResult.success("B", new Position(3, 4), Direction.S);
        SimulationResult simResult = new SimulationResult(List.of(result1, result2), false);

        assertThat(simResult.carResults()).hasSize(2);
        assertThat(simResult.hasCollision()).isFalse();
    }

    @Test
    void constructor_withCollision_returnsTrue() {
        CarResult result1 = CarResult.collision("A", new Position(1, 2), "B at (1,2) at step 1");
        SimulationResult simResult = new SimulationResult(List.of(result1), true);

        assertThat(simResult.hasCollision()).isTrue();
    }
}