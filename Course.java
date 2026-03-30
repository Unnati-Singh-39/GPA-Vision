import java.util.*;

public class Course {
    private String name;
    private int credits;
    private String grade;

    public Course(String name, int credits, String grade) {
        this.name = name;
        this.credits = credits;
        this.grade = grade.toUpperCase();
    }

    public int getCredits() {
        return credits;
    }

    public double getGradePoint() {
        switch (grade) {
            case "S": return 10.0;  // super grade
            case "A": return 9.0;
            case "B": return 8.0;
            case "C": return 7.0;
            case "D": return 6.0;
            case "F": return 0.0;
            default: return 0.0;
        }
    }

    public String getGrade() {
        return grade;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " | Credits: " + credits + " | Grade: " + grade;
    }
}