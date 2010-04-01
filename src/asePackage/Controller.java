package asePackage;

import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import clockUtils.BankClock;


public class Controller {

	GUI gui;
	Bank bank;
	BankClock clockModel;
	
	/**
	 * Constructor without a clock
	 */
	public Controller(GUI gui, Bank bank){
		super();
		this.gui = gui;
		this.bank = bank;
		
		//sets the generatios delay in the JSlider from the initial value in the bank Class
		gui.setCustomerGenerationDelay(bank.getCustomerGenerationDelay());
		gui.setTellerGenerationDelay(bank.getTellerGenerationDelay());
		
		//adds listeners to the buttons of the GUI
		gui.addRunButtonListener(new RunBankListener());
		gui.addCustomerSliderListener(new CustomerSlideListener());
		gui.addTellerSliderListener(new TellerSlideListener());
		gui.addCloseButtonListener(new CloseButtonListener());
		gui.addTellersMenuItemListener(new NumberOfTellersListener());
		gui.addQueueCheckboxListener(new QueueCheckboxListener());
		gui.addProofButtonActionListener(new ProofActionListener());
		gui.addCustomerItemListener(new LoadCustomerActionlistener());
		gui.addAccountItemListener(new LoadAccountActionlistener());
		
		//registers the observers
		bank.setObserver(gui);
		bank.setObserver(gui.getQueueGui());
		ArrayList<TellerGui> tellersGui = gui.getTellerGuis();
		for (TellerGui tg : tellersGui){
			bank.setObserver(tg);
		}
	}

	/**
	 * Constructor with clock
	 * 
	 */
	public Controller(GUI gui2, Bank bank2, BankClock clkModel) {
		this(gui2,bank2);
		this.clockModel = clkModel;
		clockModel.addTimeObserver(bank);
	}
	
	/**
	 * when the start button is pressed , runs the bank
	 * by 1) setting the bank open, 2) disabling all the other buttons 
	 * (except the close button), 3) start the countDown 
	 *
	 */
	class RunBankListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			bank.setOpen(true);
			bank.start();
			
			//disables buttons
			((JComponent)e.getSource()).setEnabled(false);
			gui.getCloseButton().setEnabled(true);
			JComponent[] cbs = gui.getCb();
			for (JComponent cb : cbs)cb.setEnabled(false);
			JComponent[] proofButtons = gui.getProofButtons();
			for (JComponent pb : proofButtons)pb.setEnabled(false);
			//starts the clock
			if (clockModel != null && !bank.isProofOfAccurateTransactions())new Thread(clockModel).start();
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
	
	/**
	 * when the close button is pressed closes the bank and
	 * resets the clock
	 */
	class CloseButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			bank.setOpen(false);
			((JComponent)e.getSource()).setEnabled(false);
			if (clockModel!=null) clockModel.setToZero();
		}
	}

	/**
	 * When the tellers radio buttons are clicked the method of this
	 * class in invoked and sets the number of tellers in the GUI and Bank 
	 * 
	 */
	class NumberOfTellersListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			//get the already existing Guis of the tellers and unregister them
			//from the bank
			ArrayList<TellerGui> tellerGuis = gui.getTellerGuis();
			for (TellerGui tg :tellerGuis) bank.removeObserver(tg);
			
			//gets the action command which contains the information how many tellers we are
			//going to use and cast this information to int.
			int numberOfTellers =Integer.parseInt(e.getActionCommand());
			//sets the number of tellers and Gui Tellers in the bank and the GUI
			bank.setNumberOfTellers(numberOfTellers);
			bank.createTellers();
			gui.setNumberTellers(numberOfTellers);
			gui.createTellerGuis();
			
			// register to the bank the new tellers Guis
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
	
	/**
	 * 
	 * turns on / off the "Proof of accurate Transaction" mode
	 * Also changes the displayed corresponding picture according to
	 * the selection 
	 *
	 */
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

