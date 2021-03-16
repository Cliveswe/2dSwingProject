package com.cliveleddy.gmail.user_interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Create a status bar containing the mouse coordinates and the colour of a
 * selected shape.
 * 
 * @author Clive Leddy
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MyStatusBar extends JPanel {

	/**
	 * The key values to get a text description.
	 */
	public static enum SBTextEnum {
		COORDINATES, SELECT_COLOUR, END, MOUSE_COORDINATES_TITLE, SELECTED_COLOUR
	};

	/**
	 * A dictionary that is indexed by a key.
	 */
	public static Map<SBTextEnum, String> Text = Map.ofEntries(Map.entry(SBTextEnum.COORDINATES, "Coordinates"),
			Map.entry(SBTextEnum.SELECT_COLOUR, "Select colour"), Map.entry(SBTextEnum.END, ":"),
			Map.entry(SBTextEnum.MOUSE_COORDINATES_TITLE, "Coordinates"),
			Map.entry(SBTextEnum.SELECTED_COLOUR, "Selected colour"));

	public MyStatusBar() {
		super();
		setLayout(new BorderLayout());
		// add a mouse coordinates class
		add(new MyMouseCoordinates(getText(SBTextEnum.MOUSE_COORDINATES_TITLE) + getText(SBTextEnum.END), "0", "0"),
				BorderLayout.LINE_START);
		// add a selected colour class
		add(new MyShapeColour(getText(SBTextEnum.SELECTED_COLOUR) + getText(SBTextEnum.END)), BorderLayout.LINE_END);
		setBackground(Color.LIGHT_GRAY);
	}

	/**
	 * Get a text description.
	 * 
	 * @param key to index the container that holds the text description as Enum.
	 * @return a String.
	 */
	private String getText(SBTextEnum key) {
		return Text.get(key);
	}

	/**
	 * Abstract class that set the layout type and opaque.
	 * 
	 * @author Clive Leddy
	 * @version 1.0
	 *
	 */
	abstract class MyStatusBarComponents extends JPanel {
		public MyStatusBarComponents() {
			super();
			setLayout(new FlowLayout());
			setOpaque(false);
		}
	}

	/**
	 * Display the mouse coordinates as x, y.
	 * 
	 * @author Clive Leddy
	 * @version 1.0
	 *
	 */
	class MyMouseCoordinates extends MyStatusBarComponents {

		public MyMouseCoordinates(String title, String dummyValue1, String dummyValue2) {
			super();
			add(new JLabel(title));
			add(new JLabel(dummyValue1 + "," + dummyValue2));

		}
	}

	/**
	 * Show the selected colour.
	 * 
	 * @author Clive Leddy
	 * @version 1.0
	 *
	 */
	class MyShapeColour extends MyStatusBarComponents {

		public MyShapeColour(String title) {
			super();
			add(new JLabel(title));

			// TODO code for this label will be modified to show a shape instead
			JLabel imageLabel = new JLabel();
			imageLabel.setOpaque(true);
			imageLabel.setBackground(Color.GREEN);
			imageLabel.setPreferredSize(new Dimension(20, 20));
			// add(new JLabel("image"));
			add(imageLabel);

		}
	}

}
