package asePackage;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class QueueGui extends JFrame implements Observer{
	JPanel panel;
	JScrollPane scrollPane;
	JTextArea textArea;
	
	public QueueGui() {
		super("Customers in Queue");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		textArea = new JTextArea(20, 15);
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




	public void update(Observable o, Object arg) {
		textArea.setText((String)arg);
	}

}
