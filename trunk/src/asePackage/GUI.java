package asePackage;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Graphical User Interface (GUI) that shows the results of the bank session 
 * @author Ali
 *
 */

public class GUI extends JFrame implements GuiWrapper
{
/**
 * The panel containing the button and the textArea
 */
	private JPanel panel; 
		private JTextArea textArea;
	/**
	 * it is used to put scroll bars in the textArea
	 */
	private JScrollPane	scrollPane;   
	private JButton button = new JButton("Run Bank");
	
	
	public GUI()
	{
		super("Simple GUI Stage 1");
		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		textArea = new JTextArea(35, 45);
		textArea.setEditable(false);
		scrollPane = new JScrollPane(textArea);
						
		panel.add(scrollPane);
		panel.add(button);
		
		this.getContentPane().add(panel);
		this.pack();  //used to put all the items in the correct position
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	//used to exit the program if
														//the close button(x) is pressed
	}


	public void setText(String report) {
		textArea.append(report);
		textArea.append("\n---------------------------------------------------" +
				"------------------------------------------------------\n");
		
	}


	public void addRunButtonListener(ActionListener al) {
		button.addActionListener(al);
	}


	public void update(Observable o, Object arg) {
		textArea.append((String)arg + "\n");
	}
}
