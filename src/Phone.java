import java.util.HashMap;
import java.util.Scanner;

public class Phone {

	/**
	 * Check for wrong inputs, made static for now to avoid instantiating phone
	 * object
	 * 
	 * @param input will be scanner input for now
	 * @return boolean to be used in a while loop
	 */
	public boolean incorrectUserInputs(String input) {
		boolean wrongInput = false;
		if (!(input.equalsIgnoreCase("yes")) && !(input.equalsIgnoreCase("no"))) {
			wrongInput = true;
			System.out.println("You entered an invalid choice. Please provide type yes or no.");
		}
		return wrongInput;
	}
	

	/*
	 * Use scanner object after instantiating phone object for error checking, etc.
	 */
	private Scanner input = new Scanner(System.in);

	public static void main(String[] args) {

		Phone runner = new Phone();

		boolean runProgram = true; // changed name from continue since continue is java keyword

		// we don't need to create a new spam algo instance every time we go through the
		// while loop, reuse this object
		SpamAlgorithm spamAlgorithm = new SpamAlgorithm();

		// Read CSV
		// Turn CSV into contactinfo map
		ContactInfoReader list = new ContactInfoReader("contacts10.csv");
		HashMap<String, ContactInfo> map = list.getContactInfoMap();

		System.out.println("Contacts map " + map);

		// Create user contact list map
		UsersContactList users = new UsersContactList(map, 5);
		HashMap<String, ContactInfo> userMap = users.getContactList();

		System.out.println("User map " + userMap);

		System.out.print("\n");

		/**
		 * This needs to be split into methods.. Can't have this much code in the main
		 * method but still very good start and all of the logic makes sense, just will
		 * have to split it up so we don't get docked on points for putting everything
		 * in main
		 */
		while (runProgram) {
//			Scanner input = new Scanner(System.in); commented out 

			// Create incoming call from contactinfo map
			IncomingCall incomingCall = new IncomingCall(map);
			ContactInfo incomingInfo = incomingCall.getIncoming();

			System.out.println(incomingInfo.getPhoneNumbers() + " is calling.");

			Boolean spam = spamAlgorithm.compareAgainst(incomingInfo, userMap);

			incomingCall.checkIfSpamCall(spam);
			
//			if (spam) {
//				System.out.println("Call is likely spam");
//			}
//
//			else {
//				System.out.println("Call is not likely spam");
//			}
//			System.out.print("\n");
//
//			System.out.println("Next call? yes or no?");

			String answer = runner.input.nextLine();
			// error checking for user input
			while (runner.incorrectUserInputs(answer)) {
				answer = runner.input.nextLine();
			}

			if (answer.toLowerCase().equals("yes")) {
				runProgram = true;
			}

			else if (answer.toLowerCase().equals("no")) {
				runProgram = false;
				runner.input.close(); // close scanner
//				break; don't think you need this break since boolean handles while loop
			}
		}
	}
}
