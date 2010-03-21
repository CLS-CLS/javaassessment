package asePackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Controller {
	
	GuiWrapper gui;
	Bank bank;
	/**
	 * @param gui
	 * @param bank
	 */
	public Controller(GuiWrapper gui, Bank bank) {
		super();
		this.gui = gui;
		this.bank = bank;
		gui.setCustomerGenerationDelay(bank.getCustomerGenerationDelay());
		gui.setTellerGenerationDelay(bank.getTellerGenerationDelay());

		gui.addRunButtonListener(new RunBankListener());
		gui.addCustomerSliderListener(new CustomerSlideListener());
		gui.addTellerSliderListener(new TellerSlideListener());
		bank.setObserver(gui);
	}
	
	
	class RunBankListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			bank.setOpen(true);
			bank.start();
			gui.getRunButton().setEnabled(false);
			
		}
	}
	
	class CustomerSlideListener implements ChangeListener{

		public void stateChanged(ChangeEvent e) {
			 JSlider source = (JSlider)e.getSource();
		     int delay = (int)source.getValue();
		     bank.setCustomerGenerationDelay(delay);
		}		
	}
	
	class TellerSlideListener implements ChangeListener{

		public void stateChanged(ChangeEvent e) {
			 JSlider source = (JSlider)e.getSource();
		     int delay = (int)source.getValue();
		     bank.setTellerGenerationDelay(delay);
		}		
	}
}

