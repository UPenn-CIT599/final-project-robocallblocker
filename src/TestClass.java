import java.util.HashMap;

public class TestClass {

	public static void main(String[] args) {


		ContactInfoReader allContactsInCSV = new ContactInfoReader("contacts100.csv");
		HashMap<String, ContactInfo> allContactsMap = allContactsInCSV.getAllContactsInCSV();
		System.out.println("Without phone class: " + allContactsMap + "\n");
		System.out.println("Number of contacts: " + allContactsMap.size() + "\n"); // same size as below phone.getAllContactsMap
		
		System.out.println("-------------" + "\n" + "Now using ContactInfo instance variable from phone class");
		
		
		Phone phone = new Phone();
		System.out.println("With phone class " + phone.getAllContactsMap() + "\n");
		System.out.println("Number of contacts: " + phone.getAllContactsMap().size() + "\n"); // same size as above allContactsMap
		
		System.out.println("--------");
		phone.createIncomingCallDisplayOnPhoneScreenGUI(phone.getAllContactsMap());
		System.out.println("After creating incoming call, which is done by calling the contactInfoReader's removeBlankPhoneNumbers method");

		
		System.out.println("Number of contacts: " + phone.getAllContactsInHashMapCleaned().size() + "\n");
		
		System.out.println("-----" + "\n" + "After cleaning the list from ContactInfoReader: " + allContactsMap.size());
		

	}

}