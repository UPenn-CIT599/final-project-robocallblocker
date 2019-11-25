import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class IncomingCall {

	ContactInfo incomingCallerInfo;

	/***
	 * Shuffle Contact Info to create a random incoming call from CSV of contacts
	 * 
	 * @param allContactsFromFile - takes in hashmap created when we read in CSV
	 */
	public IncomingCall(HashMap<String, ContactInfo> allContactsFromFile) {
		ArrayList<String> phoneNumbers = new ArrayList<String>(allContactsFromFile.keySet());
		/*
		 * Given that data in CSV contains blanks for some phone numbers 
		 * in the file which are filled with 0s, we don't want to ever 
		 * create an incoming call from our keySet with the phone number
		 * "0". 
		 */
		for (String phoneNumber : phoneNumbers) {
			if(phoneNumber.equals("0")) {
				phoneNumbers.remove(phoneNumber);
			}
		}
		Collections.shuffle(phoneNumbers);
		incomingCallerInfo = allContactsFromFile.get(phoneNumbers.get(0));
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
