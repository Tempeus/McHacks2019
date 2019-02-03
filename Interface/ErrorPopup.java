import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ErrorPopup extends JPanel {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Error");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(700, 100);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLayout(new FlowLayout());
		JLabel errorLabel = new JLabel(
				"The correct input is a number. The angle goes from 30 degrees to 135. The max velocity goes to 15 m/s.");
		frame.add(errorLabel);
	}

}
