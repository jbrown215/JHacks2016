package jhacks.GUI;

import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class LoadButtonActionListener implements ActionListener {

	private JPanel frame;
	final JFileChooser fc = new JFileChooser();
	int returnVal;
	File file;

    public LoadButtonActionListener(JPanel frame) {
    	
    	this.frame = frame;
    	
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		//this.fc = new JFileChooser();
		fc.showOpenDialog(frame);
		
	}
	/*
	public String getFile(){
		if(returnVal == fc.APPROVE_OPTION){
			String filePath = fc.getSelectedFile().getAbsolutePath();
			System.out.println(filePath);
			return filePath;
	}*/
	//method that saves the file selected
}