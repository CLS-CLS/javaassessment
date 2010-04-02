package chrisGui;

import java.awt.BorderLayout;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import asePackage.LogEvent;


@SuppressWarnings("serial")
public class TellerGui extends JFrame implements Observer {
	private static final String TELLERIMAGE = "/teller.jpg";
	private JTextArea ta;
	private Image tellerImage;
	private JPanel displayPanel = new JPanel();
	private MediaTracker mt;   //used to load images 
	private int id;            //the id of this teller gui
	private CustomGlassPane glassPane;  
	private boolean timeToErase;   //used to signal that the textArea should be cleaned 
	                               //(erase all the contents)

	public TellerGui(int id) {
		super("Teller No " + id);
		this.id = id;
		loadImage();
		createTextArea();
		
		displayPanel.setLayout(new FlowLayout());
				
		
		displayPanel.setBorder(BorderFactory.createBevelBorder(0));
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		displayPanel.add(ta);
		add(displayPanel,BorderLayout.NORTH);
		
		
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

	@SuppressWarnings("serial")
	private void createTextArea() {
		//creates the text area with background image
		
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

	

	
	/**
	 * updates the gui with information about the transaction stage. After
	 * the teller serves the customer and Gui automatically erases the information from the
	 * display.
	 */
	public void update(Observable arg, Object arg1) {
		LogEvent logEvent = (LogEvent)arg1;
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
