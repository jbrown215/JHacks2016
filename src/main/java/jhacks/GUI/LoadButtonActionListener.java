package jhacks.GUI;

import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class LoadButtonActionListener implements ActionListener {

	private JPanel frame;
	final JFileChooser fc = new JFileChooser();
//	int returnVal;
	private String filePath;

    public LoadButtonActionListener(JPanel frame) {
    	
    	this.frame = frame;	
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		fc.showOpenDialog(frame);
		e.getSource();
		filePath = fc.getSelectedFile().getAbsolutePath();
		System.out.println(filePath);
	}
	
	public String getFile(){
		return filePath;
	}
	//method that saves the file selected
}