package asePackage;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.*;
import javax.swing.event.ChangeListener;

class AlternativeGUI extends JFrame implements GuiControl
{
	JTextField custs, out, in;
	JButton button;
	
	public AlternativeGUI ()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	
		this.setSize(300,200);
		JPanel panel = new JPanel (new GridLayout (4, 2));
		
		JLabel lcusts = new JLabel ("Total Customers");
		panel.add(lcusts);
		custs = new JTextField(10);
		panel.add(custs);
		
		JLabel lout = new JLabel ("Total Withdrawn");
		panel.add(lout);
		out= new JTextField(10);	
		panel.add(out);
		
		
		JLabel lin = new JLabel ("Total Deposited");
		panel.add(lin);
		in= new JTextField(10);	
		panel.add(in);
		
		button = new JButton("Start");
		panel.add(button);
		
		this.add(panel);
		this.setLocation(300, 10);
		this.setVisible(true);	
	}
		
	public void setWithdrawn(double w) {
		out.setText(String.format("%1.2f", w));
	}
	
	public void setCusts(int c) {
		custs.setText(Integer.toString(c));
	}
	
	public void setDeposited(double d) {
		in.setText(String.format("%1.2f", d));
	}

	public void addRunButtonListener(ActionListener al) {
		button.addActionListener(al);
		
	}

	public void setText(String report) {
		double withdrawMoney = getWithdrawMoney(report);
		double depositMoney = getDepositMoney(report);
		int totCusts = getTotalCusts(report);
		setCusts(totCusts);
		setWithdrawn(withdrawMoney);
		setDeposited(depositMoney);
	}

	/*
	 *The following methods take as parameter the report of the          
	 * bank and each one searches in the report to find the 
	 * appropriate information 
	 */
	private int getTotalCusts(String report) {
		String indexString = "Number of Processed Customers : ";
		int index = report.indexOf(indexString);
		index = index + indexString.length();
		int endIndex = report.indexOf("\n",index);
		System.err.println(report.substring(index, endIndex));
		return Integer.parseInt(
				report.substring(index, endIndex));
	}

	private double getDepositMoney(String report) {
		String indexString = "Total Deposited Money : �";
		int index = report.indexOf(indexString);
		index = index + indexString.length();
		int endIndex = report.indexOf("\n",index);
		System.err.println(report.substring(index, endIndex));
		return Double.parseDouble(
				report.substring(index, endIndex));
	}

	private double getWithdrawMoney(String report) {
		String indexString = "Total Withdrawn Money : �";
		int index = report.indexOf(indexString);
		index = index + indexString.length();
		int endIndex = report.indexOf("\n",index);
		System.err.println(report.substring(index, endIndex));
		return Double.parseDouble(
				report.substring(index, endIndex));
	}

	public void update(Observable o, Object arg) {
		// TODO Updatge with the final report at the end
		
	}

	public JButton getRunButton() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addCustomerSliderListener(ChangeListener cl) {
		// TODO Auto-generated method stub
		
	}

	public void addTellerSliderListener(ChangeListener cl) {
		// TODO Auto-generated method stub
		
	}

	public void setCustomerGenerationDelay(int customerGenerationDelay) {
		// TODO Auto-generated method stub
		
	}

	public void setTellerGenerationDelay(int tellerGenerationDelay) {
		// TODO Auto-generated method stub
		
	}

	public void addCloseButtonListener(ActionListener al) {
		// TODO Auto-generated method stub
		
	}

	public JComponent getCloseButton() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addPauseButtonListener(ActionListener al) {
		// TODO Auto-generated method stub
		
	}

	public QueueGui getQueueGui() {
		// TODO Auto-generated method stub
		return null;
	}

	public void loadItemListner(ActionListener la) {
		// TODO Auto-generated method stub
		
	}

	public void addAccountItemListener(ActionListener la) {
		// TODO Auto-generated method stub
		
	}

	public void addCustomerItemListener(ActionListener la) {
		// TODO Auto-generated method stub
		
	}

}
