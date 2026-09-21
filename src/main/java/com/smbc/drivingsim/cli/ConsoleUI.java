package com.smbc.drivingsim.cli;

import com.smbc.drivingsim.model.Car;
import com.smbc.drivingsim.model.CarResult;
import com.smbc.drivingsim.model.Field;
import com.smbc.drivingsim.model.Position;
import com.smbc.drivingsim.model.SimulationResult;
import com.smbc.drivingsim.service.SimulationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class ConsoleUI {
    private final Scanner scanner;
    private final SimulationService simulationService;
    private final Set<String> carNames;

    public ConsoleUI(Scanner scanner, SimulationService simulationService) {
        this.scanner = scanner;
        this.simulationService = simulationService;
        this.carNames = new HashSet<>();
    }

    public void start() {
        printWelcome();
        Field field = createField();
        System.out.println("\nYou have created a field of " + field.width() + " x " + field.height() + ".");

        List<Car> cars = new ArrayList<>();

        while (true) {
            printMainMenu();
            String choice = readInput();

            if (InputValidator.isValidMenuChoice(choice, 2)) {
                int option = Integer.parseInt(choice.trim());
                if (option == 1) {
                    Car car = addCar(field);
                    if (car != null) {
                        cars.add(car);
                        carNames.add(car.getName());
                        printCarList(cars);
                    }
                } else if (option == 2) {
                    if (cars.isEmpty()) {
                        System.out.println("No cars added. Please add at least one car.");
                        continue;
                    }
                    printCarList(cars);
                    SimulationResult result = simulationService.runSimulation(field, cars);
                    printSimulationResult(result);
                    if (handleEndMenu()) {
                        break;
                    } else {
                        return;
                    }
                }
            } else {
                System.out.println("Invalid option. Please choose 1 or 2.");
            }
        }
    }

    private void printWelcome() {
        System.out.println("Welcome to Car Crash Java!\n");
    }

    private Field createField() {
        while (true) {
            System.out.print("Please enter the width and height of the simulation field in x y format:\n");
            String input = readInput();
            if (InputValidator.isValidFieldDimensions(input)) {
                int[] dims = InputValidator.parseFieldDimensions(input);
                return new Field(dims[0], dims[1]);
            }
            System.out.println("Invalid input. Please enter two positive integers separated by space.");
        }
    }

    private void printMainMenu() {
        System.out.println("\nPlease choose from the following options:");
        System.out.println("[1] Add a car to field");
        System.out.println("[2] Run simulation");
        System.out.print("\n");
    }

    private Car addCar(Field field) {
        System.out.print("Please enter the name of the car:\n");
        String name = readInput();
        if (!InputValidator.isValidCarName(name, carNames)) {
            System.out.println("Invalid car name or name already exists. Please try again.");
            return null;
        }

        System.out.print("Please enter initial position of car " + name.trim() + " in x y Direction format:\n");
        String positionInput = readInput();
        if (!InputValidator.isValidCarPosition(positionInput, field.width(), field.height())) {
            System.out.println("Invalid position. Please enter x y Direction (e.g., 1 2 N).");
            return null;
        }

        Object[] parsed = InputValidator.parseCarPosition(positionInput);
        Position position = (Position) parsed[0];
        com.smbc.drivingsim.model.Direction direction = (com.smbc.drivingsim.model.Direction) parsed[1];

        System.out.print("Please enter the commands for car " + name.trim() + ":\n");
        String commands = readInput();
        if (!InputValidator.isValidCommands(commands)) {
            System.out.println("Invalid commands. Only L, R, F are allowed.");
            return null;
        }
        commands = InputValidator.sanitizeCommands(commands);

        return new Car(name.trim(), position, direction, commands);
    }

    private void printCarList(List<Car> cars) {
        System.out.println("\nYour current list of cars are:");
        for (Car car : cars) {
            System.out.println("- " + car.getFullStatus());
        }
    }

    private void printSimulationResult(SimulationResult result) {
        System.out.println("\nAfter simulation, the result is:");
        for (CarResult carResult : result.carResults()) {
            System.out.println("- " + carResult);
        }
    }

    private boolean handleEndMenu() {
        while (true) {
            System.out.println("\nPlease choose from the following options:");
            System.out.println("[1] Start over");
            System.out.println("[2] Exit");
            System.out.print("\n");
            String choice = readInput();

            if (InputValidator.isValidMenuChoice(choice, 2)) {
                int option = Integer.parseInt(choice.trim());
                if (option == 1) {
                    return true;
                } else {
                    System.out.println("\nThank you for running the simulation. Goodbye!");
                    return false;
                }
            }
            System.out.println("Invalid option. Please choose 1 or 2.");
        }
    }

    private String readInput() {
        return scanner.nextLine();
    }
}