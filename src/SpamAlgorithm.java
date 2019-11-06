import java.util.ArrayList;
import java.util.HashMap;

public class SpamAlgorithm {

	

	public boolean compareAgainst (ContactInfo incoming, HashMap<String, ContactInfo> User) {

		int score = 0; 
		Boolean spam = false;
		ArrayList<String> Namekeys = new ArrayList<String>(User.keySet());

		// Go through User Contact List and Compare name, email, address, social media, and phone number 
		for (String key : Namekeys) {

			if (incoming.getName() == User.get(key).getName()) {
				score ++;
				System.out.println ("Caller is " + incoming.getName());
			}
			if (incoming.getEmailAddress() == User.get(key).getEmailAddress()) {
				score ++;
			}
			if (incoming.getAddress() == User.get(key).getAddress()) {
				score ++;
			}
			if (incoming.getSocialMediaHandle() == User.get(key).getSocialMediaHandle()) {
				score ++;
			}		
			if (incoming.getPhoneNumbers() == User.get(key).getPhoneNumbers()) {
				score ++;
			}			
		}

		
		System.out.println("The number of contact info matches is " + score);
		
		// If there are only 2 or less matches, then set as Spam 
		if (score <= 2) {
			spam = true;
		}
		return spam;
	}
}
