import java.io.File;
import java.io.FileNotFoundException;
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
				/*
				 * need negative integer limit to keep applying pattern to empty strings in file
				 * use RegEx with negative lookahead to capture blanks after a "," and not count
				 * a "," as an actual input to the string [], and if a data entry has a
				 * whitespace character after a comma, we want that whole data entry as its own
				 * string and do not want to split it.
				 */
				String[] columnData = line.split(",(?! )", -1);
				// was using this to ensure all string [] were length 12
//				if (columnData.length != 12) {
//					System.out.println(columnData);
//				}
				// Concatenate first and last name
				String name = checkIfBlankThenFill(columnData, 0) + " " + checkIfBlankThenFill(columnData, 1);
				String number = checkIfBlankThenFill(columnData, 8);
				String email = checkIfBlankThenFill(columnData, 9);
				String socialMediaHandle = checkIfBlankThenFill(columnData, 11);
				String address = checkIfBlankThenFill(columnData, 3);
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
	 * Get HashMap with key as contact's name, value as contactInfo object that has
	 * all attributes of a contact
	 * 
	 * @return HashMap of all contacts from file
	 */
	public HashMap<String, ContactInfo> getContactInfoMap() {
		return contactInfoMap;
	}

	/***
	 * Check if a data entry is blank, and just simply fill as string with 0 if so.
	 * Will be useful if necessary for parsing ints, etc. given that we can modify
	 * our read in process with flexibility from the method.
	 * 
	 * @param columnData
	 * @param column
	 * @return
	 */
	public String checkIfBlankThenFill(String[] columnData, int column) {
		if (columnData[column].isEmpty()) {
			return "0";
		} else
			return columnData[column];
	}

}
