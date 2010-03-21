package asePackage;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Observable;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeListener;

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
	
	static final int MIN_DELAY = 0;
	static final int MAX_DELAY = 100;
	static final int INIT_DELAY = 50;
	private JSlider sliderCustomer;
	private JSlider sliderTeller;
	private Hashtable<Integer, JLabel> labelTable;
	
	public GUI()
	{
		super("Simple GUI Stage 1");
		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		textArea = new JTextArea(35, 45);
		textArea.setEditable(false);
		scrollPane = new JScrollPane(textArea);
		
		sliderCustomer = new JSlider(JSlider.VERTICAL, MIN_DELAY, MAX_DELAY, INIT_DELAY);
		sliderTeller = new JSlider(JSlider.VERTICAL, MIN_DELAY, MAX_DELAY, INIT_DELAY);
		
		panel.add(scrollPane);
		panel.add(button);
		panel.add(sliderCustomer);
		panel.add(sliderTeller);
		
		sliderCustomer.setMajorTickSpacing(10);
		sliderCustomer.setPaintTicks(true);
		
		sliderTeller.setMajorTickSpacing(10);
		sliderTeller.setPaintTicks(true);

		//Create the label table
		labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(new Integer( 0 ), new JLabel("Slow"));
		labelTable.put(new Integer( MAX_DELAY ), new JLabel("Fast"));
		
		sliderCustomer.setLabelTable(labelTable);
		sliderTeller.setLabelTable(labelTable);
		

		sliderCustomer.setPaintLabels(true);
		sliderTeller.setPaintLabels(true);

		
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
	
	public void addCustomerSliderListener(ChangeListener cl){
		sliderCustomer.addChangeListener(cl);
	}

	public void update(Observable o, Object arg) {
		textArea.append((String)arg + "\n");
		textArea.setCaretPosition( textArea.getDocument().getLength());
	}


	public JButton getRunButton() {
		return button;
	}
}
