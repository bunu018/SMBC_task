package com.smbc.drivingsim.model;

public record CarResult(String carName, Position finalPosition, Direction finalDirection, boolean collided, String collisionDetails) {

    public static CarResult success(String carName, Position position, Direction direction) {
        return new CarResult(carName, position, direction, false, null);
    }

    public static CarResult collision(String carName, Position position, String collisionDetails) {
        return new CarResult(carName, position, null, true, collisionDetails);
    }

    @Override
    public String toString() {
        if (collided) {
            return carName + ", collides with " + collisionDetails;
        } else {
            return carName + ", " + finalPosition + " " + finalDirection;
        }
    }
}