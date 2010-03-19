package asePackage;

import java.awt.*;
import javax.swing.*;

class AlternativeGUI extends JFrame 
{
	JTextField custs, out, in;
	public AlternativeGUI ()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	
		this.setSize(300,200);
		JPanel panel = new JPanel (new GridLayout (3, 2));
		
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

}
