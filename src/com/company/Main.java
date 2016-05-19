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
            // Leest de CoursesFile in
            BufferedReader Courseinfo =
                    new BufferedReader(new FileReader("resources/CoursesFile.csv"));
            while (true) {
                // Leest de volgende regel uit de CoursesFile in
                String name = Courseinfo.readLine();
                if (name == null) break;
                // Voegt course toe aan de lijst van courses
                courses.add(new Course(name.split(",")[0],
                        Integer.parseInt(name.split(",")[1]),
                        Integer.parseInt(name.split(",")[2]),
                        Integer.parseInt(name.split(",")[4])));
            }
            Courseinfo.close();
        } catch (IOException ex) {
            System.out.println("Can't find file");
            System.exit(1);
        }

        //while je nog kan lezen
        try {
            // lees de StudentFile in
            BufferedReader Studentinfo =
                    new BufferedReader(new FileReader("resources/StudentsFile.csv"));
            while (true) {
                // lees volgende regel uit de StudentFile in
                String line = Studentinfo.readLine();
                if (line == null) break;
                // voegt naam van student en studentnummer toe aan lijst van studenten
                students.add(new Student(line.split(",")[0] + ", " + line.split(",")[1], Integer.parseInt(line.split(",")[2])));
                Student student = students.get(students.size() - 1);
                // haalt de vakken op die de student volgt
                String[] coursedata = line.split(",");
                // voor elk vak dat de student volgt
                for (int i = 3; i < coursedata.length; i++) {
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
        } catch (IOException ex) {
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
                    activities.add(new Activity("hoorcollege", courses.get(regelInCourses), -1, i));
                }
                Course course = courses.get(regelInCourses);
                // werkcolleges
                int amountWerkcolleges = Integer.parseInt(name.split(",")[2]);
                int capacityWerkcolleges = Integer.parseInt(name.split(",")[3]);
                int amountOfStudentsWerkcollege = course.students.size();
                int werkcollegeMultiplier = (int) Math.ceil(((double) amountOfStudentsWerkcollege) / capacityWerkcolleges);
                for (int j = 0; j < amountWerkcolleges; j++) {
                    for (int i = 0; i < werkcollegeMultiplier; i++) {
                        activities.add(new Activity("werkcollege", courses.get(regelInCourses), Integer.parseInt(name.split(",")[3]), i));
                    }
                }
                //practica
                int amountPractica = Integer.parseInt(name.split(",")[4]);
                int capacityPractica = Integer.parseInt(name.split(",")[5]);
                int amountOfStudentsPractica = course.students.size();
                int practicaMultiplier = (int) Math.ceil(((double) amountOfStudentsPractica) / capacityPractica);
                for (int k = 0; k < amountPractica; k++) {
                    for (int i = 0; i < practicaMultiplier; i++) {
                        activities.add(new Activity("practicum", courses.get(regelInCourses), Integer.parseInt(name.split(",")[5]), i));
                    }
                }
                regelInCourses = regelInCourses + 1;
            }
            System.out.println(activities);
            System.out.println(activities.size());
            Course course = courses.get(4);
            System.out.println(course.students.size());
        } catch (IOException ex) {
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
        } catch (IOException ex) {
            System.out.println("Can't find file");
            System.exit(1);
        }

        // Creëert een lijst met elk mogelijke roomsslot
        for (int j = 1; j < 6; j++) {
            for (int i = 9; i <= 15; i = i + 2) {
                for (int k = 0; k <= 6; k++) {
                    roomslots.add(new RoomSlot(j, i, rooms.get(k)));
                }
            }
            roomslots.add(new RoomSlot(j, 17, rooms.get(5)));
        }


        indelenStudentenWerkcolleges(activities, students, courses);
        indelenStudentenPractica(activities, students, courses);
        indelenStudentenHoorcolleges(activities, students, courses);
        HashMap<RoomSlot, Activity> schedule = randomSchedule(roomslots, (ArrayList<Activity>) activities.clone());
        System.out.println(score(schedule));

        // Begin stuk scorefunctie, moet in aparte void.
        //int score = 1000;
        //String name = Room;
        //int capicityRoom = Integer.parseInt(name.split(",")[1]);


    }

    public static HashMap<RoomSlot, Activity> randomSchedule(ArrayList<RoomSlot> roomslots, ArrayList<Activity> activities) {
        Random rgen = new Random();
        HashMap<RoomSlot, Activity> schedule = new HashMap<>();
        while (activities.size() > 0) {
            int indexActivity = rgen.nextInt(activities.size());
            int indexRoomSlot = rgen.nextInt(roomslots.size());
            schedule.put(roomslots.get(indexRoomSlot), activities.get(indexActivity));
            activities.remove(indexActivity);
            roomslots.remove(indexRoomSlot);
        }
        for (RoomSlot name : schedule.keySet()) {
            String key = name.toString();
            String value = schedule.get(name).toString();
            System.out.println(key + " = " + value);
        }
        return schedule;
    }

    public static void indelenStudentenWerkcolleges(ArrayList<Activity> activities, ArrayList<Student> students, ArrayList<Course> courses) {
        ArrayList<Activity> werkcolleges = new ArrayList<>();
        for (Activity activity : activities) {
            if (activity.typeActivity.equals("werkcollege")) {
                werkcolleges.add(activity);
            }
        }
        for (Course course : courses) {
            for (Student student : students) {
                if (student.courses.contains(course)) {
                    int gevolgdeWerkcolleges = 0;
                    for (Activity werkcollege : werkcolleges) {
                        if (werkcollege.course.equals(course)) {
                            if (werkcollege.students.size() < werkcollege.capacity) {
                                werkcollege.students.add(student);
                                gevolgdeWerkcolleges += 1;
                                if (gevolgdeWerkcolleges > course.amountWerkcolleges) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println(werkcolleges.get(2).students);
    }

    public static void indelenStudentenPractica(ArrayList<Activity> activities, ArrayList<Student> students, ArrayList<Course> courses) {
        ArrayList<Activity> practica = new ArrayList<>();
        for (Activity activity : activities) {
            if (activity.typeActivity.equals("practicum")) {
                practica.add(activity);
            }
        }
        for (Course course : courses) {
            for (Student student : students) {
                if (student.courses.contains(course)) {
                    int gevolgdePractica = 0;
                    for (Activity practicum : practica) {
                        if (practicum.course.equals(course)) {
                            if (practicum.students.size() < practicum.capacity) {
                                practicum.students.add(student);
                                gevolgdePractica += 1;
                                if (gevolgdePractica > course.amountPractica) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println(practica.get(2).students);
    }

    public static void indelenStudentenHoorcolleges(ArrayList<Activity> activities, ArrayList<Student> students, ArrayList<Course> courses) {
        ArrayList<Activity> hoorcolleges = new ArrayList<>();
        for (Activity activity : activities) {
            if (activity.typeActivity.equals("hoorcollege")) {
                hoorcolleges.add(activity);
            }
        }
        for (Course course : courses) {
            for (Student student : students) {
                if (student.courses.contains(course)) {
                    for (Activity hoorcollege : hoorcolleges) {
                        if (hoorcollege.course.equals(course)) {
                            hoorcollege.students.add(student);
                            break;
                        }
                    }
                }
            }
        }
        System.out.println(hoorcolleges.get(2).students);
    }

    public static int score(HashMap<RoomSlot, Activity> schedule, ArrayList<Course> courses) {
        // Stelt basisscore op 1000
        int score = 1000;
        // maluspunt voor elke student in een werkgroep die over de capacity heengaat
        for (RoomSlot roomslot : schedule.keySet()) {
            Activity activity = schedule.get(roomslot);
            if (activity.students.size() > roomslot.room.capacity) {
                int studentsOverCapacity = activity.students.size() - roomslot.room.capacity;
                score = score - studentsOverCapacity;
            }
        }

        // maluspunt voor elke keer dat student dubbel is ingeroosterd
        for (RoomSlot roomslot : schedule.keySet()) {
            for (RoomSlot  otherRoomslot : schedule.keySet()) {
                if(roomslot == otherRoomslot) {
                    break;
                }
                if(roomslot.day == otherRoomslot.day && roomslot.time == otherRoomslot.time) {
                    ArrayList<Student> students = schedule.get(roomslot).students;
                    ArrayList<Student> otherStudents = schedule.get(otherRoomslot).students;
                    for (Student student : students) {
                        if (otherStudents.contains(student)) {
                            score = score - 1;
                        }
                    }
                    //System.out.println(students);
                    //System.out.println(otherStudents);
                }
            }
        }

        System.out.println(score);

        // maluspunten wanneer de avondslot wordt gebruikt
        for(RoomSlot roomslot : schedule.keySet()) {
            if(roomslot.time == 17) {
                score = score - 50;
            }
        }

        /*
        for(Course course : courses) {
            for(RoomSlot roomslot : schedule.keySet()) {
                Activity activity = schedule.get(roomslot);
                if(activity.course == course) {

                }
            }

        }
        */

        return score;
    }
}