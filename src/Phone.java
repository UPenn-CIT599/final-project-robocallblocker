import java.util.HashMap;
import java.util.Scanner;

public class Phone {

	public static void main(String[] args) {

		boolean Continue = true;

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
		
		while (Continue = true){
			Scanner input = new Scanner(System.in);

			// Create incoming call from contactinfo map
			IncomingCall incomingCall = new IncomingCall(map);
			ContactInfo incomingInfo = incomingCall.getIncoming();

			System.out.println(incomingInfo.getPhoneNumbers() + " is calling." );

			SpamAlgorithm spamAlgorithm = new SpamAlgorithm();
			Boolean spam = spamAlgorithm.compareAgainst(incomingInfo, userMap);

			if (spam == true) {
				System.out.println("Call is likely spam");
			}

			else {
				System.out.println("Call is not likely spam");
			}
			System.out.print("\n");

			System.out.println("Next call? yes or no?");

			String answer = input.nextLine();

			if (answer.equals("yes")) {
				Continue = true;
			}

			else if (answer.equals("no")) {
				Continue = false;
				break;
			}	
		}
	}
}
