import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;

/**
 * This class will be used to create the GUI for the Call Blocker program, and
 * displays the interface of a user receiving calls, and allows them to continue
 * to receive calls or exit the program in a user-friendly manner.
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
	private ImageIcon start = new ImageIcon("startButton.gif"); // TODO figure out how to incorporate

	// Frame and buttons used in GUI
	private JFrame frame = new JFrame("Call Blocker Program"); // TODO make a better name
	private JButton acceptButton = new JButton("Accept",
			new ImageIcon((accept.getImage()).getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));
	private JButton declineButton = new JButton("Decline",
			new ImageIcon((decline.getImage()).getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));
	private JButton startButton = new JButton("Start",
			new ImageIcon((start.getImage()).getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));
	

	private double numberOfCalls;
	private double numberOfSpamCalls;
	private double percentageSpamCalls;
	
	// create panels to hold labels and buttons
	private JPanel backgroundPanel = new JPanel(); // overall outer panel of program
	private JPanel acceptAndDeclineBottomPanel = new JPanel(); // hold accept & decline
	private JPanel panelForIncomingCall = new JPanel(); // details for calls
	
	
	// labels displayed in GUI 
	private JLabel welcomeThenDisplayCallInfo = new JLabel("<html>" + "Welcome to the Robo-Call Blocker Program."
			+ "<br>" + "<br>" + "Please click the start button to receive your first call." + "<br>" + "<br>" + "<br>"
			+ "<br>" + "<br>" + "<br>" + "<br>" + "</html>");

	private JLabel userInstructions = new JLabel("<html>" + "<br>" + "Press accept to continue receiving calls."
			+ "<br>" + "<br> Press decline to stop receiving calls and see session statistics." + "</html>");
	
	// used for displaying dynamic info on labels, conditionally if call is spam or not 
	private boolean isIncomingCall; // use for dynamically changing label in GUI after call accepted
	private boolean isSpam; // use to determine if we show a phone number (if spam) or name (if not).

	/*
	 * Need a phone object to run in the GUI, kept static as it does not modify the
	 * GUI object itself, but rather is used in the overall program.
	 */
	private static Phone phone = new Phone();

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new CallBlockerGUI_JoshEdits());
	}

	@Override
	public void run() {
		phone.createPhoneUserWithContacts(phone.getAllContactsInHashMap(), phone.getNumberOfContactsForUser());
		formatLabel(welcomeThenDisplayCallInfo, true);
		formatLabel(userInstructions, false); // instructions shown only when first call occurs

		// format buttons
		formatButton(declineButton, false); //initially don't set visible
		formatButton(acceptButton, false); // initially don't set visible
		formatButton(startButton, true); // set visible from the start 

		acceptButton.addActionListener(new ActionListener() {
			/***
			 * If accept button was pressed to begin the program, then display the
			 * corresponding information for the incoming call on the GUI.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
//				phone.createIncomingCallDisplayOnPhoneScreenGUI(phone.getUsersContacts());
				try {
					phone.closeRingtone();
				} catch (LineUnavailableException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// details of incoming call are based on if the call is spam or not
				isIncomingCall = false; // not a new call, just picking up the phone
				// re-use isSpam instance variable that startButton listener set to true or
				// false.
				welcomeThenDisplayCallInfo
						.setText(displaySpamOrNotSpamToUserForIncomingOrAcceptedCalls(isSpam, isIncomingCall));

				userInstructions.setVisible(false); // show user instructions for accept/decline call
				startButton.setVisible(true);
				acceptButton.setVisible(false);
				declineButton.setVisible(false);
			}
		});

		declineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					phone.closeRingtone();
				} catch (LineUnavailableException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				percentageSpamCalls = 100 * (numberOfSpamCalls / numberOfCalls);
				DecimalFormat df = new DecimalFormat("###.##"); // format to 2 decimal places
				// re-use user instructions label and set it to these statistics from the
				// session
				welcomeThenDisplayCallInfo.setVisible(false);
				userInstructions.setText("<html>" + "<br>" + "<br>" + "Total number of calls were: " + numberOfCalls
						+ "<br>" + "Total spam calls received were: " + numberOfSpamCalls + "<br>"
						+ "Percentage of spam calls was: " + df.format(percentageSpamCalls) + "%" + "</html>");

				acceptButton.setVisible(false);
				declineButton.setVisible(false);

			}
		});

		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				phone.ringtone();
				isIncomingCall = true; // start button creates an incoming call
				try {
					phone.startRingtone();
				} catch (LineUnavailableException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				phone.createIncomingCallDisplayOnPhoneScreenGUI(phone.getUsersContacts());
				isSpam = phone.isIncomingCallSpam();
				if (isSpam) {
					numberOfSpamCalls++;
				}
				welcomeThenDisplayCallInfo
						.setText(displaySpamOrNotSpamToUserForIncomingOrAcceptedCalls(isSpam, isIncomingCall));
				userInstructions.setVisible(true); // show user instructions for accept/decline call
				startButton.setVisible(false);
				acceptButton.setVisible(true);
				declineButton.setVisible(true);
				numberOfCalls++;
			}
		});

		setAllPanelsBackgroundColorToBlack(panelForIncomingCall, backgroundPanel, acceptAndDeclineBottomPanel);

		frame.add(backgroundPanel, BorderLayout.CENTER);
		backgroundPanel.add(userInstructions);
		backgroundPanel.add(startButton);

		panelForIncomingCall.add(welcomeThenDisplayCallInfo);

		frame.add(panelForIncomingCall, BorderLayout.NORTH);

		acceptAndDeclineBottomPanel.add(acceptButton);
		acceptAndDeclineBottomPanel.add(declineButton);
		frame.add(acceptAndDeclineBottomPanel, BorderLayout.SOUTH);

		// required pieces of code
		frame.setSize(650, 700); // sizes frame to whatever we want
		frame.setLocationRelativeTo(null); // puts at center of screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.pack();
		frame.setVisible(true);
	}

	/***
	 * This method sets the background of button to black, text to white hide
	 * border, and places text below image, and ensures that the text is centered.
	 * 
	 * 
	 * @param button used with accept and decline buttons
	 * @param initial visibility setting for button 
	 */
	private void formatButton(JButton button, boolean setVisibility) {
		button.setBackground(Color.BLACK);
		button.setForeground(Color.WHITE);
		button.setBorderPainted(false);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVisible(setVisibility);
	}

	/***
	 * This method formats a label by setting the foreground (color of text), font,
	 * and alignment of a label that is added to a component of the overall frame.
	 * 
	 * @param label pass in label to be formatted
	 * @param initial visibility setting for label
	 */
	private void formatLabel(JLabel label, boolean setVisible) {
		label.setForeground(Color.WHITE); // set color of label
		label.setFont(new Font("Helvetica", Font.BOLD, 17));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVisible(setVisible);
	}

	/***
	 * Programmatically sets all colors of our panels to desired color; we want all
	 * of them to be black.
	 * 
	 * @param panel1 background panel
	 * @param panel2 bottom panel with buttons
	 * @param panel3 top panel with call details/welcome message
	 */
	private void setAllPanelsBackgroundColorToBlack(JPanel panel1, JPanel panel2, JPanel panel3) {
		ArrayList<JPanel> panels = new ArrayList<>();
		panels.addAll(Arrays.asList(panel1, panel2, panel3));
		for (JPanel panel : panels) {
			panel.setBackground(Color.BLACK);
		}
	}

	/***
	 * Method makes use of DRY principles by passing in whether a call is spam or
	 * not, and uses output to pass to the label used in the GUI, informing user if
	 * a call is spam or not.
	 * 
	 * @param isSpam          pass in boolean from spamAlgo compareAgainst output
	 * @param newIncomingCall if startbutton is pressed this is true, else false
	 *                        since just an accepted call
	 * @return
	 */
	private String displaySpamOrNotSpamToUserForIncomingOrAcceptedCalls(boolean isSpam, boolean newIncomingCall) {
		String displayNumberOrName;
		String isSpamOrNot;
		if (isSpam) {
			displayNumberOrName = phone.getDisplayIncomingCallerPhoneNumber();
			isSpamOrNot = " is likely spam.";
		} else {
			displayNumberOrName = phone.getDisplayIncomingCallerName();
			isSpamOrNot = " is likely not spam.";
		}
		if (newIncomingCall) {
			return "<html>" + displayNumberOrName + " is calling." + "<br>"
					+ phone.getDisplayIncomingCallerPhoneNumber() + isSpamOrNot + "<br>" + "<br>Accept or Decline?"
					+ "</html>";
		} else {
			return "<html>" + "You are now speaking with " + displayNumberOrName + "<br>" + "<br>"
					+ "Please click the start button to receive your next call." + "<br>" + "<br>" + "<br>" + "<br>"
					+ "<br>" + "<br>" + "<br>" + "</html>";
		}
	}

}
