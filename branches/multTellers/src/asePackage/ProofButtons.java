package asePackage;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class ProofButtons extends JPanel implements ActionListener {
    static String on = "on";
    static String off = "off";
    
    JLabel picture;

    public ProofButtons() {
        super(new BorderLayout());

        //Create the radio buttons.
        JRadioButton onButton = new JRadioButton(on);
        onButton.setActionCommand(on);
        JRadioButton offButton = new JRadioButton(off);
        offButton.setActionCommand(off);
        offButton.setSelected(true);
        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(onButton);
        group.add(offButton);
        
       
        
        onButton.addActionListener(this);
        offButton.addActionListener(this);
        

        //Set up the picture label.
        picture = new JLabel(new ImageIcon("images/" + off + ".png"));

        
       


        //Put the radio buttons in a column in a panel.
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        radioPanel.add(onButton);
        radioPanel.add(offButton);
        

        add(radioPanel, BorderLayout.LINE_START);
        add(picture, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    /** Listens to the radio buttons. */
    public void actionPerformed(ActionEvent e) {
        picture.setIcon(new ImageIcon("images/"
                                        + e.getActionCommand()
                                        + ".png"));
    }

   
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("RadioButtonDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new ProofButtons();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
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
