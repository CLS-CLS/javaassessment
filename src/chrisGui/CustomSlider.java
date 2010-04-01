package chrisGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.plaf.SliderUI;
/**
 * 
 * @author Chris
 * A JSlider that has a backgound image
 */
public class CustomSlider extends JSlider{
	private Image backGroundImage;
	
	
	public CustomSlider(Image image) {
		super();
		backGroundImage = image;
	}
	public CustomSlider(int oriantation,int min, int max, int value,Image image){
		super(oriantation,min,max,value);
		backGroundImage = image;
		SliderUI sUI = getUI();
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(backGroundImage, 0, 0, null);
		g.setColor(Color.RED);
		//g.fillRect(0, 0, 500, 500);
		super.paintComponent(g);
	}
	
	@Override
	public void setOpaque(boolean bool){
		super.setOpaque(false);
	}


}
