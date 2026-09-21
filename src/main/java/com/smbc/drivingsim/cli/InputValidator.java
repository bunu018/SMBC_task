package com.smbc.drivingsim.cli;

import com.smbc.drivingsim.model.Direction;
import com.smbc.drivingsim.model.Position;

public final class InputValidator {

    public static boolean isValidFieldDimensions(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 2) {
            return false;
        }
        try {
            int width = Integer.parseInt(parts[0]);
            int height = Integer.parseInt(parts[1]);
            return width > 0 && height > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int[] parseFieldDimensions(String input) {
        String[] parts = input.trim().split("\\s+");
        return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
    }

    public static boolean isValidCarName(String name, java.util.Set<String> existingNames) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        String trimmed = name.trim();
        if (existingNames.contains(trimmed)) {
            return false;
        }
        return true;
    }

    public static boolean isValidCarPosition(String input, int fieldWidth, int fieldHeight) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        String[] parts = input.trim().split("\\s+");
        if (parts.length != 3) {
            return false;
        }
        try {
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            Direction.fromChar(parts[2].charAt(0));
            return x >= 0 && x < fieldWidth && y >= 0 && y < fieldHeight;
        } catch (Exception e) {
            return false;
        }
    }

    public static Object[] parseCarPosition(String input) {
        String[] parts = input.trim().split("\\s+");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        Direction direction = Direction.fromChar(parts[2].charAt(0));
        return new Object[]{new Position(x, y), direction};
    }

    public static boolean isValidCommands(String commands) {
        if (commands == null) {
            return false;
        }
        String trimmed = commands.trim().toUpperCase();
        for (char c : trimmed.toCharArray()) {
            if (c != 'L' && c != 'R' && c != 'F') {
                return false;
            }
        }
        return true;
    }

    public static String sanitizeCommands(String commands) {
        return commands.trim().toUpperCase();
    }

    public static boolean isValidMenuChoice(String input, int maxOption) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        try {
            int choice = Integer.parseInt(input.trim());
            return choice >= 1 && choice <= maxOption;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}