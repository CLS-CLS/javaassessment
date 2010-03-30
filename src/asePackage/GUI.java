package asePackage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;


import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;

/**
 * Graphical User Interface (GUI) that shows the results of the bank session 
 * @author Ali
 *
 */

public class GUI extends JFrame implements GuiControl,Observer
{
/**
 * The panel containing the button and the textArea
 */
	private JPanel buttonPanel; 
	private JPanel displayPanel;
	private JTextArea textArea;
	private JScrollPane	scrollPane;   
	private JButton startButton = new JButton("Run Bank");
	private JButton closeButton = new JButton("Close Bank");
	private JToggleButton pauseButton = new JToggleButton("Pause");
	private QueueGui queueGui;
	private ArrayList<TellerGui> tellerGuiList = new ArrayList<TellerGui>();
	private JCheckBox queueCheckbox;
	private ArrayList<JCheckBox> tellersCheckbox;

	private int numberTellers=3;
	
	static final int MIN_DELAY = 100;
	static final int MAX_DELAY = 4000;
	private static final int MAXNUMBEROFTELLERS = 4;
	private int customerGenerationDelay = 500;
	private int tellerGenerationDelay = 500;
	private JSlider sliderCustomer;
	private JSlider sliderTeller;
	private Hashtable<Integer, JLabel> labelTable;
	private JMenuBar mb;
	private JCheckBoxMenuItem[] cb = new JCheckBoxMenuItem[MAXNUMBEROFTELLERS];
	
	public GUI(){
		
		super("Simple GUI Stage 2");
		createButtonPanel();
		createDisplayPanel();
	    createTellerGuis();
	    createMenuBar();
		
	    setJMenuBar(mb);
		queueGui = new QueueGui();
			
		
		this.pack();  //used to put all the items in the correct position
		
		Point p = this.getLocation();
		queueGui.setLocation(p.x + this.getWidth(), p.y);
		for (TellerGui tGui :tellerGuiList)
			tGui.setLocation(p.x + tGui.getWidth()*(tGui.getId()-1) , p.y + this.getHeight());
		
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	//used to exit the program if
														//the close button(x) is pressed
	}



	private void createMenuBar() {
		mb = new JMenuBar();
		JMenu mn = new JMenu("Options");
		JMenu tellerSubMenu = new JMenu("Tellers");
		ButtonGroup bg = new ButtonGroup();
		for (int i = 0; i < MAXNUMBEROFTELLERS; i++){
			cb[i] = new JCheckBoxMenuItem(i+1 + "Teller(s)");
			cb[i].setActionCommand(Integer.toString(i+1));
			bg.add(cb[i]);
			tellerSubMenu.add(cb[i]);
		}
						
		cb[2].setSelected(true);
		mn.add(tellerSubMenu);
		mb.add(mn);
	}
		

	private void createDisplayPanel() {
		displayPanel = new JPanel();
		textArea = new JTextArea(35, 45);
		textArea.setEditable(false);
		scrollPane = new JScrollPane(textArea);
		displayPanel.add(scrollPane);
		this.getContentPane().add(displayPanel);
	}


	private void createButtonPanel() {
		JPanel helperPanel = new JPanel();
		helperPanel.setLayout(new GridLayout(3,1));
		
		JPanel customerPanel = new JPanel();
		customerPanel.setLayout(new GridLayout(1,1));
		
		JPanel tellerPanel = new JPanel();
		tellerPanel.setLayout(new GridLayout(1,1));
		
		setLayout(new FlowLayout());		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout(3,3));
		
		helperPanel.add(startButton);
		helperPanel.add(closeButton);
		closeButton.setEnabled(false);
		helperPanel.add(pauseButton);
		
		sliderCustomer = new JSlider(JSlider.VERTICAL, MIN_DELAY, MAX_DELAY, customerGenerationDelay);
		customerPanel.add(sliderCustomer);
        TitledBorder borderCustomer = new TitledBorder(new LineBorder(Color.LIGHT_GRAY), "Customer", TitledBorder.LEFT, TitledBorder.TOP);
        customerPanel.setBorder(borderCustomer);
		
