package codeAlpha;
import java.util.Scanner;

public class StudentGradeTracker {
	public static void main(String[] args) {
		// Program by Shravani Borkar
        // CodeAlpha Java Internship Task – Student Grade Tracker
        // This program stores student names and marks, 
        // then calculates average, highest, and lowest marks.
		Scanner sc = new Scanner(System.in);

        System.out.print("Enter total number of students: ");
        int n= sc.nextInt();

        String[] studentNames = new String[n];
        int[] marks = new int[n];

        // Input student data
        for (int i=0;i<n;i++) {
            System.out.println("\nStudent " + (i + 1) + ":");
            System.out.print("Enter name: ");
            studentNames[i] = sc.next();
            System.out.print("Enter marks: ");
            marks[i] = sc.nextInt();
        }

        // Initialize variables for calculations
        int sum = 0;
        int highest = marks[0];
        int lowest = marks[0];

        // Process marks
        for (int i = 0; i <n; i++) {
            sum += marks[i];
            if (marks[i] > highest) {
                highest = marks[i];
            }
            if (marks[i] < lowest) {
                lowest = marks[i];
            }
        }

        // Calculate average
        double average = (double) sum / n;

        // Display Summary
        System.out.println("\n==============================");
        System.out.println("      Student Grade Report     ");
        System.out.println("==============================");
        for (int i = 0; i <n; i++) {
            System.out.println((i + 1) + ". " + studentNames[i] + " - " + marks[i]);
        }
        System.out.println("------------------------------");
        System.out.println("Average Marks : " + String.format("%.2f", average));
        System.out.println("Highest Marks : " + highest);
        System.out.println("Lowest Marks  : " + lowest);
        System.out.println("==============================");
        System.out.println("Report generated successfully");

        
    }
}
