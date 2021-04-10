package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        ElevatorSystem es = new ElevatorSystem();
        for(int i=1;i<17;i++){
            es.add_elevator(new Elevator(i));
        }
        //ArrayList<ElevatorPanel> elevator_panels_list = new ArrayList<ElevatorPanel>();

        ArrayList<Integer> intList1 = new ArrayList<Integer>();
        intList1.add(2);
        intList1.add(3);
        es.get_elevator_obj(1).pickup(7,-1,intList1);

        ElevatorFrame ef = new ElevatorFrame(es);
        ef.pack();
        ef.setVisible(true);
        ef.setLocation(150,100);
    }
}

class GUIPanel extends  JPanel {
    public ElevatorSystem es;
    public JFrame frame;
    public JPanel elevator_group_panel;
    public GUIPanel(ElevatorSystem es, JFrame frame,JPanel elevator_group_panel){
        this.es = es;
        this.frame = frame;
        this.elevator_group_panel = elevator_group_panel;
        setLayout(new GridLayout(4,4));

        JButton jb1 = new JButton("Wykonaj krok symulacji");
        jb1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                make_step();
            }
        } );


        JButton pickup_el_b = new JButton("Zamow winde");
        pickup_el_b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pickup_function();
            }
        } );

        JButton add_elevator_b = new JButton("Dodaj winde");
        add_elevator_b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                add_function();
            }
        } );

        JButton remove_elevator_b = new JButton("Usun winde");
        remove_elevator_b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove_function();
            }
        } );
        add(jb1);
        add(pickup_el_b);
        add(add_elevator_b);
        add(remove_elevator_b);

    }

    public void make_step(){
        this.es.make_simulation_step();
        this.es.show_all_status();
        for(Component x: this.elevator_group_panel.getComponents()){
            if(x instanceof ElevatorPanel){
                ((ElevatorPanel) x).update();
            }
        }
        frame.getContentPane().repaint();

    }

    public Component get_component(int id){
        for(Component x: this.elevator_group_panel.getComponents()){
            if(x instanceof ElevatorPanel){
                if(((ElevatorPanel) x).get_id()==id){
                    return x;
                }
            }
        }
        return null;
    }
    public void remove_elevator(String elevator_id){
        int e_i = Integer.parseInt(elevator_id.trim());
        this.es.remove_elevator(e_i);
        Component c = get_component(e_i);
        if(c!=null){
            this.elevator_group_panel.remove(c);
        }else{
            System.out.println("winda o takim id nie istnieje");
        }
        frame.getContentPane().revalidate();
    }

    private void remove_function(){
        JLabel remove_elevator = new JLabel("Remove elevator",SwingConstants.CENTER);
        JLabel elevator_id = new JLabel("Elevator id ",SwingConstants.CENTER);
        JButton r_e_b = new JButton("Remove");
        JTextField elevator_id_tf = new JTextField();
        JPanel panel = new JPanel(new GridLayout(1,1));
        JPanel main_panel = new JPanel(new BorderLayout());

        JPanel wrapper = new JPanel( new FlowLayout(FlowLayout.CENTER) );
        JPanel wrapper1 = new JPanel();
        wrapper1.setLayout(new GridBagLayout());
        elevator_id_tf.setPreferredSize(new Dimension(100,24));
        wrapper.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        wrapper.add(elevator_id_tf);
        wrapper1.add(wrapper, new GridBagConstraints());


        panel.add(elevator_id);
        panel.add(wrapper1);
        main_panel.add(remove_elevator,BorderLayout.NORTH);
        main_panel.add(panel,BorderLayout.CENTER);
        main_panel.add(r_e_b,BorderLayout.SOUTH);

        JFrame remove_elevator_frame = new JFrame();
        remove_elevator_frame.add(main_panel);
        remove_elevator_frame.setPreferredSize(new Dimension(300,200));
        remove_elevator_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        remove_elevator_frame.pack();
        remove_elevator_frame.setVisible(true);
        remove_elevator_frame.setLocation(150,100);

        r_e_b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove_elevator(elevator_id_tf.getText());
                frame.getContentPane().revalidate();
            }
        } );
    }

    public void add_elevator(String id){
        int id1 = Integer.parseInt(id.trim());
        try{
            if(this.es.add_elevator(new Elevator(id1))==1){
                this.elevator_group_panel.add(new ElevatorPanel(this.es.get_elevator_obj(id1)));
                System.out.println(this.es.get_elevator_obj(id1).id);
            }else{
                //System.out.println("juz dodano");
            }
        }catch(Exception e){
            //System.out.println("Winda o takim id juz istnieje");
        }
        frame.getContentPane().revalidate();
    }

    private void add_function(){
        JLabel add_elevator = new JLabel("Add elevator",SwingConstants.CENTER);
        JLabel elevator_id = new JLabel("Elevator id ",SwingConstants.CENTER);
        JButton a_e_b = new JButton("Add");
        JTextField elevator_id_tf = new JTextField();
        JPanel panel = new JPanel(new GridLayout(1,1));
        JPanel main_panel = new JPanel(new BorderLayout());

        JPanel wrapper = new JPanel( new FlowLayout(FlowLayout.CENTER) );
        JPanel wrapper1 = new JPanel();
        wrapper1.setLayout(new GridBagLayout());
        elevator_id_tf.setPreferredSize(new Dimension(100,24));
        wrapper.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        wrapper.add(elevator_id_tf);
        wrapper1.add(wrapper, new GridBagConstraints());


        panel.add(elevator_id);
        panel.add(wrapper1);
        main_panel.add(add_elevator,BorderLayout.NORTH);
        main_panel.add(panel,BorderLayout.CENTER);
        main_panel.add(a_e_b,BorderLayout.SOUTH);

        JFrame add_elevator_frame = new JFrame();
        add_elevator_frame.add(main_panel);
        add_elevator_frame.setPreferredSize(new Dimension(300,200));
        add_elevator_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add_elevator_frame.pack();
        add_elevator_frame.setVisible(true);
        add_elevator_frame.setLocation(150,100);

        a_e_b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                add_elevator(elevator_id_tf.getText());
                frame.getContentPane().revalidate();
            }
        } );
    }

    public void pickup_form(String floor, String floors, String elevator_id, String direction){
        int floor_nr =  Integer.parseInt(floor.trim());
        int dir = Integer.parseInt(direction.trim());
        System.out.println(floor_nr);
        ArrayList<Integer> fl = new ArrayList<Integer>();
        String[] fl_ar = floors.trim().split(",");
        for(String x: fl_ar){
            fl.add(Integer.parseInt(x));
        }
        int el_id = Integer.parseInt(elevator_id.trim());
        this.es.elevator_pickup(el_id,floor_nr,dir,fl);

    }

    private void pickup_function(){
        JLabel pickup_elevator = new JLabel("Zamow winde",SwingConstants.CENTER);
        JLabel pickup_floor = new JLabel("Podaj pietro zamowienia",SwingConstants.CENTER);
        JLabel elevator_pickup_id = new JLabel("Podaj id windy",SwingConstants.CENTER);
        JLabel pickup_direction = new JLabel("Podaj kierunek (1/-1)",SwingConstants.CENTER);
        JLabel pickup_floors = new JLabel("Podaj pietro (po przecinku, jezeli > 1)",SwingConstants.CENTER);
        JTextField pickup_floor_tf = new JTextField();
        JTextField elevator_pickup_id_tf = new JTextField();
        JTextField pickup_floors_tf = new JTextField();
        JTextField pickup_direction_tf = new JTextField();

        JPanel panel = new JPanel();
        JPanel main_panel = new JPanel();
        main_panel.setLayout(new BorderLayout());
        panel.setLayout(new GridLayout(4,1));


        JPanel wrapper = new JPanel( new FlowLayout(FlowLayout.CENTER) );
        JPanel wrapper1 = new JPanel();
        wrapper1.setLayout(new GridBagLayout());
        pickup_floor_tf.setPreferredSize(new Dimension(100,24));
        wrapper.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        wrapper.add(pickup_floor_tf);
        wrapper1.add(wrapper, new GridBagConstraints());

        JPanel wrapper2 = new JPanel( new FlowLayout(FlowLayout.CENTER) );
        JPanel wrapper3 = new JPanel();
        wrapper3.setLayout(new GridBagLayout());
        elevator_pickup_id_tf.setPreferredSize(new Dimension(100,24));
        wrapper2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        wrapper2.add(elevator_pickup_id_tf);
        wrapper3.add(wrapper2, new GridBagConstraints());

        JPanel wrapper4 = new JPanel( new FlowLayout(FlowLayout.CENTER) );
        JPanel wrapper5 = new JPanel();
        wrapper5.setLayout(new GridBagLayout());
        pickup_floors_tf.setPreferredSize(new Dimension(100,24));
        wrapper4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        wrapper4.add(pickup_floors_tf);
        wrapper5.add(wrapper4, new GridBagConstraints());

        JPanel wrapper6 = new JPanel( new FlowLayout(FlowLayout.CENTER) );
        JPanel wrapper7 = new JPanel();
        wrapper7.setLayout(new GridBagLayout());
        pickup_direction_tf.setPreferredSize(new Dimension(100,24));
        wrapper6.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        wrapper6.add(pickup_direction_tf);
        wrapper7.add(wrapper6, new GridBagConstraints());

        panel.add(pickup_floor);
        panel.add(wrapper1);
        panel.add(elevator_pickup_id);
        panel.add(wrapper3);
        panel.add(pickup_direction);
        panel.add(wrapper7);
        panel.add(pickup_floors);
        panel.add(wrapper5);


        JButton accept = new JButton("Zamow winde");


        main_panel.add(pickup_elevator,BorderLayout.NORTH);
        main_panel.add(panel,BorderLayout.CENTER);
        main_panel.add(accept,BorderLayout.SOUTH);
        JFrame pickup_frame = new JFrame();
        pickup_frame.add(main_panel);
        pickup_frame.setPreferredSize(new Dimension(400,400));
        pickup_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pickup_frame.pack();
        pickup_frame.setVisible(true);
        pickup_frame.setLocation(150,100);

        accept.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    pickup_form(pickup_floor_tf.getText(),pickup_floors_tf.getText(),elevator_pickup_id_tf.getText(),pickup_direction_tf.getText());
                    frame.repaint();
                    pickup_frame.dispose();
                }catch(Exception ex){
                    System.out.println("podano zle dane");
                }
            }
        } );
    }
}

