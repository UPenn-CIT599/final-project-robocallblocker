import java.util.HashMap;

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

	private boolean isSpam;
	private int numberOfSpamCallsReceived;
	private HashMap<String, String> blockList = new HashMap<>();

	/***
	 * Helper method used to create HashMap used for "scoring" an incoming call
	 * based on list of attributes that would be in the user's phone, related to a
	 * contact
	 * 
	 * @param incoming
	 * @return HashMap used for scoring in compareAgainst method.
	 */
	private HashMap<String, Integer> mapForScoring(ContactInfo incoming) {
		HashMap<String, Integer> scoreByAttribute = new HashMap<String, Integer>();
		scoreByAttribute.put(incoming.getName()+"0", 0);
		scoreByAttribute.put(incoming.getCompany()+"1", 0);
		scoreByAttribute.put(incoming.getAddress()+"2", 0);
		scoreByAttribute.put(incoming.getCity()+"3", 0);
		scoreByAttribute.put(incoming.getCounty()+"4", 0);
		scoreByAttribute.put(incoming.getState()+"5", 0);
		scoreByAttribute.put(incoming.getZipCode()+"6", 0);		
		scoreByAttribute.put(incoming.getPhoneNumber()+"7", 0);
		scoreByAttribute.put(incoming.getEmailAddress()+"8", 0);
		scoreByAttribute.put(incoming.getSocialMediaHandle()+"9", 0);
		return scoreByAttribute;
	}
	

	/***
	 * This method is a helper method for compareAgainst that scores an incomingCall
	 * based on our specified attributes of contactInfo for a caller, and returns a
	 * HashMap with a score for each attribute that is passed to our compareAgainst
	 * method. 
	 * 
	 * We check the contactInfo assigned to the incoming call, which is determined through each
	 * step of the algorithm. If we find a match we replace the value associated
	 * with that attribute with 1 UNLESS it's the phone number, then we replace with
	 * 2. Doing this instead of incrementing a score variable, since we don't want
	 * to double count things like states, counties, cities, zip codes, etc.
	 * 
	 * We don't want to match blanks that were filled with 0s either, so we include
	 * this check in our spam algorithm.
	 * 
	 * @param incoming             caller contactInfo object
	 * @param phoneUsersContactMap phone user's contact map created when a phone
	 *                             user is assigned
	 * @return HashMap that has score for each attribute of a contactInfo object
	 */
	private HashMap<String, Integer> scoreIncomingCallByAttributes(ContactInfo incoming,
			HashMap<String, ContactInfo> phoneUsersContactMap) {

		HashMap<String, Integer> scoreByAttribute = mapForScoring(incoming);
		for (ContactInfo contact : phoneUsersContactMap.values()) {
			if (incoming.getName().equals(contact.getName()) && !incoming.getName().equals("0")) {
				scoreByAttribute.replace(incoming.getName()+"0", 1);
			}
			if (incoming.getCompany().equals(contact.getCompany()) && !incoming.getCompany().equals("0")) {
				scoreByAttribute.replace(incoming.getCompany()+"1", 1);
			}
			if (incoming.getAddress().equals(contact.getAddress()) && !incoming.getAddress().equals("0")) {
				scoreByAttribute.replace(incoming.getAddress()+"2", 1);
			}
			if (incoming.getCity().equals(contact.getCity()) && !incoming.getCity().equals("0")) {
				scoreByAttribute.replace(incoming.getCity()+"3", 1);
			}
			if (incoming.getCounty().equals(contact.getCounty()) && !incoming.getCounty().equals("0")) {
				scoreByAttribute.replace(incoming.getCounty()+"4", 1);
			}
			if (incoming.getState().equals(contact.getState()) && !incoming.getState().equals("0")) {
				scoreByAttribute.replace(incoming.getState()+"5", 1);
			}
			if (incoming.getZipCode().equals(contact.getZipCode()) && !incoming.getZipCode().equals("0")) {
				scoreByAttribute.replace(incoming.getZipCode()+"6", 1);
			}
			if (incoming.getPhoneNumber().equals(contact.getPhoneNumber()) && !incoming.getPhoneNumber().equals("0")) {
				// 2 points if phone number is in user's phone
				scoreByAttribute.replace(incoming.getPhoneNumber()+"7", 2);
			}
			if (incoming.getEmailAddress().equals(contact.getEmailAddress())
					&& !incoming.getEmailAddress().equals("0")) {
				scoreByAttribute.replace(incoming.getEmailAddress()+"8", 1);
			}
			if (incoming.getSocialMediaHandle().equals(contact.getSocialMediaHandle())
					&& !incoming.getSocialMediaHandle().equals("0")) {
				scoreByAttribute.replace(incoming.getSocialMediaHandle()+"9", 1);
			}
		}
		return scoreByAttribute;
	}

	/***
	 * This method makes use of the mapForScoring and scoreIncomingCallByAttribute
	 * and returns whether an incoming call is likely spam or not as a boolean,
	 * based on the total score of all attributes, where we get each score of each attribute
	 * from our scoreIncomingCallByAttribute method. 
	 * 
	 * @param incoming             caller information
	 * @param phoneUsersContactMap all contact information in users phone
	 * @return if call is spam or not
	 */
	public boolean compareAgainst(ContactInfo incoming, HashMap<String, ContactInfo> phoneUsersContactMap) {
		HashMap<String, Integer> scoresForEachAttribute = scoreIncomingCallByAttributes(incoming, phoneUsersContactMap);
		int totalScore = 0;
		// add up individual attribute scores to get total score for caller
		for (Integer attributeScore : scoresForEachAttribute.values()) {
			totalScore += attributeScore;
		}
		if (totalScore >= 3) {
			isSpam = false;
		} else {
			isSpam = true;
			numberOfSpamCallsReceived++;
			blockList.put(incoming.getName(), incoming.getPhoneNumber());
		}
		// print this out regardless of the conditional, for testing purposes.
		System.out.println("The number of contact info matches is " + totalScore + "\n");
		return isSpam;
	}

	/***
	 * Returns result from compareAgainst method, which is the the method that runs
	 * our entire spamAlgorithm. Lets us know if a call was spam or not.
	 * 
	 * @return false if not spam, true if spam.
	 */
	public boolean isSpam() {
		return isSpam;
	}

	/***
	 * Returns number of calls the spam algorithm marked as spam 
	 * @return number of spam calls 
	 */
	public int getNumberOfSpamCallsReceived() {
		return numberOfSpamCallsReceived;
	}

	/***
	 * Returns hashmap of calls marked as spam, with the incoming caller's name 
	 * as the key, and phone number as value 
	 * @return map of blocked callers due to being marked as spam 
	 */
	public HashMap<String, String> getBlockList() {
		return blockList;
	}

}