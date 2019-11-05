import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class will be used to create the GUI for the Call Blocker program.
 * 
 * @author Joshua Chopra
 *
 */
public class CallBlockerGUI implements Runnable {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new CallBlockerGUI());

	}

	@Override
	public void run() {
		JFrame frame = new JFrame("Call Blocker Program"); // TODO make a better name
		JPanel outerPanel = new JPanel(new GridLayout(2,1)); // overall outer panel of program 
		JPanel upperTopPanel = new JPanel(new FlowLayout());
		JLabel myLabel = new JLabel("Please input your phone number using the keypad below:");
		
		JPanel middlePanel = new JPanel(new GridLayout(1,0)); // will display number being dialed 
		
		JPanel innerBottomPannel = new JPanel(new GridLayout(4,3));
		// buttons 
		JButton dial1 = new JButton("1");
		JButton dial2 = new JButton("2");
		JButton dial3 = new JButton("3");
		JButton dial4 = new JButton("4");
		JButton dial5 = new JButton("5");
		JButton dial6 = new JButton("6");
		JButton dial7 = new JButton("7");
		JButton dial8 = new JButton("8");
		JButton dial9 = new JButton("9");
		JButton dialStar = new JButton("*");
		JButton dial0 = new JButton("0");
		JButton dialPound = new JButton("#");
		

		// Add the components together in this order:
		/*
		 * Frame > OuterPanel > [InnerTopPanel, > Label InnerBottomPanel] > Buttons
		 */
		frame.add(outerPanel);
		outerPanel.add(upperTopPanel);
		upperTopPanel.add(myLabel);
		outerPanel.add(middlePanel);
		outerPanel.add(innerBottomPannel);
		innerBottomPannel.add(dial1);
		innerBottomPannel.add(dial2);
		innerBottomPannel.add(dial3);
		innerBottomPannel.add(dial4);
		innerBottomPannel.add(dial5);
		innerBottomPannel.add(dial6);
		innerBottomPannel.add(dial7);
		innerBottomPannel.add(dial8);
		innerBottomPannel.add(dial9);
		innerBottomPannel.add(dialStar);
		innerBottomPannel.add(dial0);
		innerBottomPannel.add(dialPound);
		
		

		// required pieces of code
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

}
