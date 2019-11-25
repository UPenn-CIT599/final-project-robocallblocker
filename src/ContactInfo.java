/**
 * ContactInfo details for each contact are stored here
 * 
 * @author ShawnChoudhury
 * @author JoshuaChopra
 * @author ThomasTee
 */
public class ContactInfo {

	// Instance Variables

	private String name;

	private String phoneNumber;

	private String emailAddress;

	/**
	 * possibly another class to hold each account; e.g. Facebook, IG, Twitter, etc.
	 */
	private String socialMediaHandle;

	private String address;

	// Constructor
	public ContactInfo(String name, String number, String email, String socialMediaHandle, String address) {

		this.name = name;

		this.phoneNumber = number;

		this.emailAddress = email;

		this.socialMediaHandle = socialMediaHandle;

		this.address = address;

	}

	/***
	 * Get name of contact
	 * 
	 * @return contact's name
	 */
	public String getName() {
		return name;
	}

	/***
	 * Get email address of contact
	 * 
	 * @return contact's email
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/***
	 * Get social media handle of contact
	 * 
	 * @return social media handle (twitter, instagram, etc.)
	 */
	public String getSocialMediaHandle() {
		return socialMediaHandle;
	}

	/***
	 * Get address of contact
	 * 
	 * @return contact's address
	 */
	public String getAddress() {
		return address;
	}

	/***
	 * Get contact's phone number
	 * 
	 * @return phone number of contact
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/***
	 * Set phone number for contact
	 * 
	 * @param phoneNumber - pass in phone number to set for contact
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
