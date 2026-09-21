package com.smbc.drivingsim.service;

import com.smbc.drivingsim.model.Car;
import com.smbc.drivingsim.model.CarResult;
import com.smbc.drivingsim.model.Field;
import com.smbc.drivingsim.model.Position;
import com.smbc.drivingsim.model.SimulationResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationService {

    public SimulationResult runSimulation(Field field, List<Car> cars) {
        if (cars.isEmpty()) {
            return new SimulationResult(List.of(), false);
        }

        int maxSteps = cars.stream()
                .mapToInt(car -> car.getCommands().length())
                .max()
                .orElse(0);

        List<CarResult> results = new ArrayList<>();
        boolean collisionOccurred = false;

        for (int step = 0; step < maxSteps; step++) {
            List<Car> activeCars = new ArrayList<>();
            for (Car car : cars) {
                if (!car.isStopped() && car.hasMoreCommands()) {
                    activeCars.add(car);
                }
            }

            if (activeCars.isEmpty()) {
                break;
            }

            for (Car car : activeCars) {
                car.executeNextCommand(field);
            }

            Map<Position, List<Car>> positionMap = new HashMap<>();
            for (Car car : activeCars) {
                positionMap.computeIfAbsent(car.getPosition(), k -> new ArrayList<>()).add(car);
            }

            for (Map.Entry<Position, List<Car>> entry : positionMap.entrySet()) {
                List<Car> carsAtPosition = entry.getValue();
                if (carsAtPosition.size() > 1) {
                    collisionOccurred = true;
                    Position collisionPos = entry.getKey();
                    int stepNumber = step + 1;

                    for (Car car : carsAtPosition) {
                        List<String> otherNames = carsAtPosition.stream()
                                .filter(c -> c != car)
                                .map(Car::getName)
                                .toList();
                        String collisionDetails = String.join(", ", otherNames) +
                                " at " + collisionPos + " at step " + stepNumber;
                        car.stop();
                        results.add(CarResult.collision(car.getName(), collisionPos, collisionDetails));
                    }

                    for (Car car : cars) {
                        if (!car.isStopped()) {
                            car.stop();
                        }
                    }

                    for (Car car : cars) {
                        if (results.stream().noneMatch(r -> r.carName().equals(car.getName()))) {
                            results.add(CarResult.success(car.getName(), car.getPosition(), car.getDirection()));
                        }
                    }

                    return new SimulationResult(results, true);
                }
            }
        }

        for (Car car : cars) {
            results.add(CarResult.success(car.getName(), car.getPosition(), car.getDirection()));
        }

        return new SimulationResult(results, false);
    }
}