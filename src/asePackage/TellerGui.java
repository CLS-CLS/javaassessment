package asePackage;

import java.util.Observable;
import java.util.Observer;
import javax.swing.*;


public class TellerGui extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;
	JPanel panel;
	JScrollPane scrollPane;
	JTextArea textArea;
	private int id;

	/**
	 * Create the interface for the required teller
	 * @param id teller id for which is doing the visualisation
	 */
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

	/**
	 * Overwrite the update method for Observer interface.
	 * Gets a logEvent from the Observable object and it tests
	 * if the gui id is similar with the id of the teller who made the transaction.
	 * If they are similar it will append the new message to the existent text.
	 */
	public void update(Observable o, Object arg) {
		LogEvent logEvent = (LogEvent)arg;
		if(id == logEvent.getTellerID()){
			textArea.append(logEvent + "\n");
		}
		textArea.setCaretPosition( textArea.getDocument().getLength());
	}

	/**
	 * Get method to return the id of the current teller gui
	 * @return teller gui id
	 */
	public int getId() {
		return id;
	}
	/**
	 * Set method for the id of the current teller gui
	 * @param teller gui id
	 */
	public void setId(int id){
		this.id = id;
	}
}
