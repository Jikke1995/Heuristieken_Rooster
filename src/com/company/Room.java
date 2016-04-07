package com.company;

// maakt class aan die de room opslaat samen met de capacity
public class Room {
    String number;
    int capacity;

    public Room(String number, int capacity) {
        this.number = number;
        this.capacity = capacity;
    }

    // past aan wat moet worden uitgeprint
    public String toString(){
        return number + ", " + capacity;
    }
}
