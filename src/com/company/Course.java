package com.company;

import java.util.ArrayList;

// maakt class aan die een vak opslaat samen met de studenten die het vak volgen
public class Course {
    String name;
    ArrayList<Student> students = new ArrayList<>();

    public Course (String name) {
        this.name = name;
    }

    // past aan wat moet worden uitgeprint
    public String toString() {
        return name;
    }
}
