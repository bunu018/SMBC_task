package com.smbc.drivingsim.model;

public enum Direction {
    N(0, 1),
    E(1, 0),
    S(0, -1),
    W(-1, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public Direction turnLeft() {
        return switch (this) {
            case N -> W;
            case E -> N;
            case S -> E;
            case W -> S;
        };
    }

    public Direction turnRight() {
        return switch (this) {
            case N -> E;
            case E -> S;
            case S -> W;
            case W -> N;
        };
    }

    public static Direction fromChar(char c) {
        return switch (Character.toUpperCase(c)) {
            case 'N' -> N;
            case 'E' -> E;
            case 'S' -> S;
            case 'W' -> W;
            default -> throw new IllegalArgumentException("Invalid direction: " + c);
        };
    }

    @Override
    public String toString() {
        return name();
    }
}