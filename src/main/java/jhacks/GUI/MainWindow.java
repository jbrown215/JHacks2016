package jhacks.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainWindow extends JPanel implements ActionListener  {

	protected JButton button1, button2;
	
	public MainWindow(){

        button1 = new JButton("Load Program");
        button2 = new JButton("Find Game");

        button1.setToolTipText("Click this button to load program.");
        button2.setToolTipText("Click this button to find game.");

        add(button1);
        add(button2);

    }
	
	private static void createAndShowGUI() {
		//Create and set up the window.
		
		JFrame frame = new JFrame("MainFrame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MainWindow newMainFrame = new MainWindow();
		newMainFrame.setOpaque(true);
		frame.setContentPane(newMainFrame);

		//Display the window.
		frame.pack();
		frame.setVisible(true);
		}

	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
					createAndShowGUI();
				}
		}
		);
	}
	
}

