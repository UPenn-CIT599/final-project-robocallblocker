import java.util.HashMap;

/***
 * This class is where we combine all other classes to create a phone object
 * that the user makes use of and receives incoming calls, decides to continue
 * to receive calls, or turns off phone.
 * 
 * @author Shawn Choudhury
 * @author Joshua Chopra
 * @author Thomas Tee
 */
public class Phone {

	private ContactInfoReader allContacts = new ContactInfoReader("contacts10.csv");
	// store contents from CSV file in phone class to maintain all potential contacts. 
	private HashMap<String, ContactInfo> allContactsInHashMap = allContacts.getContactInfoMap();
	private SpamAlgorithm spamAlgoForPhone = new SpamAlgorithm();
	private String displayIncomingCallerPhoneNumber;
	private String displayIncomingCallerName;
	private boolean incomingCallSpam; // true if is spam, false if not
	private HashMap<String, ContactInfo> usersContacts; // used to get users contact list 

	
	/***
	 * Method to create a phone user that has a list of contacts (subset of the
	 * total contacts list) and the contact list will be used for checking against
	 * an incoming call that comes in. Is not private now for testing purposes. TODO
	 * - set private.
	 * 
	 * @param allContactsInHashMap - pass in HashMap of contacts from CSV
	 * @param numberOfContacts        - desired number of contacts for user
	 * @return HashMap of user's contacts (contact for key, contactInfo for values)
	 */
	HashMap<String, ContactInfo> createPhoneUserWithContacts(HashMap<String, ContactInfo> allContactsInHashMap, int numberOfContacts) {
		UsersContactList phoneUsersContactsList = new UsersContactList(allContactsInHashMap, numberOfContacts);
		/*
		 * returns a HashMap with the phone user's contacts names as key, and all info
		 * as ContactInfo object
		 */
		return phoneUsersContactsList.getContactList();
	}

	/***
	 * Creates an incoming call for the current phone instance and gets contact
	 * information associated with the caller
	 * 
	 * @param contactsDataMap - pass in hashmap from ContactInfoReader
	 * @return - ContactInfo associated with caller
	 */
	ContactInfo createIncomingCallGetContactInfoForCaller(HashMap<String, ContactInfo> contactsDataMap) {
		IncomingCall call = new IncomingCall(contactsDataMap);
		return call.getIncomingCallerInfo(); // return info of incoming caller
	}

	public ContactInfoReader getAllContacts() {
		return allContacts;
	}

	public SpamAlgorithm getSpamAlgoForPhone() {
		return spamAlgoForPhone;
	}

	public String getDisplayIncomingCallerPhoneNumber() {
		return displayIncomingCallerPhoneNumber;
	}

	public void setDisplayIncomingCallerPhoneNumber(String displayIncomingCallerPhoneNumber) {
		this.displayIncomingCallerPhoneNumber = displayIncomingCallerPhoneNumber;
	}
	
	public String getDisplayIncomingCallerName() {
		return displayIncomingCallerName;
	}
	
	public void setDisplayIncomingCallerName(String displayIncomingCallerName) {
		this.displayIncomingCallerName = displayIncomingCallerName;
	}

	public boolean isIncomingCallSpam() {
		return incomingCallSpam;
	}

	public void setIncomingCallSpamOrNotSpam(boolean incomingCallSpam) {
		this.incomingCallSpam = incomingCallSpam;
	}
	
	public HashMap<String, ContactInfo> getAllContactsInHashMap() {
		return allContactsInHashMap;
	}

	public void setAllContactsInHashMap(HashMap<String, ContactInfo> allContactsInHashMap) {
		this.allContactsInHashMap = allContactsInHashMap;
	}
	
	public HashMap<String, ContactInfo> getUsersContacts() {
		return usersContacts;
	}

	public void setUsersContacts(HashMap<String, ContactInfo> usersContacts) {
		this.usersContacts = usersContacts;
	}

	

	/***
	 * Allows use to use our phone object with a "user" that has a list of contacts,
	 * and the phone can receive calls and then the user is told whether or not the
	 * incoming call is spam, and is prompted to take another call or not which will
	 * determine if the phone continues to be "used" i.e., if the program continues
	 * to run
	 * 
	 * @param contactsData     - pass in object that contains all data from CSV
	 * @param numberOfContacts - desired number of contacts for phone user
	 * @param spamAlgo         - use SpamAlgorithm object
	 * @return - nothing, used as a run method for the phone object.
	 */
//	public void usePhone(ContactInfoReader contactsData, int numberOfContactsForUser, SpamAlgorithm spamAlgo) {
//		HashMap<String, ContactInfo> usersContactList = createPhoneUserWithContacts(contactsData,
//				numberOfContactsForUser);
//		while (getNextCall) {
//			ContactInfo forIncomingCaller = createIncomingCallGetContactInfoForCaller(contactsData.getContactInfoMap());
//			boolean isSpam = spamAlgo.compareAgainst(forIncomingCaller, usersContactList);
//			if (isSpam) {
//				System.out.println("Phone call is likely spam" + '\n' + "Take next incoming call?");
//			} else {
//				System.out.println("Phone call is most likely not spam" + '\n' + "Take next incoming call?");
//			}
//		}
//	}

//	public static void main(String[] args) {
//		ContactInfoReader contactsFromFile = new ContactInfoReader("contacts10.csv");
//		SpamAlgorithm spamAlgo = new SpamAlgorithm();
//		Phone temp = new Phone();
//		int numberOfContactsForUser = 5;
//		temp.usePhone(contactsFromFile, numberOfContactsForUser, spamAlgo);
//	}
}
