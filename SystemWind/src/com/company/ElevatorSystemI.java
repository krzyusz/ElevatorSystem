package com.company;

import java.util.ArrayList;

public interface ElevatorSystemI {
    int add_elevator(Elevator new_elevator);
    int remove_elevator(int elevator_id);
    void show_elevator_status(int elevator_id);
    int elevator_current_floor(int elevator_id);
    Elevator get_elevator_obj(int elevator_id);
    void make_simulation_step();
    void make_single_elevator_step(int elevator_id);
    void elevator_pickup(int elevator_id,int floor, int direction, ArrayList<Integer> floors);
    void show_all_status();
}
