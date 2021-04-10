package com.company;

import java.util.ArrayList;

public interface ElevatorI {
    int direction(ArrayList<int[]> queue, int current_floor);
    void pickup(int floor, int direction, ArrayList<Integer> floors);
    ArrayList<Integer> status(int current_floor,ArrayList<int[]> queue, int id);
    void step();
}
