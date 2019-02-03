import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.color.*;
import java.awt.event.*;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

public class Simulation extends JPanel implements ActionListener {
//	velocity is tangential? that means that it is when it leaves the arm
//	Private variables
	private static final double G = 9.8 * Menu.SCALE; // Gravity constant
	private static final double length = 30 * Menu.SCALE; // The length of the arm
	private static double D = 1; // Drag

//	Public variables
	public static double time = 0;
	public static double TIME_INTERVAL = 0.001;
	Color ballColor = Color.BLACK;
	double height = Menu.initialHeight;
	double xPos = Menu.initialxPos;
	double yVelocity = Menu.yVelocity;
	double xVelocity = Menu.xVelocity;
	Timer tm = new Timer(5, this);
	
	public Simulation(){
		tm.start();
	}

	public static void main(String[] args ) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Simulation");
				Simulation co = new Simulation();
				frame.add(co, BorderLayout.CENTER);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(Menu.WIDTH, Menu.HEIGHT);
				frame.setVisible(true);
			}
		});
	}

	public void paint(Graphics g) {
		g.drawOval((int) xPos, (int) height, (int) 5, (int) 5);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		simulate();
		repaint();
	}

	public void simulate() {
//		Start the simulation
		height -= yVelocity * TIME_INTERVAL;
		xPos += xVelocity * TIME_INTERVAL;
		yVelocity += -G * time;
		time += TIME_INTERVAL;
		if (height >= Menu.HEIGHT) {
			yVelocity = 0;
			xVelocity = 0;
			System.out.println(xPos);
		}

	}

}
