package clockUtils;


import java.awt.Color;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ClockGUIDigital extends JFrame implements Observer{
	
	JTextArea ta = new JTextArea(1, 5);
	ClockModel clockMode;
	
	
	public ClockGUIDigital(ClockModel clockModel) {
		super("Digital CountDown Clock");
		this.clockMode = clockModel;
		clockModel.addObserver(this);
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
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	
	}


	public void update(Observable arg0, Object obj) {
		int[] time = (int[])obj;
		int minutes = time[0];
		int seconds = time[1];
		ta.setText(minutes+ ":" + seconds);
	}
	
	public static void main(String[] args) { 
		ClockModel cm = new ClockModel(1,60);
		new ClockGUIDigital(cm);
		new Thread(cm).start();
	}

}
