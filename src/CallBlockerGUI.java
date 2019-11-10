import java.awt.*;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.Icon;

/**
 * This class will be used to create the GUI for the Call Blocker program.
 * 
 * @author Joshua Chopra
 *
 */
public class CallBlockerGUI implements Runnable {

	Icon accept = new ImageIcon("accept_button.PNG");
	Icon decline = new ImageIcon("decline_button.PNG");
	String incomingCalls = "";
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new CallBlockerGUI());
	}
	
	
	@Override
	public void run() {
		
		JFrame frame = new JFrame("Call Blocker Program"); // TODO make a better name
		JPanel outerPanel = new JPanel(new GridLayout(2,1)); // overall outer panel of program 
		JPanel upperTopPanel = new JPanel(new FlowLayout());
		upperTopPanel.setBackground(Color.BLACK); // set background to black
		JLabel myLabel = new JLabel("You are receiving a call from" + "\n" + incomingCalls);
		myLabel.setForeground(Color.WHITE); // set color of label 
		
//		JPanel middlePanel = new JPanel(new GridLayout(1,0)); // will display number being dialed 
		
		JPanel innerBottomPanel = new JPanel(new GridLayout(1,2));
		
		// buttons 
		JButton acceptButton = new JButton(accept);
		JButton declineButton = new JButton(decline);
//		JButton dial3 = new JButton("3");
//		JButton dial4 = new JButton("4");
//		JButton dial5 = new JButton("5");
//		JButton dial6 = new JButton("6");
//		JButton dial7 = new JButton("7");
//		JButton dial8 = new JButton("8");
//		JButton dial9 = new JButton("9");
//		JButton dialStar = new JButton("*");
//		JButton dial0 = new JButton("0");
//		JButton dialPound = new JButton("#");
//		

		// Add the components together in this order:
		/*
		 * Frame > OuterPanel > [InnerTopPanel, > Label InnerBottomPanel] > Buttons
		 */
		frame.add(outerPanel);
		outerPanel.setBackground(Color.BLACK);
		outerPanel.add(upperTopPanel);
		upperTopPanel.add(myLabel);
		
//		outerPanel.add(middlePanel);
		outerPanel.add(innerBottomPanel);
		innerBottomPanel.add(acceptButton);
		innerBottomPanel.add(declineButton);
//		innerBottomPannel.add(dial3);
//		innerBottomPannel.add(dial4);
//		innerBottomPannel.add(dial5);
//		innerBottomPannel.add(dial6);
//		innerBottomPannel.add(dial7);
//		innerBottomPannel.add(dial8);
//		innerBottomPannel.add(dial9);
//		innerBottomPannel.add(dialStar);
//		innerBottomPannel.add(dial0);
//		innerBottomPannel.add(dialPound);
		
		

		// required pieces of code
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}
	
	/** dynamically update myLabel with incoming calls */
	public void mysteriousIncomingCall() {
		
		ContactInfoReader list = new ContactInfoReader("contacts10.csv");
		HashMap<String, ContactInfo> map = list.getContactInfoMap();
		IncomingCall incomingCall = new IncomingCall(map);
		ContactInfo incomingInfo = incomingCall.getIncoming();
		incomingCalls = incomingInfo.getPhoneNumbers();
		
	}

}
