package asePackage;

import javax.swing.JOptionPane;

public class BankSimulator{
	  public static void main(String[] args){	
	   GUI gui;
	   //ADDED CODE : Ask the user to select the desired GUI
	   int result = JOptionPane.showOptionDialog(null,"Selece GUI","Selector",0,
			   JOptionPane.INFORMATION_MESSAGE,null,new String[]{"Original GUI","Alternative GUI"},0);
	   if (result == 0 ) gui = new GUI();
	   else gui = new GuiAdapter(new AlternativeGUI());

	   //UNCHANGED CODE		
	   Bank bank = new Bank();     
	   bank.runBank(); 
	   String report = bank.getLog();
	   gui.setText(report);
	 }
	} 

