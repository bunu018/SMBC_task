package com.smbc.drivingsim.model;

public record Position(int x, int y) {

    public Position move(Direction direction) {
        return new Position(x + direction.getDx(), y + direction.getDy());
    }

    public boolean isWithinBounds(int width, int height) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}