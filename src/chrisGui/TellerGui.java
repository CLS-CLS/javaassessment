package chrisGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;

import asePackage.LogEvent;

import com.sun.java.swing.plaf.motif.MotifBorders.BevelBorder;

import sun.awt.image.ToolkitImage;

public class TellerGui extends JFrame implements Observer {
	private static final String TELLERIMAGE = "/teller.jpg";
	private JTextArea ta;
	
	private Image tellerImage;
	private JPanel controlPanel = new JPanel();
	private JPanel displayPanel = new JPanel();
	private MediaTracker mt;
	private int id;
	private CustomGlassPane glassPane;
	private boolean timeToErase;

	public TellerGui(int id) {
		super("Teller No " + id);
		this.id = id;
		loadImage();
		createTextArea();
		
		displayPanel.setLayout(new FlowLayout());
				
		controlPanel.setBorder(BorderFactory.createBevelBorder(0));
		controlPanel.setBackground(new Color(153,204,204));
		displayPanel.setBorder(BorderFactory.createBevelBorder(0));
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		displayPanel.add(ta);
		add(displayPanel,BorderLayout.NORTH);
		add(controlPanel,BorderLayout.SOUTH);
		
		
		pack();
		glassPane = new CustomGlassPane(this.getWidth(), this.getHeight());
		setGlassPane(glassPane);
		setVisible(true);
	}

	

	/**
	 * instantiates the image and waits to get loaded
	 */
	private void loadImage() {
		tellerImage = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource(TELLERIMAGE));
		mt = new MediaTracker(this);
		mt.addImage(tellerImage, 1);
		try { mt.waitForID(1); }
		catch(InterruptedException e) { }
	}

	private void createTextArea() {
		
		ta =  new JTextArea(10,45){
			@Override
			public void setOpaque(boolean isOpaque) {
				super.setOpaque(false);
			}
			public void paintComponent (Graphics g){
				g.drawImage(tellerImage, 0, 0, null);				
				super.paintComponent(g);
			}
		};
		ta.setEditable(false);
		ta.setLineWrap(true);

	}

	public static void main(String[] args) {
		TellerGui tg = new TellerGui(1);
		tg.ta.setText("ASDasdas");

	}

	public void update(Observable arg, Object arg1) {
		
		
		LogEvent logEvent = (LogEvent)arg1;
		System.err.println(logEvent.getStatus());
		if(id == logEvent.getTellerID()){
			if(timeToErase){
				ta.setText("");
				timeToErase = false;
			}
			ta.append(logEvent + "\n");
		}
		if (logEvent.getStatus().equals(LogEvent.EXITBANK))timeToErase = true;
		if (logEvent.getStatus().equals(LogEvent.MESSAGE)){
				glassPane.setVisible(true);
				glassPane.animate();
		}
	}

	
}
