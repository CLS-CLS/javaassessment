package asePackage;

import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Controller {
	
	GuiControl gui;
	Bank bank;
	QueueGui queueGui;
	/**
	 * @param gui
	 * @param bank
	 */
	public Controller(GuiControl gui, Bank bank, QueueGui queueGui) {
		super();
		this.gui = gui;
		this.bank = bank;
		this.queueGui = queueGui;
		gui.setCustomerGenerationDelay(bank.getCustomerGenerationDelay());
		gui.setTellerGenerationDelay(bank.getTellerGenerationDelay());

		gui.addRunButtonListener(new RunBankListener());
		gui.addCustomerSliderListener(new CustomerSlideListener());
		gui.addTellerSliderListener(new TellerSlideListener());
		gui.addCloseButtonListener(new CloseButtonListener());
		gui.addPauseButtonListener(new PauseButtonListener());
		gui.addQueueCheckboxListener(new QueueCheckboxListener());
		bank.setObserver(gui);
	}
	
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
	
	class QueueCheckboxListener implements ItemListener{
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.DESELECTED) {
				queueGui.setVisible(false);
				((JCheckBox)e.getSource()).setSelected(false);
			}
			else {
				queueGui.setVisible(true);
				((JCheckBox)e.getSource()).setSelected(true);
			}
		}
	}
}

