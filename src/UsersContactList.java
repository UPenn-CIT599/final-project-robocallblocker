import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * This class populates a HashMap to serve as the user's contact list
 * 
 * @author Shawn Choudhury
 * @author Joshua Chopra
 * @author Thomas Tee
 */

public class UsersContactList {

	// Instance variables
	private HashMap<String, ContactInfo> contactList;

	/**
	 * Creates the users contact list by randomly grabbing a specified number of
	 * contacts from the contacts100 list
	 * 
	 * @param map              - ContactInfoReader processed file
	 * @param numberOfContacts - Specify how many numbers in the user's phone book
	 */
	public UsersContactList(HashMap<String, ContactInfo> map, int numberOfContacts) {
		contactList = new HashMap<String, ContactInfo>();
		// ArrayList of shuffled keys from the map 
		ArrayList<String> nameKeys = shuffleMapKeys(map);
		int index = 0;
		while (index < numberOfContacts) {
			contactList.put(nameKeys.get(index), map.get(nameKeys.get(index)));
			index++;
		}
	}

	/**
	 * Helper method to shuffle the keys in the map HashMap doesn't need shuffling
	 * but we want to make sure we don't generate the same values again
	 * 
	 * @param mapKeys
	 * @return ArrayList of shuffled keys
	 */
	public ArrayList<String> shuffleMapKeys(HashMap<String, ContactInfo> mapKeys) {
		ArrayList<String> keys = new ArrayList<String>(mapKeys.keySet());
		Collections.shuffle(keys);
		return keys;
	}

	/***
	 * Gets user's contact list after UserContactList object is created
	 * 
	 * @return HashMap of user's contacts
	 */
	public HashMap<String, ContactInfo> getContactList() {
		return contactList;
	}

}
