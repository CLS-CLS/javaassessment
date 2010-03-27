package asePackage;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TellerOriginalGui extends JFrame implements Observer{
	private int tellerID;
	JPanel panel;
	JScrollPane scrollPane;
	JTextArea textArea;
	
	public TellerOriginalGui(int tellerID) {
		super("Teller " + (tellerID+1));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		textArea = new JTextArea(15, 27);
		scrollPane = new JScrollPane(textArea);
		panel = new JPanel();
		panel.add(scrollPane);
		
		add(panel);
		pack();
		setVisible(true);
	}	
	
	public static void main(String[] args) {
		new QueueGui();
	}

	public void toggleVisible(boolean state) {
			panel.setVisible(state);
	}

	public void update(Observable o, Object arg) {
		textArea.setText((String)arg);
	}

	public int getTellerID() {
		return tellerID;
	}

}
