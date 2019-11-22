import java.util.HashMap;

/**
 * BlockList
 * a hashmap of filled with contactInfo of callers that are tagged as spam
 * Can add them to this list and remove from the contactInfoMap,
 * so that these people can't call again
 *  
 * @author ShawnChoudhury
 * @author JoshuaChopra
 *
 */
public class BlockList {

	// instance variable
	HashMap<String, ContactInfo> blockedList;
	
	// constructor
	public BlockList() {
		// TODO fill up a hashmap of blocked contacts when spam
	}
}
