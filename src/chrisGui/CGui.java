package chrisGui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.*;
import javax.swing.event.ChangeListener;

import asePackage.*;

public class CGui extends JFrame{
	private static final String IMAGELOCATION = "images/teller.jpg";
	private static Image backGroundImage;
	
	
	
	private ControlButtons cb;
	private TellerGui[] cTeller = new TellerGui[3];
	private QueueGui qGui; 
	
	
	public CGui() {
		loadImage();
		cb = new ControlButtons(backGroundImage);
		qGui = new QueueGui();
		getContentPane().add(cb);
		for (int i=0; i<3; i++){
			cTeller[i] = new TellerGui(i+1);
		}
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setBackground(Color.RED);
	}
	
	public static void main(String[] args) {
		new CGui();
	}
	
	
	
	private void loadImage() {
		backGroundImage = Toolkit.getDefaultToolkit().createImage(IMAGELOCATION);
		MediaTracker mt = new MediaTracker(this);
		mt.addImage(backGroundImage, 1);
		try { mt.waitForID(1); }
		catch(InterruptedException e) { }
	}

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

	public int getNumberTellers() {
		return 3;
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
