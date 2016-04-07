package com.company;

import java.util.ArrayList;

// maakt een class Student die naam, studentnummer en een lijst met vakken die de persoon volgt op kan slaan
public class Student {
    String name;
    int studentnummer;
    ArrayList<Course> courses = new ArrayList<>();

    public Student(String name, int studentnummer) {
        this.name = name;
        this.studentnummer = studentnummer;
    }

    // past aan wat moet worden uitgeprint
    public String toString() {
        return name + ", " + studentnummer + ", " + courses;
    }
}
