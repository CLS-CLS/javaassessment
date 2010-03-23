package asePackage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class TellerGui extends JFrame {
	
	private JTextArea ta;
	private JRadioButton lazyButton = new JRadioButton("Lazy");
	private JRadioButton okButton = new JRadioButton("Ok");
	private JRadioButton hardButton = new JRadioButton("Hard\nWorking");
	private ButtonGroup group = new ButtonGroup();
	private Image tellerImage;
	private JPanel controlPanel = new JPanel();
	private JPanel displayPanel = new JPanel();
	private MediaTracker mt;
	private int id;
	
	public TellerGui(int id) {
		super("Teller No " + id);
		this.id = id;
		loadImage();
		createTextArea();
		createGroupButtons();
		displayPanel.setLayout(new FlowLayout());
		
		controlPanel.setBorder(BorderFactory.
				createBevelBorder(0));
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		displayPanel.add(ta);
		add(displayPanel,BorderLayout.NORTH);
		add(controlPanel,BorderLayout.SOUTH);
		pack();
		setVisible(true);
	}
	
	private void createGroupButtons() {
		group.add(lazyButton);
		group.add(okButton);
		group.add(hardButton);
		controlPanel.add(lazyButton);
		controlPanel.add(okButton);
		controlPanel.add(hardButton);
	}

	/**
	 * instantiates the image and waits to get loaded
	 */
	private void loadImage() {
		tellerImage = Toolkit.getDefaultToolkit().createImage("teller.jpg");
		mt = new MediaTracker(this);
		mt.addImage(tellerImage, 1);
		try { mt.waitForID(1); }
        catch(InterruptedException e) { }
	}

	private void createTextArea() {
		ta =  new JTextArea(10,40){
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
		
	}

	public static void main(String[] args) {
		TellerGui tg = new TellerGui(1);
		tg.ta.setText("ASDasdas");
		
	}
}
