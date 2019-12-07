import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * ContactInfoReader purpose: read in a csv file of contact information Populate
 * a HashMap with names as keys and contact information as values CSV Source:
 * https://letterhub.com/sample-csv-file-with-contacts/ Downloaded CSV was then
 * altered and randomized to have a file with blanks and duplicates so we can
 * demonstrate file handling for datasets with incomplete information
 * 
 * @author ShawnChoudhury
 * @author JoshuaChopra
 * @author ThomasTee
 */

public class ContactInfoReader {

	// HashMap of contacts with name as key and ContactInfo as value.
	private HashMap<String, ContactInfo> allContactsInCSV = new HashMap<>();
	int uniqueID = 0;

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
				// Concatenate first and last name
				String name = checkIfBlankThenFill(columnData, 0) + " " + checkIfBlankThenFill(columnData, 1);
				// we don't want a last name displayed on GUI with a "0", so check if last char
				// is 0 if the last name was blank when read in.
				if (name.charAt(name.length() - 1) == '0') {
					name = name.substring(0, name.length() - 1);
				}
				String number = checkIfBlankThenFill(columnData, 8);
				/*
				 * REMEMBER that contacts100.csv has 11 columns, and contacts10.csv has 12 (when
				 * counting using 0 indexing, i.e., first column is 0) so email here is 9, in
				 * the other file it's 10.
				 */
				String email = checkIfBlankThenFill(columnData, 9);
				/*
				 * REMEMBER that contacts100.csv has 11 columns, and contacts10.csv has 12 (when
				 * counting using 0 indexing, i.e., first column is 0)
				 */
				String socialMediaHandle = checkIfBlankThenFill(columnData, 11);
				String company = checkIfBlankThenFill(columnData, 2);
				String address = checkIfBlankThenFill(columnData, 3);
				String city = checkIfBlankThenFill(columnData, 4);
				String county = checkIfBlankThenFill(columnData, 5);
				String state = checkIfBlankThenFill(columnData, 6);
				String zipCode = checkIfBlankThenFill(columnData, 7);

				// fill up the constructor
				ContactInfo contact = new ContactInfo(name, number, email, socialMediaHandle, address, company, city,
						county, state, zipCode);
				// put the contact info in to the HashMap

				allContactsInCSV.put(Integer.toString(uniqueID), contact);
				uniqueID += 1; // create new uniqueID for each contact read in
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
	public HashMap<String, ContactInfo> getAllContactsInCSV() {
		return allContactsInCSV;
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

	/***
	 * Helper method used to ensure we never use a key-value pair that has a
	 * ContactInfo object where the phone number of that ContactInfo object is
	 * blank, AKA "0", since we set blanks to 0 when reading in the data.
	 * Intuitively, wouldn't make any sense to receive a call from a phone number of
	 * "0" or blank.
	 * 
	 * @param allContacts hashMap we create upon reading in contacts from CSV
	 * @return cleaned map of contacts used to generate calls (no blank phone #s)
	 * 
	 */
	public HashMap<String, ContactInfo> removeBlankPhoneNumbersFromMapUsedToCreateCalls(
			HashMap<String, ContactInfo> allContacts) {
		/*
		 * use iterator since we can't iterate over a keySet and remove key-value pair
		 * simultaneously or java throws ConcurrentModificationException
		 */

		Iterator<Map.Entry<String, ContactInfo>> iterator = allContacts.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, ContactInfo> nameWithContactInfo = iterator.next();
			/*
			 * if value (ContactInfo object) associated with key (name of contact) has
			 * variable value for phone number as "0" then remove it from our map. Else keep
			 * it.
			 */
			if (nameWithContactInfo.getValue().getPhoneNumber().equals("0")) {
				iterator.remove();
			}
		}
		return allContacts;
	}

	/***
	 * Set contacts map associated with all contacts in CSV
	 * 
	 * @param allContactsInCSV pass in map to use
	 */
	public void setAllContactsInCSV(HashMap<String, ContactInfo> allContactsInCSV) {
		this.allContactsInCSV = allContactsInCSV;
	}

	/***
	 * Set a unique ID for a a contact
	 * 
	 * @param uniqueID
	 */
	public void setUniqueID(int uniqueID) {
		this.uniqueID = uniqueID;
	}

	/***
	 * Get unique ID for contact, which is created upon reading in data
	 * 
	 * @return
	 */
	public int getUniqueID() {
		return uniqueID;
	}

}
