package asePackage;

import java.awt.event.ActionListener;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.event.ChangeListener;


public interface GuiControl extends Observer{
		
	public void	addRunButtonListener(ActionListener al);
	public void addCustomerSliderListener(ChangeListener cl);
	public void addTellerSliderListener(ChangeListener cl);
	public void addCloseButtonListener (ActionListener al);
	public void addPauseButtonListener (ActionListener al);
	public void addCustomerItemListener(ActionListener la);
	public void addAccountItemListener(ActionListener la);
	public void setCustomerGenerationDelay(int customerGenerationDelay);
	public void setTellerGenerationDelay(int tellerGenerationDelay);
	public QueueGui getQueueGui();
	public JComponent getCloseButton();
}
