import java.util.HashMap;
import java.util.Scanner;

public class Phone {

	/*
	 * Use scanner object after instantiating phone object for user input, error
	 * checking, etc.
	 */
	private Scanner input = new Scanner(System.in);

	/**
	 * Handle user inputs from scanner (for now) -- TODO implement same for button
	 * listening interface.
	 * 
	 * @param input will be scanner input for now
	 * @return boolean to be used in a while loop
	 */
	private boolean handleUserInputsAndPromptForNextCall() {
		boolean takeAnotherPhoneCall = true;
		String answer = input.next();
		while (!(answer.equalsIgnoreCase("yes")) && !(answer.equalsIgnoreCase("no"))) {
			System.out.println("You entered an invalid choice. Please type yes or no.");
			answer = input.next();
		}
		if (answer.equalsIgnoreCase("no")) {
			takeAnotherPhoneCall = false;
		}
		return takeAnotherPhoneCall;
	}

	/***
	 * Method to create a phone user that has a list of contacts (subset of the
	 * total contacts list) and the contact list will be used for checking against
	 * an incoming call that comes in
	 * 
	 * @param fileNameWithAllContacts - pass in name of CSV with data
	 * @param numberOfContacts        - desired number of contacts for user
	 * @return HashMap of user's contacts (contact for key, contactInfo for values)
	 */
	private HashMap<String, ContactInfo> createPhoneUserWithContacts(ContactInfoReader contactsData,
			int numberOfContacts) {
		HashMap<String, ContactInfo> allContactsToHashMap = contactsData.getContactInfoMap();
		UsersContactList phoneUsersContactsList = new UsersContactList(allContactsToHashMap, numberOfContacts);
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
	private ContactInfo createIncomingCallGetContactInfoForCaller (HashMap<String, ContactInfo> contactsDataMap) {
		IncomingCall call = new IncomingCall(contactsDataMap);
		return call.getIncoming(); // return info of incoming caller
	}

	/***
	 * Allows use to use our phone object with a "user" that has a list of contacts,
	 * and the phone can receive calls and then the user is told whether or not the
	 * incoming call is spam, and is prompted to take another call or not which will
	 * determine if the phone continues to be "used" i.e., if the program continues
	 * to run
	 * 
	 * @param contactsData - pass in object that contains all data from CSV
	 * @param numberOfContacts - desired number of contacts for phone user 
	 * @param spamAlgo - use SpamAlgorithm object
	 * @return - nothing, used as a run method for the phone object. 
	 */
	public void usePhone(ContactInfoReader contactsData, int numberOfContactsForUser, SpamAlgorithm spamAlgo) {
		HashMap<String, ContactInfo> usersContactList = createPhoneUserWithContacts(contactsData,
				numberOfContactsForUser);
		boolean runPhoneProgram = true;
		while (runPhoneProgram) {
			ContactInfo forIncomingCaller = createIncomingCallGetContactInfoForCaller(contactsData.getContactInfoMap());
			boolean isSpam = spamAlgo.compareAgainst(forIncomingCaller, usersContactList);
			if (isSpam) {
				System.out.println("Phone call is likely spam" + '\n' + "Take next incoming call?");
				runPhoneProgram = handleUserInputsAndPromptForNextCall(); // if yes will be true, else false & end program
			} else {
				System.out.println("Phone call is most likely not spam" + '\n' + "Take next incoming call?");
				runPhoneProgram = handleUserInputsAndPromptForNextCall(); // if yes will be true, else false & end program
			}
		}
	}
	
	/***
	 * Get scanner object used in class
	 * @return scanner
	 */
	public Scanner getInput() {
		return input;
	}

	public static void main(String[] args) {
		ContactInfoReader contactsFromFile = new ContactInfoReader("contacts10.csv");
		SpamAlgorithm spamAlgo = new SpamAlgorithm();
		Phone temp = new Phone();
		int numberOfContactsForUser = 5;
		temp.usePhone(contactsFromFile, numberOfContactsForUser, spamAlgo);
	}
}

/**
 * Tests initially before creating all needed methods for phone class
 */
//Phone runner = new Phone();
//
//boolean runProgram = true; // changed name from continue since continue is java keyword
//
//// we don't need to create a new spam algo instance every time we go through the
//// while loop, reuse this object
//SpamAlgorithm spamAlgorithm = new SpamAlgorithm();
//
//// Read CSV
//// Turn CSV into contactinfo map
//ContactInfoReader list = new ContactInfoReader("contacts10.csv");
//HashMap<String, ContactInfo> map = list.getContactInfoMap();
//
//System.out.println("Contacts map " + map);
//
//// Create user contact list map
//UsersContactList users = new UsersContactList(map, 5);
//HashMap<String, ContactInfo> userMap = users.getContactList();
//
//System.out.println("User map " + userMap);
//
//System.out.print("\n");
//
///**
// * This needs to be split into methods.. Can't have this much code in the main
// * method but still very good start and all of the logic makes sense, just will
// * have to split it up so we don't get docked on points for putting everything
// * in main
// */
//while (runProgram) {
////	Scanner input = new Scanner(System.in); commented out 
//
//	// Create incoming call from contactinfo map
//	IncomingCall incomingCall = new IncomingCall(map);
//	ContactInfo incomingInfo = incomingCall.getIncoming();
//
//	System.out.println(incomingInfo.getPhoneNumbers() + " is calling.");
//
//	Boolean spam = spamAlgorithm.compareAgainst(incomingInfo, userMap);
//
//	incomingCall.checkIfSpamCall(spam);
//
////	if (spam) {
////		System.out.println("Call is likely spam");
////	}
////
////	else {
////		System.out.println("Call is not likely spam");
////	}
////	System.out.print("\n");
////
////	System.out.println("Next call? yes or no?");
//
//	String answer = runner.input.nextLine();
//	// error checking for user input
//	while (runner.incorrectUserInputs(answer)) {
//		answer = runner.input.nextLine();
//	}
//
//	if (answer.toLowerCase().equals("yes")) {
//		runProgram = true;
//	}
//
//	else if (answer.toLowerCase().equals("no")) {
//		runProgram = false;
//		runner.input.close(); // close scanner
////		break; don't think you need this break since boolean handles while loop
//	}
//}
