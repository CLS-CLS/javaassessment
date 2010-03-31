package asePackage;

import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import chrisGui.CGui;

public class BankSimulator {
	public static void main(String[] args){
		GuiControl gui;
		Bank bank = new Bank();
		//Ask the user to select the desired GUI
		int result = JOptionPane.showOptionDialog(null,"Select GUI","Selector",0,
				JOptionPane.INFORMATION_MESSAGE,null,new String[]{"Original GUI","Alternative GUI"},0);
		if (result == 0 ) {
			gui = new GUI();
						
			new Controller(gui, bank);
		}
		else
			if(result == 1) {
				CGui cGui= new CGui();
				new chrisGui.Controller(cGui, bank);
			}
	}
}

