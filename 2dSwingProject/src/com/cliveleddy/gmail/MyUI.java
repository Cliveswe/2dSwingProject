package com.cliveleddy.gmail;

import com.cliveleddy.gmail.view.MyGUIContainer;

/**
 * 
 * 
 * @author Clive Leddy
 * @version 1.0
 *
 */
public class MyUI {

	private MyGUIContainer mMyGUI;

	public MyUI() {
		mMyGUI = new MyGUIContainer();
	}

	/**
	 * Start the application.
	 */
	public void Start() {
		// Make sure GUI is created on the event dispatching thread.
		mMyGUI.Initialise();
	}
}
