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
	HashMap<String, ContactInfo> blockList;
	
	// constructor
	public BlockList(String name, ContactInfo contactInfo) {
		
		blockList = new HashMap<>(); 
		
		blockList.put(name, contactInfo);
	}
	
	// Getter for blockList instance variable
	public HashMap<String, ContactInfo> getBlockList() {
		return blockList;
	}
	
}
