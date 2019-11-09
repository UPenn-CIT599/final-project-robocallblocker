import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class IncomingCall {

	ContactInfo incoming;

	/***
	 * Shuffle Contact Info to create a random incoming call from CSV of contacts
	 * 
	 * @param allContactsFromFile - takes in hashmap created when we read in CSV
	 */
	public IncomingCall(HashMap<String, ContactInfo> allContactsFromFile) {
		ArrayList<String> keys = new ArrayList<String>(allContactsFromFile.keySet());
		Collections.shuffle(keys);

		incoming = allContactsFromFile.get(keys.get(0));
	}

	/***
	 * Get contact information for the incoming caller
	 * 
	 * @return ContactInfo object that has all attributes of a caller
	 */
	public ContactInfo getIncoming() {
		return incoming;
	}

}
