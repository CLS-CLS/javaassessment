package asePackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CustomGlassPane extends JPanel implements Runnable {
	int width, height;
	int linePosition ;
	Image image;
	
	public CustomGlassPane(int width,int height) {
		super();
		this.width = width;
		this.height = height;
		setOpaque(false);
		image = Toolkit.getDefaultToolkit().createImage("rolo.jpg");
		image = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		linePosition = -height;
	}
	
	public void animate(){
		new Thread(this).start();
	}
	
	public void nextPosition(){
		linePosition+=1;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, linePosition, null);
	}
	
	
	public void run() {
		while(linePosition<0){
			nextPosition();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	

	public static void main(String[] args) {
		JFrame fr = new JFrame();
		fr.add(new JButton("Hello World"));
		fr.setPreferredSize(new Dimension(500,500));
		fr.setGlassPane(new CustomGlassPane(500,500));
		fr.pack();
		fr.setVisible(true);
		CustomGlassPane glass = (CustomGlassPane)fr.getGlassPane();
		glass.setVisible(true);
		glass.nextPosition();
//		new Thread(glass).start();
		glass.animate();
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}


