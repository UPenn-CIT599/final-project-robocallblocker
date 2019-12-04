import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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
	private ContactInfoReader allContactsInCSV = new ContactInfoReader("contacts100.csv");
	/*
	 * This is all the contacts from the CSV in the HashMap, which isn't cleaned.
	 * When we create a phone user, we have no need to first clean the contact data.
	 * By design, there can be bits of pieces of contactInfo scattered in the users
	 * phone, so if there is a blank phone number for a contactInfo object in the
	 * user's phone, this is fine, since in theory the user could have contact
	 * information for a person without having their phone number. This is NOT the
	 * map used to create incoming calls.
	 */
	private HashMap<String, ContactInfo> allContactsMap = allContactsInCSV.getAllContactsInCSV();
	/*
	 * We use this HashMap to create incoming calls to the phone which is passed to
	 * the GUI so we want a clean contact list with no blank phone numbers,
	 * otherwise on GUI it will show that "blank" or 0 is calling, which is clearly
	 * nonsense.
	 */
	private HashMap<String, ContactInfo> allContactsInHashMapCleaned = new HashMap<String, ContactInfo>();

	// the phone has a built in algorithm that checks for spam calls
	private SpamAlgorithm spamAlgoForPhone = new SpamAlgorithm();
	private String displayIncomingCallerPhoneNumber;
	private String displayIncomingCallerName;
	private boolean incomingCallSpam; // true if is spam, false if not
	private HashMap<String, ContactInfo> usersContacts; // used to get users contact list

	Clip clip;
	String soundName = "ringtoneFile.wav";
	AudioInputStream audioInputStream;

	/***
	 * *Updated Nov 30* Use random.nextInt to generate a phonebook of a random
	 * number of contacts for the user Default number of contacts a phone user has.
	 * Could instead incorporate this into the GUI and let the person using the GUI
	 * pick from a number in a combo-box. Removed from GUI class to avoid creating
	 * another instance variable that should belong in the phone class.
	 */

	// generate numbers between 40 and 60
	private int numberOfContactsForUser = ThreadLocalRandom.current().nextInt(40, 61);

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
	 * @return - ContactInfo associated with caller (use for testing that GUI
	 *         displays proper info)
	 */
	public ContactInfo createIncomingCallDisplayOnPhoneScreenGUI(HashMap<String, ContactInfo> usersContactList) {
		/*
		 * Clean the contact list with all contacts from the CSV reader, removing any
		 * contact with blank phone numbers before creating an incoming call. We need
		 * this map to be a new object instead of a reference since if we edit the
		 * underlying allContactsMap, that map will be cleaned too and we don't want
		 * that.
		 */
		allContactsInHashMapCleaned = allContactsInCSV
				.removeBlankPhoneNumbersFromMapUsedToCreateCalls(allContactsInCSV.getAllContactsInCSV());
		IncomingCall call = new IncomingCall(allContactsInHashMapCleaned);
		ContactInfo forCaller = call.getIncomingCallerInfo();
		setDisplayIncomingCallerName(forCaller.getName());
		setDisplayIncomingCallerPhoneNumber(forCaller.getPhoneNumber());
		setIncomingCallSpamOrNotSpam(getSpamAlgoForPhone().compareAgainst(forCaller, usersContactList));
		return forCaller;
	}

	/**
	 * Filewriter to output a textfile of all the spam callers which will be added
	 * to a HashMap called blockList the blocklist is populated from the results of
	 * the SpamAlgorithm
	 */
	public void blockListTextFile() {
		HashMap<String, String> blockList = spamAlgoForPhone.getBlockList();

		try {
			FileWriter fw = new FileWriter("BlockList.txt", true);
			for (String name : blockList.keySet()) {
				fw.write(name + ": " + blockList.get(name) + "\n");
				fw.flush();
			}
			fw.close();
		} catch (IOException e) {
			System.out.println("Unable to produce blocklist");
		}
	}

	/***
	 * Method to print out blockedCallers in the GUI
	 * 
	 * @return list of block callers as a string, each caller w/ phone number on a
	 *         line
	 */
	public String printBlockedCallers() {
		HashMap<String, String> blockList = spamAlgoForPhone.getBlockList();

		StringBuilder sb = new StringBuilder();
		for (String name : blockList.keySet()) {
			if (blockList.isEmpty()) {
				sb.append("No robo-callers today!");
			} else {
				sb.append(name + ": " + blockList.get(name) + '\n');
				sb.append(System.getProperty("line.separator"));
			}
		}
		return sb.toString();
	}

	/**
	 * ringtone method for phone to play sound in the GUI when a phone call comes in
	 * Currently on a loop of 3 TODO terminate the sound and reinitialize with each
	 * call
	 */
	public void ringtone() {
		try {

			audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());

			try {

				clip = AudioSystem.getClip();
			} catch (LineUnavailableException e1) {
				System.out.println("Didn't get clip!");
			}
		} catch (UnsupportedAudioFileException e1) {
			System.out.println("Doesn't support 'wav' files");
		} catch (IOException e1) {
			System.out.println("Check which folder the file is in");
		}
	}

	public void startRingtone() throws LineUnavailableException, IOException {
		clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.start();
	}

	public void closeRingtone() throws LineUnavailableException, IOException {
		clip.close();
	}

	public ContactInfoReader getAllContacts() {
		return allContactsInCSV;
	}

	public HashMap<String, ContactInfo> getAllContactsMap() {
		return allContactsMap;
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

	public HashMap<String, ContactInfo> getAllContactsInHashMapCleaned() {
		return allContactsInHashMapCleaned;
	}

	public void setAllContactsInHashMapCleaned(HashMap<String, ContactInfo> allContactsInHashMap) {
		this.allContactsInHashMapCleaned = allContactsInHashMap;
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
