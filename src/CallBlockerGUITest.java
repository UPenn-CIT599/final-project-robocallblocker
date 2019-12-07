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

class CallBlockerGUITest {

	/***
	 * Test that GUI is reading CSV and creating allContacts HashMap. Test that
	 * allContacts HashMap is same before and after run() method.
	 */
	@Test
	void testCSVRead() {

		HashMap<String, ContactInfo> allContacts1 = null;
		HashMap<String, ContactInfo> allContacts2 = null;
		assertNull(allContacts1);
		assertNull(allContacts2);

		CallBlockerGUI test = new CallBlockerGUI();
		Phone phone = test.getPhone();
		allContacts1 = phone.getAllContactsMap();
		test.run();
		allContacts2 = phone.getAllContactsMap();

		assertNotNull(allContacts1);
		assertNotNull(allContacts2);
		assertEquals(allContacts1, allContacts2);
	}

	/***
	 * Test that GUI is creating usersContact HashMap. Test that usersContact
	 * HashMap is different before and after run() method.
	 */
	@Test
	void testUsersContactsCreation() {

		HashMap<String, ContactInfo> usersContacts1 = null;
		HashMap<String, ContactInfo> usersContacts2 = null;
		assertNull(usersContacts1);
		assertNull(usersContacts2);
		assertEquals(usersContacts1, usersContacts2);

		CallBlockerGUI test = new CallBlockerGUI();
		Phone phone = test.getPhone();
		usersContacts1 = phone.getUsersContacts();
		test.run();
		usersContacts2 = phone.getUsersContacts();

		assertNotNull(usersContacts1);
		assertNotNull(usersContacts2);
		assertNotEquals(usersContacts1, usersContacts2);
	}

	/***
	 * Test that GUI is creating an incoming call. Test that incoming call is
	 * different before and after run() method.
	 */
	@Test
	void testIncomingCallCreation() {

		ContactInfo testContactInfo1 = null;
		ContactInfo testContactInfo2 = null;
		assertNull(testContactInfo1);
		assertNull(testContactInfo2);
		assertEquals(testContactInfo1, testContactInfo2);

		CallBlockerGUI test = new CallBlockerGUI();
		Phone phone = test.getPhone();
		testContactInfo1 = phone.createIncomingCallDisplayOnPhoneScreenGUI(phone.getUsersContacts());
		test.run();
		testContactInfo2 = phone.createIncomingCallDisplayOnPhoneScreenGUI(phone.getUsersContacts());

		assertNotNull(testContactInfo1);
		assertNotNull(testContactInfo2);
		assertNotEquals(testContactInfo1, testContactInfo2);
	}

	/***
	 * Set spamTest as a Null Object Test that GUI is calling for spamAlgo and then
	 * setting spamTest to true or false
	 */
	@Test
	void testSpamAlgoCreation() {

		Boolean spamTest = null;
		assertNull(spamTest);

		CallBlockerGUI test = new CallBlockerGUI();
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
