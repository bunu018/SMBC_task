package com.smbc.drivingsim.cli;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class InputValidatorTest {

    @ParameterizedTest
    @CsvSource({
        "10 10, true",
        "5 5, true",
        "1 1, true",
        "100 200, true",
        "0 10, false",
        "10 0, false",
        "-1 10, false",
        "10 -1, false",
        "10, false",
        "10 10 10, false",
        "abc def, false",
        "10 abc, false",
        "  10  10  , true",
        "'', false"
    })
    void isValidFieldDimensions_variousInputs(String input, boolean expected) {
        assertThat(InputValidator.isValidFieldDimensions(input)).isEqualTo(expected);
    }

    @Test
    void parseFieldDimensions_validInput_returnsArray() {
        int[] dims = InputValidator.parseFieldDimensions("10 10");
        assertThat(dims).containsExactly(10, 10);
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", "B", "Car1", "MyCar", "a"})
    void isValidCarName_uniqueName_returnsTrue(String name) {
        assertThat(InputValidator.isValidCarName(name, Set.of())).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "A"})
    void isValidCarName_duplicateName_returnsFalse(String name) {
        assertThat(InputValidator.isValidCarName(name, Set.of("A"))).isFalse();
    }

    @ParameterizedTest
    @CsvSource({
        "1 2 N, 10, 10, true",
        "0 0 S, 10, 10, true",
        "9 9 W, 10, 10, true",
        "5 5 E, 10, 10, true",
        "-1 2 N, 10, 10, false",
        "1 -2 N, 10, 10, false",
        "10 2 N, 10, 10, false",
        "1 10 N, 10, 10, false",
        "1 2 X, 10, 10, false",
        "1 2, 10, 10, false",
        "1 2 N S, 10, 10, false",
        "abc def N, 10, 10, false"
    })
    void isValidCarPosition_variousInputs(String input, int width, int height, boolean expected) {
        assertThat(InputValidator.isValidCarPosition(input, width, height)).isEqualTo(expected);
    }

    @Test
    void parseCarPosition_validInput_returnsPositionAndDirection() {
        Object[] result = InputValidator.parseCarPosition("1 2 N");
        assertThat(result).hasSize(2);
        assertThat(result[0]).isInstanceOf(com.smbc.drivingsim.model.Position.class);
        assertThat(result[1]).isInstanceOf(com.smbc.drivingsim.model.Direction.class);
        assertThat(((com.smbc.drivingsim.model.Position) result[0]).x()).isEqualTo(1);
        assertThat(((com.smbc.drivingsim.model.Position) result[0]).y()).isEqualTo(2);
        assertThat(result[1]).isEqualTo(com.smbc.drivingsim.model.Direction.N);
    }

    @ParameterizedTest
    @ValueSource(strings = {"FFRFFFFRRL", "LRLRLR", "FFFFF", "LLLLRRRR", "ffrff", "  FFLFF  ", ""})
    void isValidCommands_validCommands_returnsTrue(String commands) {
        assertThat(InputValidator.isValidCommands(commands)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"X", "FXF", "G", "123", "FFRFFX", "LRFZ"})
    void isValidCommands_invalidCommands_returnsFalse(String commands) {
        assertThat(InputValidator.isValidCommands(commands)).isFalse();
    }

    @Test
    void sanitizeCommands_trimsAndUppercases() {
        assertThat(InputValidator.sanitizeCommands("  ffrff  ")).isEqualTo("FFRFF");
        assertThat(InputValidator.sanitizeCommands("FFRFFFFRRL")).isEqualTo("FFRFFFFRRL");
    }

    @ParameterizedTest
    @CsvSource({
        "1, 2, true",
        "2, 2, true",
        "0, 2, false",
        "3, 2, false",
        "abc, 2, false",
        "'', 2, false"
    })
    void isValidMenuChoice_variousInputs(String input, int maxOption, boolean expected) {
        assertThat(InputValidator.isValidMenuChoice(input, maxOption)).isEqualTo(expected);
    }
}