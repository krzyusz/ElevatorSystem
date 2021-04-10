package com.company;

import java.util.ArrayList;

public class ElevatorSystem implements ElevatorSystemI {

    private ArrayList<Elevator> elevator_list = new ArrayList<Elevator>();
    @Override
    public int add_elevator(Elevator new_elevator) {
        Elevator el;
        if((el = this.get_elevator_obj(new_elevator.id))==null){
            elevator_list.add(new_elevator);
            return 1;
        }else {
            System.out.println("Winda o takim id już istnieje");
            return 0;
        }
    }

    @Override
    public int remove_elevator(int elevator_id) {
        Elevator el;
        if((el = this.get_elevator_obj(elevator_id)) != null){
            this.elevator_list.remove(el);
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public void show_elevator_status(int elevator_id) {
        this.get_elevator_obj(elevator_id).show_status();
    }

    @Override
    public int elevator_current_floor(int elevator_id) {
        return this.get_elevator_obj(elevator_id).id;
    }

    @Override
    public Elevator get_elevator_obj(int elevator_id) {
        for(Elevator x : this.elevator_list){
            if(x.id == elevator_id){
                return x;
            }
        }
        return null;
    }

    @Override
    public void make_simulation_step() {
        for(Elevator x : this.elevator_list){
            x.make_step();
        }
    }

    @Override
    public void make_single_elevator_step(int elevator_id) {
        this.get_elevator_obj(elevator_id).make_step();
    }

    @Override
    public void elevator_pickup(int elevator_id, int floor, int direction, ArrayList<Integer> floors) {
        this.get_elevator_obj(elevator_id).pickup(floor,direction,floors);
    }

    @Override
    public void show_all_status() {
        for(Elevator x : this.elevator_list){
            x.show_status();
        }
    }

    public ArrayList<Elevator> get_elevators(){
        return this.elevator_list;
    }

}
