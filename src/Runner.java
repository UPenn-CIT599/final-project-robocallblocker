import javax.swing.SwingUtilities;

/***
 * This class runs our entire CallBlockerProgram, using all of the classes: 
 * 
 * - Phone 
 * - CallBlockerGUI 
 * - ContactInfoReader 
 * - ContactInfo 
 * - IncomingCall 
 * - SpamAlgorithm 
 * - UsersContactList
 * 
 * @author JoshuaChopra
 * @author ShawnChoudhury
 * @author ThomasTee
 *
 */
public class Runner {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new CallBlockerGUI());
	}

}
