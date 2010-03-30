package asePackage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TellerGui extends JFrame implements Observer {
	
	JPanel panel;
	JScrollPane scrollPane;
	JTextArea textArea;
	private int id;
	
	public TellerGui(int id) {
		super("Teller No " + id);
		this.id = id;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		textArea = new JTextArea(15, 25);
		textArea.setEditable(false);
		scrollPane = new JScrollPane(textArea);
		panel = new JPanel();
		panel.add(scrollPane);
		
		add(panel);
		pack();
		setVisible(true);
	}
	
	public void update(Observable o, Object arg) {
		LogEvent logEvent = (LogEvent)arg;
		if(id == logEvent.getTellerID()){
			textArea.append(logEvent + "\n");
		}
		textArea.setCaretPosition( textArea.getDocument().getLength());
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
}
