import java.util.*;
import java.io.*;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static List<Semester> semesters = new ArrayList<>();

    public static void main(String[] args) {
        int choice = 0;

        while (choice != 9) {
            System.out.println("\n--- Campus Course Manager ---");
            System.out.println("1. Add Courses");
            System.out.println("2. View Courses");
            System.out.println("3. Semester GPA");
            System.out.println("4. Cumulative GPA");
            System.out.println("5. Predict GPA for Future Semester");
            System.out.println("6. Required GPA for Target");
            System.out.println("7. Performance Analysis");
            System.out.println("8. Save Courses to File");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addCourses();
                    break;
                case 2:
                    viewCourses();
                    break;
                case 3:
                    calculateSemesterGPA();
                    break;
                case 4:
                    calculateCumulativeGPA();
                    break;
                case 5:
                    predictFutureGPA();
                    break;
                case 6:
                    requiredGPAForTarget();
                    break;
                case 7:
                    performanceAnalysis();
                    break;
                case 8:
                    saveToFile();
                    break;
                case 9:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void addCourses() {
        System.out.print("Enter semester number: ");
        int semNum = sc.nextInt();
        sc.nextLine(); // consume newline

        // Find or create semester
        Semester sem = null;
        for (Semester s : semesters) {
            if (s.getSemNumber() == semNum) {
                sem = s;
                break;
            }
        }
        if (sem == null) {
            sem = new Semester(semNum);
            semesters.add(sem);
        }

        System.out.print("How many courses do you want to add for Semester " + semNum + "? ");
        int numCourses = sc.nextInt();
        sc.nextLine(); // consume newline

        for (int i = 1; i <= numCourses; i++) {
            System.out.println("\nCourse " + i + ":");
            System.out.print("Enter course name: ");
            String name = sc.nextLine();

            System.out.print("Enter credits: ");
            int credits = sc.nextInt();

            System.out.print("Enter grade (S/A/B/C/D/F): ");
            String grade = sc.next();
            sc.nextLine(); // consume newline

            sem.addCourse(new Course(name, credits, grade));
            System.out.println("Course added!");
        }
    }

    private static void viewCourses() {
        if (semesters.isEmpty()) {
            System.out.println("No courses added yet!");
            return;
        }
        for (Semester sem : semesters) {
            System.out.println("\nSemester " + sem.getSemNumber() + ":");
            sem.viewCourses();
        }
    }

    private static void calculateSemesterGPA() {
        System.out.print("Enter semester number to calculate GPA: ");
        int semNum = sc.nextInt();
        for (Semester sem : semesters) {
            if (sem.getSemNumber() == semNum) {
                double gpa = sem.calculateGPA();
                System.out.printf("Semester %d GPA: %.2f%n", semNum, gpa);
                return;
            }
        }
        System.out.println("Semester not found!");
    }

    private static void calculateCumulativeGPA() {
        int totalCredits = 0;
        double totalPoints = 0;

        for (Semester sem : semesters) {
            for (Course c : sem.getCourses()) {
                totalCredits += c.getCredits();
                totalPoints += c.getGradePoint() * c.getCredits();
            }
        }

        if (totalCredits == 0) {
            System.out.println("No courses added yet!");
            return;
        }

        double cumulativeGPA = totalPoints / totalCredits;
        System.out.printf("Cumulative GPA: %.2f%n", cumulativeGPA);
    }

    private static void predictFutureGPA() {
        System.out.print("Enter expected GPA for future semester: ");
        double futureGPA = sc.nextDouble();
        System.out.print("Enter credits for future semester: ");
        int futureCredits = sc.nextInt();

        int completedCredits = 0;
        double currentPoints = 0;
        for (Semester sem : semesters) {
            for (Course c : sem.getCourses()) {
                completedCredits += c.getCredits();
                currentPoints += c.getGradePoint() * c.getCredits();
            }
        }

        double predictedCumulativeGPA = (currentPoints + (futureGPA * futureCredits)) / (completedCredits + futureCredits);
        System.out.printf("Predicted Cumulative GPA after future semester: %.2f%n", predictedCumulativeGPA);
    }

    private static void requiredGPAForTarget() {
        System.out.print("Enter target cumulative GPA: ");
        double targetGPA = sc.nextDouble();
        System.out.print("Enter remaining credits: ");
        int remainingCredits = sc.nextInt();

        int completedCredits = 0;
        double currentPoints = 0;
        for (Semester sem : semesters) {
            for (Course c : sem.getCourses()) {
                completedCredits += c.getCredits();
                currentPoints += c.getGradePoint() * c.getCredits();
            }
        }

        if (remainingCredits <= 0) {
            System.out.println("Remaining credits must be greater than 0.");
            return;
        }

        double requiredGPA = (targetGPA * (completedCredits + remainingCredits) - currentPoints) / remainingCredits;
        System.out.printf("Required GPA in remaining credits to reach target: %.2f%n", requiredGPA);
    }

    private static void performanceAnalysis() {
        Course best = null;
        Course worst = null;

        for (Semester sem : semesters) {
            for (Course c : sem.getCourses()) {
                if (best == null || c.getGradePoint() > best.getGradePoint()) best = c;
                if (worst == null || c.getGradePoint() < worst.getGradePoint()) worst = c;
            }
        }

        if (best == null) {
            System.out.println("No courses added yet!");
            return;
        }

        System.out.println("Best Course: " + best);
        System.out.println("Weakest Course: " + worst);
    }

    private static void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new File("courses.txt"))) {
            for (Semester sem : semesters) {
                pw.println("Semester " + sem.getSemNumber() + ":");
                for (Course c : sem.getCourses()) {
                    pw.println(c);
                }
                pw.println();
            }
            System.out.println("Courses saved to courses.txt successfully!");
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }
}