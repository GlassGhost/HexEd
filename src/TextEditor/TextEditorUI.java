/**********************************License(s)***********************************
Copyright © 2009, 2010, 2011 Roy Pfund.                     All rights reserved.
Use of this source code is governed by a  BSD-style License(the "License")  that
can be found in the LICENSE file. You should have received a copy of the License
along with this distribution.  If not,  You may obtain a copy of the License  at
    http://github.com/GlassGhost/Text-Editor/raw/master/LICENSE.txt
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
import java.awt.event.*;
import javax.swing.*;
import java.util.regex.*;
import java.lang.*;
import java.net.*;
//import org.apache.tools.*;
//import org.apache.tools.ant.Main.*;
/*********************************GUI Creation*********************************/
public class TextEditorUI extends JFrame
implements ActionListener,
		javax.swing.event.ChangeListener,
		java.awt.event.MouseListener {
JFileChooser chooser = new JFileChooser();
private File currentFile = null;
private String nextLine = null;
JTextPane mainJTextPane = new JTextPane();
JScrollPane mainJScrollPane = new JScrollPane(mainJTextPane);
JMenuBar TEUIJMenuBar = new JMenuBar();
	JMenu fileJMenu = new JMenu("File");
		JMenuItem newJMenuItem = new JMenuItem("New"),
		openJMenuItem = new JMenuItem("Open"),
		saveJMenuItem = new JMenuItem("Save"),
		saveasJMenuItem = new JMenuItem("Save As . . ."),
		saveallJMenuItem = new JMenuItem("Save All"),
		closeJMenuItem = new JMenuItem("Close"),
		closeallJMenuItem = new JMenuItem("Close All"),
		printJMenuItem = new JMenuItem("Print"),
		quitJMenuItem = new JMenuItem("Quit");
	JMenu editJMenu = new JMenu("Edit");
		JMenuItem undoJMenuItem = new JMenuItem("Undo"),
		redoJMenuItem = new JMenuItem("Redo"),
		cutJMenuItem = new JMenuItem("Cut"),
		copyJMenuItem = new JMenuItem("Copy"),
		pasteJMenuItem = new JMenuItem("Paste"),
		deleteJMenuItem = new JMenuItem("Delete"),
		selectJMenuItem = new JMenuItem("Select-All"),
		findAnReplaceJMenuItem = new JMenuItem("Find & Replace");
	JMenu viewJMenu = new JMenu("View");
		JMenuItem fullscrnJMenuItem = new JMenuItem("Full Screen");
		JCheckBoxMenuItem TextWrapCheckBox = new JCheckBoxMenuItem("Word-Wrap", true);
	JMenu sourceJMenu = new JMenu("Source");
		JMenuItem antJMenuItem = new JMenuItem("Run Apache Ant");
	JMenu helpJMenu = new JMenu("Help");
		JMenuItem contentsJMenuItem = new JMenuItem("Contents");
		JMenuItem aboutJMenuItem = new JMenuItem("About");

//org.apache.tools.ant.Main ANTPILE = new org.apache.tools.ant.Main();

public TextEditorUI(){
	this.setTitle("Text Editor");
	this.setSize(640, 450);
	this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	this.setVisible(true);
//		noWrapPanel.add( mainJTextPane );
	this.add(mainJScrollPane);
	mainJTextPane.setFont(new java.awt.Font("FreeMono", 1, 12));
	this.setJMenuBar(TEUIJMenuBar);
		TEUIJMenuBar.add(fileJMenu);
			addmenuitemtomenu(fileJMenu, newJMenuItem, "ctrl N");
			addmenuitemtomenu(fileJMenu, openJMenuItem, "ctrl O");
			addmenuitemtomenu(fileJMenu, saveJMenuItem, "ctrl S");
			addmenuitemtomenu(fileJMenu, saveasJMenuItem, "ctrl shift S");
			addmenuitemtomenu(fileJMenu, saveallJMenuItem, "ctrl shift L");
			addmenuitemtomenu(fileJMenu, closeJMenuItem, "ctrl W");
			addmenuitemtomenu(fileJMenu, closeallJMenuItem, "ctrl shift W");
				fileJMenu.addSeparator();
			addmenuitemtomenu(fileJMenu, printJMenuItem, "ctrl P");
			addmenuitemtomenu(fileJMenu, quitJMenuItem, "ctrl Q");
		TEUIJMenuBar.add(editJMenu);
			addmenuitemtomenu(editJMenu, undoJMenuItem, "ctrl Z");
			addmenuitemtomenu(editJMenu, redoJMenuItem, "ctrl Y");
				editJMenu.addSeparator();
			addmenuitemtomenu(editJMenu, cutJMenuItem, "ctrl X");
			addmenuitemtomenu(editJMenu, copyJMenuItem, "ctrl C");
			addmenuitemtomenu(editJMenu, pasteJMenuItem, "ctrl V");
			addmenuitemtomenu(editJMenu, deleteJMenuItem, "ctrl D");
				editJMenu.addSeparator();
			addmenuitemtomenu(editJMenu, selectJMenuItem, "ctrl A");
			addmenuitemtomenu(editJMenu, findAnReplaceJMenuItem, "ctrl F");
		TEUIJMenuBar.add(viewJMenu);
			viewJMenu.add(TextWrapCheckBox);
			TextWrapCheckBox.addChangeListener(this);
			addmenuitemtomenu(viewJMenu, fullscrnJMenuItem, "F11");
			//TextWrapCheckBox.addpropertychangedlistener(this);
		TEUIJMenuBar.add(sourceJMenu);
			addmenuitemtomenu(sourceJMenu, antJMenuItem, "F5");
		TEUIJMenuBar.add(helpJMenu);
			addmenuitemtomenu(helpJMenu, contentsJMenuItem, "F1");
				editJMenu.addSeparator();
			addmenuitemtomenu(helpJMenu, aboutJMenuItem);
}
/********************************Event Handling********************************/
public void actionPerformed( ActionEvent evt ){
	if (evt.getSource() == newJMenuItem){
		File("New");
	}
	else if (evt.getSource() == openJMenuItem){
		File("Open");
	}
	else if (evt.getSource() == saveJMenuItem){
		File("Save");
	}
	else if (evt.getSource() == saveasJMenuItem){
		File("Save As");
	}
	else if (evt.getSource() == saveallJMenuItem){
	}
	else if (evt.getSource() == closeJMenuItem){
	}
	else if (evt.getSource() == closeallJMenuItem){
	}
	else if (evt.getSource() == printJMenuItem){
	}
	else if (evt.getSource() == quitJMenuItem){
		System.exit(0);
	}

	else if (evt.getSource() == undoJMenuItem){
	}
	else if (evt.getSource() == redoJMenuItem){
	}
	else if (evt.getSource() == cutJMenuItem){
	}
	else if (evt.getSource() == copyJMenuItem){
	}
	else if (evt.getSource() == pasteJMenuItem){
	}
	else if (evt.getSource() == deleteJMenuItem){
	}
	else if (evt.getSource() == selectJMenuItem){
	}
	else if (evt.getSource() == findAnReplaceJMenuItem){
	}

	else if (evt.getSource() == TextWrapCheckBox){
	}
	else if (evt.getSource() == fullscrnJMenuItem){
	}
/*/
	else if (evt.getSource() == antJMenuItem){
		JOptionPane.showMessageDialog(null, 
		//ANTPILE.getAntVersion()
		"Current version of ANT:\n" + org.apache.tools.ant.Main.getAntVersion()
		);
	}/**/

	else if (evt.getSource() == contentsJMenuItem){
	}
	else if (evt.getSource() == aboutJMenuItem){
	}
}
public void stateChanged(javax.swing.event.ChangeEvent evt){
	if (evt.getSource() == TextWrapCheckBox){
		if (TextWrapCheckBox.getState() == true){
			
		} else { //state is false
			
		}
	}
}
public void mouseClicked(java.awt.event.MouseEvent evt){
}
public void mouseEntered(java.awt.event.MouseEvent evt){
}
public void mouseExited(java.awt.event.MouseEvent evt){
}
public void mousePressed(java.awt.event.MouseEvent evt){
}
public void mouseReleased(java.awt.event.MouseEvent evt){
}
/**********************************Functions***********************************/
private void File(String filedialogtypeSTR){
//filedialog switch for desired filedialog
	if (filedialogtypeSTR.contentEquals("New")){
		currentFile = null;mainJTextPane.setText("");
	}
	else if (chooser.showDialog(null, filedialogtypeSTR) == JFileChooser.APPROVE_OPTION){
	currentFile = chooser.getSelectedFile();
//	CFO = currentFile.toString();
		if (filedialogtypeSTR.contentEquals("Open")){//user picked open file
			try {
			BufferedReader in = new BufferedReader (new FileReader(currentFile));
			nextLine = null;
			String crrntstr = null;
			if ((nextLine = in.readLine()) != null) crrntstr = nextLine;
			while ((nextLine = in.readLine()) != null){
				crrntstr = crrntstr + "\n" + nextLine;
			}mainJTextPane.setText(crrntstr);
			in.close();
			} catch (IOException e){
			JOptionPane.showMessageDialog(null, "Could not load the file " + e.getMessage());mainJTextPane.setText("");
			}/**/
		}

		else if (filedialogtypeSTR.contentEquals("Save")){//user picked save file
			try {
				PrintWriter out = new PrintWriter (new FileWriter(currentFile));
				out.print (mainJTextPane.getText());
				out.close();
			} catch (IOException e){
				JOptionPane.showMessageDialog(null, "Could not save the file " + e.getMessage());
			}/**/
		}

		else if (filedialogtypeSTR.contentEquals("Save As")){//
			try {
				PrintWriter out = new PrintWriter (new FileWriter(currentFile));
				out.print(mainJTextPane.getText());
				out.close();
			} catch (IOException e){
				JOptionPane.showMessageDialog(null, "Could not save the file " + e.getMessage());
			}/**/
		}
	}
//	this.setTitle(CFO);
}/*___________________________________________________________________________*/

private void addmenuitemtomenu(JMenu parentmenu, JMenuItem MenuItemInstance){
	parentmenu.add(MenuItemInstance);
//	MenuItemInstance.addMouseListener(this);
	MenuItemInstance.addActionListener(this);
}/*___________________________________________________________________________*/
private void addmenuitemtomenu(JMenu parentmenu, JMenuItem MenuItemInstance, String keymnemonic){
	parentmenu.add(MenuItemInstance);
//	MenuItemInstance.addMouseListener(this);
	MenuItemInstance.addActionListener(this);
	MenuItemInstance.setAccelerator(KeyStroke.getKeyStroke(keymnemonic));
}/*___________________________________________________________________________*/

public static void main(String args[]){
	{try {//Set native look and feel
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (InstantiationException e){
	} catch (ClassNotFoundException e){
	} catch (UnsupportedLookAndFeelException e){
	} catch (IllegalAccessException e){
	}}//__________________________________
	new TextEditorUI();
}}//___________________________________________________________end TextEditor UI
