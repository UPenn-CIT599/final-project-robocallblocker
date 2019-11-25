import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.*;

/**
 * This class will be used to create the GUI for the Call Blocker program.
 * 
 * @author JoshuaChopra
 * @author ShawnChoudhury
 * @author ThomasTee
 *
 */
public class CallBlockerGUI_JoshEdits implements Runnable {

	// Images used in GUI
	private ImageIcon accept = new ImageIcon("acceptCall.gif");
	private ImageIcon decline = new ImageIcon("declineCall.gif");
//	private ImageIcon start = new ImageIcon("startButton.jpg"); // TODO figure out how to incorporate

	// Frame and buttons used in GUI
	JFrame frame = new JFrame("Call Blocker Program"); // TODO make a better name
	private JButton acceptButton = new JButton("Accept",
			new ImageIcon((accept.getImage()).getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));
//	private JButton acceptButton = new JButton(accept);
//	private JButton declineButton = new JButton(decline);
	private JButton declineButton = new JButton("Decline",
			new ImageIcon((decline.getImage()).getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));

//	JButton startButton = new JButton(new ImageIcon(
//			((new ImageIcon("startButton.jpg")).getImage()).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH))); // TODO
//																														// resize
//																														// images
//	private JButton startButton = new JButton(start);

	/*
	 * Need a phone object to run in the GUI, kept static as it does not modify the
	 * GUI object itself, but rather is used in the overall program.
	 */
	private static Phone phone = new Phone();

	public static void main(String[] args) {

		phone.createPhoneUserWithContacts(phone.getAllContactsInHashMap(), phone.getNumberOfContactsForUser());
		SwingUtilities.invokeLater(new CallBlockerGUI_JoshEdits());
	}

	@Override
	public void run() {

		// create panels to hold labels and buttons
		JPanel backgroundPanel = new JPanel(); // overall outer panel of program
		JPanel holdAcceptAndDecline = new JPanel(); // hold accept & decline 
		JPanel panelForIncomingCall = new JPanel(); // details for calls
		
//		JPanel middlePanel = new JPanel(new GridLayout(1,1)); // will display start button

		
		panelForIncomingCall.setBackground(Color.BLACK); // set background to black
		
		// welcome user to the program initially 
		JLabel incomingCallDetails = new JLabel();
		incomingCallDetails.setFont(new Font("Helvetica", Font.BOLD, 17));
		incomingCallDetails.setText("<html>" + "Welcome to Robo-Call Blocker Program." + "<br>" + "<br>"
				+ "Please click the accept button to recieve your first call." + "</html>");
		incomingCallDetails.setForeground(Color.WHITE); // set color of label
		incomingCallDetails.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		JLabel userInstructions = new JLabel("");
		userInstructions.setForeground(Color.WHITE); // set color of label
		userInstructions.setFont(new Font("Helvetica", Font.BOLD, 17));
		userInstructions.setHorizontalAlignment(SwingConstants.CENTER);

		/*
		 * If accept button was pressed to begin the program, then display the
		 * corresponding information for the incoming call on the GUI else prompt user
		 * to begin the program.
		 */
		acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				phone.createIncomingCallDisplayOnPhoneScreenGUI(phone.getUsersContacts());
				if (phone.isIncomingCallSpam()) {
					incomingCallDetails.setText("<html>" + phone.getDisplayIncomingCallerPhoneNumber() + " is calling."
							+ "<br>" + phone.getDisplayIncomingCallerPhoneNumber() + " is likely spam." + "<br>"
							+ "<br>Accept or Decline?");
					userInstructions.setText("<html>" + "<br>If you accept, the program will continue to your next call."
							+ "<br>If you decline, the program will exit." + "</html>");
				} else {
					incomingCallDetails.setText("<html>" + phone.getDisplayIncomingCallerName() + " is calling."
							+ "<br>" + phone.getDisplayIncomingCallerPhoneNumber() + " is likely not spam." + "<br>"
							+ "<br>Accept or Decline?");
					userInstructions.setText("<html>" + "<br>If you accept, the program will continue to your next call."
							+ "<br>If you decline, the program will exit." + "</html>");
				}
			}
		});

		// exit program if user presses decline
		declineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});

		// commented out since haven't figured out how to incorporate
//		startProgram.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				getNextCall = true;
//				run();
//				startProgram.setVisible(false);
//			}
//		});

		/*
		 * Add the components together in this order: Frame > OuterPanel >
		 * [InnerTopPanel, > Label InnerBottomPanel] > Buttons
		 */ 
		
		//OLD LAYOUT
//		frame.add(outerPanel, BorderLayout.CENTER);
//		outerPanel.setBackground(Color.BLACK);
//		outerPanel.add(upperTopPanel, BorderLayout.NORTH);
////		outerPanel.add(middlePanel);
//		upperTopPanel.add(incomingCallDetails);
////		upperTopPanel.add(startProgram);
//
//		outerPanel.add(innerBottomPanel, BorderLayout.SOUTH);
//		innerBottomPanel.add(acceptButton);
//		innerBottomPanel.add(declineButton);
//		middlePanel.add(startButton);

		/*
		 * Set background of button to black, text to white hide border, and place text
		 * below image, but centered
		 */
		declineButton.setBackground(Color.BLACK);
		declineButton.setForeground(Color.WHITE);
		declineButton.setBorderPainted(false);
		declineButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		declineButton.setHorizontalTextPosition(SwingConstants.CENTER);

		/*
		 * Set background of button to black, text to white hide border, and place text
		 * below image, but centered
		 */
		acceptButton.setBackground(Color.BLACK);
		acceptButton.setForeground(Color.WHITE);
		acceptButton.setBorderPainted(false);
		acceptButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		acceptButton.setHorizontalTextPosition(SwingConstants.CENTER);

		backgroundPanel.setBackground(Color.BLACK);
		holdAcceptAndDecline.setBackground(Color.BLACK);

		frame.add(backgroundPanel, BorderLayout.CENTER);
		backgroundPanel.add(userInstructions);
		
		
		panelForIncomingCall.add(incomingCallDetails);
		frame.add(panelForIncomingCall, BorderLayout.NORTH);
		
		holdAcceptAndDecline.add(acceptButton);
		holdAcceptAndDecline.add(declineButton);
		frame.add(holdAcceptAndDecline, BorderLayout.SOUTH);

		// required pieces of code
		frame.setSize(600, 700); // sizes frame to whatever we want
		frame.setLocationRelativeTo(null); // puts at center of screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.pack();
		frame.setVisible(true);
	}
}
