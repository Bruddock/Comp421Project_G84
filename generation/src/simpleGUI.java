import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Dimension2D;
import java.sql.SQLException;


public class simpleGUI extends JPanel
        implements ActionListener {
    protected JButton b1, b2, b3, b4, b5, b6;
    protected static JFrame frame;

    public simpleGUI() {

        Border raisedbevel = BorderFactory.createRaisedBevelBorder();

        JLabel welcome = new JLabel("Hello, welcome to the catering co application.", JLabel.CENTER);
        welcome.setOpaque(true);
        welcome.setOpaque(true);
        welcome.setBackground(new Color(248, 213, 131));
        welcome.setPreferredSize(new Dimension(400, 180));

        b1 = new JButton("Option 1: Update the status of an invoice.");
        b1.setActionCommand("Option1");
        b1.setBorder(raisedbevel);
        b1.setPreferredSize(new Dimension(300,40));

        b2 = new JButton("Option 2: Place an order for ingredients.");
        b2.setActionCommand("Option2");
        b2.setBorder(raisedbevel);
        b2.setPreferredSize(new Dimension(300,40));


        b3 = new JButton("Option 3: Cancel a reservation.");
        b3.setActionCommand("Option3");
        b3.setBorder(raisedbevel);
        b3.setPreferredSize(new Dimension(300,40));

        b4 = new JButton("Option 4: View or modify a menu.");
        b4.setActionCommand("Option4");
        b4.setBorder(raisedbevel);
        b4.setPreferredSize(new Dimension(300,40));

        b5 = new JButton("Option 5: Create a customer or supplier invoice.");
        b5.setActionCommand("Option5");
        b5.setBorder(raisedbevel);
        b5.setPreferredSize(new Dimension(300,40));

        b6 = new JButton();
        b6.setText("Quit");
        b6.setActionCommand("Option6");
        b6.setBorder(raisedbevel);
        b6.setPreferredSize(new Dimension(300,40));

        //Listen for actions on buttons 1 and 3.
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);

        b1.setToolTipText("Click this button to disable the middle button.");
        b2.setToolTipText("This middle button does nothing when you click it.");
        b3.setToolTipText("Click this button to enable the middle button.");

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Add Components to this container, using the default FlowLayout.
//        add(Box.createRigidArea(new Dimension(10, 100)));
        add(welcome);
        add(Box.createVerticalGlue());
        add(Box.createHorizontalGlue());
        add(b1);
        add(Box.createVerticalGlue());
        add(new JSeparator(SwingConstants.HORIZONTAL));
        add(b2);
        add(Box.createVerticalGlue());
        add(new JSeparator(SwingConstants.HORIZONTAL));
        add(b3);
        add(Box.createVerticalGlue());
        add(new JSeparator(SwingConstants.HORIZONTAL));
        add(b4);
        add(Box.createVerticalGlue());
        add(new JSeparator(SwingConstants.HORIZONTAL));
        add(b5);
        add(Box.createVerticalGlue());
        add(new JSeparator(SwingConstants.HORIZONTAL));
        add(b6);
        add(Box.createVerticalGlue());
        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    }


    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Option1": System.out.println("Option1");
                frame.getContentPane().setVisible(false);
                Option1 menu1 = null;
                try {
                    menu1 = new Option1();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                menu1.setOpaque(true);
                frame.setContentPane(menu1);
                frame.getContentPane().setVisible(true);
                break;
            case "Option2": System.out.println("Option2");
                frame.getContentPane().setVisible(false);
                Option2 menu2 = null;
                try {
                    menu2 = new Option2();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                menu2.setOpaque(true);
                frame.setContentPane(menu2);
                frame.getContentPane().setVisible(true);
                break;
            case "Option3": System.out.println("Option3");
                break;
            case "Option4": System.out.println("Option4");
                break;
            case "Option5": System.out.println("Option5");
                break;
            case "Option6": System.out.println("Option6");

                break;
        }
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = simpleGUI.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {

        //Create and set up the window.
        frame = new JFrame("CateringCO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addGUI();

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private static void addGUI(){
        JPanel cont = new JPanel(new BorderLayout());
        cont.setOpaque(true);
        cont.setBorder(new EmptyBorder(15,15,15,15));
        cont.setPreferredSize(new Dimension(1000,1000));
        frame.setContentPane(cont);

        //Create and set up the content pane.
        simpleGUI newContentPane = new simpleGUI();
//        newContentPane.setOpaque(true); //content panes must be opaque
        newContentPane.setBorder(BorderFactory.createLineBorder(Color.black));
        cont.add(newContentPane, BorderLayout.CENTER);
    }

    public static void showGUI(){
        frame.getContentPane().setVisible(false);
        addGUI();
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}