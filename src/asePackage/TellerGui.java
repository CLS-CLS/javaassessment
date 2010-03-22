package asePackage;

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

import com.sun.java.swing.plaf.motif.MotifBorders.BevelBorder;

import sun.awt.image.ToolkitImage;

public class TellerGui extends JFrame implements Observer {

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
	private CustomGlassPane glassPane;
	private boolean isClosed = false;

	public TellerGui(int id) {
		super("Teller No " + id);
		this.id = id;
		loadImage();
		createTextArea();
		createGroupButtons();
		displayPanel.setLayout(new FlowLayout());
				
		controlPanel.setBorder(BorderFactory.createBevelBorder(0));
		controlPanel.setBackground(new Color(153,204,204));
		displayPanel.setBorder(BorderFactory.createBevelBorder(0));
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		displayPanel.add(ta);
		add(displayPanel,BorderLayout.NORTH);
		add(controlPanel,BorderLayout.SOUTH);
		
		pack();
		glassPane = new CustomGlassPane(this.getWidth(), this.getHeight());
		setGlassPane(glassPane);
		setVisible(true);
	}

	private void createGroupButtons() {
		group.add(lazyButton);
		group.add(okButton);
		group.add(hardButton);
		controlPanel.add(lazyButton);
		controlPanel.add(okButton);
		controlPanel.add(hardButton);
		okButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				glassPane.setVisible(true);
				glassPane.animate();
				System.out.println("Asdasd");
				
			}
		});
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

	public void update(Observable arg0, Object arg1) {
		//if(!isClosed){
			if (arg1 instanceof String[]){
				String[] str = (String[])arg1;
				if(str[0].equals("1") || str[0].equals("2"))
	//				ta.setText(str[1]);
					isClosed = true;
					glassPane.setVisible(true);
					glassPane.animate();
			}
	//	}
		
		
	}

	public void myUpdate(Object o) {
		if (!isClosed){
			String[] str = (String[])o;
			ta.setText(str[1]);
			if(str[0].equals("theEnd")){
				glassPane.setVisible(true);
				glassPane.animate();
				isClosed = true;
				System.err.println("asdasdasdasd");
			}
		}
	}
}
