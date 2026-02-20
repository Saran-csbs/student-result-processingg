import java.io.*;
import java.util.*;

// Requirement: Main class on top ensures the JVM finds 'main' immediately
public class StudentResult {
    static final String FILE_PATH = "student_records.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Student> studentList = new ArrayList<>();

        System.out.print("How many students to record? ");
        int n = sc.nextInt();

        // Data Input Phase
        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Entering Details for Student " + (i + 1) + " ---");
            System.out.print("Roll Number: ");
            int r = sc.nextInt();
            sc.nextLine(); // clear buffer
            System.out.print("Student Name: ");
            String name = sc.nextLine();

            int[] m = new int[5];
            for (int j = 0; j < 5; j++) {
                System.out.print("Mark for Subject " + (j + 1) + ": ");
                m[j] = sc.nextInt();
            }
            studentList.add(new Student(r, name, m));
        }

        // Requirement C: Store records (including average) using file
        saveData(studentList);

        // Requirement C: Read records from file
        loadAndPrintFile();

        // Requirement D: Display failed students
        System.out.println("\n--- List of Failed Students (Mark < 40) ---");
        for (Student s : studentList) {
            if (s.isFailed()) {
                // Requirement E: Using overloaded display method
                s.display("FAIL ALERT"); 
            }
        }
        sc.close();
    }

    // Static helper to write data to disk
    static void saveData(List<Student> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Student s : list) {
                // Included average in the file entry
                pw.println(s.rollNo + "|" + s.name + "|Total:" + s.total + "|Avg:" + s.average + "|Grade:" + s.grade);
            }
            System.out.println("\n[Success] All records saved to " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    // Static helper to read data from disk
    static void loadAndPrintFile() {
        System.out.println("\n--- Reading Data From Text File ---");
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("Stored File Entry: " + line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }
}

// Requirement A: Student Class
class Student {
    int rollNo;
    String name;
    int[] marks;
    int total;
    double average;
    String grade;

    Student(int rollNo, String name, int[] marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.marks = marks;
        // Requirement B: Calculate total, average and grade
        this.calculateResults();
    }

    void calculateResults() {
        this.total = 0;
        for (int m : marks) {
            this.total += m;
        }
        // Calculation of average
        this.average = this.total / 5.0;

        // Grading logic
        if (average >= 90) grade = "Excellent";
        else if (average >= 75) grade = "First Class";
        else if (average >= 50) grade = "Second Class";
        else grade = "Unsatisfactory";
    }

    // Requirement D: Check for failure
    boolean isFailed() {
        for (int m : marks) {
            if (m < 40) return true;
        }
        return false;
    }

    // Requirement E: Method Overloading for display
    void display() {
        System.out.println("Roll: " + rollNo + " | Name: " + name + 
                           " | Total: " + total + " | Average: " + average + " | Grade: " + grade);
    }

    void display(String label) {
        System.out.print("[" + label + "] ");
        this.display(); // Calls the first display method
        System.out.println("   Subject-wise Marks: " + Arrays.toString(marks));
    }
}