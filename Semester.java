import java.util.*;

public class Semester {
    private int semNumber;
    private List<Course> courses;

    public Semester(int semNumber) {
        this.semNumber = semNumber;
        courses = new ArrayList<>();
    }

    public int getSemNumber() {
        return semNumber;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course c) {
        courses.add(c);
    }

    public void viewCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses in this semester.");
            return;
        }
        for (Course c : courses) {
            System.out.println(c);
        }
    }

    public double calculateGPA() {
        if (courses.isEmpty()) return 0.0;
        int totalCredits = 0;
        double totalPoints = 0;
        for (Course c : courses) {
            totalCredits += c.getCredits();
            totalPoints += c.getGradePoint() * c.getCredits();
        }
        return totalPoints / totalCredits;
    }
}