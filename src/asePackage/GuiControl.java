package asePackage;

import java.awt.event.ActionListener;
import java.util.Observer;
import javax.swing.event.ChangeListener;

public interface GuiControl extends Observer{
		
	public void	addRunButtonListener(ActionListener al);
	public void addCustomerSliderListener(ChangeListener cl);
	public void addTellerSliderListener(ChangeListener cl);
	public void addCloseButtonListener (ActionListener al);

	public void setCustomerGenerationDelay(int customerGenerationDelay);
	public void setTellerGenerationDelay(int tellerGenerationDelay);
}
