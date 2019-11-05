import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * ContactInfoReader
 * purpose: read in a csv file of contact information
 * Populate a HashMap with names as keys
 * and contact information as values
 * @author ShawnChoudhury
 * @author JoshuaChopra
 * @author ThomasTee
 */

public class ContactInfoReader {

	// Instance Variables
	HashMap<String, ContactInfo> contactInfoMap = new HashMap<>();
	
	// Constructor
	public ContactInfoReader(String filename) {
		
		try {
			
			Scanner scan = new Scanner(new File(filename));
			
			// skip the column headers
			if (scan.hasNextLine()) {
				
				scan.hasNextLine();
				
			}
			
			while (scan.hasNextLine()) {
				
				String line = scan.nextLine();
				
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
			
			// TODO Auto-generated catch block
			
			System.out.println("Uh-oh! Error processing file; please check the name and file location.");
		
		}
		
	}
	
	// Get HashMap
	public HashMap<String, ContactInfo> getContactInfoMap() {
		return contactInfoMap;
	}
	
	
	
}
