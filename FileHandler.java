import java.io.*;
import java.util.*;

public class FileHandler {

    public static void saveSemesters(List<Semester> semesters) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("courses.txt"))) {
            for (Semester s : semesters) {
                pw.println("Semester:" + s.getSemNumber());
                for (Course c : s.getCourses()) {
                    pw.println(c.getName() + "," + c.getCredits() + "," + c.getGrade());
                }
            }
            System.out.println("Courses saved successfully!");
        } catch (Exception e) {
            System.out.println("Error saving file.");
        }
    }

    public static List<Semester> loadSemesters() {
        List<Semester> semesters = new ArrayList<>();
        Semester currentSemester = null;

        try (BufferedReader br = new BufferedReader(new FileReader("courses.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Semester:")) {
                    int semNum = Integer.parseInt(line.split(":")[1]);
                    currentSemester = new Semester(semNum);
                    semesters.add(currentSemester);
                } else {
                    String[] data = line.split(",");
                    if (currentSemester != null) {
                        currentSemester.addCourse(new Course(data[0], Integer.parseInt(data[1]), data[2]));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("No previous data found.");
        }

        return semesters;
    }
}