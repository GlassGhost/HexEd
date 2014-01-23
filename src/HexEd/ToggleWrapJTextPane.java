/**********************************License(s)***********************************
Copyright © 2009, 2010, 2011 Roy Pfund.                     All rights reserved.
You may not use this file except in compliance with the  License(the  "License")
that can be found in the LICENSE file. If you did not  receive  a  copy  of  the
License along with this distribution,  you may obtain a copy of the  License  at
    http://github.com/GlassGhost/HexEd/raw/master/LICENSE.txt
*************************************Inputs*************************************
user input
*********************************Pre-Conditions*********************************
none
******************************PROGRAM DESCRIPTION*******************************
Extends the JTextPane class to include methods to retrieve cursor/selection info
and to change text-wrapping modes, and to change tab size.
*********************************Post-Conditions********************************
none
*************************************Output*************************************
cursor/selection info events.
***********************************Revisions************************************
(2012-03-28)-v0.01-File created
(2012-04-02)-v0.02-CaretInfo events and Data work
**********************************Package Name*********************************/
package HexEd;
/********************************System Headers********************************/
import java.io.*;
import java.util.*;
//import java.util.regex.*;
//import java.util.HashMap;
//import java.util.EventObject;
import java.lang.*;

import java.net.*;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.undo.*;

import java.awt.Dimension;
import java.awt.event.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.Rectangle;
import java.awt.GridLayout;
import java.awt.BorderLayout;

interface CaretInfoListener {
	public void CaretInfoUpdate();
}

public class ToggleWrapJTextPane extends JTextPane implements Scrollable, CaretListener{
/**********************************Variables***********************************/
List<CaretInfoListener> listeners = new ArrayList<CaretInfoListener>();
private int visiblePixels = 16;
private Dimension[] CaretInfo = new Dimension[2];
private boolean TracksViewportWidth = false, TracksViewportHeight = false,
				LineWrap = false;
/**********************************Functions***********************************/
	public ToggleWrapJTextPane(){
		super();
		this.addCaretListener(this);
		this.setLineWrap(this.getLineWrap());
		this.revalidate();
	}
	public void addCaretInfoListener(CaretInfoListener toAdd) {
		listeners.add(toAdd);
	}
	public Dimension[] getCaretInfo() {
		return CaretInfo;
	}
	public boolean getLineWrap(){
		return LineWrap;
	}
	public void setLineWrap(boolean xBool){
		if (LineWrap != xBool){
			LineWrap = xBool;
			if (LineWrap == true){
				setScrollableTracksViewportWidth(true);
//				setScrollableTracksViewportHeight(false);
			} else {
				setScrollableTracksViewportWidth(false);
//				setScrollableTracksViewportHeight(false);
			}
		}
	}
	@Override public boolean getScrollableTracksViewportWidth(){
		return TracksViewportWidth;
	}
//	@Override public boolean getScrollableTracksViewportHeight(){
//		return TracksViewportWidth;
//	}
	public void setScrollableTracksViewportWidth(boolean xBool){
		if (TracksViewportWidth != xBool){
			TracksViewportWidth = xBool;
			this.revalidate();
		}
	}
	public void setScrollableTracksViewportHeight(boolean xBool){
//		if (TracksViewportHeight != xBool){
			TracksViewportHeight = xBool;
			this.revalidate();
//		}
	}
//		if (this.getHeight() >= mainJScrollPane.getHeight()){
//			setScrollableTracksViewportHeight(false);
//		}else {
//			setScrollableTracksViewportHeight(true);
//		}
//		if (LineWrap == false){
//			if (this.getWidth() >= mainJScrollPane.getWidth()){
//				setScrollableTracksViewportWidth(false);
//			}else {
//				setScrollableTracksViewportWidth(true);
//			}
//		}

//	public void setTabSize(int size){
//		int tabWidth = size * this.getFontMetrics(this.getFont()).charWidth('w');
//		TabStop[] tabs = new TabStop[10];
//		for (int j = 0; j < tabs.length; j++){
//			tabs[j] = new TabStop( (j+1) * tabWidth );
//		}
//		TabSet tabSet = new TabSet(tabs);
//		SimpleAttributeSet attributes = new SimpleAttributeSet();
//		StyleConstants.setTabSet(attributes, tabSet);
//		int length = this.getDocument().getLength();
//		this.getStyledDocument().setParagraphAttributes(0, length, attributes, true);
//	}

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
/********************************Event Handling********************************/
	public void caretUpdate(final CaretEvent evt){//Implement CaretListener interface
		try {
			int caretpos = evt.getDot();
			int selection = evt.getMark();
			// provide cursor info events
			if (caretpos == selection){// no selection
				CaretInfo[0]=new Dimension(getColumnNumber(caretpos), getLineNumber(caretpos));
				CaretInfo[1]=CaretInfo[0];
			}else if (caretpos < selection){
				CaretInfo[0]=new Dimension(getColumnNumber(caretpos), getLineNumber(caretpos));
				CaretInfo[1]=new Dimension(getColumnNumber(selection), getLineNumber(selection));
			}else {
				CaretInfo[0]=new Dimension(getColumnNumber(selection), getLineNumber(selection));
				CaretInfo[1]=new Dimension(getColumnNumber(caretpos), getLineNumber(caretpos));
			}
			for (CaretInfoListener hl : listeners) hl.CaretInfoUpdate();
			//  Attempt to scroll the viewport to make sure Caret is visible
			Rectangle r = this.modelToView(caretpos);
			r.x += visiblePixels;
			this.scrollRectToVisible(r);
		}
		catch(Exception e) {}
	}
}//______________________________________________________end ToggleWrapJTextPane
