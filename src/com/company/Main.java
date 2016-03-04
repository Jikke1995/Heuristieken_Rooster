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

        try {
            BufferedReader Studentinfo =
            new BufferedReader (new FileReader("resources/StudentsFile.csv"));
            while(true) {
                String line = Studentinfo.readLine();
                if (line == null) break;
                students.add(new Student(line.split(",")[0] + ", " + line.split(",")[1], Integer.parseInt(line.split(",")[2])));
                Student student = students.get(students.size() - 1);
                String courseName = line.split(",")[3];
                for (Course course : courses) {
                    if (courseName == course.name) {
                        student.courses.add(course);
                        course.students.add(student);
                    }
                }
            }
            Studentinfo.close();
        }

        catch (IOException ex) {
                System.out.println("Can't find file");
                System.exit(1);
        }

        System.out.println(students.get(1));

    }
}

