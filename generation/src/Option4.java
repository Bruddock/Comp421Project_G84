import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class Option4 extends JPanel
            implements ActionListener {

    static JLabel information;
    protected JButton backButton;
    protected JButton selectButton;
    protected JPanel buttonSelector;

    protected ButtonGroup group;
    protected JPanel radioPanel;

    protected static JList myList;
    protected JScrollPane listScroller;

    protected static ArrayList<String> reservations;
    protected static String reservationIdTarget;

    protected JTextField newdish;
    protected JPanel newdishPanel;

    protected static ArrayList<String> menus;
    protected static ArrayList<String> dishNames;
    protected static ArrayList<Integer> menuIds;
    protected static String dishTarget;
    protected static int menuIdTarget;

    private int choice = 4;

    public Option4() throws SQLException {
        Border raisedbevel = BorderFactory.createRaisedBevelBorder();

        information = new JLabel("Option 4.\n Select your reservation ID to view or modify a menu.", JLabel.CENTER);
        information.setOpaque(true);
        information.setOpaque(true);
        information.setBackground(new Color(248, 213, 131));
        information.setPreferredSize(new Dimension(400, 180));

        showReservationOptions();

        backButton= new JButton("Back");
        backButton.setActionCommand("back");
        backButton.setBorder(raisedbevel);
        backButton.addActionListener(this);

        selectButton = new JButton("Select Reservation");
        selectButton.setActionCommand("Select Reservation");
        selectButton.setBorder(raisedbevel);
        selectButton.addActionListener(this);

        setLayout(new BorderLayout());

        buttonSelector = new JPanel();
        buttonSelector.add(selectButton);
        buttonSelector.add(backButton);
        add(information, BorderLayout.NORTH);
        add(listScroller, BorderLayout.CENTER);
        add(buttonSelector, BorderLayout.SOUTH);
        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        switch(e.getActionCommand()){
            case "Add Dish":
                System.out.println(e.getActionCommand());
                System.out.println(newdish.getText());
                try {
                    simpleApp.addDish(menuIdTarget, newdish.getText());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                simpleGUI.showGUI();
                break;
            case "Replace Item":
                System.out.println(e.getActionCommand());
                System.out.println(newdish.getText());
                dishTarget = (String) myList.getSelectedValue();
                try {
                    simpleApp.replaceDish(menuIdTarget, dishTarget, newdish.getText());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                simpleGUI.showGUI();
                break;
            case "Select Menu2":
                //doesnt need to show anything new... just calls remove.
                System.out.println(e.getActionCommand());
                System.out.println((String) myList.getSelectedValue());
                dishTarget = (String) myList.getSelectedValue();
                try {
                    simpleApp.removeDish(menuIdTarget, dishTarget);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                simpleGUI.showGUI();
                break;
            case "Select Menu3":
                System.out.println(e.getActionCommand());
                this.setVisible(false);
                listScroller.setVisible(false);
                showNewDish();
                this.add(newdishPanel, BorderLayout.CENTER);
                information.setText("Option 4: Please enter the name of the new dish.");
                selectButton.setText("Replace Item");
                selectButton.setActionCommand("Replace Item");
                newdishPanel.setVisible(true);
                this.setVisible(true);
                break;
            case "Select Option":
                System.out.println(e.getActionCommand());
                System.out.println(choice);
                this.setVisible(false);
                radioPanel.setVisible(false);
                try {
                    showDishes();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                switch(choice){
                    case 1:
                        showNewDish();
                        this.add(listScroller, BorderLayout.WEST);
                        this.add(newdishPanel, BorderLayout.CENTER);
                        selectButton.setText("Add Dish");
                        selectButton.setActionCommand("Add Dish");
                        information.setText("Option 4: Here are the current dishes associated with this menu.");
                        newdishPanel.setVisible(true);
                        break;
                    case 2:
                        this.add(listScroller, BorderLayout.CENTER);
                        selectButton.setText("Select Dish");
                        selectButton.setActionCommand("Select Menu2");
                        information.setText("Option 4: Select a Menu to add to.");
                        break;
                    case 3:
                        this.add(listScroller, BorderLayout.CENTER);
                        selectButton.setText("Select Dish");
                        selectButton.setActionCommand("Select Menu3");
                        information.setText("Option 4: Select a Menu to add to.");
                        break;
                    case 4:
                        this.add(listScroller, BorderLayout.CENTER);
                        buttonSelector.remove(selectButton);
                        information.setText("Option 4: These are the dishes for the selected menu.");
                        break;
                }
                listScroller.setVisible(true);
                this.setVisible(true);
                break;
            case "Add":
                choice = 1;
                break;
            case "Remove":
                choice = 2;
                break;
            case "Replace":
                choice = 3;
                break;
            case "View":
                choice = 4;
                break;
            case "Select Reservation":
                System.out.println(e.getActionCommand());
                this.setVisible(false);
                reservationIdTarget = (String) myList.getSelectedValue();
                listScroller.setVisible(false);
                showOptions();
                selectButton.setText("Select Option");
                selectButton.setActionCommand("Select Option");
                information.setText("Option 4: What would you like to do to the menu.");
                this.add(radioPanel, BorderLayout.CENTER);
                radioPanel.setVisible(true);
                this.setVisible(true);
                break;
            case "back":
                simpleGUI.showGUI();
                break;
        }


    }

    private void showReservationOptions() throws SQLException {
        reservations = new ArrayList<>();
        reservations = simpleApp.getHasRes();
//        reservations.add("test");
        DefaultListModel listModel = new DefaultListModel();
        for(String s: reservations){
            listModel.addElement(s);
        }

        myList = new JList(listModel);
        myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myList.setLayoutOrientation(JList.VERTICAL);
        myList.setVisibleRowCount(-1);
        myList.setSelectedIndex(0);
        listScroller = new JScrollPane(myList);
        listScroller.setPreferredSize(new Dimension(250, 80));
    }

    private void showOptions(){
        radioPanel = new JPanel(new GridLayout(0, 1));

        JRadioButton addButton = new JRadioButton("Add an Item");
        addButton.setActionCommand("Add");

        JRadioButton removeButton = new JRadioButton("Remove an Item");
        removeButton.setActionCommand("Remove");

        JRadioButton replaceButton = new JRadioButton("Replace an Item");
        replaceButton.setActionCommand("Replace");

        JRadioButton viewButton = new JRadioButton("View Menu");
        viewButton.setActionCommand("View");
        viewButton.setSelected(true);

        //Group the radio buttons.
        group = new ButtonGroup();
        group.add(addButton);
        group.add(removeButton);
        group.add(replaceButton);
        group.add(viewButton);

        //Register a listener for the radio buttons.
        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        replaceButton.addActionListener(this);
        viewButton.addActionListener(this);

        radioPanel.add(addButton);
        radioPanel.add(removeButton);
        radioPanel.add(replaceButton);
        radioPanel.add(viewButton);
    }

    private void showNewDish(){
        newdish = new JTextField(10);
        newdishPanel = new JPanel(new GridLayout(1,0));
        newdishPanel.add(new JLabel("Enter the name of the new dish."));
        newdishPanel.add(newdish);
    }

    private void showDishes() throws SQLException {
        dishNames = new ArrayList<>();
        dishNames = simpleApp.getHasDish(Integer.parseInt(reservationIdTarget));
        menuIdTarget = Integer.parseInt(reservationIdTarget);
//        dishNames.add("test");
        DefaultListModel listModel = new DefaultListModel();
        for(String s: dishNames){
            listModel.addElement(s);
        }
        if(dishNames.size()>0) {
            myList = new JList(listModel);
            myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            myList.setLayoutOrientation(JList.VERTICAL);
            myList.setVisibleRowCount(-1);
            myList.setSelectedIndex(0);
            listScroller = new JScrollPane(myList);
            listScroller.setPreferredSize(new Dimension(250, 80));
        } else {
            simpleGUI.showGUI();
            simpleGUI.showError("The reservation's menu that you selected has no dishes associated.");
        }
    }


}
