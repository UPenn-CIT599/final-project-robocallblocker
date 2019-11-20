import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	JFrame frame = new JFrame("Call Blocker Program"); // TODO make a better name

	static String phonenumber; 
	static boolean spam;

	// Read CVS one time only 
	static ContactInfoReader contactsFromFile = new ContactInfoReader("contacts10.csv");
	static HashMap<String, ContactInfo> map = contactsFromFile.getContactInfoMap();

	// Create User and User Contacts one time only
	static Phone user = new Phone();
	static int numberOfContactsForUser = 5;
	static HashMap<String, ContactInfo> usersContactList = user.createPhoneUserWithContacts(contactsFromFile.getContactInfoMap(), numberOfContactsForUser);


	public static void main(String[] args) {
		usePhone();
		SwingUtilities.invokeLater(new CallBlockerGUI());
	}


	/*
	 * Create a phone call from CVS file 
	 * Compares phone call to User Contact List
	 */
	static void usePhone(){

		IncomingCall incomingCall = new IncomingCall(map);
		ContactInfo incomingInfo = incomingCall.getIncomingCallerInfo();
		phonenumber = incomingInfo.getPhoneNumbers();

		SpamAlgorithm spamAlgo = new SpamAlgorithm();
		spam = spamAlgo.compareAgainst(incomingInfo, usersContactList);

		System.out.println("Spam: " + spam + "\n");		
	}


	@Override
	public void run() {

		JFrame frame = new JFrame("Call Blocker Program"); // TODO make a better name
		JPanel outerPanel = new JPanel(new GridLayout(2,1)); // overall outer panel of program 

		//	JPanel middlePanel = new JPanel(new GridLayout(1,0)); // will display number being dialed 

		JPanel innerBottomPanel = new JPanel(new GridLayout(1,2));
		JPanel upperTopPanel = new JPanel(new FlowLayout());

		upperTopPanel.setBackground(Color.BLACK); // set background to black
		JLabel myLabel = new JLabel();


		if (spam){
			myLabel.setText("<html>" + phonenumber + " is calling." + "<br>" + phonenumber + " is likely spam." + "<br>Accept or Decline? </html>");

		}		
		else {	
			myLabel.setText("<html>" + phonenumber + " is calling." + "<br>" + phonenumber + " is likely not spam." + "<br>Accept or Decline? </html>");
		}


		//		JLabel myLabel2 = new JLabel("You are receiving a call from" + " " + phonenumber);
		myLabel.setForeground(Color.WHITE); // set color of label 
		//		myLabel2.setForeground(Color.WHITE); // set color of label 
		//		myLabel.setVerticalAlignment(0);
		//		myLabel.setHorizontalAlignment(0);
		//		
		//		myLabel2.setVerticalAlignment(2);
		//		myLabel2.setHorizontalAlignment(2);

		// buttons 

		ActionListener acceptListener = new AddAcceptListener();
		ActionListener declineListener = new AddDeclineListener();
		
		JButton acceptButton = new JButton(accept);
		acceptButton.addActionListener(acceptListener);

		JButton declineButton = new JButton(decline);
		declineButton.addActionListener(declineListener);

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
		// upperTopPanel.add(myLabel2);


		//	upperTopPanel.add(myLabel);
		//	upperTopPanel.add(myLabel2);
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

	class AddAcceptListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			usePhone();
			run();
		}            
	}

	class AddDeclineListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			System.exit(0);
		}            
	}

	
	/***
	 * dynamically update myLabel with incoming calls
	 * @return variable used in label in GUI
	 */
	//	public String mysteriousIncomingCall(IncomingCall abc) {
	//
	////		String incomingCalls = "";
	////		ContactInfo incomingInfo = incomingCall.getIncomingCallerInfo();
	////		incomingCalls = incomingInfo.getPhoneNumbers();
	////		return incomingCalls;
	//	}
}
