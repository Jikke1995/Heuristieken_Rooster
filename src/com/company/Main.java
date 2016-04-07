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
        ArrayList<RoomSlot> roomslots = new ArrayList<>();
        ArrayList<Activity> activities = new ArrayList<>();

        // while je nog kan lezen:
        try {
            BufferedReader Courseinfo =
                    new BufferedReader (new FileReader("resources/CoursesFile.csv"));
            while(true) {
                String name = Courseinfo.readLine();
                if (name == null) break;
                courses.add(new Course(name.split(",")[0]));
                int amountHoorcolleges = Integer.parseInt(name.split(",")[1]);
                for(int i = 0; i < amountHoorcolleges; i++) {
                    activities.add(new Activity("hoorcollege", courses.get(courses.size() - 1), -1));
                    int amountWerkcolleges = Integer.parseInt(name.split(",")[2]);
                    for (int j = 0; j < amountWerkcolleges; j++) {
                        activities.add(new Activity("werkcollege", courses.get(courses.size() - 1), Integer.parseInt(name.split(",")[3])));

                        int amountPractica = Integer.parseInt(name.split(",")[4]);
                        for (int k = 0; k < amountPractica; k++) {
                            activities.add(new Activity("practicum", courses.get(courses.size() - 1), Integer.parseInt(name.split(",")[5])));
                        }
                    }
                }
                System.out.println(activities);
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
                String[] coursedata = line.split(",");
                for(int i = 3; i < coursedata.length; i++) {
                    String courseName = coursedata[i];
                    for (Course course : courses) {
                        if (courseName.equals(course.name)) {
                            student.courses.add(course);
                            course.students.add(student);
                        }
                    }
                }
            }
            Studentinfo.close();
        }

        catch (IOException ex) {
                System.out.println("Can't find file");
                System.exit(1);
        }

        System.out.println(students.get(3));

        try {
            BufferedReader Roominfo =
                    new BufferedReader(new FileReader("resources/RoomsFile.csv"));
            while (true) {
                String name = Roominfo.readLine();
                if (name == null) break;
                rooms.add(new Room(name.split(",")[0], Integer.parseInt(name.split(",")[1])));
            }
            Roominfo.close();
        }

        catch (IOException ex) {
            System.out.println("Can't find file");
            System.exit(1);
        }

        for(int j=1; j<6; j++) {
           for(int i=9; i <= 17; i=i+2) {
               for(int k=0; k<=6; k++) {
                   roomslots.add(new RoomSlot(j, i, rooms.get(k)));
                   System.out.println(roomslots.get(roomslots.size() -1));
               }
           }
        }

    }
}

L

