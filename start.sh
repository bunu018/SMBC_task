#!/bin/bash
# Driving Simulation - Start Script
# This script builds and runs the driving simulation application

set -e

echo "Building Driving Simulation..."

# Build the project
mvn clean package -q

echo "Build successful!"
echo "Starting Driving Simulation..."
echo ""

# Run the application
java -jar target/driving-simulation-1.0-SNAPSHOT.jar