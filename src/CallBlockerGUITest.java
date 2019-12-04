import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;

import org.junit.jupiter.api.Test;

class CallBlockerGUITest {


	/*
	 * Test that GUI is reading CVS and creating allContacts HashMap. 
	 * Test that allContacts HashMap is same before and after run() method. 
	 */
	@Test
	void testCVSRead() {

		HashMap<String, ContactInfo> allContacts1 = null;
		HashMap<String, ContactInfo> allContacts2 = null;
		assertNull(allContacts1);
		assertNull(allContacts2);

		CallBlockerGUI test = new  CallBlockerGUI(); 
		Phone phone = test.getPhone();
		allContacts1 = phone.getAllContactsMap();
		test.run();
		allContacts2 = phone.getAllContactsMap();

		assertNotNull(allContacts1);
		assertNotNull(allContacts2);
		assertEquals(allContacts1, allContacts2); 
	}


	/*
	 * Test that GUI is creating usersContact HashMap. 
	 * Test that usersContact HashMap is different before and after run() method. 
	 */
	@Test
	void testUsersContactsCreation() {

		HashMap<String, ContactInfo> usersContacts1 = null;
		HashMap<String, ContactInfo> usersContacts2 = null;
		assertNull(usersContacts1);
		assertNull(usersContacts2);
		assertEquals(usersContacts1, usersContacts2); 
		
		CallBlockerGUI test = new  CallBlockerGUI(); 
		Phone phone = test.getPhone();
		usersContacts1 = phone.getUsersContacts();
		test.run();
		usersContacts2 = phone.getUsersContacts();	

		assertNotNull(usersContacts1);
		assertNotNull(usersContacts2);
		assertNotEquals(usersContacts1, usersContacts2); 
	}


	/*
	 * Test that GUI is creating an incoming call. 
	 * Test that incoming call is different before and after run() method. 
	 */
	@Test
	void testIncomingCallCreation() {

		ContactInfo testContactInfo1 = null;
		ContactInfo testContactInfo2 = null;
		assertNull(testContactInfo1);
		assertNull(testContactInfo2);
		assertEquals(testContactInfo1, testContactInfo2); 
		
		CallBlockerGUI test = new  CallBlockerGUI(); 
		Phone phone = test.getPhone();
		testContactInfo1 = phone.createIncomingCallDisplayOnPhoneScreenGUI(phone.getUsersContacts());
		test.run();
		testContactInfo2 = phone.createIncomingCallDisplayOnPhoneScreenGUI(phone.getUsersContacts());

		assertNotNull(testContactInfo1);
		assertNotNull(testContactInfo2);
		assertNotEquals(testContactInfo1, testContactInfo2); 
	}


	/*
	 * Set spamTest as a Null Object
	 * Test that GUI is calling for spamAlgo and then setting spamTest to true or false
	 */
	@Test
	void testSpamAlgoCreation() {

		Boolean spamTest = null;
		assertNull(spamTest);
		
		CallBlockerGUI test = new  CallBlockerGUI(); 
		Phone phone = test.getPhone();
		spamTest = phone.getSpamAlgoForPhone().isSpam();

		assertNotNull(spamTest);
	}
}
