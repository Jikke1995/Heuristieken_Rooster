package com.company;

import java.util.ArrayList;

// maakt class aan die de dag, tijd en room aan een roomslot toevoegt
public class RoomSlot {
    int day;
    int time;
    Room room;
    //Activity activity;

    public RoomSlot (int day, int time, Room room) {
        this.day = day;
        this.time = time;
        this.room = room;
        //this.activity = activity;
    }


    // past aan wat moet worden uitgeprint
    public String toString() {
        return "Zaalslot: " + day +  ", " + time + ", " + room;
    }
}

