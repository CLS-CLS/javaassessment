package asePackage;

import java.awt.event.ActionListener;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

public interface GuiWrapper extends Observer{
	public void setText(String report);
	
	public void	addRunButtonListener(ActionListener al);
	public void addCustomerSliderListener(ChangeListener cl);
	public void addTellerSliderListener(ChangeListener cl);
	public void addCloseButtonListener (ActionListener al);

	public void setCustomerGenerationDelay(int customerGenerationDelay);
	public void setTellerGenerationDelay(int tellerGenerationDelay);
}
