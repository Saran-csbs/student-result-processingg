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

        /
}

class Student {
    int rollNo;
    String name;
    int[] points;
    int total;
    double average;
    String grade;

    //
}