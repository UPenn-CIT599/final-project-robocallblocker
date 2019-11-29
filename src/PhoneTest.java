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

	@Test
	void testCreatePhoneUserWithContacts() {
		Phone ph = new Phone();
		ContactInfoReader cdr = new ContactInfoReader("contacts10.csv");
		int numberOfContacts = 5;
		HashMap<String, ContactInfo> usersContactList = ph.createPhoneUserWithContacts(cdr.getAllContactsInCSV(), numberOfContacts);
		// number of key-value pairs should b equal to numberofContacts variable
		assertEquals(numberOfContacts, usersContactList.size()); 
	}

}
