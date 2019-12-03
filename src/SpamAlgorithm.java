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
		scoreByAttribute.put(incoming.getName(), 0);
		scoreByAttribute.put(incoming.getPhoneNumber(), 0);
		scoreByAttribute.put(incoming.getEmailAddress(), 0);
		scoreByAttribute.put(incoming.getSocialMediaHandle(), 0);
		scoreByAttribute.put(incoming.getAddress(), 0);
		scoreByAttribute.put(incoming.getCompany(), 0);
		scoreByAttribute.put(incoming.getCity(), 0);
		scoreByAttribute.put(incoming.getCounty(), 0);
		scoreByAttribute.put(incoming.getState(), 0);
		scoreByAttribute.put(incoming.getZipCode(), 0);
		return scoreByAttribute;
	}

	/***
	 * This method makes use of the mapForScoring and returns whether an incoming
	 * call is likely spam or not as a boolean, based on the score assigned to the
	 * call, which is determined through each step of the algorithm. If we find a
	 * match we replace the value associated with that attribute with 1 UNLESS it's
	 * the phone number, then we replace with 2. Doing this instead of incrementing
	 * a score variable, since we don't want to double count things like states,
	 * counties, cities, zip codes, etc.
	 * 
	 * @param incoming             caller information
	 * @param phoneUsersContactMap all contact information in users phone
	 * @return if call is spam or not
	 */
	public boolean compareAgainst(ContactInfo incoming, HashMap<String, ContactInfo> phoneUsersContactMap) {
		HashMap<String, Integer> scoreByAttribute = mapForScoring(incoming);
		int totalScore = 0;
		for (String uniqueID : phoneUsersContactMap.keySet()) {
			if (incoming.getName().equals(phoneUsersContactMap.get(uniqueID).getName())
					&& !incoming.getName().equals("0")) {
				scoreByAttribute.replace(incoming.getName(), 1);

			}
			if (incoming.getEmailAddress().equals(phoneUsersContactMap.get(uniqueID).getEmailAddress())
					&& !incoming.getEmailAddress().equals("0")) {
				scoreByAttribute.replace(incoming.getEmailAddress(), 1);
			}
			if (incoming.getAddress().equals(phoneUsersContactMap.get(uniqueID).getAddress())
					&& !incoming.getAddress().equals("0")) {
				scoreByAttribute.replace(incoming.getAddress(), 1);

			}
			if (incoming.getSocialMediaHandle().equals(phoneUsersContactMap.get(uniqueID).getSocialMediaHandle())
					&& !incoming.getSocialMediaHandle().equals("0")) {
				scoreByAttribute.replace(incoming.getSocialMediaHandle(), 1);

			}
			if (incoming.getPhoneNumber().equals(phoneUsersContactMap.get(uniqueID).getPhoneNumber())
					&& !incoming.getPhoneNumber().equals("0")) {
				// 2 points if phone number is in user's phone
				scoreByAttribute.replace(incoming.getPhoneNumber(), 2);
			}
			if (incoming.getCompany().equals(phoneUsersContactMap.get(uniqueID).getCompany())
					&& !incoming.getCompany().equals("0")) {
				scoreByAttribute.replace(incoming.getCompany(), 1);
			}
			if (incoming.getCity().equals(phoneUsersContactMap.get(uniqueID).getCity())
					&& !incoming.getCity().equals("0")) {
				scoreByAttribute.replace(incoming.getCity(), 1);
			}
			if (incoming.getCounty().equals(phoneUsersContactMap.get(uniqueID).getCounty())
					&& !incoming.getCounty().equals("0")) {
				scoreByAttribute.replace(incoming.getCounty(), 1);
			}
			if (incoming.getState().equals(phoneUsersContactMap.get(uniqueID).getState())
					&& !incoming.getState().equals("0")) {
				scoreByAttribute.replace(incoming.getState(), 1);
			}
			if (incoming.getZipCode().equals(phoneUsersContactMap.get(uniqueID).getZipCode())
					&& !incoming.getZipCode().equals("0")) {
				scoreByAttribute.replace(incoming.getZipCode(), 1);
			}
		}

		// add up individual attribute scores to get total score for caller
		for (Integer attributeScore : scoreByAttribute.values()) {
			totalScore += attributeScore;
		}
		if (totalScore >= 3) {
			isSpam = false;
		} else {
			isSpam = true;
			numberOfSpamCallsReceived++;
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

	public int getNumberOfSpamCallsReceived() {
		return numberOfSpamCallsReceived;
	}

}