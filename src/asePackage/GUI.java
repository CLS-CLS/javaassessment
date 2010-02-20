package asePackage;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
//import javax.swing.border.BevelBorder;
//import javax.swing.border.EtchedBorder;


public class GUI extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JTextArea textArea;
	private JScrollPane	scrollPane;
	private JButton button = new JButton("Run Bank");
	
	
	public GUI() {
		super("Simple GUI Stage 1");
		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		textArea = new JTextArea(35, 45);
		scrollPane = new JScrollPane(textArea);
		button.addActionListener(this);
				
		panel.add(scrollPane);
		panel.add(button);
		
		this.getContentPane().add(panel);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);		
	}


	public void actionPerformed(ActionEvent arg0) {
		Bank bank = new Bank();
		bank.runBank();
		textArea.setText(bank.getLog());
	}
}