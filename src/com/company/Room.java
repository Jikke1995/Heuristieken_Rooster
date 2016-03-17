package com.company;

public class Room {
    String number;
    int capacity;

    public Room(String number, int capacity) {
        this.number = number;
        this.capacity = capacity;
    }

    public String toString(){
        return number + ", " + capacity;
    }
}