class ElevatorPanel extends JPanel {
    private Elevator ev;
    JLabel current_floor_label; //= new JLabel("Current floor: "+this.ev.current_floor,SwingConstants.CENTER);
    JLabel going_to_label; //= new JLabel("Going to: ");
    public ElevatorPanel(Elevator ev){
        this.ev = ev;
        setPreferredSize(new Dimension(350,600));
        setSize(new Dimension(350,600));
        setLayout(new GridLayout(1,2));
        JPanel leftPanel = new JPanel();

        current_floor_label = new JLabel("Current floor: "+ev.current_floor,SwingConstants.CENTER);
        going_to_label = new JLabel("Going to: ");
        try{
            going_to_label = new JLabel("Going to: "+ev.queue.get(0)[0],SwingConstants.CENTER);
        }catch(IndexOutOfBoundsException e){
            going_to_label = new JLabel("Going to: ----",SwingConstants.CENTER);
        }
        leftPanel.setLayout(new GridLayout(2,1));
        leftPanel.add(current_floor_label);
        leftPanel.add(going_to_label);

        DrawPanel drawPanel = new DrawPanel(this.ev);
        drawPanel.setPreferredSize(new Dimension(100,800));
        setBorder(BorderFactory.createLineBorder(Color.black));
        add(leftPanel);
        add(drawPanel);
        add(Box.createHorizontalStrut(20));

    }
    public void update(){
        this.current_floor_label.setText("Current floor: "+ev.current_floor);
        try{
            this.going_to_label.setText("Going to: "+ev.queue.get(0)[0]);
        }catch(IndexOutOfBoundsException e){
            this.going_to_label.setText("Going to: ----");
        }
    }
    public int get_id(){
        return this.ev.id;
    }

}

