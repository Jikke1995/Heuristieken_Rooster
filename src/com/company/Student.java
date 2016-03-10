package com.company;

import
        java.util.ArrayList;

public class Student {
    String name;
    int studentnummer;
    ArrayList<Course> courses = new ArrayList<>();

    public Student(String name, int studentnummer) {
        this.name = name;
        this.studentnummer = studentnummer;
    }

    public String toString() {
        return name + ", " + studentnummer + ", " + courses;
    }
}
