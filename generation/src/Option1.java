import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class Option1 extends JPanel
        implements ActionListener {

    static JLabel information;
    protected JButton backButton;
    protected JButton selectButton;
    protected JPanel radioPanel;
    protected JPanel buttonSelector;
    protected static JList myList;
    protected static JScrollPane listScroller;
    protected ButtonGroup group;
    protected static ArrayList<String> invoices;
    protected static ArrayList<Integer> invoiceid;
    protected static int invoiceIdTarget;
    public Option1() throws SQLException {

        Border raisedbevel = BorderFactory.createRaisedBevelBorder();

        information = new JLabel("Option 1.\n Please select the target email address to view all current invoices.", JLabel.CENTER);
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
            case "Under Review":
                System.out.println("Under Review");
                try {
                    simpleApp.updateInvoice(invoiceIdTarget, "under review");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                simpleGUI.showGUI();
                break;
            case "Accepted":
                System.out.println("Accepted");
                try {
                    simpleApp.updateInvoice(invoiceIdTarget, "accepted");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                simpleGUI.showGUI();
                break;
            case "Rejected":
                System.out.println("Rejected");
                try {
                    simpleApp.updateInvoice(invoiceIdTarget, "rejected");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                simpleGUI.showGUI();
                break;
            case "Select Customer":
                System.out.println(myList.getSelectedValue());
                this.setVisible(false);
                listScroller.setVisible(false);
                try {
                    showInvoices((String) myList.getSelectedValue());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                selectButton.setText("Select Invoice");
                selectButton.setActionCommand("Select Invoice");
                information.setText("Option 1: Please select the desired invoice to alter.");
                this.add(listScroller,BorderLayout.CENTER);
                listScroller.setVisible(true);
                this.setVisible(true);
                break;
            case "Select Invoice":
                System.out.println(myList.getSelectedValue());
                invoiceIdTarget = invoiceid.get(invoices.indexOf(myList.getSelectedValue()));
                this.setVisible(false);
                listScroller.setVisible(false);
                showUpdateOptions();
                buttonSelector.remove(selectButton);
                information.setText("Option 1: Please select an updated invoice status.");
                this.add(radioPanel, BorderLayout.CENTER);
                this.setVisible(true);
                break;
            case "back":
                simpleGUI.showGUI();
                break;
        }
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
        myList.setSelectedIndex(0);
        myList.setVisibleRowCount(-1);
        listScroller = new JScrollPane(myList);
        listScroller.setPreferredSize(new Dimension(250, 80));

    }

    private void showInvoices(String Address) throws SQLException {
//        invoices = new ArrayList<>();
        invoices = simpleApp.getInvoices(Address);
        invoiceid = simpleApp.getInvoiceId(Address);
        DefaultListModel listModel = new DefaultListModel();
//        invoices.add(Address + " part 2");
        for(String s: invoices){
            listModel.addElement(s);
        }
        myList.setSelectedIndex(0);
        if (invoices.size() > 0) {
            myList = new JList(listModel);
            myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            myList.setLayoutOrientation(JList.VERTICAL);
            myList.setVisibleRowCount(-1);
            listScroller = new JScrollPane(myList);
            listScroller.setPreferredSize(new Dimension(250, 80));
        } else {
            simpleGUI.showGUI();
            simpleGUI.showError("The customer you selected had no associated invoices.");
        }


    }

    private void showUpdateOptions() {

        radioPanel = new JPanel(new GridLayout(0, 1));

        JRadioButton reviewButton = new JRadioButton("Under Review");
        reviewButton.setActionCommand("Under Review");
        reviewButton.setSelected(true);

        JRadioButton acceptButton = new JRadioButton("Accepted");
        acceptButton.setActionCommand("Accepted");

        JRadioButton rejectButton = new JRadioButton("Rejected");
        rejectButton.setActionCommand("Rejected");


        //Group the radio buttons.
        group = new ButtonGroup();
        group.add(reviewButton);
        group.add(acceptButton);
        group.add(rejectButton);

        //Register a listener for the radio buttons.
        reviewButton.addActionListener(this);
        acceptButton.addActionListener(this);
        rejectButton.addActionListener(this);

        radioPanel.add(reviewButton);
        radioPanel.add(rejectButton);
        radioPanel.add(acceptButton);
    }
}
