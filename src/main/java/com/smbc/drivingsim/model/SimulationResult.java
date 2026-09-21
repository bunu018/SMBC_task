package com.smbc.drivingsim.model;

import java.util.List;

public record SimulationResult(List<CarResult> carResults, boolean hasCollision) {
}