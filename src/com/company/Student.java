package com.company;

public class Student {
    String name;
    String studentnummer;

    public Student(String name, String studentnummer) {
        this.name = name;
        this.studentnummer = studentnummer;
    }

    public String toString() {
        return name + ", " + studentnummer;
    }
}
