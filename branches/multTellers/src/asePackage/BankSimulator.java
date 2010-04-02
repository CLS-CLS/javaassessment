package asePackage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import clockUtils.ClockGUIDigital;
import clockUtils.ClockModel;
import chrisGui.CGui;

public class BankSimulator {
	public static void main(String[] args){

		//Ask the user to select the desired GUI
		int result = JOptionPane.showOptionDialog(null,"Select GUI","Selector",0,
				JOptionPane.INFORMATION_MESSAGE,null,
				new String[]{"Original GUI","Alternative GUI"},0);
		if (result == 0) {

			//sets up a clock with 30 seconds countdown
			final ClockModel clkModel = new ClockModel(0, 30);
			final JFrame clkGui = new ClockGUIDigital(clkModel);
			final Bank bank = new Bank();
			
			//used to solve some concurecy problems. If you manage to press the start button very fast
			//immediately after loading the program the clock sometimes doesnot work. Using invokeLAter
			//the program is accessible after all the components are fully instansiated.
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					GUI gui = new GUI(clkGui);
					new Controller(gui, bank,clkModel);
				}
			});
		}
		else
			if(result == 1) {
				Bank bank = new Bank();
				CGui cGui= new CGui();
				new chrisGui.Controller(cGui, bank);
			}
	}
}

