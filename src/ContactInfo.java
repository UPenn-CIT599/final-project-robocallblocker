/**
 * ContactInfo details for each contact are stored here
 * 
 * @author ShawnChoudhury
 * @author JoshuaChopra
 * @author ThomasTee
 */
public class ContactInfo {

	
	/**
	 * A contact has a name, phone number, email address, social media handle, etc. 
	 */
	private String name, phoneNumber, emailAddress, socialMediaHandle, address, company, city, county, state, zipCode;

	/***
	 * Create an object to hold the contact info of a person, read in from our
	 * contacts CSV file.
	 * 
	 * @param name              (this will be both first and last)
	 * @param number            - phone number
	 * @param email             address
	 * @param socialMediaHandle name on social media
	 * @param address
	 * @param company
	 * @param city
	 * @param county
	 * @param state
	 * @param zipCode
	 */
	public ContactInfo(String name, String number, String email, String socialMediaHandle, String address,
			String company, String city, String county, String state, String zipCode) {
		this.name = name;
		this.phoneNumber = number;
		this.emailAddress = email;
		this.socialMediaHandle = socialMediaHandle;
		this.address = address;
		this.company = company;
		this.city = city;
		this.county = county;
		this.state = state;
		this.zipCode = zipCode;
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

	/**
	 * Overriden toString() method to print the ContactInfo object contents
	 * Otherwise, it would print the memory location
	 */
	public String toString() {
		return "Name: " + name + "\nPhone Number: " + phoneNumber + "\nEmail: " + emailAddress + " "
				+ "\nSocial Media Handle: " + socialMediaHandle + "\nAddress: " + address + "\nCompany: " + company
				+ "\nCity: " + city + "\nCounty: " + county + "\nState: " + state + "\nZipcode: " + zipCode;
	}

	/***
	 * Returns company associated with contact
	 * 
	 * @return company employed at (e.g., Facebook)
	 */
	public String getCompany() {
		return company;
	}

	/***
	 * Returns city contact lives in
	 * 
	 * @return city (e.g., Orlando)
	 */
	public String getCity() {
		return city;
	}

	/***
	 * Returns county contact lives in
	 * 
	 * @return county (e.g., Warren)
	 */
	public String getCounty() {
		return county;
	}

	/***
	 * Returns state contact lives in
	 * 
	 * @return state (e.g., NY)
	 */
	public String getState() {
		return state;
	}

	/***
	 * Returns zipCode for contact's address
	 * 
	 * @return zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

}
