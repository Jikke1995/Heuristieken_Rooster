package com.company;

import java.util.ArrayList;

public class RoomSlot {
    int time;
    Room room;

    public RoomSlot (int time, Room room) {
        this.time = time;
        this.room = room;
    }

    public String toString() {
        return time + "";
    }
}

