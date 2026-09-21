package com.smbc.drivingsim.model;

import java.util.ArrayList;
import java.util.List;

public final class Car {
    private final String name;
    private Position position;
    private Direction direction;
    private final String commands;
    private int commandIndex;
    private boolean stopped;

    public Car(String name, Position position, Direction direction, String commands) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Car name cannot be empty");
        }
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        if (direction == null) {
            throw new IllegalArgumentException("Direction cannot be null");
        }
        this.name = name.trim();
        this.position = position;
        this.direction = direction;
        this.commands = commands != null ? commands.trim().toUpperCase() : "";
        this.commandIndex = 0;
        this.stopped = false;
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getCommands() {
        return commands;
    }

    public int getCommandIndex() {
        return commandIndex;
    }

    public boolean hasMoreCommands() {
        return commandIndex < commands.length();
    }

    public boolean isStopped() {
        return stopped;
    }

    public void stop() {
        this.stopped = true;
    }

    public void executeNextCommand(Field field) {
        if (stopped || !hasMoreCommands()) {
            return;
        }

        char command = commands.charAt(commandIndex);
        commandIndex++;

        switch (command) {
            case 'L' -> direction = direction.turnLeft();
            case 'R' -> direction = direction.turnRight();
            case 'F' -> {
                Position newPosition = position.move(direction);
                if (field.isValidPosition(newPosition)) {
                    position = newPosition;
                }
            }
            default -> {
            }
        }
    }

    public String getStatus() {
        return name + ", " + position + " " + direction;
    }

    public String getFullStatus() {
        return name + ", " + position + " " + direction + ", " + commands;
    }

    @Override
    public String toString() {
        return getStatus();
    }
}