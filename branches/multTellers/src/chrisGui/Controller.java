package chrisGui;


import java.awt.event.*;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import asePackage.*;

public class Controller {

	CGui gui;
	Bank bank;
	/**
	 * @param gui
	 * @param bank
	 */
	public Controller(CGui gui, Bank bank){
		super();
		this.gui = gui;
		this.bank = bank;

		gui.setCustomerGenerationDelay(bank.getCustomerGenerationDelay());
		gui.setTellerGenerationDelay(bank.getTellerGenerationDelay());

		gui.addRunButtonListener(new RunBankListener());
		gui.addCustomerSliderListener(new CustomerSlideListener());
		gui.addTellerSliderListener(new TellerSlideListener());
		gui.addCloseButtonListener(new CloseButtonListener());
		//gui.addPauseButtonListener(new PauseButtonListener());
						
		TellerGui[] tellersGui = gui.getTellerGuis();
		for (TellerGui tg : tellersGui){
			bank.setObserver(tg);
		}


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

	
	

}

