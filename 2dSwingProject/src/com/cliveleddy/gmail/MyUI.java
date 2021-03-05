package com.cliveleddy.gmail;

import com.cliveleddy.gmail.view.MyGUI;

public class MyUI {

	private MyGUI mMyGUI;

	public MyUI() {
		mMyGUI = new MyGUI();
	}

	public void Start() {
		// Make sure GUI is created on the event dispatching thread.
		mMyGUI.Initialise();
	}
}
