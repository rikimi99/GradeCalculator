package com.riki;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Main extends Application {

    @FXML
    private TextField courseCountField;

    @FXML
    private TextField gradeField;

    @FXML
    private TextField creditsField;

    @FXML
    private Label courseLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label gpaLabel;

    @FXML
    private Button addCourseButton;

    @FXML
    private Button calculateButton;

    private final List<Course> courses = new ArrayList<>();
    private int courseCount;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(
                getClass().getResource("/GPA_Calculator.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("GPA Calculator");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void initialize() {
        courseLabel.setText("Course grade:");
        gradeField.setDisable(true);
        creditsField.setDisable(true);
        addCourseButton.setDisable(false);
        addCourseButton.setText("Set Course Count");
        statusLabel.setText("Enter the number of courses to begin.");
    }

    @FXML
    private void addCourse() {
        try {
            if (courseCount == 0) {
                courseCount = parsePositiveInteger(
                        courseCountField.getText(), "Number of courses");

                if (courses.size() >= courseCount) {
                    statusLabel.setText("All courses have already been added.");
                    return;
                }

                courseCountField.setDisable(true);
                gradeField.setDisable(false);
                creditsField.setDisable(false);
                addCourseButton.setText("Add Course");
            }

            if (courses.size() >= courseCount) {
                statusLabel.setText("All courses have already been added.");
                return;
            }

            String grade = gradeField.getText().trim().toUpperCase();
            int credits = parsePositiveInteger(
                    creditsField.getText(), "Course credits");
            double gradePoints = gradeConversion(grade);

            courses.add(new Course(gradePoints, credits));
            courseLabel.setText(
                    "Course " + (courses.size() + 1) + " grade:");

            gradeField.clear();
            creditsField.clear();
            gradeField.requestFocus();

            if (courses.size() == courseCount) {
                gradeField.setDisable(true);
                creditsField.setDisable(true);
                addCourseButton.setDisable(true);
                addCourseButton.setText("All Courses Added");
                statusLabel.setText(
                        "All courses added. Select Calculate GPA.");
            } else {
                statusLabel.setText(
                        "Course " + courses.size() + " added.");
            }
        } catch (IllegalArgumentException exception) {
            statusLabel.setText(exception.getMessage());
        }
    }

    @FXML
    private void calculateGpa() {
        if (courses.isEmpty()) {
            statusLabel.setText("Add at least one course first.");
            gpaLabel.setText("GPA: --");
            return;
        }

        if (courseCount > 0 && courses.size() < courseCount) {
            statusLabel.setText(
                    "Add " + (courseCount - courses.size())
                            + " more course(s) before calculating.");
            return;
        }

        int totalCredits = 0;
        double weightedSum = 0.0;

        for (Course course : courses) {
            weightedSum += course.getGrade() * course.getCredits();
            totalCredits += course.getCredits();
        }

        double gpa = totalCredits > 0
                ? weightedSum / totalCredits
                : 0.0;

        gpaLabel.setText(String.format("GPA: %.2f", gpa));
        statusLabel.setText("GPA calculated successfully.");
    }

    @FXML
    private void resetCalculator() {
        courses.clear();
        courseCount = 0;
        courseCountField.clear();
        courseCountField.setDisable(false);
        gradeField.clear();
        gradeField.setDisable(true);
        creditsField.clear();
        creditsField.setDisable(true);
        addCourseButton.setDisable(false);
        addCourseButton.setText("Set Course Count");
        courseLabel.setText("Course grade:");
        statusLabel.setText("Enter the number of courses to begin.");
        gpaLabel.setText("GPA: --");
        courseCountField.requestFocus();
    }

    private int parsePositiveInteger(String text, String fieldName) {
        try {
            int value = Integer.parseInt(text.trim());
            if (value <= 0) {
                throw new IllegalArgumentException(
                        fieldName + " must be greater than 0.");
            }
            return value;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    fieldName + " must be a valid integer.");
        }
    }

    public static double gradeConversion(String gradeString) {
        return switch (gradeString.toUpperCase()) {
            case "A" -> 4.0;
            case "B" -> 3.0;
            case "C" -> 2.0;
            case "D" -> 1.0;
            case "F" -> 0.0;
            default -> throw new IllegalArgumentException(
                    "Invalid grade: " + gradeString);
        };
    }
}