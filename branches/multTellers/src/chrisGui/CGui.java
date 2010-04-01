package chrisGui;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import asePackage.*;

public class CGui extends JFrame{
	//the location of the image it is going to be used by the componenents 
	//of this gui
	private static final String IMAGELOCATION = "/teller.jpg";
	private static Image backGroundImage;
	
	//the control buttons of this Gui
	private ControlButtons cb;
	
	private TellerGui[] cTeller = new TellerGui[3];
	private QueueGui qGui; 
	
	
	public CGui() {
		loadImage();    //loads the image and waits until the image is loaded
		cb = new ControlButtons(backGroundImage);
		qGui = new QueueGui();
		getContentPane().add(cb);
		for (int i=0; i<3; i++){
			cTeller[i] = new TellerGui(i+1);
		}
		pack();
		Point p = this.getLocation();
		
		//put the components in specific places in the screen
		for (int i=0; i<3; i++)
			cTeller[i].setLocation(p.x + getWidth(),i*cTeller[i].getHeight());
		qGui.setLocation(p.x,p.y+getHeight());
		qGui.setSize(getWidth(), qGui.getHeight());
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setBackground(Color.RED);
	}
	
	/**
	 * loads the image and blocks the program until the image is fully loaded	
	 */
	private void loadImage() {
		backGroundImage = Toolkit.getDefaultToolkit().createImage(
				this.getClass().getResource(IMAGELOCATION));
		MediaTracker mt = new MediaTracker(this);
		mt.addImage(backGroundImage, 1);
		try { mt.waitForID(1); }
		catch(InterruptedException e) { }
	}
	
	
	// addListner methods for the control buttons
	public void addCloseButtonListener(ActionListener al) {
		((JButton)cb.getCloseButton()).addActionListener(al);
		
	}

	public void addCustomerSliderListener(ChangeListener cl) {
		((JSlider)cb.getSliderCustomer()).addChangeListener(cl);
		
	}


	public void addRunButtonListener(ActionListener al) {
		((JButton)cb.getStartButton()).addActionListener(al);
		
	}

	public void addTellerSliderListener(ChangeListener cl) {
		((JSlider)cb.getSliderTeller()).addChangeListener(cl);
		
	}

	
	public void createTellerGuis() {
		for (int i = 0; i < 3; i++){
			cTeller[i] = new TellerGui(i); 
		}
		
	}

	
	public JComponent getCloseButton() {
		return cb.getCloseButton();
	}

	
	public QueueGui getQueueGui() {
		return qGui;
		
	}

	public TellerGui[] getTellerGuis() {
		return cTeller;
	}

	public void setCustomerGenerationDelay(int customerGenerationDelay) {
		cb.setCustomerGenerationDelay(customerGenerationDelay);
		cb.getSliderCustomer().setValue(customerGenerationDelay);
		
	}

	
	public void setTellerGenerationDelay(int tellerGenerationDelay) {
		cb.setTellerGenerationDelay(tellerGenerationDelay);
		cb.getSliderTeller().setValue(tellerGenerationDelay);
		
	}


}
