package jhacks.GUI;

import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class LoadButtonActionListener implements ActionListener {

	private JPanel frame;

    public LoadButtonActionListener(JPanel frame) {
    	
    	this.frame = frame;
    	
    	
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		final JFileChooser fc = new JFileChooser();
		fc.showOpenDialog(frame);
		
	}
}