package com.company;

import java.util.ArrayList;
import java.util.Collections;


public class Elevator implements ElevatorI {
    public int id;
    public ArrayList<int[]> queue = new ArrayList<int[]>();
    public ArrayList<ArrayList<Integer>> pickup_queue = new ArrayList<ArrayList<Integer>>();
    public ArrayList<Integer> pickup_directions = new ArrayList<Integer>();
    public int current_floor = 0;

    public Elevator(int id){
        this.id = id;
    }

    @Override
    public int direction(ArrayList<int[]> queue, int current_floor) {
        try{
            return (queue.get(0)[0] - current_floor)/Math.abs((queue.get(0)[0] - current_floor));
        }catch(Exception e){
            //System.out.println("brak zakolejkowanych zlecen");
        }
        return 0;
    }

    @Override
    public void pickup(int floor, int direction, ArrayList<Integer> floors) {
        System.out.println("Przywołano winde na "+floor+ " pietro, kierunek: " + ((direction<0)? "dol":"gora"));
        int[] n = {floor,direction};
        //this.queue.add(n);
        ArrayList<Integer> floors1 = new ArrayList<Integer>();
        ArrayList<Integer> floors2 = new ArrayList<Integer>();
        if(direction>0){ //winda jedzie do góry, więc będzie się otwierać kolejno na rosnących piętrach
            Collections.sort(floors); // ta część odopowiada za zoptymalizowanie pięter w przypadku kliknięcia złego przycisku zamówienia
            for(int x: floors){
                if(x<floor){
                    floors2.add(x);
                    Collections.sort(floors2,Collections.reverseOrder());
                }else{
                    floors1.add(x);
                    Collections.sort(floors1);
                }
            }
        }else{
            Collections.sort(floors,Collections.reverseOrder());
            for(int x: floors){
                if(x>floor){
                    floors2.add(x);
                    Collections.sort(floors2);
                }else{
                    floors1.add(x);
                    Collections.sort(floors1,Collections.reverseOrder());
                }
            }
        }
        floors1.addAll(floors2);
        floors1.add(0,floor);
        this.pickup_queue.add(floors1);
        this.pickup_directions.add(direction);
    }

    @Override
    public ArrayList<Integer> status(int current_floor, ArrayList<int[]> queue, int id) {
        System.out.println("-----------------------");
        System.out.println("Id: "+this.id);
        System.out.println("Current floor: "+this.current_floor);
        int q = 0;
        try{
            q = this.queue.get(0)[0];
            System.out.println("Next stop: "+q);
        }catch(Exception e){
            System.out.println("Next stop: -");
            q = -1000;
        }
        try{
            for(int[] x : this.queue){

                System.out.print("["+x[0]+","+x[1]+"]"+",");
            }
            System.out.println("");
        }catch(IndexOutOfBoundsException e){
            System.out.println("kolejka jest pusta");
        }
        System.out.println("-----------------------");
        ArrayList<Integer> status_arr = new ArrayList<Integer>();
        status_arr.add(id);
        status_arr.add(current_floor);
        status_arr.add(q);

        return status_arr;
    }

    @Override
    public void step() {
        int step = 1;
        try{
            if(this.queue.isEmpty()){
                int[] n = {this.pickup_queue.get(0).get(0),(this.pickup_queue.get(0).get(0)-this.current_floor)/Math.abs((this.pickup_queue.get(0).get(0)-this.current_floor))};
                this.queue.add(n);
                System.out.println("cccccc " + n[0]);
            }
        }catch(Exception e){
            //System.out.println("brak zamowien windy");
        }
        try{
            System.out.println("");
            if(this.current_floor == this.queue.get(0)[0]){
                System.out.println("Otwarcie drzwi");
                this.queue.remove(0);
            }
        }catch(IndexOutOfBoundsException e){

        }

        try{
            for(int i=0;i<this.pickup_queue.size();i++){
                if((this.current_floor == this.pickup_queue.get(i).get(0))&&(((this.pickup_directions.get(i)==this.pickup_directions.get(0))&&this.queue.size()<1)||(this.pickup_directions.get(i)==this.queue.get(0)[1]))){
                    //System.out.println("Otwarcie drzwi");
                    String p = "";
                    for(int x: this.pickup_queue.get(i)){
                        if(this.current_floor!=x) {
                            try{
                                int direction1 = 0;
                                try{
                                    direction1 = (x-this.current_floor)/Math.abs(x-this.current_floor);
                                }catch(Exception e){}
                                int[] n = {x, direction1};
                                this.queue.add(n);
                            }catch (ArithmeticException e){

                            }
                            p += x + ", ";
                        }
                    }
                    System.out.println("Wciśnieto: "+p);
                    this.pickup_queue.remove(i);
                    this.pickup_directions.remove(i);
                    this.queue = removeDuplicates(this.queue);
                    step = 0;
                }else{
                }
            }

        }catch(IndexOutOfBoundsException e){
            //System.out.println("brak przywolan windy");
        }
        if(this.direction(this.queue,this.current_floor)<0){
            queue_decomposition();
            this.current_floor -= step;
        }else if(this.direction(this.queue,this.current_floor)>0){
            queue_decomposition();
            this.current_floor += step;
        }



    }

    public int show_direction(){
        return this.direction(this.queue,this.current_floor);
    }

    public void show_status(){
        this.status(this.current_floor,this.queue,this.id);
    }

    public void make_step(){
        this.step();
    }

    private static ArrayList<int[]> removeDuplicates(ArrayList<int[]> list) {
        ArrayList<int[]> newList = new ArrayList<int[]>();
        for (int[] element : list) {
            boolean flag = false;
            for (int[] el : newList){
                if(el[0]==element[0]){
                    flag = true;
                }
            }
            if(!flag){
                newList.add(element);
            }
        }

        return newList;
    }

    public int get_next_stop(){
        try{
            return this.queue.get(0)[0];
        }catch(Exception e){
            return -100;
        }
    }


    private void queue_decomposition(){

            for(int i=0; i<this.queue.size(); i++){
                if(this.queue.get(i)[0]>this.queue.get(0)[0]&&this.queue.get(i)[1]==-1){
                    int[] a = {this.queue.get(i)[0],this.queue.get(i)[1]};
                    this.queue.remove(get_from_array_list(this.queue.get(i)[0],this.queue.get(i)[1]));
                    this.queue.add(0,a);
                    return;
                }
                if(this.queue.get(i)[0]<this.queue.get(0)[0]&&this.queue.get(i)[1]==1){
                    int[] a = {this.queue.get(i)[0],this.queue.get(i)[1]};
                    this.queue.remove(get_from_array_list(this.queue.get(i)[0],this.queue.get(i)[1]));
                    this.queue.add(0,a);
                    return;
                }
            }
    }

    private int[] get_from_array_list(int floor, int direction){
        for(int[] x : this.queue){
            if(x[0]==floor && x[1]==direction){
                return x;
            }
        }
        return null;
    }

    public boolean floor_in_pickupqueue(){
        for(int x=0;x<this.pickup_queue.size();x++){
            try{
                if(this.pickup_queue.get(x).get(0)==this.current_floor&&this.pickup_directions.get(x)==this.queue.get(0)[1]){
                    return true;
                }
            }catch(IndexOutOfBoundsException e){}
        }
        return false;
    }
}
