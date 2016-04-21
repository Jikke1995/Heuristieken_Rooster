package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Creëeren Arraylists
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<RoomSlot> roomslots = new ArrayList<>();
        ArrayList<Activity> activities = new ArrayList<>();

        // while je nog kan lezen:
        try {
            // lees de CoursesFile in
            BufferedReader Courseinfo =
                    new BufferedReader (new FileReader("resources/CoursesFile.csv"));
            while(true) {
                // lees de volgende regel uit de CoursesFile in
                String name = Courseinfo.readLine();
                if (name == null) break;
                // voegt course toe aan de lijst van courses
                courses.add(new Course(name.split(",")[0]));
            }
            Courseinfo.close();
        }

        catch (IOException ex) {
            System.out.println("Can't find file");
            System.exit(1);
        }

        //while je nog kan lezen
        try {
            // lees de StudentFile in
            BufferedReader Studentinfo =
            new BufferedReader (new FileReader("resources/StudentsFile.csv"));
            while(true) {
                // lees volgende regel uit de StudentFile in
                String line = Studentinfo.readLine();
                if (line == null) break;
                // voegt naam van student en studentnummer toe aan lijst van studenten
                students.add(new Student(line.split(",")[0] + ", " + line.split(",")[1], Integer.parseInt(line.split(",")[2])));
                Student student = students.get(students.size() - 1);
                // haalt de vakken op die de student volgt
                String[] coursedata = line.split(",");
                // voor elk vak dat de student volgt
                for(int i = 3; i < coursedata.length; i++) {
                    String courseName = coursedata[i];
                    // zoek het vak uit de lijst van courses dat het desbetreffende vak equals
                    for (Course course : courses) {
                        // voeg het vak aan de student toe en het student aan het vak
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

        try {
            // lees de CoursesFile in
            BufferedReader Courseinfo =
                    new BufferedReader(new FileReader("resources/CoursesFile.csv"));
            int regelInCourses = 0;
            while (true) {
                // lees de volgende regel uit de CoursesFile in
                String name = Courseinfo.readLine();
                if (name == null) break;
                // kijkt hoe veel hoorcolleges/werkcolleges/practica er van de desbetreffende course moet worden gegeven, en voegt
                // voor elk college een activity toe aan de lijst van colleges, samen met de maximale hoeveelheid studenten per activity.
                //hoorcolleges
                int amountHoorcolleges = Integer.parseInt(name.split(",")[1]);
                for (int i = 0; i < amountHoorcolleges; i++) {
                    activities.add(new Activity("hoorcollege", courses.get(regelInCourses), -1));
                }
                Course course = courses.get(regelInCourses);
                // werkcolleges
                int amountWerkcolleges = Integer.parseInt(name.split(",")[2]);
                int capacityWerkcolleges = Integer.parseInt(name.split(",")[3]);
                int amountOfStudentsWerkcollege = course.students.size();
                int werkcollegeMultiplier = (int)Math.ceil(((double) amountOfStudentsWerkcollege)/capacityWerkcolleges);
                for (int j = 0; j < (amountWerkcolleges*werkcollegeMultiplier); j++) {
                    activities.add(new Activity("werkcollege", courses.get(regelInCourses), Integer.parseInt(name.split(",")[3])));
                }
                //practica
                int amountPractica = Integer.parseInt(name.split(",")[4]);
                int capacityPractica = Integer.parseInt(name.split(",")[5]);
                int amountOfStudentsPractica = course.students.size();
                int practicaMultiplier = (int)Math.ceil(((double) amountOfStudentsPractica)/capacityPractica);
                for (int k = 0; k < amountPractica*practicaMultiplier; k++) {
                    activities.add(new Activity("practicum", courses.get(regelInCourses), Integer.parseInt(name.split(",")[5])));
                }
                regelInCourses = regelInCourses + 1;
            }
            System.out.println(activities);
            System.out.println(activities.size());
            Course course = courses.get(4);
            System.out.println(course.students.size());
        }
        catch (IOException ex) {
            System.out.println("Can't find file");
            System.exit(1);
        }

        // while je nog kan lezen
        try {
            // lees de RoomsFile in
            BufferedReader Roominfo =
                    new BufferedReader(new FileReader("resources/RoomsFile.csv"));
            while (true) {
                // lees volgende regel uit de RoomsFile in
                String name = Roominfo.readLine();
                if (name == null) break;
                // voeg room en capacity toe aan het de lijst met rooms
                rooms.add(new Room(name.split(",")[0], Integer.parseInt(name.split(",")[1])));
            }
            Roominfo.close();
        }

        catch (IOException ex) {
            System.out.println("Can't find file");
            System.exit(1);
        }

        // Creëert een lijst met elk mogelijke roomsslot
        for(int j=1; j<6; j++) {
           for(int i=9; i <= 17; i=i+2) {
               for(int k=0; k<=6; k++) {
                   roomslots.add(new RoomSlot(j, i, rooms.get(k)));
               }
           }
        }

        randomSchedule(roomslots, activities);

        int score = 1000;
        String name = Room;
        int capicityRoom = Integer.parseInt(name.split(",")[1]);


    }

    public static void randomSchedule(ArrayList<RoomSlot> roomslots, ArrayList<Activity> activities) {
        Random rgen = new Random();
        HashMap<RoomSlot, Activity> schedule = new HashMap<>();
        while(activities.size() > 0) {
            int indexActivity = rgen.nextInt(activities.size());
            int indexRoomSlot = rgen.nextInt(roomslots.size());
            schedule.put(roomslots.get(indexRoomSlot), activities.get(indexActivity));
            activities.remove(indexActivity);
            roomslots.remove(indexRoomSlot);
        }
        for (RoomSlot name: schedule.keySet()) {
            String key = name.toString();
            String value = schedule.get(name).toString();
            System.out.println(key + " = " + value);
        }
    }

    public static void indelenStudentenWerkcolleges(ArrayList<Activity> activities, ArrayList<Student> students, ArrayList<Room> rooms) {
        ArrayList<Activity> werkcolleges = new ArrayList<>();
        int capacityWerkcollege =
        for(Activity activity : activities) {
            if(activity.typeActivity.equals("werkcollege")) {
                werkcolleges.add(activity);
            }
        }
        for(Student student : students) {
            for(Activity werkcollege : werkcolleges) {

            }
        }
    }

    public static void indelenStudentenPractica(ArrayList<Activity> activities, ArrayList<Student> students) {
        ArrayList<Activity> practica = new ArrayList<>();
        for(Activity activity : activities) {
            if(activity.typeActivity.equals("practicum")) {
                practica.add(activity);
            }
        }
        for(Student student : students) {
            for(Activity practicum : practica) {

            }
        }
    }
}