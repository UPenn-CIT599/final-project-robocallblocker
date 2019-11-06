/**
 * ContactInfo
 * details for each contact are stored here
 * @author ShawnChoudhury
 * @author JoshuaChopra
 * @author ThomasTee
 */
public class ContactInfo {

	// Instance Variables
	
	private String name;
	
	private String phoneNumbers;
	
	private String emailAddress;
	
	/** possibly another class to hold each account; e.g. Facebook, IG, Twitter, etc. */
	private String socialMediaHandle;
	
	private String address;
	
	// Constructor
	public ContactInfo(String name, String number, String email, String socialMediaHandle,
			String address) {
		
		this.name = name;
		
		this.phoneNumbers = number;
		
		this.emailAddress = email;
		
		this.socialMediaHandle = socialMediaHandle;
		
		this.address = address;
		
	}
	
	// Getters
	public String getName() {
		return name;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getSocialMediaHandle() {
		return socialMediaHandle;
	}

	public String getAddress() {
		return address;
	}

	public String getPhoneNumbers() {
		return phoneNumbers;
	}
	
	public void setPhoneNumbers(String phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}
	
	
	
}
