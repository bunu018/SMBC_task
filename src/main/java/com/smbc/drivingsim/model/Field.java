package com.smbc.drivingsim.model;

public record Field(int width, int height) {

    public Field {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Field dimensions must be positive");
        }
    }

    public boolean isValidPosition(Position position) {
        return position.isWithinBounds(width, height);
    }

    @Override
    public String toString() {
        return width + " x " + height;
    }
}