class DrawPanel extends JPanel {
    int x = 50;
    int y = 50;
    private Elevator ev;

    public DrawPanel(Elevator ev){
        this.ev = ev;
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(this.ev.get_next_stop()==this.ev.current_floor || this.ev.floor_in_pickupqueue()){
            g.setColor(Color.blue);
        }else{
            g.setColor(Color.green);
        }
        g.fillRect(this.x, 500-this.y*(this.ev.current_floor), 50, 50);
        g.setColor(Color.red);
        g.drawRect(this.x,this.y,50,500);
        g.setColor(Color.BLACK);
        g.drawString("ID: "+this.ev.id,this.x,30);
        g.setColor(Color.red);
        try{
            if(this.ev.floor_in_pickupqueue()){
                g.fillOval(10, 500-(this.y)*(this.ev.current_floor)+10, 30, 30);
            }
            g.fillOval(10, 500-(this.y)*(ev.pickup_queue.get(0).get(0))+10, 30, 30);
        }catch(IndexOutOfBoundsException e){
            //System.out.println("brak zamowien windy");
        }
    }
}

class ElevatorFrame extends JFrame {
    private ElevatorSystem es;
    public ElevatorFrame(ElevatorSystem es){
        this.es = es;
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("System do zarządzania windami (Krzysztof Uszko)",SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        titlePanel.add(titleLabel);

        JPanel elevatorGroupPanel = new JPanel();
        elevatorGroupPanel.setLayout(new BoxLayout(elevatorGroupPanel,BoxLayout.X_AXIS));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(elevatorGroupPanel, BorderLayout.NORTH);
        GUIPanel guiPanel = new GUIPanel(es,this,elevatorGroupPanel);

        guiPanel.setLayout(new GridLayout(4,4));

        for(Elevator x : this.es.get_elevators()){
            elevatorGroupPanel.add(new ElevatorPanel(x));
        }

        JScrollPane scrPane = new JScrollPane(elevatorGroupPanel);
        scrPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(scrPane, BorderLayout.CENTER);
        mainPanel.add(guiPanel, BorderLayout.SOUTH);
        add(mainPanel);
        //frame.add(current_floor_label);
        setPreferredSize(new Dimension(800,800));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}
