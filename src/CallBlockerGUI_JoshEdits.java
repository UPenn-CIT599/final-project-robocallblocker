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
	private ImageIcon accept = new ImageIcon("accept_button.PNG");
	private ImageIcon decline = new ImageIcon("decline_button.PNG");
//	private ImageIcon start = new ImageIcon("startButton.jpg"); // TODO figure out how to incorporate

	// Frame and buttons used in GUI
	private JFrame frame = new JFrame("Call Blocker Program"); // TODO make a better name
	private JButton acceptButton = new JButton(accept);
	private JButton declineButton = new JButton(decline);

//	JButton startButton = new JButton(new ImageIcon(((new ImageIcon("startButton.jpg")).getImage())
//	.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH))); //TODO resize images 
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
		JPanel outerPanel = new JPanel(new GridLayout(2, 1)); // overall outer panel of program
		JPanel innerBottomPanel = new JPanel(new GridLayout(1, 2));
		JPanel upperTopPanel = new JPanel(new FlowLayout());
//		JPanel middlePanel = new JPanel(new GridLayout(1,1)); // will display start button
		
		upperTopPanel.setBackground(Color.BLACK); // set background to black
		JLabel incomingCallDetails = new JLabel();
		
		incomingCallDetails.setText("<html>" + "Please click accept to begin" + "<br>" + "</html>");
		incomingCallDetails.setForeground(Color.WHITE); // set color of label

		// create all buttons

		// should incorporate this to start the program, then change the screen when
		// user clicks start

		// Create actionListeners for events of pressing accept or decline

		/*
		 * If accept button was pressed to begin the program, then display the
		 * corresponding information for the incoming call on the GUI else prompt user
		 * to begin the program.
		 */
		acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				phone.createIncomingCallGetContactInfoForCaller(phone.getUsersContacts());
				if (phone.isIncomingCallSpam()) {
					incomingCallDetails.setText("<html>" + phone.getDisplayIncomingCallerPhoneNumber() + " is calling."
							+ "<br>" + phone.getDisplayIncomingCallerPhoneNumber() + " is likely spam."
							+ "<br>Accept or Decline? </html>");
				} else {
					incomingCallDetails.setText("<html>" + phone.getDisplayIncomingCallerName() + " is calling."
							+ "<br>" + phone.getDisplayIncomingCallerPhoneNumber() + " is likely not spam."
							+ "<br>Accept or Decline? </html>");
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
		frame.add(outerPanel);
		outerPanel.setBackground(Color.BLACK);
		outerPanel.add(upperTopPanel);
//		outerPanel.add(middlePanel);
		upperTopPanel.add(incomingCallDetails);
//		upperTopPanel.add(startProgram);

		outerPanel.add(innerBottomPanel);
		innerBottomPanel.add(acceptButton);
		innerBottomPanel.add(declineButton);
//		middlePanel.add(startButton);

		// required pieces of code
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
