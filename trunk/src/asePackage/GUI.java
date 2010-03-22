package asePackage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeListener;

/**
 * Graphical User Interface (GUI) that shows the results of the bank session 
 * @author Ali
 *
 */

public class GUI extends JFrame implements GuiControl,Observer
{
/**
 * The panel containing the button and the textArea
 */
	private JPanel buttonPanel; 
	private JPanel displayPanel;
	private JTextArea textArea;
	private JScrollPane	scrollPane;   
	private JButton startButton = new JButton("Run Bank");
	private JButton closeButton = new JButton("Close Bank");
	private JToggleButton pauseButton = new JToggleButton("Pause");
	
	static final int MIN_DELAY = 10;
	static final int MAX_DELAY = 4000;
	private int customerGenerationDelay = 500;
	private int tellerGenerationDelay = 500;
	private JSlider sliderCustomer;
	private JSlider sliderTeller;
	private Hashtable<Integer, JLabel> labelTable;
	
	public GUI()
	{
		super("Simple GUI Stage 1");
		createButtonPanel();
		createDisplayPanel();
		
		this.getContentPane().add(buttonPanel);
		this.getContentPane().add(displayPanel);
		this.pack();  //used to put all the items in the correct position
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	//used to exit the program if
														//the close button(x) is pressed
	}


	private void createDisplayPanel() {
		displayPanel = new JPanel();
		textArea = new JTextArea(35, 45);
		textArea.setEditable(false);
		scrollPane = new JScrollPane(textArea);
		displayPanel.add(scrollPane);
	}


	private void createButtonPanel() {
		JPanel helperPanel = new JPanel();
		helperPanel.setLayout(new GridLayout(0,1,0,3));
		helperPanel.setBackground(Color.LIGHT_GRAY);
		setLayout(new FlowLayout());
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		buttonPanel.setLayout(new BorderLayout(3,3));
		sliderCustomer = new JSlider(JSlider.VERTICAL, MIN_DELAY, MAX_DELAY, customerGenerationDelay);
		sliderTeller = new JSlider(JSlider.VERTICAL, MIN_DELAY, MAX_DELAY, tellerGenerationDelay);
		helperPanel.add(startButton);
		helperPanel.add(closeButton);
		closeButton.setEnabled(false);
		helperPanel.add(pauseButton);
		buttonPanel.add(helperPanel,BorderLayout.WEST);
		buttonPanel.add(sliderCustomer,BorderLayout.CENTER);
		buttonPanel.add(sliderTeller,BorderLayout.EAST );
		
		sliderCustomer.setMajorTickSpacing(100);
		sliderCustomer.setPaintTicks(true);
		
		sliderTeller.setMajorTickSpacing(100);
		sliderTeller.setPaintTicks(true);

		//Create the label table
		labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(new Integer( MIN_DELAY ), new JLabel("Fast"));
		labelTable.put(new Integer( MAX_DELAY ), new JLabel("Slow"));
		
		sliderCustomer.setLabelTable(labelTable);
		sliderTeller.setLabelTable(labelTable);
		

		sliderCustomer.setPaintLabels(true);
		sliderTeller.setPaintLabels(true);
		
	}


	public void setText(String report) {
		textArea.append(report);
		textArea.append("\n---------------------------------------------------" +
				"------------------------------------------------------\n");
		
	}

 
	public void addRunButtonListener(ActionListener al) {
		startButton.addActionListener(al);
	}
	
	public void addCustomerSliderListener(ChangeListener cl){
		sliderCustomer.addChangeListener(cl);
	}
	public void addTellerSliderListener(ChangeListener cl){
		sliderTeller.addChangeListener(cl);
	}

	public void update(Observable o, Object arg) {
		textArea.append((String)arg + "\n");
		textArea.setCaretPosition( textArea.getDocument().getLength());
	}
	
	public void addCloseButtonListener (ActionListener al){
		closeButton.addActionListener(al);
	}
	public void addPauseButtonListener (ActionListener al){
		pauseButton.addActionListener(al);
	}
	
	public void setCustomerGenerationDelay(int customerGenerationDelay) {
		this.customerGenerationDelay = customerGenerationDelay;
		this.sliderCustomer.setValue(customerGenerationDelay);
	}
	public void setTellerGenerationDelay(int tellerGenerationDelay) {
		this.tellerGenerationDelay = tellerGenerationDelay;
		this.sliderTeller.setValue(tellerGenerationDelay);
	}
	
	public JComponent getCloseButton(){
		return closeButton;
	}
	
}
