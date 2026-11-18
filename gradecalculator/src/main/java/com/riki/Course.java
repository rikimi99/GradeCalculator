package com.riki;

public class Course {

    private double grade;
    private int credits;

    public Course(double grade, int credits) {
        this.grade = grade;
        this.credits = credits;
    }

    public double getGrade() {
        return grade;
    }

    public int getCredits() {
        return credits;
    }
}