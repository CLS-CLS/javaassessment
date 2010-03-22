package asePackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Controller {
	
	GuiControl gui;
	Bank bank;
	/**
	 * @param gui
	 * @param bank
	 */
	public Controller(GuiControl gui, Bank bank) {
		super();
		this.gui = gui;
		this.bank = bank;
		gui.setCustomerGenerationDelay(bank.getCustomerGenerationDelay());
		gui.setTellerGenerationDelay(bank.getTellerGenerationDelay());

		gui.addRunButtonListener(new RunBankListener());
		gui.addCustomerSliderListener(new CustomerSlideListener());
		gui.addTellerSliderListener(new TellerSlideListener());
		gui.addCloseButtonListener(new CloseButtonListener());
		gui.addPauseButtonListener(new PauseButtonListener());
		bank.setObserver(gui);
	}
	
	
	class RunBankListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			bank.setOpen(true);
			bank.start();
			((JComponent)e.getSource()).setEnabled(false);
			gui.getCloseButton().setEnabled(true);
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
	
	class CloseButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			bank.setOpen(false);
		    ((JComponent)e.getSource()).setEnabled(false);
		}
	}
	
	class PauseButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if(bank.isPaused()) {
				bank.setPaused(false);
				((JComponent)e.getSource()).setEnabled(true);
			}
			else {
				bank.setPaused(true);
				((JComponent)e.getSource()).setEnabled(true);
			}
		}
	}
}

