package chrisGui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

public class CustomGlassPane extends JPanel implements Runnable {
	private static final String IMAGE = "images/rolo.jpg";
	int width, height;
	int linePosition ; //the current position of the image
	Image image;
	
	public CustomGlassPane(int width,int height) {
		super();
		this.width = width;
		this.height = height;
		setOpaque(false);
		
		//loads , scales,  and positions the image
		image = Toolkit.getDefaultToolkit().createImage(IMAGE);
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
	
	/**
	 * the animation of the image
	 * each loop moves the image down
	 */
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
	
}


