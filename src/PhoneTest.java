import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

/***
 * Tests phone class
 * 
 * @author Shawn Choudhury
 * @author Joshua Chopra
 * @author Thomas Tee
 */
class PhoneTest {

	/***
	 * This tests whether our createPhoneUsersWithContacts method works properly in
	 * creating a phone user with the specified number of contacts we want, from the
	 * list of all contacts read in from the CSV
	 */
	@Test
	void testCreatePhoneUserWithContacts() {
		Phone ph = new Phone();
		ContactInfoReader cdr = new ContactInfoReader("contacts10.csv");
		int numberOfContacts = 5;
		HashMap<String, ContactInfo> usersContactList = ph.createPhoneUserWithContacts(cdr.getAllContactsInCSV(),
				numberOfContacts);
		// number of key-value pairs should b equal to numberofContacts variable
		assertEquals(numberOfContacts, usersContactList.size());
	}
	
	/***
	 * This method tests whether we properly pass the details of an incoming caller (phone, name, if call is spam) 
	 * to the method that sets the variable of the phone equal to the incoming caller details, to ensure
	 * the proper information is displayed on the GUI. Testing this helps, since we can't directly test 
	 * the GUI itself, and this will let us know that the GUI is displaying what we expect. 
	 */
	@Test
	void testCreateIncomingCallDisplayOnPhoneScreenGUI() {
		Phone ph = new Phone();
		ph.createPhoneUserWithContacts(ph.getAllContactsMap(), ph.getNumberOfContactsForUser());
		ContactInfo incomingCalltest = ph.createIncomingCallDisplayOnPhoneScreenGUI(ph.getUsersContacts());
		assertEquals(ph.getDisplayIncomingCallerName(), incomingCalltest.getName());
		assertEquals(ph.getDisplayIncomingCallerPhoneNumber(), incomingCalltest.getPhoneNumber());
		assertEquals(ph.isIncomingCallSpam(), ph.getSpamAlgoForPhone().compareAgainst(incomingCalltest, ph.getUsersContacts()));
	}

}
