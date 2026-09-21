package com.smbc.drivingsim.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CarTest {

    @Test
    void constructor_validParams_createsCar() {
        Car car = new Car("A", new Position(1, 2), Direction.N, "FFRFFFFRRL");
        assertThat(car.getName()).isEqualTo("A");
        assertThat(car.getPosition()).isEqualTo(new Position(1, 2));
        assertThat(car.getDirection()).isEqualTo(Direction.N);
        assertThat(car.getCommands()).isEqualTo("FFRFFFFRRL");
        assertThat(car.getCommandIndex()).isEqualTo(0);
        assertThat(car.isStopped()).isFalse();
    }

    @Test
    void constructor_emptyName_throwsException() {
        assertThatThrownBy(() -> new Car("", new Position(1, 2), Direction.N, "F"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructor_nullName_throwsException() {
        assertThatThrownBy(() -> new Car(null, new Position(1, 2), Direction.N, "F"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructor_nullPosition_throwsException() {
        assertThatThrownBy(() -> new Car("A", null, Direction.N, "F"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructor_nullDirection_throwsException() {
        assertThatThrownBy(() -> new Car("A", new Position(1, 2), null, "F"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructor_nullCommands_treatedAsEmpty() {
        Car car = new Car("A", new Position(1, 2), Direction.N, null);
        assertThat(car.getCommands()).isEqualTo("");
    }

    @Test
    void executeNextCommand_F_movesForward() {
        Field field = new Field(10, 10);
        Car car = new Car("A", new Position(1, 2), Direction.N, "F");
        car.executeNextCommand(field);
        assertThat(car.getPosition()).isEqualTo(new Position(1, 3));
        assertThat(car.getCommandIndex()).isEqualTo(1);
    }

    @Test
    void executeNextCommand_L_turnsLeft() {
        Field field = new Field(10, 10);
        Car car = new Car("A", new Position(1, 2), Direction.N, "L");
        car.executeNextCommand(field);
        assertThat(car.getDirection()).isEqualTo(Direction.W);
    }

    @Test
    void executeNextCommand_R_turnsRight() {
        Field field = new Field(10, 10);
        Car car = new Car("A", new Position(1, 2), Direction.N, "R");
        car.executeNextCommand(field);
        assertThat(car.getDirection()).isEqualTo(Direction.E);
    }

    @Test
    void executeNextCommand_F_atBoundary_ignored() {
        Field field = new Field(10, 10);
        Car car = new Car("A", new Position(0, 0), Direction.S, "F");
        car.executeNextCommand(field);
        assertThat(car.getPosition()).isEqualTo(new Position(0, 0));
    }

    @Test
    void executeNextCommand_F_atEastBoundary_ignored() {
        Field field = new Field(10, 10);
        Car car = new Car("A", new Position(9, 5), Direction.E, "F");
        car.executeNextCommand(field);
        assertThat(car.getPosition()).isEqualTo(new Position(9, 5));
    }

    @Test
    void executeNextCommand_invalidCommand_ignored() {
        Field field = new Field(10, 10);
        Car car = new Car("A", new Position(1, 2), Direction.N, "X");
        car.executeNextCommand(field);
        assertThat(car.getPosition()).isEqualTo(new Position(1, 2));
        assertThat(car.getDirection()).isEqualTo(Direction.N);
    }

    @Test
    void executeNextCommand_afterStop_doesNothing() {
        Field field = new Field(10, 10);
        Car car = new Car("A", new Position(1, 2), Direction.N, "FF");
        car.stop();
        car.executeNextCommand(field);
        assertThat(car.getPosition()).isEqualTo(new Position(1, 2));
    }

    @Test
    void executeNextCommand_noMoreCommands_doesNothing() {
        Field field = new Field(10, 10);
        Car car = new Car("A", new Position(1, 2), Direction.N, "F");
        car.executeNextCommand(field);
        car.executeNextCommand(field);
        assertThat(car.getPosition()).isEqualTo(new Position(1, 3));
    }

    @Test
    void hasMoreCommands_returnsCorrectly() {
        Car car = new Car("A", new Position(1, 2), Direction.N, "FF");
        assertThat(car.hasMoreCommands()).isTrue();
        car.executeNextCommand(new Field(10, 10));
        assertThat(car.hasMoreCommands()).isTrue();
        car.executeNextCommand(new Field(10, 10));
        assertThat(car.hasMoreCommands()).isFalse();
    }

    @Test
    void getStatus_returnsCorrectFormat() {
        Car car = new Car("A", new Position(1, 2), Direction.N, "FFRFFFFRRL");
        assertThat(car.getStatus()).isEqualTo("A, (1,2) N");
    }

    @Test
    void getFullStatus_returnsCorrectFormat() {
        Car car = new Car("A", new Position(1, 2), Direction.N, "FFRFFFFRRL");
        assertThat(car.getFullStatus()).isEqualTo("A, (1,2) N, FFRFFFFRRL");
    }

    @Test
    void commands_trimmedAndUppercased() {
        Car car = new Car("A", new Position(1, 2), Direction.N, "  ffrff  ");
        assertThat(car.getCommands()).isEqualTo("FFRFF");
    }
}