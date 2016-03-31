package com.company;

public class Activity {
    String typeActivity;
    Course course;
    int capacity;

    public Activity (String typeActivity, Course course, int capacity) {
        this.typeActivity = typeActivity;
        this.course = course;
        this.capacity = capacity;
    }

    public String toString() {
        return "activity: " + typeActivity + ", " + course + ", " + capacity;
    }
}
