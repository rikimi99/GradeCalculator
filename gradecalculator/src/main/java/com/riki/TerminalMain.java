package com.riki;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class TerminalMain {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int courseCount = readPositiveInteger(
                    scanner, "Please enter the number of your courses: ");
            List<Course> courses = new ArrayList<>();

            for (int i = 1; i <= courseCount; i++) {
                double gradePoints = readGrade(scanner, i);
                int credits = readPositiveInteger(
                        scanner, "Enter the number of credits: ");
                courses.add(new Course(gradePoints, credits));
            }

            double weightedSum = 0.0;
            int totalCredits = 0;
            for (Course course : courses) {
                weightedSum += course.getGrade() * course.getCredits();
                totalCredits += course.getCredits();
            }

            System.out.printf(
                    Locale.ROOT, "Your GPA is: %.2f%n",
                    weightedSum / totalCredits);
        }
    }

    private static int readPositiveInteger(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            int value;
            try {
                value = Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid integer greater than 0.");
                continue;
            }

            if (value > 0) {
                return value;
            }

            System.out.println("Please enter a valid integer greater than 0.");
        }
    }

    private static double readGrade(Scanner scanner, int courseNumber) {
        while (true) {
            System.out.print("Please enter your grade for course "
                    + courseNumber + " (A, B, C, D, or F): ");
            String grade = scanner.nextLine().trim().toUpperCase();

            try {
                return gradeConversion(grade);
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private static double gradeConversion(String grade) {
        return switch (grade) {
            case "A" -> 4.0;
            case "B" -> 3.0;
            case "C" -> 2.0;
            case "D" -> 1.0;
            case "F" -> 0.0;
            default -> throw new IllegalArgumentException(
                    "Invalid grade. Enter A, B, C, D, or F.");
        };
    }
}
