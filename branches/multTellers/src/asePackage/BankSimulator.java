package asePackage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import clockUtils.ClockGUIDigital;
import clockUtils.ClockModel;
import chrisGui.CGui;

public class BankSimulator {
	public static void main(String[] args){
		
		
		//Ask the user to select the desired GUI
		int result = JOptionPane.showOptionDialog(null,"Select GUI","Selector",0,
				JOptionPane.INFORMATION_MESSAGE,null,
				new String[]{"Original GUI","Alternative GUI"},0);
		if (result == 0 ) {
			ClockModel clkModel = new ClockModel(0, 5);
			JFrame clkGui = new ClockGUIDigital(clkModel);
			Bank bank = new Bank();
			GUI gui = new GUI(clkGui);
			new Controller(gui, bank,clkModel);
		}
		else
			if(result == 1) {
				Bank bank = new Bank();
				CGui cGui= new CGui();
				new chrisGui.Controller(cGui, bank);
			}
	}
}

