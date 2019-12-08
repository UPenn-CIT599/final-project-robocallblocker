import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * This class takes in AllContacts HashMap, turns the HashMap key into a string array, 
 * and then shuffles the array. Class then creates the IncomingCall by setting index 0 of
 * the string array as the HashMap key. 
 * 
 * @author ShawnChoudhury
 * @author JoshuaChopra
 * @author ThomasTee
 */

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
		ArrayList<String> contactNames = new ArrayList<String>(allContactsFromFile.keySet());
		Collections.shuffle(contactNames);
		incomingCallerInfo = allContactsFromFile.get(contactNames.get(0));
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
