package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();

        // while je nog kan lezen:
        try {
            BufferedReader Studentinfo =
            new BufferedReader (new FileReader("resources/StudentsFile.csv"));
            while(true) {
                String name = Studentinfo.readLine();
                if (name == null) break;
                students.add(new Student(name.split(",")[0] + ", " + name.split(",")[1], name.split(",")[2]));
            }
            Studentinfo.close();
        }

        catch (IOException ex) {
                System.out.println("Can't find file");
                System.exit(1);
        }

        for (Student student : students) {
            System.out.println(student);
        }

        try {
            BufferedReader Courseinfo =
                    new BufferedReader (new FileReader("resources/CoursesFile.csv"));
            while(true) {
                String name = Courseinfo.readLine();
                if (name == null) break;
                courses.add(new Course(name.split(",")[0]));
            }
            Courseinfo.close();
        }

        catch (IOException ex) {
            System.out.println("Can't find file");
            System.exit(1);
        }

        for (Course course : courses) {
            System.out.println(course);
        }

    }
}

