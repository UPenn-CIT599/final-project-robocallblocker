import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class IncomingCall {

	ContactInfo incoming;

	// Shuffle Contact Info and create incoming call 
	public IncomingCall(HashMap<String, ContactInfo> map) {
		ArrayList<String> keys = new ArrayList<String>(map.keySet());
		Collections.shuffle(keys);

		incoming = map.get(keys.get(0));
	}

	// Getter
	public ContactInfo getIncoming() {
		return incoming;
	}
}