		sliderTeller = new JSlider(JSlider.VERTICAL, MIN_DELAY, MAX_DELAY, tellerGenerationDelay);
		tellerPanel.add(sliderTeller);
        TitledBorder borderTeller = new TitledBorder(new LineBorder(Color.LIGHT_GRAY), "Teller", TitledBorder.LEFT, TitledBorder.TOP);
        tellerPanel.setBorder(borderTeller);

        queueCheckbox = new JCheckBox("Queue Window");
		
		buttonPanel.add(helperPanel,BorderLayout.WEST);
		buttonPanel.add(customerPanel,BorderLayout.CENTER);
		buttonPanel.add(tellerPanel,BorderLayout.EAST );
		buttonPanel.add(queueCheckbox,BorderLayout.SOUTH);

		sliderCustomer.setMajorTickSpacing(100);
		sliderCustomer.setPaintTicks(true);
		
		sliderTeller.setMajorTickSpacing(100);
		sliderTeller.setPaintTicks(true);

		//Create the label table
		labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(new Integer( MIN_DELAY ), new JLabel("Fast  "));
		labelTable.put(new Integer( MAX_DELAY ), new JLabel("Slow  "));
		
		sliderCustomer.setLabelTable(labelTable);
		sliderTeller.setLabelTable(labelTable);
		

		sliderCustomer.setPaintLabels(true);
		sliderTeller.setPaintLabels(true);
		
		queueCheckbox.setMnemonic(KeyEvent.VK_C); 
        queueCheckbox.setSelected(true);
        
		this.getContentPane().add(buttonPanel);
		
	}

	public void setText(String report) {
		textArea.append(report);
		textArea.append("\n---------------------------------------------------" +
				"------------------------------------------------------\n");
		
	}

 
	public void addRunButtonListener(ActionListener al) {
		startButton.addActionListener(al);
	}
	
	public void addCustomerSliderListener(ChangeListener cl){
		sliderCustomer.addChangeListener(cl);
	}
	public void addTellerSliderListener(ChangeListener cl){
		sliderTeller.addChangeListener(cl);
	}

	public void update(Observable o, Object arg) {
		LogEvent logEvent = (LogEvent)arg;
		textArea.append(logEvent.toString() + "\n");
		textArea.setCaretPosition( textArea.getDocument().getLength());
	}
	
	public void addCloseButtonListener (ActionListener al){
		closeButton.addActionListener(al);
	}
	public void addPauseButtonListener (ActionListener al){
		pauseButton.addActionListener(al);
	}
	
	public void addTellersMenuItemListener(ActionListener al){
		for (int i = 0; i < MAXNUMBEROFTELLERS ;i++){
			cb[i].addActionListener(al);
			
		}
	}
	
	public void setCustomerGenerationDelay(int customerGenerationDelay) {
		this.customerGenerationDelay = customerGenerationDelay;
		this.sliderCustomer.setValue(customerGenerationDelay);
	}
	public void setTellerGenerationDelay(int tellerGenerationDelay) {
		this.tellerGenerationDelay = tellerGenerationDelay;
		this.sliderTeller.setValue(tellerGenerationDelay);
	}
	
	
	
	public JComponent getCloseButton(){
		return closeButton;
	}


	public QueueGui getQueueGui() {
		return queueGui;
	}
	
	public void setNumberTellers(int numberTellers) {
		this.numberTellers = numberTellers;
	}

	public int getNumberTellers() {
		return numberTellers;
	}

	public void addTellerGui(TellerGui tGui) {
		tellerGuiList.add(tGui);
		Point p = this.getLocation();
	}
	
	public void createTellerGuis() {
		for(TellerGui tg :tellerGuiList)tg.dispose();
		tellerGuiList.clear();
		for (int i=0; i < numberTellers; i++){
			TellerGui tg = new TellerGui(i+1);
			addTellerGui(tg);
		}
		Point p =this.getLocation();
		for (TellerGui tGui :tellerGuiList)
			tGui.setLocation(p.x + tGui.getWidth()*tGui.getId() , p.y + this.getHeight());
		
	}
	
	public JCheckBoxMenuItem[] getCb() {
		return cb;
	}

    public void addQueueCheckboxListener(ItemListener il) {
        queueCheckbox.addItemListener(il);
    }
  
	public ArrayList<TellerGui> getTellerGuis(){
		return tellerGuiList;
	}
}
