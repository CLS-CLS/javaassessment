package chrisGui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
/**
 * 
 * @author Chris
 * A panel containing all the Buttons of the Gui
 */
public class ControlButtons extends CustomPanel{
	
	private Image backGroundImage;
	private Image sliderImage;
	
	private static final int MIN_DELAY = 100;
	private static final int MAX_DELAY = 8000;
	private CustomSlider sliderCustomer;
	private CustomSlider sliderTeller;
	private int tellerGenerationDelay = 5600;
	private int customerGenerationDelay = 5200;
	private JButton startButton = new JButton("Start");
	private JButton closeButton = new JButton("Close");
	
	
	
	public ControlButtons(Image image) {
		super(image);
		backGroundImage = image;
		createButtonPanel();
	}
	
	
	

	private void createButtonPanel() {
		CustomPanel helperPanel = new CustomPanel(backGroundImage);
		helperPanel.setLayout(new GridLayout(3,1));
		
		CustomPanel customerPanel = new CustomPanel(backGroundImage);
		customerPanel.setLayout(new GridLayout(1,1));
		
		CustomPanel tellerPanel = new CustomPanel(backGroundImage);
		tellerPanel.setLayout(new GridLayout(1,1));
					
		
		this.setLayout(new BorderLayout(3,3));
		
		
		helperPanel.add(startButton);
		
		helperPanel.add(closeButton);
		closeButton.setEnabled(false);
		
		
		
		sliderCustomer = new CustomSlider(JSlider.VERTICAL, 
				MIN_DELAY,
				MAX_DELAY,
				customerGenerationDelay,
				backGroundImage);
		customerPanel.add(sliderCustomer);
        TitledBorder borderCustomer = new TitledBorder(
        		new LineBorder(Color.BLUE), "Customer", TitledBorder.LEFT, TitledBorder.TOP);
        
        customerPanel.setBorder(borderCustomer);
		
		sliderTeller = new CustomSlider(JSlider.VERTICAL, 
				MIN_DELAY, 
				MAX_DELAY, 
				tellerGenerationDelay,
				backGroundImage);
		tellerPanel.add(sliderTeller);
        TitledBorder borderTeller = new TitledBorder(
        		new LineBorder(Color.BLUE), "Teller", TitledBorder.LEFT, TitledBorder.TOP);
        tellerPanel.setBorder(borderTeller);

       		
		add(helperPanel,BorderLayout.WEST);
		add(customerPanel,BorderLayout.CENTER);
		add(tellerPanel,BorderLayout.EAST );
		

		sliderCustomer.setMajorTickSpacing(100);
		sliderCustomer.setPaintTicks(true);
		
		sliderTeller.setMajorTickSpacing(100);
		sliderTeller.setPaintTicks(true);

		//Create the label table
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(new Integer( MIN_DELAY ), new JLabel("Fast"));
		labelTable.put(new Integer( MAX_DELAY ), new JLabel("Slow"));
		
		sliderCustomer.setLabelTable(labelTable);
		sliderTeller.setLabelTable(labelTable);
		

		sliderCustomer.setPaintLabels(true);
		sliderTeller.setPaintLabels(true);
	        		
	}
	
		

	public JSlider getSliderCustomer() {
		return sliderCustomer;
	}

	
	public JSlider getSliderTeller() {
		return sliderTeller;
	}

	

	public int getTellerGenerationDelay() {
		return tellerGenerationDelay;
	}

	public void setTellerGenerationDelay(int tellerGenerationDelay) {
		this.tellerGenerationDelay = tellerGenerationDelay;
	}

	public int getCustomerGenerationDelay() {
		return customerGenerationDelay;
	}

	public void setCustomerGenerationDelay(int customerGenerationDelay) {
		this.customerGenerationDelay = customerGenerationDelay;
	}

	public JComponent getStartButton() {
		return startButton;
	}
	

	public JComponent getCloseButton() {
		return closeButton;
	}

	
}
