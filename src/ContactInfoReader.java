import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * ContactInfoReader purpose: read in a csv file of contact information Populate
 * a HashMap with names as keys and contact information as values
 * 
 * @author ShawnChoudhury
 * @author JoshuaChopra
 * @author ThomasTee
 */

public class ContactInfoReader {

	// Instance Variables
	private HashMap<String, ContactInfo> contactInfoMap = new HashMap<>();

	// Constructor
	public ContactInfoReader(String filename) {

		// try with resources to automatically close out scanner object 
		try (Scanner contactsDataReader = new Scanner(new File(filename));) {

			// skip the column headers in first line 
			contactsDataReader.nextLine(); 
			while (contactsDataReader.hasNextLine()) {
				String line = contactsDataReader.nextLine();
				String[] columnData = line.split(",");
				// Concatenate first and last name
				String name = columnData[0] + " " + columnData[1];
				String number = columnData[8];
				String email = columnData[9];
				String socialMediaHandle = columnData[10];
				String address = columnData[3];
				// fill up the constructor
				ContactInfo contact = new ContactInfo(name, number, email, socialMediaHandle, address);
				// put the contact info in to the HashMap
				contactInfoMap.put(contact.getName(), contact);
			}

		} catch (FileNotFoundException e) {
			System.out.println("Uh-oh! Error processing file; please check the name and file location.");

		}

	}

	/***
	 * Get HashMap with key as contact's name, value as contactInfo object 
	 * that has all attributes of a contact
	 * @return HashMap of all contacts from file 
	 */
	public HashMap<String, ContactInfo> getContactInfoMap() {
		return contactInfoMap;
	}

}
