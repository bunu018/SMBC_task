package com.smbc.drivingsim.service;

import com.smbc.drivingsim.model.Car;
import com.smbc.drivingsim.model.CarResult;
import com.smbc.drivingsim.model.Direction;
import com.smbc.drivingsim.model.Field;
import com.smbc.drivingsim.model.Position;
import com.smbc.drivingsim.model.SimulationResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SimulationServiceTest {

    private final SimulationService service = new SimulationService();

    @Test
    void runSimulation_singleCar_scenario1() {
        Field field = new Field(10, 10);
        Car car = new Car("A", new Position(1, 2), Direction.N, "FFRFFFFRRL");
        List<Car> cars = List.of(car);

        SimulationResult result = service.runSimulation(field, cars);

        assertThat(result.hasCollision()).isFalse();
        assertThat(result.carResults()).hasSize(1);
        CarResult carResult = result.carResults().get(0);
        assertThat(carResult.carName()).isEqualTo("A");
        assertThat(carResult.finalPosition()).isEqualTo(new Position(5, 4));
        assertThat(carResult.finalDirection()).isEqualTo(Direction.S);
        assertThat(carResult.collided()).isFalse();
    }

    @Test
    void runSimulation_twoCars_collision_scenario2() {
        Field field = new Field(10, 10);
        Car carA = new Car("A", new Position(1, 2), Direction.N, "FFRFFFFRRL");
        Car carB = new Car("B", new Position(7, 8), Direction.W, "FFLFFFFFFF");
        List<Car> cars = List.of(carA, carB);

        SimulationResult result = service.runSimulation(field, cars);

        assertThat(result.hasCollision()).isTrue();
        assertThat(result.carResults()).hasSize(2);

        CarResult resultA = result.carResults().stream()
                .filter(r -> r.carName().equals("A"))
                .findFirst().orElseThrow();
        CarResult resultB = result.carResults().stream()
                .filter(r -> r.carName().equals("B"))
                .findFirst().orElseThrow();

        assertThat(resultA.collided()).isTrue();
        assertThat(resultA.collisionDetails()).isEqualTo("B at (5,4) at step 7");
        assertThat(resultA.finalPosition()).isEqualTo(new Position(5, 4));

        assertThat(resultB.collided()).isTrue();
        assertThat(resultB.collisionDetails()).isEqualTo("A at (5,4) at step 7");
        assertThat(resultB.finalPosition()).isEqualTo(new Position(5, 4));
    }

    @Test
    void runSimulation_twoCars_noCollision() {
        Field field = new Field(10, 10);
        Car carA = new Car("A", new Position(1, 2), Direction.N, "FF");
        Car carB = new Car("B", new Position(7, 8), Direction.W, "FF");
        List<Car> cars = List.of(carA, carB);

        SimulationResult result = service.runSimulation(field, cars);

        assertThat(result.hasCollision()).isFalse();
        assertThat(result.carResults()).hasSize(2);
    }

    @Test
    void runSimulation_threeCars_multipleCollisions() {
        Field field = new Field(10, 10);
        Car carA = new Car("A", new Position(1, 2), Direction.N, "FFRFFFF");
        Car carB = new Car("B", new Position(7, 8), Direction.W, "FFLFFFF");
        Car carC = new Car("C", new Position(5, 4), Direction.S, "F");
        List<Car> cars = List.of(carA, carB, carC);

        SimulationResult result = service.runSimulation(field, cars);

        assertThat(result.hasCollision()).isTrue();
        assertThat(result.carResults()).hasSize(3);
    }

    @Test
    void runSimulation_emptyCarList_returnsEmptyResult() {
        Field field = new Field(10, 10);
        SimulationResult result = service.runSimulation(field, List.of());

        assertThat(result.hasCollision()).isFalse();
        assertThat(result.carResults()).isEmpty();
    }

    @Test
    void runSimulation_carStopsAtBoundary() {
        Field field = new Field(5, 5);
        Car car = new Car("A", new Position(4, 2), Direction.E, "FF");
        List<Car> cars = List.of(car);

        SimulationResult result = service.runSimulation(field, cars);

        assertThat(result.hasCollision()).isFalse();
        CarResult carResult = result.carResults().get(0);
        assertThat(carResult.finalPosition()).isEqualTo(new Position(4, 2));
    }

    @Test
    void runSimulation_carWithEmptyCommands_staysInPlace() {
        Field field = new Field(10, 10);
        Car car = new Car("A", new Position(1, 2), Direction.N, "");
        List<Car> cars = List.of(car);

        SimulationResult result = service.runSimulation(field, cars);

        assertThat(result.hasCollision()).isFalse();
        CarResult carResult = result.carResults().get(0);
        assertThat(carResult.finalPosition()).isEqualTo(new Position(1, 2));
        assertThat(carResult.finalDirection()).isEqualTo(Direction.N);
    }

    @Test
    void runSimulation_differentCommandLengths_handlesCorrectly() {
        Field field = new Field(10, 10);
        Car carA = new Car("A", new Position(1, 2), Direction.N, "FFRFFFFRRL");
        Car carB = new Car("B", new Position(5, 5), Direction.W, "FF");
        List<Car> cars = List.of(carA, carB);

        SimulationResult result = service.runSimulation(field, cars);

        assertThat(result.hasCollision()).isFalse();
        assertThat(result.carResults()).hasSize(2);
    }

    @Test
    void runSimulation_fiveCars_simultaneousExecution() {
        Field field = new Field(10, 10);
        Car carA = new Car("A", new Position(0, 0), Direction.E, "FFFFF");
        Car carB = new Car("B", new Position(0, 1), Direction.E, "FFFFF");
        Car carC = new Car("C", new Position(0, 2), Direction.E, "FFFFF");
        Car carD = new Car("D", new Position(0, 3), Direction.E, "FFFFF");
        Car carE = new Car("E", new Position(0, 4), Direction.E, "FFFFF");
        List<Car> cars = List.of(carA, carB, carC, carD, carE);

        SimulationResult result = service.runSimulation(field, cars);

        assertThat(result.hasCollision()).isFalse();
        assertThat(result.carResults()).hasSize(5);
    }

    @Test
    void runSimulation_collisionAtStep1() {
        Field field = new Field(10, 10);
        Car carA = new Car("A", new Position(0, 0), Direction.E, "F");
        Car carB = new Car("B", new Position(2, 0), Direction.W, "F");
        List<Car> cars = List.of(carA, carB);

        SimulationResult result = service.runSimulation(field, cars);

        assertThat(result.hasCollision()).isTrue();
        assertThat(result.carResults()).hasSize(2);
        for (CarResult r : result.carResults()) {
            assertThat(r.collided()).isTrue();
            assertThat(r.collisionDetails()).contains("at step 1");
        }
    }
}