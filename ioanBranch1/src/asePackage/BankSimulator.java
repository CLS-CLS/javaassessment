package asePackage;

import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class BankSimulator {
	public static void main(String[] args){
		GuiControl gui;
		Bank bank = new Bank();
		//Ask the user to select the desired GUI
		int result = JOptionPane.showOptionDialog(null,"Select GUI","Selector",0,
				JOptionPane.INFORMATION_MESSAGE,null,new String[]{"Original GUI","Alternative GUI"},0);
		if (result == 0 ) {
			gui = new GUI();
			QueueGui qGui = new QueueGui();
			
			//TellerOriginalGui[] tellerGuiList = new TellerOriginalGui[Bank.NUMBEROFTELLERS];
			TellerOriginalGui tellerGuiList;
			
			Point p = ((JFrame)gui).getLocation();
			qGui.setLocation(p.x + ((JFrame)gui).getWidth(), p.y);
			
			new QueueController(qGui,bank);
			new Controller(gui, bank, qGui);
			
			for (int i = 0; i < Bank.NUMBEROFTELLERS; i++){
				tellerGuiList = new TellerOriginalGui(i);
				tellerGuiList.setLocation(p.x+(((JFrame)tellerGuiList).getHeight()+30)*i, p.y + ((JFrame)gui).getHeight());
				new TellerOriginalController(tellerGuiList, bank);
			}
		}
		else
			if(result == 1) {
				gui = new AlternativeGUI();
				new Controller(gui, bank);
			}
	}
}

