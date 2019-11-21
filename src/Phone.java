import java.util.HashMap;

/***
 * This class is where we combine all other classes to create a phone object
 * that the user makes use of and receives incoming calls, decides to continue
 * to receive calls, or turns off phone.
 * 
 * @author ShawnChoudhury
 * @author JoshuaChopra
 * @author ThomasTee
 */
public class Phone {

	// store contents from CSV file in phone class to maintain all contacts
	private ContactInfoReader allContacts = new ContactInfoReader("contacts10.csv");
	private HashMap<String, ContactInfo> allContactsInHashMap = allContacts.getContactInfoMap();
	private SpamAlgorithm spamAlgoForPhone = new SpamAlgorithm();
	private String displayIncomingCallerPhoneNumber;
	private String displayIncomingCallerName;
	private boolean incomingCallSpam; // true if is spam, false if not
	private HashMap<String, ContactInfo> usersContacts; // used to get users contact list
	/***
	 * Default number of contacts a phone user has. 
	 * Could instead incorporate this into the GUI and 
	 * let the person using the GUI pick from a number 
	 * in a combo-box. Removed from GUI class to avoid 
	 * creating another instance variable that should 
	 * belong in the phone class. 
	 */
	private final int numberOfContactsForUser = 5;

	/***
	 * Method to create a phone user that has a list of contacts (subset of the
	 * total contacts list) and the contact list will be used for checking against
	 * an incoming call that comes in. Is not private now for testing purposes. TODO
	 * - set private.
	 * 
	 * @param allContactsInHashMap - pass in HashMap of contacts from CSV
	 * @param numberOfContacts     - desired number of contacts for user
	 * @return HashMap of user's contacts (contact for key, contactInfo for values)
	 */
	public HashMap<String, ContactInfo> createPhoneUserWithContacts(HashMap<String, ContactInfo> allContactsInHashMap,
			int numberOfContacts) {
		UsersContactList phoneUsersContactsList = new UsersContactList(allContactsInHashMap, numberOfContacts);
		/*
		 * returns a HashMap with the phone user's contacts names as key, and all info
		 * as ContactInfo object
		 */
		usersContacts = phoneUsersContactsList.getContactList();
		return usersContacts;
	}

	/***
	 * Creates an incoming call for the current phone instance and gets contact
	 * information associated with the caller
	 * 
	 * @param usersContactList - pass in hashmap containing user's contact list,
	 *                         used to check if incoming call is spam
	 * @return - ContactInfo associated with caller
	 */
	public void createIncomingCallGetContactInfoForCaller(HashMap<String, ContactInfo> usersContactList) {
		IncomingCall call = new IncomingCall(allContactsInHashMap);
		ContactInfo forCaller = call.getIncomingCallerInfo();
		setDisplayIncomingCallerName(forCaller.getName());
		setDisplayIncomingCallerPhoneNumber(forCaller.getPhoneNumbers());
		setIncomingCallSpamOrNotSpam(getSpamAlgoForPhone().compareAgainst(forCaller, usersContactList));
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

	public int getNumberOfContactsForUser() {
		return numberOfContactsForUser;
	}

}
