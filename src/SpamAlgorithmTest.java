import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
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

		HashMap<String, ContactInfo> map = list.getAllContactsInCSV();

		// 3rd contact in list
		ContactInfo caller = map.get("2");

		// just put full list in user's contact list
		UsersContactList users = new UsersContactList(map, 10);

		HashMap<String, ContactInfo> userMap = users.getContactList();

		SpamAlgorithm spamAlgo = new SpamAlgorithm();

		boolean isSpam = spamAlgo.compareAgainst(caller, userMap);

		assertEquals(false, isSpam);
	}

	/***
	 * Creates user's contact list with contacts NOT in the CSV and check that the
	 * spamAlgo marks all these contacts as spam, i.e., the isSpam evaluates to
	 * true.
	 */
	@Test
	void testCompareAgainstWithPhoneUserThatHasNoContactsFromCSV() {
		Phone ph = new Phone();
		HashMap<String, ContactInfo> userContactsList = new HashMap<String, ContactInfo>();
		ContactInfo contactNotInCSV = new ContactInfo("a", "b", "c", "d", "e", "f", "g", "h", "i", "j");
		ContactInfo contact2NotInCSV = new ContactInfo("Joshua Chopra", "513-900-9000", "jchopra@seas.upenn.edu",
				"josh.chopra", "400 Walnut Street", "UPenn", "City", "County", "PA", "40013");
		ContactInfo contact3NotInCSV = new ContactInfo("Shawn Choudhury", "513-900-9000", "chshawn@seas.upenn.edu",
				"shawn.choudhury", "700 Nonexistent Street", "Google", "City", "Westchester", "NY", "12209");
		ContactInfo contact4NotInCSV = new ContactInfo("Thomas Tee", "777-870-8700", "tee@seas.upenn.edu", "thomas.tee",
				"900 Walnut Street", "Amazon", "Alabama", "County", "AL", "40013");
		// add contacts to HashMap, hard code a unique ID as key
		userContactsList.put("0", contactNotInCSV);
		userContactsList.put("1", contact2NotInCSV);
		userContactsList.put("2", contact3NotInCSV);
		userContactsList.put("3", contact4NotInCSV);
		SpamAlgorithm spamAlgo = new SpamAlgorithm();
		// only used the cleaned contacts with no blank phone numbers to create calls
		for (ContactInfo contact : ph.getAllContactsInHashMapCleaned().values()) {
			/*
			 * simulate creating an incoming call, without explicitly calling an
			 * incomingCall object since we just grab contactInfo from the incomingCall to
			 * compare anyway
			 */
			ContactInfo incomingCall = contact;
			boolean isSpam = spamAlgo.compareAgainst(incomingCall, userContactsList);
			assertEquals(true, isSpam);
		}
	}

	/**
	 * Test SpamAlgorithm's conditional statement handle for zeros &&
	 * !incoming.getName().equals("0") should return true TODO Maybe we can return
	 * the actual score which should be zero
	 */
	@Test
	void testCompareAgainstContactInfoFilledWithZeros() {

		ContactInfo zeroInfo = new ContactInfo("0", "0", "0", "0", "0", "0", "0", "0", "0", "0");

		ContactInfoReader reader = new ContactInfoReader("contacts100.csv");

		// get the HashMap
		HashMap<String, ContactInfo> map = reader.getAllContactsInCSV();

		UsersContactList userContactsMap = new UsersContactList(map, 10);

		// create user's phoneBook
		HashMap<String, ContactInfo> phoneBook = userContactsMap.getContactList();

		SpamAlgorithm spamAlgo = new SpamAlgorithm();

		boolean isSpam = spamAlgo.compareAgainst(zeroInfo, phoneBook);

		assertEquals(true, isSpam);

	}

	/***
	 * This test creates a phone user that has every single contact from the CSV in
	 * their contacts HashMap, so we expect that every single incoming call would
	 * evaluate to not spam (isSpam = false), to ensure the algorithm works properly
	 * for this corner case.
	 */
	@Test
	void testCompareAgainstWithUserThatHasAllContactsFromCSV() {
		Phone ph = new Phone();
		HashMap<String, ContactInfo> usersContacts = ph.createPhoneUserWithContacts(ph.getAllContactsMap(),
				ph.getAllContactsMap().size());
		SpamAlgorithm spamAlgo = new SpamAlgorithm();
		for (ContactInfo contact : ph.getAllContactsInHashMapCleaned().values()) {
			/*
			 * simulate creating an incoming call, without explicitly calling an
			 * incomingCall object since we just grab contactInfo from the incomingCall to
			 * compare anyway
			 */
			ContactInfo incomingCall = contact;
			boolean isSpam = spamAlgo.compareAgainst(incomingCall, usersContacts);
			assertEquals(false, isSpam);
		}
	}

	/***
	 * We add 1 specific contact from the CSV manually into a user's contact list
	 * that has a phone number in the CSV that isn't 0, and we expect that the
	 * number of spam calls received is equal to the entire cleaned contacts maps
	 * (no blank phone numbers) minus 1 contact. So every call except 1 should be
	 * spam.
	 */
	@Test
	void testCompareAgainstWithUserThatHasOneContactFromCSV() {
		Phone ph = new Phone();
		HashMap<String, ContactInfo> usersContacts = new HashMap<String, ContactInfo>();
		// create contact that is in CSV
		ContactInfo solangeKolmetz = new ContactInfo("Solange Kolmetz", "303-874-5160", "a", "b", "c", "d", "e", "f",
				"0", "0");
		usersContacts.put("1", solangeKolmetz);
		SpamAlgorithm spamAlgo = new SpamAlgorithm();
		for (ContactInfo contact : ph.getAllContactsMap().values()) {
			/*
			 * simulate creating an incoming call, without explicitly calling an
			 * incomingCall object since we just grab contactInfo from the incomingCall to
			 * compare anyway
			 */
			ContactInfo incomingCall = contact;
			spamAlgo.compareAgainst(incomingCall, usersContacts);
		}
		/*
		 * number of spam calls should be equivalent to 1 less than the number of
		 * contacts in HashMap used to generate incoming calls
		 */
		assertEquals(ph.getAllContactsMap().size() - 1, spamAlgo.getNumberOfSpamCallsReceived());

	}

	/***
	 * For a user that has every single contact, the number of spam calls received
	 * should be 0 since he has the contact info for every single user in his/her
	 * phone, and none of the calls should get scored as "spam" by the algorithm.
	 */
	@Test
	void testNumberOfSpamCallsReceivedIsZero() {
		Phone ph = new Phone();
		HashMap<String, ContactInfo> usersContacts = ph.createPhoneUserWithContacts(ph.getAllContactsMap(),
				ph.getAllContactsMap().size());
		SpamAlgorithm spamAlgo = new SpamAlgorithm();
		for (ContactInfo contact : ph.getAllContactsInHashMapCleaned().values()) {
			/*
			 * simulate creating an incoming call, without explicitly calling an
			 * incomingCall object since we just grab contactInfo from the incomingCall to
			 * compare anyway
			 */
			ContactInfo incomingCall = contact;
			spamAlgo.compareAgainst(incomingCall, usersContacts);
		}
		assertEquals(0, spamAlgo.getNumberOfSpamCallsReceived());
	}

	/***
	 * Tests that the algorithm correctly score and counts all calls received are
	 * spam when a user has no contact information related to any users from the
	 * HashMap of all potential contacts that could be calling.
	 */
	@Test
	void testAllCallsReceivedAreSpam() {
		Phone ph = new Phone();
		HashMap<String, ContactInfo> userContactsList = new HashMap<String, ContactInfo>();
		ContactInfo contactNotInCSV = new ContactInfo("Joshua Chopra", "513-900-9000", "jchopra@seas.upenn.edu",
				"josh.chopra", "400 Walnut Street", "UPenn", "City", "County", "PA", "40013");
		// add contacts to HashMap, hard code a unique ID as key
		userContactsList.put("0", contactNotInCSV);
		SpamAlgorithm spamAlgo = new SpamAlgorithm();
		// only used the cleaned contacts with no blank phone numbers to create calls
		for (ContactInfo contact : ph.getAllContactsInHashMapCleaned().values()) {
			/*
			 * simulate creating an incoming call, without explicitly calling an
			 * incomingCall object since we just grab contactInfo from the incomingCall to
			 * compare anyway
			 */
			ContactInfo incomingCall = contact;
			spamAlgo.compareAgainst(incomingCall, userContactsList);
		}
		/***
		 * Number of spam calls should be equal to number of contacts in cleaned
		 * HashMap, since every potential caller should be considered a spam call
		 */
		assertEquals(ph.getAllContactsInHashMapCleaned().size(), spamAlgo.getNumberOfSpamCallsReceived());
	}

	/***
	 * We create a "blockList" containing all the calls marked as spam by our
	 * algorithm and we want to ensure that list contains the number of calls we
	 * considered spam. This test ensures that it does.
	 */
	@Test
	void testBlockListSize() {
		Phone ph = new Phone();
		HashMap<String, ContactInfo> usersContactList = ph.createPhoneUserWithContacts(ph.getAllContactsMap(),
				ph.getNumberOfContactsForUser());
		// add contacts to HashMap, hard code a unique ID as key
		SpamAlgorithm spamAlgo = new SpamAlgorithm();
		// only used the cleaned contacts with no blank phone numbers to create calls
		for (ContactInfo contact : ph.getAllContactsInHashMapCleaned().values()) {
			/*
			 * simulate creating an incoming call, without explicitly calling an
			 * incomingCall object since we just grab contactInfo from the incomingCall to
			 * compare anyway
			 */
			ContactInfo incomingCall = contact;
			spamAlgo.compareAgainst(incomingCall, usersContactList);
		}
		// Blocklist size (number of callers in list) should equal number of calls
		// marked as spam
		assertEquals(spamAlgo.getBlockList().size(), spamAlgo.getNumberOfSpamCallsReceived());
	}

}
