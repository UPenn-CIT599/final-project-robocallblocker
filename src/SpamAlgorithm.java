import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/***
 * This class is the algorithm used to determine if an incoming call is likely
 * spam, or not spam. It uses a scoring system based on if a phone user has an
 * incoming caller's name, email, address, social media handle, or phone number
 * in their phone. We make the assumption that this data is somewhere in the
 * phone, (i.e., if the phone number isn't present then the social media handle
 * could be present in an app, or the name could be in text messages app, or
 * email could be in mail app).
 * 
 * @author ShawnChoudhury
 * @author JoshuaChopra
 * @author ThomasTee
 */
public class SpamAlgorithm {

	

	public boolean compareAgainst(ContactInfo incoming, HashMap<String, ContactInfo> User) {

		int score = 0;
		
		boolean spam = true;

		ArrayList<String> Namekeys = new ArrayList<String>(User.keySet());

		// Go through User Contact List and Compare name, email, address, social media,
		// and phone number
		
		// System.out.println("\n" + incoming);
		
	
		
		for (String key : Namekeys) {

			score = 0;
			
			if (incoming.getName().equals(User.get(key).getName()) && !incoming.getName().equals("0")) {
				score++;
			//	System.out.println("Caller is " + incoming.getName());
			}
			if (incoming.getEmailAddress().equals(User.get(key).getEmailAddress())
					&& !incoming.getEmailAddress().equals("0")) {
				score++;
			//	System.out.println("Email is " + incoming.getEmailAddress());
			}
			if (incoming.getAddress().equals(User.get(key).getAddress()) && !incoming.getAddress().equals("0")) {
				score++;
			//	System.out.println("Address is " + incoming.getAddress());
			}
			if (incoming.getSocialMediaHandle().equals(User.get(key).getSocialMediaHandle())
					&& !incoming.getSocialMediaHandle().equals("0")) {
				score++;
			//	System.out.println("Social Media is " + incoming.getSocialMediaHandle());
			}
			if (incoming.getPhoneNumber().equals(User.get(key).getPhoneNumber())
					&& !incoming.getPhoneNumber().equals("0")) {
				score++;
			//	System.out.println("Phone number is " + incoming.getPhoneNumber());
			}

			
			if (score == 5) {
				spam = false;
				System.out.println("The number of contact info matches is " + score + "\n");
			} 		
			
		}
			
		
		return spam;
	}
}