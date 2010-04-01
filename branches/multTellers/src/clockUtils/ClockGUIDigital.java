package clockUtils;


import java.awt.Color;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * 
 * @author Chris
 * The digital representation of the clock model.
 * The clock model and the GUI are connected using the observer pattern
 */
public class ClockGUIDigital extends JFrame implements Observer{
	
	//The "time left" display area
	JTextArea ta = new JTextArea(1, 5);
	
	ClockModel clockMode;
	
	
	public ClockGUIDigital(ClockModel clockModel) {
		super("Digital CountDown Clock");
		this.clockMode = clockModel;
		
		createGui();
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		clockModel.addObserver(this);
	
	}

    /**
     *  Creates the elements of the GUI
     *  and adds them in the contentPane.
     */
	private void createGui() {
		JPanel panel = new JPanel();
		panel.setBackground(new Color(130,90,100));
		ta.setFont(new Font(Font.MONOSPACED,Font.BOLD,25));
		ta.setForeground(Color.RED);
		ta.setBorder(BorderFactory.createLoweredBevelBorder());
		ta.setEditable(false);
		ta.setFocusable(false);
		panel.add(ta);
		panel.setBorder(BorderFactory.createLineBorder(Color.RED));
		getContentPane().add(panel);
		
	}

   /**
    * every time the minutes or the time changes
    * the gui updates its display
    * @param obj contains information about the new second and minute values in 
    * a int vertex of 2 elements
    * 
    */
	public void update(Observable arg0, Object obj) {
		int[] time = (int[])obj;
		int minutes = time[0];
		int seconds = time[1];
		ta.setText(minutes+ ":" + seconds);
	}
}
