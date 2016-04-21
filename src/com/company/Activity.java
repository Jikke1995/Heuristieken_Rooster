package com.company;

import java.util.ArrayList;

// maakt class Activity aan die een het type activiteit het vak en de capacity opslaat
public class Activity {
    String typeActivity;
    Course course;
    int capacity;
    ArrayList<Student> students;

    public Activity (String typeActivity, Course course, int capacity) {
        this.typeActivity = typeActivity;
        this.course = course;
        this.capacity = capacity;
        this.students = new ArrayList<>();
    }

    // past aan wat moet worden uitgeprint
    public String toString() {
        return "activity: " + typeActivity + ", " + course + ", " + capacity;
    }
}
