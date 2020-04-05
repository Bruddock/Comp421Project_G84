import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class Option2 extends JPanel
            implements ActionListener {

        protected JLabel information;
        protected JButton backButton;
        protected JButton selectButton;
        protected JPanel ingredientPanel;
        protected JTextField ingredientName;
        protected JTextField quantity;
        protected JPanel buttonSelector;
        protected JList myList;
        protected JScrollPane listScroller;
        protected ButtonGroup group;

        protected JPanel datePanel;
        protected JPanel dayPanel;
        protected JPanel monthPanel;
        protected JPanel yearPanel;
        protected ButtonGroup dayGroup;
        protected ButtonGroup monthGroup;
        protected ButtonGroup yearGroup;

        protected static ArrayList<String> menuList;
        protected static ArrayList<Integer> menuId;
        protected static ArrayList<String> dishList;
        protected static ArrayList<String> dishId;
        protected static ArrayList<String> companyList;
        protected static int staffIdTarget;
        protected static int menuIdTarget;
        protected static String dishIdTarget;
        protected static String companyIdTarget;
        protected static boolean deliver = false;


        public Option2() throws SQLException {

            Border raisedbevel = BorderFactory.createRaisedBevelBorder();

            information = new JLabel("Option 2.\n Please select your staff id.", JLabel.CENTER);
            information.setOpaque(true);
            information.setOpaque(true);
            information.setBackground(new Color(248, 213, 131));
            information.setPreferredSize(new Dimension(400, 180));

            showStaffIdList();

            backButton= new JButton("Back");
            backButton.setActionCommand("back");
            backButton.setBorder(raisedbevel);
            backButton.addActionListener(this);

            selectButton = new JButton("Select Employee");
            selectButton.setActionCommand("Select Employee");
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
                case "Deliver":
                    deliver = true;
                    break;
                case "Don't Deliver":
                    deliver = false;
                    break;
                case "Confirm Dates":
                    this.setVisible(false);
                   datePanel.setVisible(false);
                    try {
                        orderIngredients();
                    } catch (SQLException ex) {
                        ex.printStackTrace();

                    }
                    simpleGUI.showGUI();
                    break;
                case "Make Order":
                    System.out.println(myList.getSelectedValue());
                    this.setVisible(false);
                    listScroller.setVisible(false);
                    companyIdTarget = (String) myList.getSelectedValue();
                    try {
                        showDateOptions();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    selectButton.setText("Confirm Dates");
                    selectButton.setActionCommand("Confirm Dates");
                    information.setText("Option 2: Please select the date when you want your ingredients ready.");
                    this.add(datePanel,BorderLayout.CENTER);
                    datePanel.setVisible(true);
                    this.setVisible(true);
                    break;
                case "Save Options":
                    System.out.println(ingredientName.getText());
                    System.out.println(quantity.getText());
                    System.out.println(deliver);
                    ingredientPanel.setVisible(false);
                    try {
                        showCompanies();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    selectButton.setText("Make Order");
                    selectButton.setActionCommand("Make Order");
                    information.setText("Option 2: Please select the company you wish to order from.");
                    this.add(listScroller,BorderLayout.CENTER);
                    listScroller.setVisible(true);
                    this.setVisible(true);
                    break;
                case "Select Dish":
                    System.out.println(myList.getSelectedValue());
                    this.setVisible(false);
                    listScroller.setVisible(false);
                    dishIdTarget = dishId.get(dishList.indexOf(myList.getSelectedValue()));
                    try {
                        showIngredientsOptions();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    selectButton.setText("Save Options");
                    selectButton.setActionCommand("Save Options");
                    information.setText("Option 2: Please enter the name, quantity and delivery preference of the desired ingredient.");
                    this.add(ingredientPanel, BorderLayout.CENTER);
                    this.setVisible(true);
                    break;
                case "Select Menu":
                    System.out.println(myList.getSelectedValue());
                    this.setVisible(false);
                    listScroller.setVisible(false);
                    menuIdTarget = menuId.get(menuList.indexOf(myList.getSelectedValue()));
                    try {
                        showDish();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    selectButton.setText("Select Dish");
                    selectButton.setActionCommand("Select Dish");
                    information.setText("Option 2: Please select a dish you wish to order for.");
                    this.add(listScroller,BorderLayout.CENTER);
                    listScroller.setVisible(true);
                    this.setVisible(true);
                    break;
                case "Select Employee":
                    System.out.println(myList.getSelectedValue());
                    this.setVisible(false);
                    listScroller.setVisible(false);
                    staffIdTarget = (int) myList.getSelectedValue();
                    try {
                        showMenu();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    selectButton.setText("Select Menu");
                    selectButton.setActionCommand("Select Menu");
                    information.setText("Option 2: Please select a menu you wish to order for.");
                    this.add(listScroller,BorderLayout.CENTER);
                    listScroller.setVisible(true);
                    this.setVisible(true);
                    break;
                case "back":
                    simpleGUI.showGUI();
                    break;
            }
        }

        public void showStaffIdList() throws SQLException {
            ArrayList<Integer> idList = simpleApp.getStaffId();
            DefaultListModel listModel = new DefaultListModel();
            for(Integer i: idList){
                listModel.addElement(i);
            }
            myList = new JList(listModel);
            myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            myList.setLayoutOrientation(JList.VERTICAL);
            myList.setVisibleRowCount(-1);
            listScroller = new JScrollPane(myList);
            listScroller.setPreferredSize(new Dimension(250, 80));

        }

        public void showMenu() throws SQLException {
//            menuList = new ArrayList<>();
//            menuId = new ArrayList<>();
//            menuList.add("tester comp");
//            menuId.add(1);
            menuList = simpleApp.getMenu(staffIdTarget);
            menuId = simpleApp.getMenuId(staffIdTarget);
            DefaultListModel listModel = new DefaultListModel();
            for(String s: menuList){
                listModel.addElement(s);
            }
            if(menuList.size()>0) {
                myList = new JList(listModel);
                myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                myList.setLayoutOrientation(JList.VERTICAL);
                myList.setVisibleRowCount(-1);
                listScroller = new JScrollPane(myList);
                listScroller.setPreferredSize(new Dimension(250, 80));
            } else {
                simpleGUI.showGUI();
                simpleGUI.showError("The staffId that you selected has no associated menus.");
            }
        }

        public void showDish() throws SQLException {
//            dishList = new ArrayList<>();
//            dishId = new ArrayList<>();
//            dishList.add("tester comp");
//            dishId.add("tester comp");
            dishList = simpleApp.getDish(menuIdTarget);
            dishId = simpleApp.getDishName(menuIdTarget);
            DefaultListModel listModel = new DefaultListModel();
            for(String s: dishList){
                listModel.addElement(s);
            }
            if(dishList.size()>0) {
                myList = new JList(listModel);
                myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                myList.setLayoutOrientation(JList.VERTICAL);
                myList.setVisibleRowCount(-1);
                listScroller = new JScrollPane(myList);
                listScroller.setPreferredSize(new Dimension(250, 80));
            } else {
                simpleGUI.showGUI();
                simpleGUI.showError("The menu that you selected has no dishes associated.");
            }
        }

        public void showIngredientsOptions() throws SQLException {
            ingredientName = new JTextField(10);
            quantity = new JTextField(10);
            ingredientPanel = new JPanel(new GridLayout(0, 1));
            JPanel ingredientNamePanel = new JPanel(new GridLayout(1, 1));
            JPanel quantitySelectPanel = new JPanel(new GridLayout(1,0));
            JPanel radioPanel = new JPanel(new GridLayout(1, 0));
            JRadioButton deliverButton = new JRadioButton("Deliver");
            deliverButton.setActionCommand("Deliver");

            JRadioButton ddeliverButton = new JRadioButton("Don't Deliver");
            ddeliverButton.setActionCommand("Don't Deliver");
            ddeliverButton.setSelected(true);

            group = new ButtonGroup();
            group.add(deliverButton);
            group.add(ddeliverButton);

            deliverButton.addActionListener(this);
            ddeliverButton.addActionListener(this);

            ingredientNamePanel.add(new JLabel("Enter the name of the ingredient"));
            ingredientNamePanel.add(ingredientName);
            ingredientPanel.add(ingredientNamePanel);

            quantitySelectPanel.add(new JLabel("Enter the quantity you want to order."));
            quantitySelectPanel.add(quantity);
            ingredientPanel.add(quantitySelectPanel);

            radioPanel.add(deliverButton);
            radioPanel.add(ddeliverButton);
            ingredientPanel.add(radioPanel);
        }

        public void showCompanies() throws SQLException {
//            companyList = new ArrayList<>();
//            companyList.add("tester comp");
            companyList = simpleApp.getCompany(deliver);
            DefaultListModel listModel = new DefaultListModel();
            for(String s: companyList){
                listModel.addElement(s);
            }
            myList = new JList(listModel);
            myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            myList.setLayoutOrientation(JList.VERTICAL);
            myList.setVisibleRowCount(-1);
            listScroller = new JScrollPane(myList);
            listScroller.setPreferredSize(new Dimension(250, 80));
        }

        public void showDateOptions() throws SQLException {
            datePanel = new JPanel(new GridLayout(0, 1));
            dayPanel = new JPanel(new GridLayout(4, 0));
            monthPanel = new JPanel(new GridLayout(4, 0));
            yearPanel = new JPanel(new GridLayout(4, 0));

            dayGroup = new ButtonGroup();
            String name;
            for(int i = 1; i<=31; i++){
                if(i<10) name = "0" + i;
                else name = "" + i;
                JRadioButton temp = new JRadioButton(name);
                temp.addActionListener(this);
                temp.setActionCommand(name);
                dayGroup.add(temp);
                dayPanel.add(temp);
            }
            monthGroup = new ButtonGroup();
            for(int i = 1; i<=12; i++){
                if(i<10) name = "0" + i;
                else name = "" + i;
                JRadioButton temp = new JRadioButton(name);
                temp.addActionListener(this);
                temp.setActionCommand(name);
                monthGroup.add(temp);
                monthPanel.add(temp);
            }
            yearGroup = new ButtonGroup();
            for(int i = 2020; i<=2023; i++){
                JRadioButton temp = new JRadioButton("" + i);
                temp.addActionListener(this);
                temp.setActionCommand("" + i);
                yearGroup.add(temp);
                yearPanel.add(temp);
            }

            datePanel.add(new JLabel("Select a day"));
            datePanel.add(dayPanel);
            datePanel.add(new JLabel("Select a month"));
            datePanel.add(monthPanel);
            datePanel.add(new JLabel("Select a year"));
            datePanel.add(yearPanel);

        }

        public void orderIngredients() throws SQLException {
            String date = "";
            try {
                date += (yearGroup.getSelection()).getActionCommand() + "-";
                date += (monthGroup.getSelection()).getActionCommand() + "-";
                date += (dayGroup.getSelection()).getActionCommand();
            } catch (Exception NullPointerException){
                simpleGUI.showError("The date you set is not valid, setting date to today.");
                date = "" + java.time.LocalDateTime.now();
                date = date.substring(0, 10);
                simpleGUI.showGUI();
            }

            int amount;
            try{
                amount = Integer.parseInt(quantity.getText());

            } catch (Exception NumberFormatException){
                amount = 10;
                simpleGUI.showError("The amount you set is not an int, setting quantity to 10.");
                simpleGUI.showGUI();
            }
            simpleApp.orderIngredients(companyIdTarget, dishIdTarget, date, amount, ingredientName.getText());
        }
}
