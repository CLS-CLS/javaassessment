package chrisGui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class CustomPanel extends JPanel{
	Image image;
	
	public CustomPanel(Image image) {
		super();
		this.image = image;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		g.drawImage(image, 0, 0, null);
		super.paintComponent(g);
	}
	
	@Override
	public void setOpaque(boolean bool){
		super.setOpaque(false);
	}
	
	



}
