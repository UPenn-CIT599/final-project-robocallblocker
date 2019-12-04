import java.util.HashMap;

public class TestClass {

	public static void main(String[] args) {


		ContactInfoReader allContactsInCSV = new ContactInfoReader("contacts100.csv");
		HashMap<String, ContactInfo> allContactsMap = allContactsInCSV.getAllContactsInCSV();
		System.out.println("Without phone class: " + allContactsMap + "\n");
	
		
		Phone phone = new Phone();
		System.out.println("With phone class " + phone.getAllContactsMap());
		

	}

}