package asePackage;

import java.awt.event.ActionListener;
import java.util.Observer;

public interface GuiWrapper extends Observer{
	public void setText(String report);
	public void	addRunButtonListener(ActionListener al);
}
