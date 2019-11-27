import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

class IncomingCallTest {


	/***
	 * Creates a HashMap from contacts100.csv and then creates an incoming call. 
	 * The HashMap is then cleaned to remove phone numbers with "0" removeBlankPhoneNumbersFromMapUsedToCreateCalls method.
	 * Test goes through each key of HashMap to verify that not of the phone numbers contain "0". 
	 */
	@Test
	void testRemoveBlankPhoneNumbersFromMapUsedToCreateCalls() {
		Boolean isZero = true;
		ContactInfoReader contactsFromCSV = new ContactInfoReader("contacts100.csv");
		HashMap<String, ContactInfo> map = contactsFromCSV.getContactInfoMap();
		IncomingCall test = new IncomingCall(map);
		HashMap<String, ContactInfo> allContactsCleaned = test.removeBlankPhoneNumbersFromMapUsedToCreateCalls(map);

		for (String key : allContactsCleaned.keySet()) {
			isZero = allContactsCleaned.get(key).getPhoneNumber().equals("0");
			if (isZero) {
				break;
			}
		}
		assertEquals(false, isZero);
	}
}
