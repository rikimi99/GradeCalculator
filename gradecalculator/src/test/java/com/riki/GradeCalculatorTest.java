package com.riki;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

class GradeCalculatorTest {

    @Test
    void convertsLetterGradesToGradePoints() {
        assertEquals(4.0, Main.gradeConversion("A"));
        assertEquals(3.0, Main.gradeConversion("B"));
        assertEquals(2.0, Main.gradeConversion("C"));
        assertEquals(1.0, Main.gradeConversion("D"));
        assertEquals(0.0, Main.gradeConversion("F"));
        assertEquals(4.0, Main.gradeConversion("a"));
    }

    @Test
    void rejectsUnsupportedGrade() {
        assertThrows(IllegalArgumentException.class,
                () -> Main.gradeConversion("A+"));
    }

    @Test
    void terminalCalculatesCreditWeightedGpa() throws Exception {
        String output = runTerminalCalculator("2\nA\n3\nF\n1\n");

        assertTrue(output.contains("Your GPA is: 3.00"));
    }

    @Test
    void terminalRetriesInvalidGradeAndCredits() throws Exception {
        String output = runTerminalCalculator("1\nX\nA\n0\nabc\n3\n");

        assertTrue(output.contains("Invalid grade."));
        assertTrue(output.contains("Please enter a valid integer greater than 0."));
        assertTrue(output.contains("Your GPA is: 4.00"));
    }

    private String runTerminalCalculator(String input) throws Exception {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();

        try (PrintStream testOutput = new PrintStream(
                capturedOutput, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream(
                    input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(testOutput);
            TerminalMain.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        return capturedOutput.toString(StandardCharsets.UTF_8);
    }
}
