import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class Option5 extends JPanel
        implements ActionListener {

    static JLabel information;
    protected JButton backButton;
    protected JButton selectButton;
    protected JTextField amount;
    protected JPanel amountPanel;
    protected JPanel radioPanel;
    protected ButtonGroup group;
    protected JPanel buttonSelector;
    protected static JList myList;
    protected JScrollPane listScroller;
    protected static ArrayList<Integer> accountsid;
    protected static int accountIdTarget;
    private boolean decision = false; //true is ingredients and false if reservations
    private boolean option = false;
    private String partnersId = "";
    protected static ArrayList<String> partners;

    public Option5() throws SQLException {
        Border raisedbevel = BorderFactory.createRaisedBevelBorder();

        information = new JLabel("Option 5.\n Would you like to create an invoice for an ingredients purchase or reservation payment?", JLabel.CENTER);
        information.setOpaque(true);
        information.setOpaque(true);
        information.setBackground(new Color(248, 213, 131));
        information.setPreferredSize(new Dimension(400, 180));

        showSelectionOptions();

        backButton= new JButton("Back");
        backButton.setActionCommand("back");
        backButton.setBorder(raisedbevel);
        backButton.addActionListener(this);

        selectButton = new JButton("Next Step");
        selectButton.setActionCommand("Next Step");
        selectButton.setBorder(raisedbevel);
        selectButton.addActionListener(this);

        setLayout(new BorderLayout());

        buttonSelector = new JPanel();
        buttonSelector.add(selectButton);
        buttonSelector.add(backButton);
        add(information, BorderLayout.NORTH);
        add(radioPanel, BorderLayout.CENTER);
        add(buttonSelector, BorderLayout.SOUTH);
        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Enter Amount":
                System.out.println(e.getActionCommand());
                System.out.println(Integer.parseInt(amount.getText()));
                try {
                    simpleApp.createInvoice(decision, partnersId, Integer.parseInt(amount.getText()), accountIdTarget);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                simpleGUI.showGUI();
                break;
            case "Select Partner":
                System.out.println(e.getActionCommand());
                this.setVisible(false);
                partnersId = (String) myList.getSelectedValue();
                listScroller.setVisible(false);
                showEnterAmount();
                information.setText("Option 5: Please enter the amount for this invoice.");
                selectButton.setText("Enter Amount");
                selectButton.setActionCommand("Enter Amount");
                this.add(amountPanel, BorderLayout.CENTER);
                amountPanel.setVisible(true);
                this.setVisible(true);
                break;
            case "Select Option":
                System.out.println(e.getActionCommand());
                this.setVisible(false);
                radioPanel.setVisible(false);
                try {
                    showPartners(accountIdTarget);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                if(decision) {
                    information.setText("Option 5: Please select the supplier to send this invoice to.");
                    selectButton.setText("Select Supplier");
                }
                else {
                    information.setText("Option 5: Please select the customer to send this invoice to.");
                    selectButton.setText("Select Client Email");
                }

                selectButton.setActionCommand("Select Partner");
                this.add(listScroller, BorderLayout.CENTER);
                listScroller.setVisible(true);
                this.setVisible(true);
                break;
            case "Option 1":
                option = true;
                break;
            case "Option 2":
                option = false;
                break;
            case "Select Account":
                System.out.println(e.getActionCommand());
                System.out.println(myList.getSelectedValue());
                accountIdTarget = (int) myList.getSelectedValue();
                this.setVisible(false);
                listScroller.setVisible(false);
                showAssociatedOptions(decision);
                selectButton.setText("Select Option");
                selectButton.setActionCommand("Select Option");
                information.setText("Option 5: Select the type of client to see the corresponding list.");
                this.add(radioPanel, BorderLayout.CENTER);
                radioPanel.setVisible(true);
                this.setVisible(true);
                break;
            case "Next Step":
                System.out.println(e.getActionCommand());
                this.setVisible(false);
                radioPanel.setVisible(false);
                try {
                    showAcctId(decision);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                information.setText("Option 5: Please select the account you wish to be associated with this invoice.");
                selectButton.setText("Select Account");
                selectButton.setActionCommand("Select Account");
                this.add(listScroller, BorderLayout.CENTER);
                listScroller.setVisible(true);
                this.setVisible(true);
                break;
            case "Ingredients":
                System.out.println(e.getActionCommand());
                decision = true;
                break;
            case "Reservation":
                System.out.println(e.getActionCommand());
                decision = false;
                break;
            case "back":
                simpleGUI.showGUI();
                break;
        }
    }

    private void showAcctId(boolean b) throws SQLException {
        accountsid = simpleApp.getAccountIds(b);
        DefaultListModel listModel = new DefaultListModel();
        for(Integer s: accountsid){
            listModel.addElement(s);
        }

        myList = new JList(listModel);
        myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myList.setLayoutOrientation(JList.VERTICAL);
        myList.setVisibleRowCount(-1);
        listScroller = new JScrollPane(myList);
        listScroller.setPreferredSize(new Dimension(250, 80));
    }

    private void showAssociatedOptions(boolean b){
        radioPanel = new JPanel(new GridLayout(0, 1));
        JRadioButton ingredientsButton = new JRadioButton();
        JRadioButton reservationButton = new JRadioButton();

        if(b){
            ingredientsButton.setText("A supplier with an outstanding rejected invoice with this account.");
            ingredientsButton.setActionCommand("Option 1");
            reservationButton.setText("A supplier associated with this account");
            reservationButton.setActionCommand("Option 2");
        } else {
            ingredientsButton.setText("A customer with an outstanding rejected invoice with this account.");
            ingredientsButton.setActionCommand("Option 1");
            reservationButton.setText("A customer associated with this account");
            reservationButton.setActionCommand("Option 2");
        }

        //Group the radio buttons.
        group = new ButtonGroup();
        group.add(ingredientsButton);
        group.add(reservationButton);

        //Register a listener for the radio buttons.
        ingredientsButton.addActionListener(this);
        reservationButton.addActionListener(this);

        radioPanel.add(reservationButton);
        radioPanel.add(ingredientsButton);
    }

    private void showPartners(int id) throws SQLException {
        partners = simpleApp.getPartners(decision, option, id);
        DefaultListModel listModel = new DefaultListModel();
        for(String s: partners){
            listModel.addElement(s);
        }

        myList = new JList(listModel);
        myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myList.setLayoutOrientation(JList.VERTICAL);
        myList.setVisibleRowCount(-1);
        listScroller = new JScrollPane(myList);
        listScroller.setPreferredSize(new Dimension(250, 80));
    }

    private void showEnterAmount(){
        amount = new JTextField(10);
        amountPanel = new JPanel(new GridLayout(1,0));
        amountPanel.add(new JLabel("Enter the quantity you want to order."));
        amountPanel.add(amount);
    }

    private void showSelectionOptions() {
        radioPanel = new JPanel(new GridLayout(0, 1));

        JRadioButton ingredientsButton = new JRadioButton("Ingredients Purchase");
        ingredientsButton.setActionCommand("Ingredients");

        JRadioButton reservationButton = new JRadioButton("Reservation Payment");
        reservationButton.setActionCommand("Reservation");

        //Group the radio buttons.
        group = new ButtonGroup();
        group.add(ingredientsButton);
        group.add(reservationButton);

        //Register a listener for the radio buttons.
        ingredientsButton.addActionListener(this);
        reservationButton.addActionListener(this);

        radioPanel.add(reservationButton);
        radioPanel.add(ingredientsButton);

    }
}
