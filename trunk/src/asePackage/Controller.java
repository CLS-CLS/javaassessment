package asePackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		gui.addRunButtonListener(new RunBankListener());
	}
	
	
	class RunBankListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			bank.runBank();
			String finalReport = bank.getFinalReport();
			gui.setText(finalReport);
		}
	}

}

