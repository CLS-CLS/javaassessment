package asePackage;

import java.util.ArrayList;
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
	ArrayList<LogEvent> currentQueue;
	public QueueGui() {
		super("Customers in Queue");
		currentQueue = new ArrayList<LogEvent>();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		textArea = new JTextArea(20, 15);
		scrollPane = new JScrollPane(textArea);
		panel = new JPanel();
		panel.add(scrollPane);
		
		add(panel);
		pack();
		setVisible(true);
	}

	public void update(Observable o, Object arg) {
		LogEvent logEvent = (LogEvent)arg;
		if(logEvent.getStatus().equals(LogEvent.ENTERQUEUE)){
			currentQueue.add(logEvent);
			textArea.setText(getTextFromArrayList());
		}
		
		if(logEvent.getStatus().equals(LogEvent.EXITQUEUE)){
			for (int i = 0; i < currentQueue.size(); i++)
				if(currentQueue.get(i).getCustomerID() == logEvent.getCustomerID())
					currentQueue.remove(i);
			textArea.setText(getTextFromArrayList());
		}
	}
	
	private String getTextFromArrayList() {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < currentQueue.size(); i++)
			result.append(currentQueue.get(i).toStringQueue()+"\n");
		return result.toString();
	}

}
