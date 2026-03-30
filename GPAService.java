import java.util.List;

public class GPAService {

    public static double calculateGPA(List<Course> courses) {
        double totalPoints = 0;
        int totalCredits = 0;

        for (Course c : courses) {
            totalPoints += c.getGradePoint() * c.getCredits();
            totalCredits += c.getCredits();
        }

        if (totalCredits == 0) return 0;
        return totalPoints / totalCredits;
    }

    // cumulative GPA for semesters
    public static double calculateCumulativeGPA(List<Semester> semesters) {
        double totalPoints = 0;
        int totalCredits = 0;

        for (Semester s : semesters) {
            for (Course c : s.getCourses()) {
                totalPoints += c.getGradePoint() * c.getCredits();
                totalCredits += c.getCredits();
            }
        }

        if (totalCredits == 0) return 0;
        return totalPoints / totalCredits;
    }
}