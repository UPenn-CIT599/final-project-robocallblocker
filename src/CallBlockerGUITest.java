import static org.junit.jupiter.api.Assertions.*;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.SwingUtilities;
import org.junit.jupiter.api.Test;

/***
 * This class tests the CallBlockGUI using the phone object instantiated in the
 * GUI as well as a robot to click buttons to ensure specific events occur.
 * 
 * @author ShawnChoudhury
 * @author JoshuaChopra
 * @author ThomasTee
 *
 */
class CallBlockerGUITest {

	/***
	 * Test that GUI is reading CSV and creating allContacts HashMap. Test that
	 * allContacts HashMap is not null after run method, since run method creates a
	 * phone object, and when a phone object is created we have read in the CSV and
	 * created a map with all contacts
	 */
	@Test
	void testCSVReadAllContactsCreated() {
		HashMap<String, ContactInfo> allContacts1 = null;
		CallBlockerGUI test = new CallBlockerGUI();
		test.run();
		Phone phone = test.getPhone();
		allContacts1 = phone.getAllContactsMap();
		assertNotNull(allContacts1);
	}

	/***
	 * Test that when phone object is created in run method, a phone object is
	 * created and then a user is properly created for that phone, with a list of
	 * contacts that are not null.
	 */
	@Test
	void testUsersContactsCreation() {
		HashMap<String, ContactInfo> usersContacts1 = null;
		CallBlockerGUI test = new CallBlockerGUI();
		test.run();
		Phone phone = test.getPhone();
		usersContacts1 = phone.getUsersContacts();
		assertNotNull(usersContacts1);
	}

	/***
	 * Test that GUI is creating an incoming call. Test that incoming call is
	 * different before and after run() method. Originally the incomingCall
	 * contactInfo is set to null, and it should be set to the name, number, etc.
	 * once the call is created. Ensures that phone is properly being created in the
	 * GUI and that a call is created that would be passed to the GUI and displayed.
	 */
	@Test
	void testIncomingCallCreation() {
		ContactInfo testContactInfo1 = null;
		CallBlockerGUI test = new CallBlockerGUI();
		test.run();
		Phone phone = test.getPhone();
		testContactInfo1 = phone.createIncomingCallDisplayOnPhoneScreenGUI(phone.getUsersContacts());
		assertNotNull(testContactInfo1);
	}

	/***
	 * Set spamTest as a Null Object Test that GUI is calling for spamAlgo and then
	 * setting spamTest to true or false, which should happen when the GUI is
	 * running after the phone object is created.
	 */
	@Test
	void testSpamAlgoCreation() {
		Boolean spamTest = null;
		CallBlockerGUI test = new CallBlockerGUI();
		test.run();
		Phone phone = test.getPhone();
		spamTest = phone.getSpamAlgoForPhone().isSpam();
		assertNotNull(spamTest);
	}

	/***
	 * Helper method used to ensure DRY principles, since we do this for multiple
	 * tests.
	 * 
	 * @param gui pass in instance of a GUI
	 */
	void robotClicksStartButton(CallBlockerGUI gui) {
		try {
			TimeUnit.SECONDS.sleep(1); // give the GUI a second to show up so the start button displays
			// get point on screen for start button
			java.awt.Point startButtonScreenLocation = gui.getStartButton().getLocationOnScreen();
			// point is as a float, cast to integers for robot.mouseMove() method
			int xCoordinate = (int) startButtonScreenLocation.getX();
			int yCoordinate = (int) startButtonScreenLocation.getY();
			Robot bot = new Robot();
			TimeUnit.SECONDS.sleep(1); // wait a second then move mouse to start button
			/**
			 * There's a bug where for some reason with high-res screens, the mouse doesn't
			 * always move to the coordinates specified.
			 * 
			 * https://bugs.openjdk.java.net/browse/JDK-8186063
			 * 
			 * Found a fix here: -
			 * https://stackoverflow.com/questions/49577410/robot-mousemove1000-1000-moves-mouse-to-random-locations-on-screen-why
			 * 
			 * We loop up to 100 times to ensure that the mouse gets to the correct
			 * coordinates before clicking.
			 */
			// while loop also works to ensure we keep moving mouse until we're at the right
			// coordinates
			while (MouseInfo.getPointerInfo().getLocation().getX() != xCoordinate
					|| MouseInfo.getPointerInfo().getLocation().getY() != yCoordinate) {
				new Robot().mouseMove(xCoordinate, yCoordinate);
			}
			// press start button
			bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			TimeUnit.MILLISECONDS.sleep(100); // to show button being pressed
			// release start button
			bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			// wait a second before checking for any conditions (i.e., assert button
			// visibility = false, etc.)
			TimeUnit.SECONDS.sleep(1);
		} catch (AWTException e) {
			System.out.println("Could not create robot");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Wait time was interrupted.");
			e.printStackTrace();
		}
		gui.getFrame().dispose(); // close GUI
		// end ringtone since we're not hitting accept
		try {
			gui.getPhone().closeRingtone();
		} catch (LineUnavailableException | IOException e1) {
			System.out.println("Ringtone failed to stop");
		}
	}

	/***
	 * This test ensures that when the robot clicks the start button, the start
	 * button is set to invisible, since we expect that an incoming call is created
	 * and displayed on the screen and the only two buttons showing are accept and
	 * decline buttons.
	 * 
	 */
	@Test
	void testStartButtonIsClickedAndThenInvisible() {
		CallBlockerGUI gui = new CallBlockerGUI();
		SwingUtilities.invokeLater(gui);
		robotClicksStartButton(gui);
		// caller information should show up and start button should be invisible
		assertFalse(gui.getStartButton().isVisible());
	}

	/***
	 * This test ensures that when the robot clicks the start button, the accept
	 * button is visible to the user, since we expect that an incoming call is
	 * created and displayed on the screen and the only two buttons showing are
	 * accept and decline buttons.
	 */
	@Test
	void testAcceptButtonIsVisible() {
		CallBlockerGUI gui = new CallBlockerGUI();
		SwingUtilities.invokeLater(gui);
		robotClicksStartButton(gui);
		// start initiates a call, so accept should show up for user to accept call
		assertTrue(gui.getAcceptButton().isVisible());
	}

	/***
	 * This test ensures that when the robot clicks the start button, the decline
	 * button is visible to the user, since we expect that an incoming call is
	 * created and displayed on the screen and the only two buttons showing are
	 * accept and decline buttons.
	 */
	@Test
	void testDeclineButtonsIsVisible() {
		CallBlockerGUI gui = new CallBlockerGUI();
		SwingUtilities.invokeLater(gui);
		robotClicksStartButton(gui);
		// decline initiates a call, so accept should show up for user to accept call
		assertTrue(gui.getDeclineButton().isVisible());
	}

}
