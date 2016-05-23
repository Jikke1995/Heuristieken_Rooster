

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
                    activities.add(new Activity("Hoorcollege", courses.get(regelInCourses), -1, i));
                }
                Course course = courses.get(regelInCourses);
                // werkcolleges
                int amountWerkcolleges = Integer.parseInt(name.split(",")[2]);
                int capacityWerkcolleges = Integer.parseInt(name.split(",")[3]);
                int amountOfStudentsWerkcollege = course.students.size();
                int werkcollegeMultiplier = (int) Math.ceil(((double) amountOfStudentsWerkcollege) / capacityWerkcolleges);
                for (int j = 0; j < amountWerkcolleges; j++) {
                    for (int i = 0; i < werkcollegeMultiplier; i++) {
                        activities.add(new Activity("Werkcollege", courses.get(regelInCourses), Integer.parseInt(name.split(",")[3]), i));
                    }
                }
                //practica
                int amountPractica = Integer.parseInt(name.split(",")[4]);
                int capacityPractica = Integer.parseInt(name.split(",")[5]);
                int amountOfStudentsPractica = course.students.size();
                int practicaMultiplier = (int) Math.ceil(((double) amountOfStudentsPractica) / capacityPractica);
                for (int k = 0; k < amountPractica; k++) {
                    for (int i = 0; i < practicaMultiplier; i++) {
                        activities.add(new Activity("Practicum", courses.get(regelInCourses), Integer.parseInt(name.split(",")[5]), i));
                    }
                }
                regelInCourses = regelInCourses + 1;
            }
        } catch (IOException ex) {
            System.out.println("Can't find file");
            System.exit(1);
        }

        //System.out.println(activities);
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
        // deze taken worden uiteindelijk uitgevoerd
        indelenStudentenWerkcolleges(activities, students, courses);
        indelenStudentenPractica(activities, students, courses);
        indelenStudentenHoorcolleges(activities, students, courses);
        int bestScore = -1000;
        HashMap<RoomSlot, Activity> bestSchedule = null;
        ArrayList<Integer> allScores = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            HashMap<RoomSlot, Activity> newSchedule = randomSchedule(roomslots, activities);
            int newScore = score(newSchedule, courses);
            allScores.add(newScore);
            // elk gemaakte rooster uitprinten
