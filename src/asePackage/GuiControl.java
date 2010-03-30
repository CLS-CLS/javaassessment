package asePackage;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Observer;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

public interface GuiControl extends Observer{
		
	public void	addRunButtonListener(ActionListener al);
	public void addCustomerSliderListener(ChangeListener cl);
	public void addTellerSliderListener(ChangeListener cl);
	public void addCloseButtonListener (ActionListener al);
	public void addPauseButtonListener (ActionListener al);
	public void addTellersMenuItemListener(ActionListener al);
	public void setCustomerGenerationDelay(int customerGenerationDelay);
	public void setTellerGenerationDelay(int tellerGenerationDelay);
	public void setNumberTellers(int numberTellers);
	public int getNumberTellers();
	public QueueGui getQueueGui();
	public void createTellerGuis();
	public JComponent getCloseButton();
	public ArrayList<TellerGui> getTellerGuis();
	public JComponent[] getCb();
	public void addQueueCheckboxListener(ItemListener event);
}
