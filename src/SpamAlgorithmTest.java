import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class SpamAlgorithmTest {

	/***
	 * Creates user's contact list using small contacts10.csv file and creates an
	 * incoming call with a contact from that list, so we expect to return a "false"
	 * indicating not a spam call when the user is called.
	 */
	@Test
	void testCompareAgainst() {

		ContactInfoReader list = new ContactInfoReader("contacts10.csv");

		HashMap<String, ContactInfo> map = list.getContactInfoMap();

		// 3rd contact in list
		ContactInfo caller = map.get("Art Venere");

		// just put full list in user's contact list
		UsersContactList users = new UsersContactList(map, 10);

		HashMap<String, ContactInfo> userMap = users.getContactList();

		SpamAlgorithm spamAlgo = new SpamAlgorithm();

		boolean isSpam = spamAlgo.compareAgainst(caller, userMap);

		assertEquals(false, isSpam);
	}

	/***
	 * Creates user's contact list using larger contacts.csv file and creates an
	 * incoming call from that larger list EXCLUDING any of the users contacts, so
	 * we expect to return a "true" indicating a spam call is coming in when the
	 * user is called. We use the while loop to test against all inputs in the
	 * allContacts hashmap
	 */
	@Test
	void testCompareAgainstUsingLargerFile() {
		ContactInfoReader contactsFromCSV = new ContactInfoReader("contacts100.csv");
		HashMap<String, ContactInfo> allContacts = contactsFromCSV.getContactInfoMap();
		int numberOfContacts = 10;
		UsersContactList usersContacts = new UsersContactList(allContacts, numberOfContacts);
		Set<String> contactNameKeysForUsersContacts = new HashSet<String>();
		for (String usersContact : usersContacts.getContactList().keySet()) {
			contactNameKeysForUsersContacts.add(usersContact);
		}
		// remove user's contacts from list we use to generate a random call
		for (String name : contactNameKeysForUsersContacts) {
			if (allContacts.keySet().contains(name)) {
				allContacts.remove(name);
			}
		}
		int numOfIterations = 0;
		boolean isSpam = false;
		while (numOfIterations < allContacts.size()) {
			IncomingCall caller = new IncomingCall(allContacts);
			SpamAlgorithm spamAlgo = new SpamAlgorithm();
			isSpam = spamAlgo.compareAgainst(caller.getIncomingCallerInfo(), usersContacts.getContactList());
			assertEquals(true, isSpam); // check on each iteration, always should be spam (true)
			numOfIterations++;
		}
	}

}