/*            for (RoomSlot name : newSchedule.keySet()) {
                String key = name.toString();
                String value = newSchedule.get(name).toString();
                System.out.println(key + " = " + value);
            }

            System.out.println(" ");
            System.out.println(" ");
*/
            if (newScore > bestScore) {
                bestScore = newScore;
                bestSchedule = newSchedule;
            }
        }
        for (RoomSlot name : bestSchedule.keySet()) {
            String key = name.toString();
            String value = bestSchedule.get(name).toString();
            System.out.println(key + " = " + value);
        }

        System.out.println("Score beste rooster: " + bestScore);
        System.out.println("Alle scores: " + allScores);

    }
    // fucntie voor het maken van een random schedule
    public static HashMap<RoomSlot, Activity> randomSchedule(ArrayList<RoomSlot> roomslots, ArrayList<Activity> activities) {
        Random rgen = new Random();
        HashMap<RoomSlot, Activity> schedule = new HashMap<>();
        ArrayList<Activity> temporaryActivities = (ArrayList<Activity>) activities.clone();
        ArrayList<RoomSlot> temporaryRoomslots = (ArrayList<RoomSlot>) roomslots.clone();
        int amountHoorcolleges = 0;
        for(Activity activity : temporaryActivities) {
            if(activity.typeActivity.equals("Hoorcollege")) {
            amountHoorcolleges = amountHoorcolleges + 1;
            }
        }

        System.out.println("aantal hoorcolleges: " + amountHoorcolleges);
        //System.out.println(temporaryActivities.size());
        //System.out.println(temporaryRoomslots.size());

        //dit klopt nog lang niet
        ArrayList<RoomSlot> bigRoomslots = new ArrayList<>();
        for(RoomSlot roomslot : temporaryRoomslots) {
            if(roomslot.room.number.equals("C0.110") || roomslot.room.number.equals("C1.112")) {
                bigRoomslots.add(roomslot);
            }
        }

        // hier deelt hij niet alle activities in, er werkt meer nog niet
        for(Activity activity : temporaryActivities) {
            if(activity.typeActivity.equals("Hoorcollege")) {
                int indexRoomSlot = rgen.nextInt(bigRoomslots.size());
                RoomSlot roomslot = bigRoomslots.get(indexRoomSlot);
                schedule.put(roomslot, activity);
                //temporaryActivities.remove(activity);
                temporaryRoomslots.remove(roomslot);
            }
        }
        for(RoomSlot roomslot : bigRoomslots) {
            temporaryRoomslots.remove(roomslot);
        }

        //System.out.println(bigRoomslots);
        System.out.println(temporaryRoomslots.size());
        //System.out.println(temporaryRoomslots);


        //System.out.println(temporaryActivities.size());

        // dit deelt alle activities in roomslots in
        /* while (temporaryActivities.size() > 0) {
            int indexActivity = rgen.nextInt(temporaryActivities.size());
            int indexRoomSlot = rgen.nextInt(temporaryRoomslots.size());
            schedule.put(temporaryRoomslots.get(indexRoomSlot), temporaryActivities.get(indexActivity));
            temporaryActivities.remove(indexActivity);
            temporaryRoomslots.remove(indexRoomSlot);
        }
        */
        return schedule;
    }

    // // Functie voor het indelen van de studenten in werkcolleges
    public static void indelenStudentenWerkcolleges(ArrayList<Activity> activities, ArrayList<Student> students, ArrayList<Course> courses) {
        ArrayList<Activity> werkcolleges = new ArrayList<>();
        // Alle werkcolleges komen in de ArrayList 'werkcollege'
        for (Activity activity : activities) {
            if (activity.typeActivity.equals("Werkcollege")) {
                werkcolleges.add(activity);
            }
        }
        //studenten worden ingedeeld in de werkcolleges
        for (Course course : courses) {
            for (Student student : students) {
                if (student.courses.contains(course)) {
                    for (Activity werkcollege : werkcolleges) {
                        if (werkcollege.course.equals(course)) {
                            if (werkcollege.students.size() < werkcollege.capacity) {
                                werkcollege.students.add(student);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    // Functie voor het indelen van de studenten in practica
    public static void indelenStudentenPractica(ArrayList<Activity> activities, ArrayList<Student> students, ArrayList<Course> courses) {
        ArrayList<Activity> practica = new ArrayList<>();
        // Alle practica komen in de ArrayList 'practicum'
        for (Activity activity : activities) {
            if (activity.typeActivity.equals("Practicum")) {
                practica.add(activity);
            }
        }
        // studenten worden ingedeeld in de practica
        for (Course course : courses) {
            for (Student student : students) {
                if (student.courses.contains(course)) {
                    for (Activity practicum : practica) {
                        if (practicum.course.equals(course)) {
                            if (practicum.students.size() < practicum.capacity) {
                                practicum.students.add(student);
                            }
                        }
                    }
                }
            }
        }
    }

    // Functie voor het indelen van de studenten in hoorcolleges
    public static void indelenStudentenHoorcolleges(ArrayList<Activity> activities, ArrayList<Student> students, ArrayList<Course> courses) {
        ArrayList<Activity> hoorcolleges = new ArrayList<>();
        // Alle hoorcolleges komen in de ArrayList 'hoorcolleges'
        for (Activity activity : activities) {
            if (activity.typeActivity.equals("Hoorcollege")) {
                hoorcolleges.add(activity);
            }
        }
        // Per vak wordt er per student gekeken of hij/zij het vak volgt, en zo ja dan wordt het hoorcollege toegevoegd
        // aan de ArrayList van studenten. Zo worden de studenten ingedeeld.
        for (Course course : courses) {
            for (Student student : students) {
                if (student.courses.contains(course)) {
                    int gevolgdeHoorcolleges = 0;
                    for (Activity hoorcollege : hoorcolleges) {
                        if (hoorcollege.course.equals(course)) {
                            hoorcollege.students.add(student);
                            gevolgdeHoorcolleges += 1;
                            if (gevolgdeHoorcolleges > course.amountHoorcolleges) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        //System.out.println(hoorcolleges.get(0));
        //System.out.println(hoorcolleges.get(0).students);
        //System.out.println(hoorcolleges.get(1));
        //System.out.println(hoorcolleges.get(1).students);
    }
    public static int score(HashMap<RoomSlot, Activity> schedule, ArrayList<Course> courses) {
        // Stelt basisscore op 1000
        int score = 1000;

        //System.out.println("score voor alles: " + score);

        // maluspunten voor elke werkgroepstudent die over de capacity van de zaal gaat
        for (RoomSlot roomslot : schedule.keySet()) {
            Activity activity = schedule.get(roomslot);
            if (activity.students.size() > roomslot.room.capacity) {
                int studentsOverCapacity = activity.students.size() - roomslot.room.capacity;
                score = score - studentsOverCapacity;
            }
        }

        //System.out.println("score na maluspunten voor elke werkgroepstudent die over de capacity van de zaal gaat: " + score);

        // maluspunt voor elke student die twee keer dezelfde tijd ingedeeld zijn
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

        // System.out.println("score na maluspunt voor elke student die twee keer dezelfde tijd ingedeeld zijn: " + score);;

        //maluspunten voor timeslot in de avond
        for(RoomSlot roomslot : schedule.keySet()) {
            if(roomslot.time == 17) {
                score = score - 50;
            }
        }

        // System.out.println("score na maluspunten voor timeslot in de avond: " + score);

        // maluspunten voor elke activiteit die twee keer op een dag is ingeroosterd.
        HashMap<RoomSlot, Activity> temporarySchedule = (HashMap<RoomSlot, Activity>) schedule.clone();
        ArrayList<Activity> checkedActivities = new ArrayList<>();
        for(RoomSlot roomslotOne : temporarySchedule.keySet()) {
            Activity activityOne = temporarySchedule.get(roomslotOne);
            for(RoomSlot roomslotTwo : temporarySchedule.keySet()) {
                Activity activityTwo = temporarySchedule.get(roomslotTwo);
                if(roomslotOne == roomslotTwo || checkedActivities.contains(activityTwo)) {
                    continue;
                }
                if (roomslotOne.day == roomslotTwo.day && activityOne.course == activityTwo.course ) {

                    if (activityOne.typeActivity.equals("Hoorcollege")) {
                        score = score - 10;
                        if(activityTwo.typeActivity.equals("Werkcollege") || activityTwo.typeActivity.equals("Practicum")) {
                            for(RoomSlot roomslotSame : temporarySchedule.keySet()) {
                                Activity activitySame = temporarySchedule.get(roomslotSame);
                                if(activityTwo == activitySame) {
                                    continue;
                                }
                                if (activityTwo.course == activitySame.course && activityTwo.typeActivity.equals(activitySame.typeActivity)) {
                                    temporarySchedule.remove(activitySame);
                                }
                            }
                        }
                        temporarySchedule.remove(activityTwo);
                        break;
                    }

                    if (activityOne.typeActivity.equals("Werkcollege")) {
                        if(activityTwo.typeActivity.equals("Hoorcollege")) {
                            score = score - 10;
                            temporarySchedule.remove(activityTwo);
                        }
                        if(activityTwo.typeActivity.equals("Practicum")) {
                            score = score - 10;
                            for(RoomSlot roomslotSame : temporarySchedule.keySet()) {
                                Activity activitySame = temporarySchedule.get(roomslotSame);
                                if(activityTwo == activitySame) {
                                    continue;
                                }
                                if (activityTwo.course == activitySame.course && activityTwo.typeActivity.equals(activitySame.typeActivity)) {
                                    temporarySchedule.remove(activitySame);
                                }
                            }
                            temporarySchedule.remove(activityTwo);

                        }
                        break;
                    }

                    if (activityOne.typeActivity.equals("Practicum")) {
                        if(activityTwo.typeActivity.equals("Hoorcollege")) {
                            score = score - 10;
                            temporarySchedule.remove(activityTwo);

                        }
                        if(activityTwo.typeActivity.equals("Werkcollege")) {
                            score = score - 10;
                            for(RoomSlot roomslotSame : temporarySchedule.keySet()) {
                                Activity activitySame = temporarySchedule.get(roomslotSame);
                                if(activityTwo == activitySame) {
                                    continue;
                                }
                                if (activityTwo.course == activitySame.course && activityTwo.typeActivity.equals(activitySame.typeActivity)) {
                                    temporarySchedule.remove(activitySame);
                                }
                            }
                            temporarySchedule.remove(activityTwo);
                        }
                        break;
                    }
                }
            }

            if(activityOne.typeActivity.equals("Hoorcollege")) {
                temporarySchedule.remove(activityOne);
            }

            if(activityOne.typeActivity.equals("Werkcollege")) {
                for(RoomSlot roomslotSame : temporarySchedule.keySet()) {
                    Activity activitySame = temporarySchedule.get(roomslotSame);
                    if(activityOne == activitySame) {
                        continue;
                    }
                    if (activityOne.course == activitySame.course && activityOne.typeActivity.equals(activitySame.typeActivity)) {
                        temporarySchedule.remove(activitySame);
                    }
                }
                temporarySchedule.remove(activityOne);
            }

            if(activityOne.typeActivity.equals("Practicum")) {
                for(RoomSlot roomslotSame : temporarySchedule.keySet()) {
                    Activity activitySame = temporarySchedule.get(roomslotSame);
                    if(activityOne == activitySame) {
                        continue;
                    }
                    if (activityOne.course == activitySame.course && activityOne.typeActivity.equals(activitySame.typeActivity)) {
                        temporarySchedule.remove(activitySame);
                    }
                }
                temporarySchedule.remove(activityOne);
            }
            checkedActivities.add(activityOne);
        }

        //System.out.println("score na maluspunten voor elke activiteit die twee keer op een dag is ingeroosterd: " + score);

        return score;
    }
}


