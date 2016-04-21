package com.company;

import java.util.ArrayList;

// maakt class aan die een vak opslaat samen met de studenten die het vak volgen
public class Course {
    String name;
    ArrayList<Student> students = new ArrayList<>();
    int amountHoorcolleges;
    int amountWerkcolleges;
    int amountPractica;

    public Course (String name, int amountHoorcolleges, int amountWerkcolleges, int amountPractica) {
        this.name = name;
        this.amountHoorcolleges = amountHoorcolleges;
        this.amountWerkcolleges = amountWerkcolleges;
        this.amountPractica = amountPractica;
    }

    // past aan wat moet worden uitgeprint
    public String toString() {
        return name;
    }
}
