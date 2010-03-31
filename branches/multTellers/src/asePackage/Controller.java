package asePackage;

import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import clockUtils.BankClock;
import clockUtils.ClockModel;

public class Controller {

	GUI gui;
	Bank bank;
	BankClock clockModel;
	
	/**
	 * @param gui
	 * @param bank
	 */
	public Controller(GUI gui, Bank bank){
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
		gui.addTellersMenuItemListener(new NumberOfTellersListener());
		gui.addQueueCheckboxListener(new QueueCheckboxListener());
		gui.addProofButtonActionListener(new ProofActionListener());
		//gui.addCustomerItemListener(new LoadCustomerActionlistener());
		//gui.addAccountItemListener(new LoadAccountActionlistener());
		bank.setObserver(gui);
		bank.setObserver(gui.getQueueGui());
		ArrayList<TellerGui> tellersGui = gui.getTellerGuis();
		for (TellerGui tg : tellersGui){
			bank.setObserver(tg);
		}


	}


	public Controller(GUI gui2, Bank bank2, BankClock clkModel) {
		this(gui2,bank2);
		this.clockModel = clkModel;
		clockModel.addTimeObserver(bank);
	}


	class RunBankListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			bank.setOpen(true);
			bank.start();
			((JComponent)e.getSource()).setEnabled(false);
			gui.getCloseButton().setEnabled(true);
			JComponent[] cbs = gui.getCb();
			for (JComponent cb : cbs)cb.setEnabled(false);
			if (clockModel!=null && !bank.isProofOfAccurateTransactions())new Thread(clockModel).start();
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

	//	class PauseButtonListener implements ActionListener{
	//		public synchronized void actionPerformed(ActionEvent e) {
	//		}
	//	}

	class NumberOfTellersListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			ArrayList<TellerGui> tellerGuis = gui.getTellerGuis();
			for (TellerGui tg :tellerGuis) bank.removeObserver(tg);
			int numberOfTellers =Integer.parseInt(e.getActionCommand());
			bank.setNumberOfTellers(numberOfTellers);
			bank.createTellers();
			gui.setNumberTellers(numberOfTellers);
			gui.createTellerGuis();
			// register to the observer the new teller guis
			for (TellerGui tg :tellerGuis) bank.setObserver(tg);			
		}		
	}
	class QueueCheckboxListener implements ItemListener{
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.DESELECTED) {
				gui.getQueueGui().setVisible(false);
				((JCheckBox)e.getSource()).setSelected(false);
			}
			else {
				gui.getQueueGui().setVisible(true);
				((JCheckBox)e.getSource()).setSelected(true);
			}
		}
	}
	
	class ProofActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			gui.setProofPicture(new ImageIcon("images/"+ e.getActionCommand()
                    + ".png"));
			bank.setProofOfAccurateTransactions(e.getActionCommand().equals("on"));
		}
		
	}
	
	 class LoadCustomerActionlistener implements ActionListener {
	    	private JFileChooser fileChooser = new JFileChooser();
	    	//private JTextField fileName = new JTextField(15);
	    	public void actionPerformed(ActionEvent e) {
	            //Open a file dialog.
	        	int retval = fileChooser.showOpenDialog(null);
	            if (retval == JFileChooser.APPROVE_OPTION) {
					//The user selected a file, get it, use it.
	                File file = fileChooser.getSelectedFile();
	                  
	                try {
						bank.loadCustomers(file);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
	            }
	        }
	    }
	
	 class LoadAccountActionlistener implements ActionListener {
	    	private JFileChooser fileChooser = new JFileChooser();
	    	//private JTextField fileName = new JTextField(15);
	    	public void actionPerformed(ActionEvent e) {
	            //Open a file dialog.
	        	int retval = fileChooser.showOpenDialog(null);
	            if (retval == JFileChooser.APPROVE_OPTION) {
					//The user selected a file, get it, use it.
	                File file = fileChooser.getSelectedFile();
	                  
	                try {
						bank.loadAccounts(file);
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
	            }
	        }
	    }
	 

}

