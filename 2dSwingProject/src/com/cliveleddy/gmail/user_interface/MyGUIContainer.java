package com.cliveleddy.gmail.user_interface;

import java.awt.BorderLayout;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * This is the application container window. It extends the JFrame swing and
 * contains components used in this application.
 * 
 * @author Clive Leddy
 * @version 1.0
 *
 */
@SuppressWarnings("serial")
public class MyGUIContainer extends JFrame {

	private final int FRAME_WIDTH = 800;
	private final int FRAME_HEIGHT = 800;
	// private final int POSITION_X = 300;
	// private final int POSITION_Y = 50;

	/**
	 * The key values to get a text description.
	 */
	public static enum UITextEnum {
		TITLE
	};

	/**
	 * A dictionary that is indexed by a key.
	 */
	public static Map<UITextEnum, String> Text = Map.ofEntries(Map.entry(UITextEnum.TITLE, "My GUI"));

	public MyGUIContainer() {
		super();
	}

	/**
	 * Get a text description.
	 * 
	 * @param key to index the container that holds the text description as Enum.
	 * @return a String.
	 */
	public static String getText(UITextEnum key) {
		return Text.get(key);
	}

	/**
	 * Set up and initialise the application window.
	 */
	public void Initialise() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SetLookAndFeel();
		SetWindowSize();
		SetFrameTitle();
		SetLayout();
		this.setVisible(true);
	}

	private void SetLayout() {
		setLayout(new BorderLayout());
		// add the menu bar to the layout
		add(new MyMenuBar(), BorderLayout.PAGE_START);

		// add the status bar to the layout
		add(new MyStatusBar(), BorderLayout.PAGE_END);
	}

	private void SetFrameTitle() {
		this.setTitle(getText(UITextEnum.TITLE));
	}

	private void SetLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void SetWindowSize() {
		// set the size of the window
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		// this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		// setBounds(POSITION_X, POSITION_Y, FRAME_WIDTH, FRAME_HEIGHT);

		// centre the window on screen
		setLocationRelativeTo(null);
		// the window is not re-sizable
		setResizable(false);

	}
}
