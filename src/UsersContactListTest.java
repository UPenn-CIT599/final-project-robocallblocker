import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

/***
 * This class tests our UserContactsList class to ensure keys are properly
 * shuffled in the HashMap containing contactInfo objects.
 * 
 * @author ShawnChoudhury
 * @author JoshuaChopra
 * @author ThomasTee
 *
 */
class UsersContactListTest {

	/**
	 * Minor JUnit test to make sure the keys are shuffled When the keys are
	 * processed into the HashMap from the ContactInfoReader they should be
	 * shuffled; this way the initial sequence of keys are not repeated This helps
	 * us create a random HashMap for the UsersContactList class Initial key set
	 * should not match shuffled keyset arraylist equals compares each corresponding
	 * element between 2 arraylists
	 */
	@Test
	void testShuffleMapKeys() {

		ContactInfoReader reader = new ContactInfoReader("contacts10.csv");
		HashMap<String, ContactInfo> map = reader.getAllContactsInCSV();
		// keys from above map
		ArrayList<String> keys1 = new ArrayList<>(map.keySet());
		UsersContactList list = new UsersContactList(map, 10);
		// shuffled keys; these should not be in same sequence as initial keys1
		// arraylist
		ArrayList<String> shuffledKeys = list.shuffleMapKeys(map);
		boolean isEqual = keys1.equals(shuffledKeys);

		assertEquals(false, isEqual);
	}

}
