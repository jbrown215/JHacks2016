package jhacks.GUI;
import jhacks.client.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import java.io*;

public class MainWindow extends JPanel {

	protected JButton loadButton, /*saveButton*/ findGameButton;
	public String filePath;
	
	public MainWindow(){

        loadButton = new JButton("Load Program");
        findGameButton = new JButton("Find Game");

        loadButton.setToolTipText("Click this button to load program.");
        findGameButton.setToolTipText("Click this button to find game.");

        add(loadButton);
        LoadButtonActionListener listener = new LoadButtonActionListener(this);
        loadButton.addActionListener(listener);
        add(findGameButton);
        
       filePath = listener.getFile();
       String[] file = {filePath};
       PythonReader.main(file);
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

