import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class IncomingCall {

	private ContactInfo incomingCallerInfo;

	/***
	 * Creates an incoming call that is passed to a "Phone" instance which then uses
	 * SpamAlgorithm to check if this incoming call is spam. We used a "cleaned"
	 * version of allContacts HashMap from ContactInfoDataReader since we do not
	 * want to generate calls with a blank phone number. We shuffle our list of
	 * names of the contacts then take the first one to create a call.
	 * 
	 * @param allContactsFromFile - takes in HashMap created when we read in CSV
	 */
	public IncomingCall(HashMap<String, ContactInfo> allContactsFromFile) {
		HashMap<String, ContactInfo> allContactsCleaned = removeBlankPhoneNumbersFromMapUsedToCreateCalls(
				allContactsFromFile);
		ArrayList<String> contactNames = new ArrayList<String>(allContactsCleaned.keySet());
		Collections.shuffle(contactNames);
		incomingCallerInfo = allContactsCleaned.get(contactNames.get(0));
	}

	/***
	 * Helper method used to ensure we never use a key-value pair that has a
	 * ContactInfo object where the phone number of that ContactInfo object is
	 * blank, AKA "0", since we set blanks to 0 when reading in the data.
	 * Intuitively, wouldn't make any sense to receive a call from a phone number of
	 * "0" or blank.
	 * 
	 * @param allContacts hashMap we create upon reading in contacts from CSV
	 * @return cleaned map of contacts used to generate calls (no blank phone #s)
	 * 
	 */
	public HashMap<String, ContactInfo> removeBlankPhoneNumbersFromMapUsedToCreateCalls(
			HashMap<String, ContactInfo> allContacts) {
		/*
		 * use iterator since we can't iterate over a keySet and remove key-value pair
		 * simultaneously or java throws ConcurrentModificationException
		 */

		Iterator<Map.Entry<String, ContactInfo>> iterator = allContacts.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, ContactInfo> nameWithContactInfo = iterator.next();
			/*
			 * if value (ContactInfo object) associated with key (name of contact) has
			 * variable value for phone number as "0" then remove it from our map. Else keep
			 * it.
			 */
			if (nameWithContactInfo.getValue().getPhoneNumber().equals("0")) {
				iterator.remove();
			}
		}
		return allContacts;

	}

	/***
	 * Get contact information for the incoming caller
	 * 
	 * @return ContactInfo object that has all attributes of a caller
	 */
	public ContactInfo getIncomingCallerInfo() {
		return incomingCallerInfo;
	}

}
