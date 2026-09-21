package com.smbc.drivingsim;

import com.smbc.drivingsim.cli.ConsoleUI;
import com.smbc.drivingsim.service.SimulationService;

import java.util.Scanner;

public class DrivingSimApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SimulationService simulationService = new SimulationService();
        ConsoleUI consoleUI = new ConsoleUI(scanner, simulationService);

        try {
            consoleUI.start();
        } finally {
            scanner.close();
        }
    }
}