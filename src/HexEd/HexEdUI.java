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
A Simple Text Editor                                                tab=4 spaces
This class is meant to not only be the main class, but also to;  control the gui
and event handling for the program.
*********************************Post-Conditions********************************
none
*************************************Output*************************************
Depending on user input may edit or save text files.
***********************************Revisions************************************
(2009-Aug-15)-v0.01-File created
(2011-Mar-8 )-v0.05-"New", "Open", "Save", "Save As", "Quit", "Text-Wrap" work.
**********************************Package Name*********************************/
package HexEd;
/********************************System Headers********************************/
import java.io.*;
import java.util.regex.*;
import java.util.HashMap;
import java.lang.*;

import java.net.*;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.undo.*;

import java.awt.event.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.Rectangle;
import java.awt.GridLayout;
import java.awt.BorderLayout;
//import org.apache.tools.*;
//import org.apache.tools.ant.Main.*;
/*********************************GUI Creation*********************************/
public class HexEdUI extends JFrame
implements ActionListener,
		javax.swing.event.ChangeListener,
		DropTargetListener {
private JFileChooser chooser = new JFileChooser();
private File currentFile = null;
private ToggleWrapTextPane mainJTextPane = new ToggleWrapTextPane();
private int LineNum = 1, ColumnNum = 1;
private int visiblePixels = 16;
private int tabLength = 4;
private JScrollPane mainJScrollPane = new JScrollPane(mainJTextPane);
private JPanel statusPane = new JPanel(new GridLayout(1, 1));
private JLabel caretpositionJLabel = new JLabel(" ");
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
		JCheckBoxMenuItem TextWrapCheckBox = new JCheckBoxMenuItem("Word-Wrap", false);
	JMenu toolsJMenu = new JMenu("Tools");
		JMenuItem antJMenuItem = new JMenuItem("Run Apache Ant");
	JMenu helpJMenu = new JMenu("Help");
		JMenuItem contentsJMenuItem = new JMenuItem("Contents");
		JMenuItem aboutJMenuItem = new JMenuItem("About");
private DropTarget dt = new DropTarget(this, this);

//org.apache.tools.ant.Main ANTPILE = new org.apache.tools.ant.Main();
public HexEdUI(){
	setTitle("HextEd");
	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	add(mainJScrollPane, BorderLayout.CENTER);
	statusPane.add(caretpositionJLabel);
	add(statusPane, BorderLayout.SOUTH);
	//mainJTextPane.setTabSize(4);
	mainJTextPane.setFont(new java.awt.Font("FreeMono", 1, 12));
	setJMenuBar(TEUIJMenuBar);
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
		TEUIJMenuBar.add(toolsJMenu);
			addmenuitemtomenu(toolsJMenu, antJMenuItem, "F5");
			antJMenuItem.setToolTipText("Runs \"ant -find\", in the directory the current file is in");
		TEUIJMenuBar.add(helpJMenu);
			addmenuitemtomenu(helpJMenu, contentsJMenuItem, "F1");
				helpJMenu.addSeparator();
			addmenuitemtomenu(helpJMenu, aboutJMenuItem);
	this.pack();	this.setSize(640, 450);
	this.setVisible(true);
}

private class ToggleWrapTextPane extends JTextPane implements CaretListener{
	private boolean TracksViewportWidth = true, TracksViewportHeight = true,
					LineWrap = true;
	public boolean getScrollableTracksViewportWidth(){
		return TracksViewportWidth;
	}
	public boolean getScrollableTracksViewportHeight(){
		return TracksViewportWidth;
	}
	public void setScrollableTracksViewportWidth(boolean xBool){
		if (TracksViewportWidth != xBool){
			TracksViewportWidth = xBool;
			this.revalidate();
		}
	}
	public void setScrollableTracksViewportHeight(boolean xBool){
		if (TracksViewportHeight != xBool){
			TracksViewportHeight = xBool;
			this.revalidate();
		}
	}
	public boolean getLineWrap(){
		return LineWrap;
	}
	public void setLineWrap(boolean wrap){
		if (LineWrap != wrap){
			LineWrap = wrap;
			setScrollableTracksViewportWidth(wrap);
		}
	}
	public void setTabSize(int size){
		int tabWidth = size * this.getFontMetrics(this.getFont()).charWidth('w');
		TabStop[] tabs = new TabStop[10];
		for (int j = 0; j < tabs.length; j++){
			tabs[j] = new TabStop( (j+1) * tabWidth );
		}
		TabSet tabSet = new TabSet(tabs);
		SimpleAttributeSet attributes = new SimpleAttributeSet();
		StyleConstants.setTabSet(attributes, tabSet);
		int length = this.getDocument().getLength();
		this.getStyledDocument().setParagraphAttributes(0, length, attributes, true);
	}
	public ToggleWrapTextPane(){
		super();
		super.addCaretListener(this);
	}
	//	Implement CaretListener interface
	public void caretUpdate(final CaretEvent evt){
		try {
			int caretpos = evt.getDot();
			int selection = evt.getMark();
			LineNum = getLineNumber(caretpos);
			ColumnNum = getColumnNumber(caretpos);
			if (caretpos == selection){// no selection
				caretpositionJLabel.setText("Ln " + LineNum + ", Col" + ColumnNum);
			}else if (caretpos < selection){
				caretpositionJLabel.setText("Selected: " + "Ln " + LineNum + ", Col" + ColumnNum + " to " + "Ln " + getLineNumber(selection) + ", Col" + getColumnNumber(selection));
			}else {
				caretpositionJLabel.setText("Selected: " + "Ln " + getLineNumber(selection) + ", Col" + getColumnNumber(selection) + " to " + "Ln " + LineNum + ", Col" + ColumnNum);
			}
			//  Attempt to scroll the viewport to make sure Caret is visible
			Rectangle r = this.modelToView(caretpos);
			r.x += visiblePixels;
			this.scrollRectToVisible(r);
		}
		catch(Exception e) {}
	}
    public int getLineNumber(int pos) {
        int Line = (pos==0) ? 1 : 0;
        try {
            int offs=pos;
            while( offs>0) {
                offs=Utilities.getRowStart(this, offs)-1;
                Line++;
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        return Line;
    }
    public int getColumnNumber(int pos) {
        try { return pos-Utilities.getRowStart(this, pos)+1;
        } catch (BadLocationException e) { e.printStackTrace();
        } return -1;
    }
}

/********************************Event Handling********************************/
public void actionPerformed( ActionEvent evt ){
	if (evt.getSource() == newJMenuItem){
		FileMenu("New");
	} else if (evt.getSource() == openJMenuItem){
		FileMenu("Open");
	} else if (evt.getSource() == saveJMenuItem){
		FileMenu("Save");
	} else if (evt.getSource() == saveasJMenuItem){
		FileMenu("Save As");
	} else if (evt.getSource() == saveallJMenuItem){
	} else if (evt.getSource() == closeJMenuItem){
	} else if (evt.getSource() == closeallJMenuItem){
	} else if (evt.getSource() == printJMenuItem){
	} else if (evt.getSource() == quitJMenuItem){
		System.exit(0);
	}

	else if (evt.getSource() == undoJMenuItem){
	} else if (evt.getSource() == redoJMenuItem){
	} else if (evt.getSource() == cutJMenuItem){
	} else if (evt.getSource() == copyJMenuItem){
	} else if (evt.getSource() == pasteJMenuItem){
	} else if (evt.getSource() == deleteJMenuItem){
	} else if (evt.getSource() == selectJMenuItem){
	} else if (evt.getSource() == findAnReplaceJMenuItem){
	}

	else if (evt.getSource() == TextWrapCheckBox){
	} else if (evt.getSource() == fullscrnJMenuItem){
	}
	else if (evt.getSource() == antJMenuItem){
		if (currentFile != null){/*//
			JOptionPane.showMessageDialog(this, "Current version of ANT:\n"
			+ org.apache.tools.ant.Main.getAntVersion()
			//ANTPILE.getAntVersion()
			);/**/
		}
	}

	else if (evt.getSource() == contentsJMenuItem){
	} else if (evt.getSource() == aboutJMenuItem){
	}
}
public void stateChanged(javax.swing.event.ChangeEvent evt){
	if (evt.getSource() == TextWrapCheckBox){
			mainJTextPane.setLineWrap(TextWrapCheckBox.getState());
	}
}

public void dragEnter(DropTargetDragEvent evt) {}
public void dragExit(DropTargetEvent evt) {}
public void dragOver(DropTargetDragEvent evt) {}
public void dropActionChanged(DropTargetDragEvent evt) {}
public void drop(DropTargetDropEvent evt) {	try {
	Transferable tr = evt.getTransferable();
	DataFlavor[] flavors = tr.getTransferDataFlavors();
	for (int i = 0; i < flavors.length; i++) {
		if (flavors[i].isRepresentationClassInputStream()){//input stream
		//gnome file drop
			evt.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
			mainJTextPane.read(new InputStreamReader(
			(InputStream)tr.getTransferData(flavors[i])),
			"from system clipboard");
			evt.dropComplete(true); return;
		}else if (flavors[i].isFlavorJavaFileListType()){//file list
			// Great!  Accept copy drops...
			evt.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
			// And add the list of file names to our text area
			java.util.List list = (java.util.List)tr.getTransferData(flavors[i]);
			for (int j = 0; j < list.size(); j++) {
				mainJTextPane.setText(mainJTextPane.getText() + list.get(j) + "\n");
			}
			evt.dropComplete(true); return;
		}else if (flavors[i].isFlavorSerializedObjectType()){//Java object
			evt.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
			Object o = tr.getTransferData(flavors[i]);
			JOptionPane.showMessageDialog(this, "Object Dropped: " + o);
			evt.dropComplete(true); return;
		}
	}
	evt.rejectDrop();
} catch (Exception e) {
	e.printStackTrace();
	evt.rejectDrop();
}}/*__________________________________________________________________________*/
/**********************************Functions***********************************/

private void FileMenu(String filedialogtypeSTR){//switch for desired filedialog
	if (filedialogtypeSTR.contentEquals("New")){
		currentFile = null;mainJTextPane.setText("");
	} else if (filedialogtypeSTR.contentEquals("Save")){
		if (currentFile == null) FileMenu("Save As");
		else { StringToFile(mainJTextPane.getText(), currentFile); }
	} else if (chooser.showDialog(null, filedialogtypeSTR) == JFileChooser.APPROVE_OPTION){
	currentFile = chooser.getSelectedFile();
		if (filedialogtypeSTR.contentEquals("Open")){
			mainJTextPane.setText(FileToString(currentFile));
		}
		else if (filedialogtypeSTR.contentEquals("Save As")){//
			StringToFile(mainJTextPane.getText(), currentFile);
		}
	}
	if(currentFile == null){this.setTitle("HexEd");}
	else {this.setTitle("HexEd - " + currentFile.getName() );}
}/*___________________________________________________________________________*/

private String FileToString(File theFile){ try {
	BufferedReader in = new BufferedReader (new FileReader(theFile));

	String nextLine = null, crrntstr = null;
	if ((nextLine = in.readLine()) != null) crrntstr = nextLine;
	while ((nextLine = in.readLine()) != null){
		crrntstr = crrntstr + "\n" + nextLine;
	}
	in.close();
	return crrntstr;
} catch (IOException e){
	JOptionPane.showMessageDialog(this, "Could not load the file " + e.getMessage()); return "";
}}/*__________________________________________________________________________*/

private void StringToFile(String theString, File theFile){ try {
	PrintWriter out = new PrintWriter (new FileWriter(theFile));
	out.print(theString);
	out.close();
} catch (IOException e){
	JOptionPane.showMessageDialog(this, "Could not save the file " + e.getMessage());
}}/*__________________________________________________________________________*/

private void addmenuitemtomenu(JMenu parentmenu, JMenuItem MenuItemInstance){
	addmenuitemtomenu(parentmenu, MenuItemInstance, null);
}/*___________________________________________________________________________*/

private void addmenuitemtomenu(JMenu parentmenu, JMenuItem MenuItemInstance, String keymnemonic){
	parentmenu.add(MenuItemInstance);
//	MenuItemInstance.addMouseListener(this);
	MenuItemInstance.addActionListener(this);
	if(keymnemonic != null){
		MenuItemInstance.setAccelerator(KeyStroke.getKeyStroke(keymnemonic));
	}
}/*___________________________________________________________________________*/

public static void main(String args[]){
	try {//Set native look and feel
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (InstantiationException e){
	} catch (ClassNotFoundException e){
	} catch (UnsupportedLookAndFeelException e){
	} catch (IllegalAccessException e){
	}
	new HexEdUI();
}}//________________________________________________________________end HexEd UI
