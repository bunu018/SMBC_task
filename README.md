# Driving Simulation - Instruction Manual

## Overview
A Java CLI application that simulates cars moving on a rectangular field with collision detection. Cars execute commands (L=left, R=right, F=forward) step-by-step simultaneously. Collisions are reported with the step number and position.

## Requirements
- **Java 17+** (java in PATH, JAVA_HOME set)
- **Maven 3.6+** (for building)
- Internet access (for dependency download on first build)

## Building the Application

### Option 1: Using the start script (Linux/Mac)
```bash
chmod +x start.sh
./start.sh
```

### Option 2: Manual build (Windows/Linux/Mac)
```bash
mvn clean package
```

This creates a shaded JAR at `target/driving-simulation-1.0-SNAPSHOT.jar` with all dependencies included.

## Running the Application

### Linux/Mac
```bash
java -jar target/driving-simulation-1.0-SNAPSHOT.jar
```

### Windows (PowerShell)
```powershell
java -jar target\driving-simulation-1.0-SNAPSHOT.jar
```

## Using the Simulation

The application runs interactively in the console with a menu-driven interface:

### Step 1: Create Field
```
Welcome to Car Crash Java!

Please enter the width and height of the simulation field in x y format:
```
Enter two positive integers separated by space (e.g., `10 10`)

### Step 2: Main Menu
```
Please choose from the following options:
[1] Add a car to field
[2] Run simulation
```

**Option 1 - Add a Car:**
```
Please enter the name of the car:
```
Enter a unique name (e.g., `A`)

```
Please enter initial position of car A in x y Direction format:
```
Enter: x-coordinate, y-coordinate, and direction (N/E/S/W)
Example: `1 2 N`

```
Please enter the commands for car A:
```
Enter a string of commands using only L, R, F (case-insensitive)
Example: `FFRFFFFRRL`

The car is added and you return to the main menu.

**Option 2 - Run Simulation:**
Requires at least one car. Shows all cars, runs simulation, displays results.

### Step 3: Simulation Results
```
After simulation, the result is:
- A, (5,4) S
```
Or if collision:
```
After simulation, the result is:
- A, collides with B at (5,4) at step 7
- B, collides with A at (5,4) at step 7
```

### Step 4: End Menu
```
Please choose from the following options:
[1] Start over
[2] Exit
```
- **Start over**: Creates new field, clears all cars
- **Exit**: Terminates application

## Command Reference

| Command | Action |
|---------|--------|
| L | Rotate 90° left |
| R | Rotate 90° right |
| F | Move forward 1 grid (ignored if out of bounds) |

## Field Coordinates
- Bottom-left: (0, 0)
- Top-right: (width-1, height-1)
- Example: 10x10 field has valid coordinates 0-9 in both axes

## Collision Rules
- All cars move **simultaneously** each step
- Collision checked **after each step**
- First collision stops the simulation
- All collided cars reported with position and step number
- Remaining cars show final position

## Example Session (Scenario 1 - Single Car)
```
Welcome to Car Crash Java!

Please enter the width and height of the simulation field in x y format:
10 10

You have created a field of 10 x 10.

Please choose from the following options:
[1] Add a car to field
[2] Run simulation

1

Please enter the name of the car:
A

Please enter initial position of car A in x y Direction format:
1 2 N

Please enter the commands for car A:
FFRFFFFRRL

Your current list of cars are:
- A, (1,2) N, FFRFFFFRRL

Please choose from the following options:
[1] Add a car to field
[2] Run simulation

2

Your current list of cars are:
- A, (1,2) N, FFRFFFFRRL

After simulation, the result is:
- A, (5,4) S

Please choose from the following options:
[1] Start over
[2] Exit

2

Thank you for running the simulation. Goodbye!
```

## Example Session (Scenario 2 - Collision)
```
... (same field setup) ...

Please choose from the following options:
[1] Add a car to field
[2] Run simulation

1

Please enter the name of the car:
A
Please enter initial position of car A in x y Direction format:
1 2 N
Please enter the commands for car A:
FFRFFFFRRL

... (add car B) ...

Please enter the name of the car:
B
Please enter initial position of car B in x y Direction format:
7 8 W
Please enter the commands for car B:
FFLFFFFFFF

Please choose from the following options:
[2] Run simulation

After simulation, the result is:
- A, collides with B at (5,4) at step 7
- B, collides with A at (5,4) at step 7
```

## Testing
Run all unit tests:
```bash
mvn test
```
131 tests covering models, services, and input validation.

## Test Scenario Files
The following test input files are included for automated testing:

### Scenario 1 - Single Car (test_scenario1.txt)
```text
10 10
1
A
1 2 N
FFRFFFFRRL
2
2
```

Run Scenario 1:
```bash
# Linux/Mac
cat test_scenario1.txt | java -jar target/driving-simulation-1.0-SNAPSHOT.jar

# Windows PowerShell
Get-Content test_scenario1.txt | java -jar target/driving-simulation-1.0-SNAPSHOT.jar
```

Expected output: `- A, (5,4) S`

### Scenario 2 - Collision (test_scenario2.txt)
```text
10 10
1
A
1 2 N
FFRFFFFRRL
1
B
7 8 W
FFLFFFFFFF
2
2
```

Run Scenario 2:
```bash
# Linux/Mac
cat test_scenario2.txt | java -jar target/driving-simulation-1.0-SNAPSHOT.jar

# Windows PowerShell
Get-Content test_scenario2.txt | java -jar target/driving-simulation-1.0-SNAPSHOT.jar
```

Expected output:
```
- A, collides with B at (5,4) at step 7
- B, collides with A at (5,4) at step 7
```

### Start Over Test (test_startover.txt)
```text
10 10
1
A
1 2 N
FFRFFFFRRL
2
1
5 5
1
B
0 0 N
FF
2
2
```

Run Start Over test:
```bash
# Linux/Mac
cat test_startover.txt | java -jar target/driving-simulation-1.0-SNAPSHOT.jar

# Windows PowerShell
Get-Content test_startover.txt | java -jar target/driving-simulation-1.0-SNAPSHOT.jar
```

## Clean Build
```bash
mvn clean
```
Removes all compiled classes and JAR files.

## Troubleshooting

| Issue | Solution |
|-------|----------|
| "java not found" | Install JDK 17+ and add to PATH |
| "mvn not found" | Install Maven 3.6+ and add to PATH |
| Build fails | Run `mvn clean` then `mvn package` |
| JAR won't run | Ensure you're using the shaded JAR in `target/` |

## Project Structure
```
src/main/java/com/smbc/drivingsim/
├── model/          # Domain objects (Field, Car, Position, Direction)
├── service/        # Simulation logic & collision detection
├── cli/            # Console UI & input validation
└── DrivingSimApplication.java  # Entry point
```