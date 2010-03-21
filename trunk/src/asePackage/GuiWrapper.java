package asePackage;

import java.awt.event.ActionListener;
import java.util.Observer;

import javax.swing.JButton;

public interface GuiWrapper extends Observer{
	public void setText(String report);
	public void	addRunButtonListener(ActionListener al);
	public JButton getRunButton();
}
