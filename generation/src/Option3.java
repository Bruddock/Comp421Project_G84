import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class Option3 extends JPanel
        implements ActionListener {
    static JLabel information;
    protected JButton backButton;
    protected JButton selectButton;
    protected JPanel radioPanel;
    protected ButtonGroup group;
    protected JPanel buttonSelector;
    protected static JList myList;
    protected static JScrollPane listScroller;
    protected static ArrayList<String> reservations;
    protected static ArrayList<Integer> reservationid;
    protected static int reservationIdTarget;
    protected static ArrayList<String> events;
    private boolean decision = false;
    public Option3() throws SQLException {

/*        customer emails
*         use this to get all reservations
*          select a reservation
* get all events from the reservations
* ask to cancel / select another reservation
* delete all of it...
*
*
*
* */
        Border raisedbevel = BorderFactory.createRaisedBevelBorder();

        information = new JLabel("Option 3.\n Please select the target email address to view all current reservations.", JLabel.CENTER);
        information.setOpaque(true);
        information.setOpaque(true);
        information.setBackground(new Color(248, 213, 131));
        information.setPreferredSize(new Dimension(400, 180));

        showAddressList();

        backButton= new JButton("Back");
        backButton.setActionCommand("back");
        backButton.setBorder(raisedbevel);
        backButton.addActionListener(this);

        selectButton = new JButton("Select Customer");
        selectButton.setActionCommand("Select Customer");
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
            case "Submit":
                if(decision) {
                    reservationIdTarget = reservationid.get(reservations.indexOf(myList.getSelectedValue()));
                    try {
                        simpleApp.updateEvent(reservationIdTarget);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    simpleGUI.showGUI();
                } else {
                    this.setVisible(false);
                    listScroller.setVisible(false);
                    try {
                        showEvents((Integer) myList.getSelectedValue());
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    selectButton.setText("Select Event");
                    selectButton.setActionCommand("Select Event");
                    this.add(listScroller, BorderLayout.CENTER);
                    listScroller.setVisible(true);
                    this.setVisible(true);
                }
                break;
            case "Yes":
                System.out.println("Yes");
                decision = true;
                break;
            case "No":
                System.out.println("No");
                decision = false;
                break;
            case "Select Event":
                System.out.println(myList.getSelectedValue());
                this.setVisible(false);
                listScroller.setVisible(false);
                showCancelOptions();
                selectButton.setText("Submit");
                selectButton.setActionCommand("Submit");
                this.add(new JLabel("Would you like to cancel this reservation: " + myList.getSelectedValue()), BorderLayout.CENTER);
                this.add(radioPanel, BorderLayout.CENTER);
                radioPanel.setVisible(true);
                this.setVisible(true);
                break;
            case "Select Reservation":
                System.out.println(myList.getSelectedValue());
                this.setVisible(false);
                listScroller.setVisible(false);
                try {
                    showEvents((Integer) myList.getSelectedValue());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                selectButton.setText("Select Event");
                selectButton.setActionCommand("Select Event");
                this.add(listScroller, BorderLayout.CENTER);
                listScroller.setVisible(true);
                this.setVisible(true);
                break;
            case "Select Customer":
                System.out.println(myList.getSelectedValue());
                this.setVisible(false);
                listScroller.setVisible(false);
                try {
                    showReservations((String) myList.getSelectedValue());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                selectButton.setText("Select Reservation");
                selectButton.setActionCommand("Select Reservation");
                this.add(listScroller, BorderLayout.CENTER);
                listScroller.setVisible(true);
                this.setVisible(true);
                break;
            case "back":
                simpleGUI.showGUI();
                break;
        }
    }

    private void showCancelOptions() {
        radioPanel = new JPanel(new GridLayout(0, 1));

        JRadioButton acceptButton = new JRadioButton("Yes");
        acceptButton.setActionCommand("Yes");

        JRadioButton rejectButton = new JRadioButton("No");
        rejectButton.setActionCommand("No");

        //Group the radio buttons.
        group = new ButtonGroup();
        group.add(acceptButton);
        group.add(rejectButton);

        //Register a listener for the radio buttons.
        acceptButton.addActionListener(this);
        rejectButton.addActionListener(this);

        radioPanel.add(rejectButton);
        radioPanel.add(acceptButton);

    }

    private void showEvents(Integer selectedValue) throws SQLException{
        events = simpleApp.getEvents(selectedValue);
        DefaultListModel listModel = new DefaultListModel();
        for(String s: events){
            listModel.addElement(s);
        }

        myList = new JList(listModel);
        myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myList.setLayoutOrientation(JList.VERTICAL);
        myList.setVisibleRowCount(-1);
        listScroller = new JScrollPane(myList);
        listScroller.setPreferredSize(new Dimension(250, 80));
    }

    private void showReservations(String selectedValue) throws SQLException{

        reservations = simpleApp.getReservations(selectedValue);
        reservationid = simpleApp.getReservationIds(selectedValue);
        DefaultListModel listModel = new DefaultListModel();
        for(String s: reservations){
            listModel.addElement(s);
        }

        myList = new JList(listModel);
        myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myList.setLayoutOrientation(JList.VERTICAL);
        myList.setVisibleRowCount(-1);
        listScroller = new JScrollPane(myList);
        listScroller.setPreferredSize(new Dimension(250, 80));
    }

    private static void showAddressList() throws SQLException {

        ArrayList<String> addresses = new ArrayList<>();
        addresses = simpleApp.getAddresses();
        DefaultListModel listModel = new DefaultListModel();
//        addresses.add("test");
        for(String s: addresses){
            listModel.addElement(s);
        }

        myList = new JList(listModel);
        myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myList.setLayoutOrientation(JList.VERTICAL);
        myList.setVisibleRowCount(-1);
        listScroller = new JScrollPane(myList);
        listScroller.setPreferredSize(new Dimension(250, 80));

    }

}
