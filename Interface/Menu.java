import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.color.*;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Menu implements Runnable, ActionListener {
//	TODO: calculate the angle for distance, we'll need to know how far something can go
//	TODO: Max speed?
//	TODO: Take one input, don't forget the drag parameter(coefficient) user defined
	private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = (int) screenSize.getWidth();
	public static final int HEIGHT = (int) screenSize.getHeight();
	public static final double SCALE = 1000; //for meters per second

//	Constructor for Menu
	public static JFrame f = new JFrame("The Thrower");

//	Panel stuff
	private static JButton startButton = new JButton("Start!");
	private static JButton testButton = new JButton();
	private static JButton resetButton = new JButton();
	private static JButton quitButton = new JButton();
	private static JLabel titleLabel = new JLabel();
//	private static JTextField distanceText = new JTextField("0");
	private static JTextField angleText = new JTextField("0");
	private static JTextField velocityText = new JTextField("0");
	JLabel angleLabel = new JLabel("Angle");
	JLabel velocityLabel = new JLabel("Velocity");
	JPanel inputPanel = new JPanel();
//	Panel to go at the top of the screen
	JPanel north = new JPanel();
	
//	Simulation stuff
	public static double initialxPos = 1 * SCALE;
	public static double initialHeight = 0.1 * SCALE;
	public static double xVelocity = 1 * SCALE;
	public static double yVelocity = 1 * SCALE;
	private static double length = 0.3 * SCALE;
	
	@Override
	public void run() {
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

//		Set the panel layout to layer things vertically
		north.setLayout(new BoxLayout(north, BoxLayout.PAGE_AXIS));
		north.setPreferredSize(new Dimension(100, 50));
		
//		Panel for the title
		JPanel titlePanel = new JPanel();
		titlePanel.add(titleLabel);
		north.add(titlePanel);
//		Label for scale
		JLabel scaleLabel = new JLabel("cm");
//		distancePanel.add(scaleLabel);

//		north.add(distancePanel);
		addTextfield();

//		Panel to go at the bottom of the screen
		JPanel south = new JPanel();
		south.setLayout(new FlowLayout());
		south.add(startButton);
		south.add(testButton);
		south.add(resetButton);
		south.add(quitButton);

//		Panel for the simulation
		JPanel center = new JPanel();

//		Add the panels to the frame
		f.add(north, BorderLayout.NORTH);
		f.add(south, BorderLayout.SOUTH);

//		To check the input for the distance textfield
		angleText.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				warning();
			}

			public void removeUpdate(DocumentEvent e) {
				warning();
			}

			public void insertUpdate(DocumentEvent e) {
				warning();
			}

			public void warning() {
				if (!number(angleText.getText())) {
					ErrorPopup.main(null);
				}
			}
		});
		velocityText.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				warning();
			}

			public void removeUpdate(DocumentEvent e) {
				warning();
			}

			public void insertUpdate(DocumentEvent e) {
				warning();
			}

			public void warning() {
				if (!number(velocityText.getText())) {
					System.out.println("This ain't a number man");
				}
			}
		});

		f.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT / 2));
		f.pack();
		f.setVisible(true);

		labelDisplay();
		setListeners();

	}

	public static void main(String[] args) {
		Menu myMenu = new Menu();
		SwingUtilities.invokeLater(myMenu);
	}

//	Perform an action when a button is pressed
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if (button.getText() == "Start!") {
//			When start button is pressed, start the actual arm
			start();
		}
		if (button.getText() == "Test!") {
//			When test button is pressed, start the test arm (simulation)
			test();
		}
		if (button.getText() == "Reset!") {
//			When reset button is pressed, reset the program
			reset();
		}
		if (button.getText() == "Quit!") {
//			When quit button is pressed, quit the program
			System.out.println("I'm going GHOST!!");
			System.exit(0);
		}
	}

//	Starts the program with the current settings
	private void start() {
//		TODO: Start the program, should also pause the program?, take the 
		System.out.println("Starting Program!");
		if (angleText.getText().isEmpty() || velocityText.getText().isEmpty()) {
			System.out.println("I'm empty");
		} else {
			double angle = Double.parseDouble(angleText.getText());
			double velocity = Double.parseDouble(velocityText.getText());
					
			paramCalculator(angle, velocity);
		}
	}

//	Start the test program
	private void test() {
//		TODO: Program the test program
		System.out.println("Testing!");
		if (angleText.getText().isEmpty() || velocityText.getText().isEmpty()) {
			System.out.println("I'm empty"); // makes sure the text field is not empty
		} else {
			double angle = Double.parseDouble(angleText.getText());
			double velocity = Double.parseDouble(velocityText.getText());

			paramCalculator(angle, velocity);
			Simulation.main(null);
			
		}
	}

//	Reset the arm to the default position
	private void reset() {
//		TODO: Reset the arms position, should also pause the program?
		System.out.println("Resetting arm!");
	}

//	Change the display for the labels
	private void labelDisplay() {
		startButton.setText("Start!");
		testButton.setText("Test!");
		resetButton.setText("Reset!");
		quitButton.setText("Quit!");
		titleLabel.setText("Welcome to the worlds most useful arm in the entire galaxy!");
	}

//	Check if the input is a number
	public static boolean number(String str) {
		boolean result = true;
		for (int i = 0; i < str.length(); i++) {
			if ((str.charAt(i) < '0') || (str.charAt(i) > '9')) {
				result = false;
			}
		}
		return result;
	}

//	Initialize the ActionListeners
	private void setListeners() {
		startButton.addActionListener(this);
		testButton.addActionListener(this);
		resetButton.addActionListener(this);
		quitButton.addActionListener(this);
	}

//	Calculate the parameters for the simulation
	private void paramCalculator(double angle, double velocity) {
//		This will run when start and test are called
		System.out.println(angle);
		System.out.println(velocity);
		
		double phi = angle + 90; //angle for the tangential velocity
		angle = Math.toRadians(angle);
		initialxPos = length - length * Math.cos(angle);
		initialHeight = HEIGHT - length*Math.sin(angle);
		
		phi = Math.toRadians(phi);
		xVelocity = -velocity * Math.cos(phi)*SCALE;
		yVelocity = velocity * Math.sin(phi)*SCALE;
	}

//	Add textfield
	private void addTextfield() {
//		Add the angle related objects
		inputPanel.add(angleLabel);
		angleText.setPreferredSize(new Dimension(50, 25));
		JLabel degree = new JLabel("degree(s)");
		inputPanel.add(angleText);
		inputPanel.add(degree);
		JLabel space = new JLabel("          ");
		inputPanel.add(space);
//		Add the velocity related objects
		inputPanel.add(velocityLabel);
		velocityText.setPreferredSize(new Dimension(50, 25));
		JLabel mpers = new JLabel("m/s");
		inputPanel.add(velocityText);
		inputPanel.add(mpers);
		north.add(inputPanel);
	}
	
//	Pop up correction menu
	private void popup() {
		
	}
	
//	Getters for the angle
	public String angle() {
		return angleText.getText();
	}
	
//	Getters for the velocity
	public String velocity() {
		return velocityText.getText();
	}
}
