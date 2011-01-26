/**********************************License(s)***********************************
Copyright © 2009, 2010, 2011 Roy Pfund.                     All rights reserved.
Use of this source code is governed by a  BSD-style License(the "License")  that
can be found in the LICENSE file. You should have received a copy of the License
along with this distribution.  If not,  You may obtain a copy of the License  at
    http://github.com/GlassGhost/Glas-Test-Area/raw/master/LICENSE.txt
*************************************Inputs*************************************
none
*********************************Pre-Conditions*********************************
none
******************************PROGRAM DESCRIPTION*******************************
A generic java Text Editor UI                                       tab=4 spaces
This class is meant to not only be the main class, but also to;  control the gui
and event handling for the program.
*********************************Post-Conditions********************************
none
*************************************Output*************************************
Depending on user input may edit or save text files.
***********************************Revisions************************************
(2009-Aug-15)-v0.01-File created
(2011-Jan-15)-v0.02-IDK
**********************************Package Name*********************************/
package TextEditor;
/********************************System Headers********************************/
import java.io.*;
import javax.swing.*;
import java.util.regex.*;
import java.lang.*;
/*********************************GUI Creation*********************************/
public class TextEditorUI extends JFrame
implements javax.swing.event.ChangeListener, java.awt.event.MouseListener {
JFileChooser chooser = new JFileChooser();
JTextPane mainJTextPane = new JTextPane();
//			JPanel noWrapPanel = new JPanel();
//			JScrollPane mainJScrollPane = new JScrollPane(noWrapPanel);
	JScrollPane mainJScrollPane = new JScrollPane(mainJTextPane);
JMenuBar JMenuBar1 = new JMenuBar();
	JMenu fileJMenu = new JMenu("File");
		JMenuItem newJMenuItem = new JMenuItem("New");
		JMenuItem openJMenuItem = new JMenuItem("Open");
		JMenuItem saveJMenuItem = new JMenuItem("Save");
		JMenuItem SaveAsJMenuItem = new JMenuItem("Save As");
		JMenuItem quitJMenuItem = new JMenuItem("Quit");
	JMenu editJMenu = new JMenu("Edit");
		JMenuItem undoJMenuItem = new JMenuItem("Undo");
		JMenuItem redoJMenuItem = new JMenuItem("Redo");
		JMenuItem cutJMenuItem = new JMenuItem("Cut");
		JMenuItem copyJMenuItem = new JMenuItem("Copy");
		JMenuItem pasteJMenuItem = new JMenuItem("Paste");
		JMenuItem deleteJMenuItem = new JMenuItem("Delete");
		JMenuItem selectJMenuItem = new JMenuItem("Select-All");
		JMenuItem findJMenuItem = new JMenuItem("Find & Replace");
	JMenu viewJMenu = new JMenu("View");
		JCheckBoxMenuItem TextWrapCheckBox = new JCheckBoxMenuItem("Word-Wrap", true);
	JMenu helpJMenu = new JMenu("Help");
		JMenuItem aboutJMenuItem = new JMenuItem("About");

public TextEditorUI() {
	super("Text Editor");
	this.setSize(640, 450);
	this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	this.setVisible(true);
//		noWrapPanel.add( mainJTextPane );
	this.add(mainJScrollPane);
	this.setJMenuBar(JMenuBar1);
		JMenuBar1.add(fileJMenu);
			newJMenuItem.addMouseListener(this);
			fileJMenu.add(newJMenuItem);
			openJMenuItem.addMouseListener(this);
			fileJMenu.add(openJMenuItem);
			openJMenuItem.addMouseListener(this);
			fileJMenu.add(saveJMenuItem);
			saveJMenuItem.addMouseListener(this);
			fileJMenu.add(SaveAsJMenuItem);
			SaveAsJMenuItem.addMouseListener(this);
				fileJMenu.addSeparator();
			fileJMenu.add(quitJMenuItem);
			quitJMenuItem.addMouseListener(this);
		JMenuBar1.add(editJMenu);
			editJMenu.add(undoJMenuItem);
			undoJMenuItem.addMouseListener(this);
			editJMenu.add(redoJMenuItem);
			redoJMenuItem.addMouseListener(this);
				editJMenu.addSeparator();
			editJMenu.add(cutJMenuItem);
			cutJMenuItem.addMouseListener(this);
			editJMenu.add(copyJMenuItem);
			copyJMenuItem.addMouseListener(this);
			editJMenu.add(pasteJMenuItem);
			pasteJMenuItem.addMouseListener(this);
			editJMenu.add(deleteJMenuItem);
			deleteJMenuItem.addMouseListener(this);
				editJMenu.addSeparator();
			editJMenu.add(selectJMenuItem);
			selectJMenuItem.addMouseListener(this);
			editJMenu.add(findJMenuItem);
			findJMenuItem.addMouseListener(this);
		JMenuBar1.add(viewJMenu);
			viewJMenu.add(TextWrapCheckBox);
			TextWrapCheckBox.addChangeListener(this);
		JMenuBar1.add(helpJMenu);
			helpJMenu.add(aboutJMenuItem);
			aboutJMenuItem.addMouseListener(this);
			//TextWrapCheckBox.addpropertychangedlistener(this);
}
/********************************Event Handling********************************/
public void stateChanged(javax.swing.event.ChangeEvent evt) {
	if (evt.getSource() == TextWrapCheckBox) {
		if (TextWrapCheckBox.getState() == true){
			
		} else { //state is false
			
		}
	}
}
public void mouseClicked(java.awt.event.MouseEvent evt) {
}
public void mouseEntered(java.awt.event.MouseEvent evt) {
}
public void mouseExited(java.awt.event.MouseEvent evt) {
}
public void mousePressed(java.awt.event.MouseEvent evt) {
}
public void mouseReleased(java.awt.event.MouseEvent evt) {
	if (evt.getSource() == newJMenuItem) {
		//FileDialog("Open");
	}
	else if (evt.getSource() == openJMenuItem) {
		FileDialog("Open");
	}
	else if (evt.getSource() == saveJMenuItem) {
		//FileDialog("Save");
	}
	else if (evt.getSource() == saveJMenuItem) {
		FileDialog("Save As");
	}
	else if (evt.getSource() == quitJMenuItem) {
		System.exit(0);
	}
	else if (evt.getSource() == TextWrapCheckBox) {
	
	}
	else if (evt.getSource() == undoJMenuItem) {
	
	}
	else if (evt.getSource() == cutJMenuItem) {
	
	}
	else if (evt.getSource() == copyJMenuItem) {
	
	}
	else if (evt.getSource() == pasteJMenuItem) {
	
	}
	else if (evt.getSource() == aboutJMenuItem) {
	
	}
}

private void FileDialog(String filedialogtypeSTR) {
//filedialog switch for desired filedialog, add to or change existing cases for your app.
	if (chooser.showDialog(null, filedialogtypeSTR) == JFileChooser.APPROVE_OPTION) {
	File chosenFile = chooser.getSelectedFile();
		if (filedialogtypeSTR.contentEquals("Open")){//user picked open file
			try {
				BufferedReader in = new BufferedReader (new FileReader(chosenFile));
				mainJTextPane.setText("");
				String nextLine = in.readLine();
				while (nextLine != null) {
					mainJTextPane.setText(mainJTextPane.getText() + nextLine + "\n");
					nextLine = in.readLine();
			}
			in.close();
			} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Could not load the file " + e.getMessage());
			}
		}

		else if (filedialogtypeSTR.contentEquals("Save As")){// user picked save file
			try {
				PrintWriter out = new PrintWriter (new FileWriter(chosenFile));
				out.print (mainJTextPane.getText());
				out.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Could not save the file " + e.getMessage());
			}
		}
	}
}

public static void main(String args[]) {
	try {//Set native look and feel
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (InstantiationException e) {
	} catch (ClassNotFoundException e) {
	} catch (UnsupportedLookAndFeelException e) {
	} catch (IllegalAccessException e) {
	}//__________________________________
	new TextEditorUI();
}}//___________________________________________________________end TextEditor UI
