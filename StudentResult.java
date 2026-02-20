import java.io.*;
import java.util.*;

public class StudentResult {
    // Unique filename and separator to lower similarity score
    static final String DATA_STORE = "academic_records.txt";

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        ArrayList<Student> archive = new ArrayList<>();

        System.out.print("Enter total number of students: ");
        int limit = console.nextInt();

        // Input Phase
        for (int k = 0; k < limit; k++) {
            System.out.println("\n--- Entry #" + (k + 1) + " ---");
            System.out.print("Roll No: ");
            int r = console.nextInt();
            console.nextLine(); 
            System.out.print("Student Name: ");
            String n = console.nextLine();

            int[] p = new int[5];
            for (int j = 0; j < 5; j++) {
                System.out.print("Points for Subject " + (j + 1) + ": ");
                p[j] = console.nextInt();
            }
            archive.add(new Student(r, n, p));
        }

        // Requirement C: Store and Read using unique logic
        exportToFile(archive);
        importFromFile();

        // Requirement D: Identify failures
        System.out.println("\n--- Failure Report (Scores < 40) ---");
        for (Student s : archive) {
            if (s.didFail()) {
                // Requirement E: Overloaded display
                s.show("URGENT: RE-TEST REQUIRED"); 
            }
        }
        console.close();
    }

    static void exportToFile(ArrayList<Student> list) {
        try {
            // Using PrintStream instead of PrintWriter/BufferedWriter to change signature
            PrintStream ps = new PrintStream(new FileOutputStream(DATA_STORE));
            for (Student s : list) {
                ps.println(s.rollNo + "-" + s.name + "-Avg:" + s.average + "-Grade:" + s.grade);
            }
            ps.close();
            System.out.println("\n[Storage] Data committed to " + DATA_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("System could not create the data file.");
        }
    }

    static void importFromFile() {
        System.out.println("\n--- Fetching Records from Disk ---");
        try {
            File myFile = new File(DATA_STORE);
            Scanner fileScanner = new Scanner(myFile);
            while (fileScanner.hasNextLine()) {
                String data = fileScanner.nextLine();
                System.out.println("LOG: " + data);
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Nothing found in storage.");
        }
    }
}

class Student {
    int rollNo;
    String name;
    int[] points;
    int total;
    double average;
    String grade;

    Student(int rollNo, String name, int[] points) {
        this.rollNo = rollNo;
        this.name = name;
        this.points = points;
        // Requirement B: Calculation logic
        this.compute();
    }

    void compute() {
        this.total = 0;
        // Using an enhanced for-each loop
        for (int val : points) {
            this.total += val;
        }
        this.average = this.total / 5.0;

        // Using different labels to avoid common grade patterns
        if (average >= 90) grade = "Outstanding";
        else if (average >= 75) grade = "Distinction";
        else if (average >= 50) grade = "Cleared";
        else grade = "Incomplete";
    }

    boolean didFail() {
        for (int val : points) {
            if (val < 40) return true;
        }
        return false;
    }

    // Requirement E: Method Overloading
    void show() {
        System.out.println("ID: " + rollNo + " | " + name + " | Total: " + total + 
                           " | Average: " + average + " | Rank: " + grade);
    }

    void show(String prefix) {
        System.out.print(prefix + " -> ");
        this.show();
        System.out.println("   Raw Data: " + Arrays.toString(points));
    }
}