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
public class CallBlockerGUI_JoshEdits implements Runnable {

	Icon accept = new ImageIcon("accept_button.PNG");
	Icon decline = new ImageIcon("decline_button.PNG");
	JFrame frame = new JFrame("Call Blocker Program"); // TODO make a better name
	
	
	// hard-coded we should make a combo-box to let user decide number of contacts.
	static final int numberOfContactsForUser = 5;
	static Phone phone = new Phone();
	static HashMap<String, ContactInfo> usersContactList;

	public static void main(String[] args) {
		usersContactList = phone.createPhoneUserWithContacts(phone.getAllContactsInHashMap(),
				numberOfContactsForUser);
		phone.setUsersContacts(usersContactList);
		activatePhone();
		SwingUtilities.invokeLater(new CallBlockerGUI_JoshEdits());
	}

	/***
	 * This method simply activates an instance of a phone, mimicking a users phone 
	 * who would have a list of contacts, and then turns on the phone and allows 
	 * it to start receiving incoming calls. It is also used to set the phone 
	 * number displayed on the GUI as well as display if a call is likely spam 
	 * or not, making use of the other classes involved. 
	 */
	private static void activatePhone() {
//		// first create HashMap of phone user's contacts 
//		HashMap<String, ContactInfo> usersContactList = phone.createPhoneUserWithContacts(phone.getAllContactsInHashMap(),
//				numberOfContactsForUser);
//		// set current phone state contact list to the users
//		phone.setUsersContacts(usersContactList);
		// create an incoming call and simultaneously get the contact info for the caller
		ContactInfo forIncomingCaller = phone
				.createIncomingCallGetContactInfoForCaller(phone.getAllContactsInHashMap());
		// set the phone number displayed on the GUI to the incoming caller phone number
		phone.setDisplayIncomingCallerPhoneNumber(forIncomingCaller.getPhoneNumbers());
		// set the name displayed on the GUI to the incoming caller name, if it's not spam
		phone.setDisplayIncomingCallerName(forIncomingCaller.getName());
		// set whether or not we display a spam message on the GUI 
		phone.setIncomingCallSpamOrNotSpam(
				phone.getSpamAlgoForPhone().compareAgainst(forIncomingCaller, usersContactList));
	}

	@Override
	public void run() {
		JPanel outerPanel = new JPanel(new GridLayout(2, 1)); // overall outer panel of program
		JPanel innerBottomPanel = new JPanel(new GridLayout(1, 2));
		JPanel upperTopPanel = new JPanel(new FlowLayout());
		upperTopPanel.setBackground(Color.BLACK); // set background to black
		JLabel myLabel = new JLabel();
		if (phone.isIncomingCallSpam()) {
			myLabel.setText("<html>" + phone.getDisplayIncomingCallerPhoneNumber() + " is calling." + "<br>"
					+ phone.getDisplayIncomingCallerPhoneNumber() + " is likely spam."
					+ "<br>Accept or Decline? </html>");
		} else {
			myLabel.setText("<html>" + phone.getDisplayIncomingCallerName() + " is calling." + "<br>"
					+ phone.getDisplayIncomingCallerPhoneNumber() + " is likely not spam."
					+ "<br>Accept or Decline? </html>");
		}
		myLabel.setForeground(Color.WHITE); // set color of label
		ActionListener acceptListener = new AddAcceptListener();
		ActionListener declineListener = new AddDeclineListener();
		JButton acceptButton = new JButton(accept);
		acceptButton.addActionListener(acceptListener);
		JButton declineButton = new JButton(decline);
		declineButton.addActionListener(declineListener);

		/*
		 * Add the components together in this order: Frame > OuterPanel >
		 * [InnerTopPanel, > Label InnerBottomPanel] > Buttons
		 */
		frame.add(outerPanel);
		outerPanel.setBackground(Color.BLACK);
		outerPanel.add(upperTopPanel);
		upperTopPanel.add(myLabel);

		outerPanel.add(innerBottomPanel);
		innerBottomPanel.add(acceptButton);
		innerBottomPanel.add(declineButton);

		// required pieces of code
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	class AddAcceptListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			activatePhone();
			run();
		}
	}

	class AddDeclineListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
		}
	}

}