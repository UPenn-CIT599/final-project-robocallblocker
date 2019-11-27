import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

class ContactInfoReaderTest {

	
	/***
	 * Creates a map from contacts100.csv and then loops through the map.
	 * Test verifies that not of the uniqueIDs are the same and are independent from each other. 
	 */
	@Test
	void testContactInfoReaderForUniqueID() {
		ContactInfoReader contactsFromCSV = new ContactInfoReader("contacts100.csv");
		HashMap<String, ContactInfo> map = contactsFromCSV.getContactInfoMap();
		ArrayList<String> list =  new ArrayList<String>();
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
	 * Creates a map from contacts100.csv and then loops through the map by constructing ContactInfoReader.
	 * ContactInfoReader calls CheckIfBlankThenFill method before filling the map. 
	 * Test verifies that reader replaces all blanks and that none are left in the map. 
	 */
	@Test
	void testCheckIfBlankThenFill() {
		ContactInfoReader contactsFromCSV = new ContactInfoReader("contacts100.csv");
		HashMap<String, ContactInfo> map = contactsFromCSV.getContactInfoMap();
		Boolean isEmpty = true;
		ArrayList<Boolean> list =  new ArrayList<Boolean>();
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
			if (list.contains(true)){
				isEmpty = true;
				break;
			}
			isEmpty = false; 
		}
		assertEquals(false, isEmpty);
	}
}
