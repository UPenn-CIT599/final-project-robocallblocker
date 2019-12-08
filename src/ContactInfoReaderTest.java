import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

/***
 * This class tests our CSV reader, to ensure we properly read in data, address
 * blank data entries and properly clean the data.
 * 
 * @author ShawnChoudhury
 * @author JoshuaChopra
 * @author ThomasTee
 *
 */
class ContactInfoReaderTest {

	/***
	 * Creates a map from contacts100.csv and then loops through the map. Test
	 * verifies that not of the uniqueIDs are the same and are independent from each
	 * other.
	 */
	@Test
	void testContactInfoReaderForUniqueID() {
		ContactInfoReader contactsFromCSV = new ContactInfoReader("contacts100.csv");
		HashMap<String, ContactInfo> map = contactsFromCSV.getAllContactsInCSV();
		ArrayList<String> list = new ArrayList<String>();
		Boolean isSame = true;
		for (String key : map.keySet()) {
			if (list.contains(key)) {
				isSame = true;
				break;
			}
			isSame = false;
			list.add(key);
		}
		assertEquals(false, isSame);
	}

	/***
	 * Creates a map from contacts100.csv and then loops through the map by
	 * constructing ContactInfoReader. ContactInfoReader calls CheckIfBlankThenFill
	 * method before filling the map. Test verifies that reader replaces all blanks
	 * and that none are left in the map.
	 */
	@Test
	void testCheckIfBlankThenFill() {
		ContactInfoReader contactsFromCSV = new ContactInfoReader("contacts100.csv");
		HashMap<String, ContactInfo> map = contactsFromCSV.getAllContactsInCSV();
		Boolean isEmpty = true;
		ArrayList<Boolean> list = new ArrayList<Boolean>();
		for (String key : map.keySet()) {
			list.add(map.get(key).getName().isEmpty());
			list.add(map.get(key).getPhoneNumber().isEmpty());
			list.add(map.get(key).getEmailAddress().isEmpty());
			list.add(map.get(key).getSocialMediaHandle().isEmpty());
			list.add(map.get(key).getAddress().isEmpty());
			list.add(map.get(key).getCompany().isEmpty());
			list.add(map.get(key).getCity().isEmpty());
			list.add(map.get(key).getCounty().isEmpty());
			list.add(map.get(key).getState().isEmpty());
			list.add(map.get(key).getZipCode().isEmpty());
			if (list.contains(true)) {
				isEmpty = true;
				break;
			}
			isEmpty = false;
		}
		assertEquals(false, isEmpty);
	}

	/**
	 * Check to see if last row of contacts100.csv is available in our hashmap
	 * produced from the ContactInfoReader class Francine,Abdallah,"Healy, George W
	 * Iv",,Kulpsville,Montgomery,PA,19443,,FrancineAbdallah345@aol.com,,
	 */
	@Test
	void testContactInfoReaderForLastRecord() {
		ContactInfoReader reader = new ContactInfoReader("contacts100.csv");
		HashMap<String, ContactInfo> contacts = reader.getAllContactsInCSV();
		assertTrue(contacts.containsKey("197") && contacts.get("197") != null);
	}

	/**
	 * check to make sure each ContactInfo object is created inside hashmap make
	 * sure no objects are null there are 198 ContactInfo objects with 0 indexing
	 * and title column, assertequals should return 198 this may be the same as
	 * using hashMap.size()
	 */
	@Test
	void testContactInfoReader() {
		ContactInfoReader reader = new ContactInfoReader("contacts100.csv");
		HashMap<String, ContactInfo> contacts = reader.getAllContactsInCSV();
//		// 198 includes 1st row with column headers
		assertEquals(198, contacts.size());
	}

	/***
	 * This test ensures that the ContactInfoReader that removes all contacts that
	 * have a phone number of blank which is filled with 0 after being read in, are
	 * removed from the HashMap when we "clean" the HashMap containing all the
	 * contacts from the CSV. This is important since we used the "cleaned" HashMap
	 * to generate calls, and we NEVER want a call with phone number "0" incoming.
	 */
	@Test
	void testRemoveBlankPhoneNumbersFromMapUsedToCreateCalls() {
		boolean contactWithPhoneNumberOfZeroExists;
		ContactInfoReader reader = new ContactInfoReader("contacts100.csv");
		HashMap<String, ContactInfo> allContactsNoBlankPhoneNumbers = reader
				.removeBlankPhoneNumbersFromMapUsedToCreateCalls(reader.getAllContactsInCSV());
		for (ContactInfo contact : allContactsNoBlankPhoneNumbers.values()) {
			if (contact.getPhoneNumber().equals("0")) {
				contactWithPhoneNumberOfZeroExists = true;
			} else {
				contactWithPhoneNumberOfZeroExists = false;
			}
			// check on each iteration that no contact with ph. # of 0 exists
			assertEquals(false, contactWithPhoneNumberOfZeroExists);
		}
	}

}
