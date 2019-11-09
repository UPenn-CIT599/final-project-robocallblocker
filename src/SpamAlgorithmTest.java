package src;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SpamAlgorithmTest {

	@Test
	void testCompareAgainst() {
		
		ContactInfoReader list = new ContactInfoReader("contacts10.csv");
		
		HashMap<String, ContactInfo> map = list.getContactInfoMap();
		
		// 3rd contact in list
		ContactInfo caller = map.get("Art Venere");
		
		// just put full list in user's contact list
		UsersContactList users = new UsersContactList(map, 10);
		
		HashMap<String, ContactInfo> userMap = users.getContactList();
		
		SpamAlgorithm spamAlgo = new SpamAlgorithm();
		
		boolean match = spamAlgo.compareAgainst(caller, users);
		
		assertEquals(true, match);		
		
	}

}
