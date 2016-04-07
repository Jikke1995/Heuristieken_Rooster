package com.company;

import java.util.ArrayList;

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

    public String toString() {return "roomslot: " + day +  ", " + time + ", " + room;
    }
}

