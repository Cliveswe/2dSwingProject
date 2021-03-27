package com.cliveleddy.gmail.user_interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <h1>Class MyStatusBar</h1> Create a status bar containing the mouse
 * coordinates and the colour of a selected shape.
 * <p>
 * Added a listener class as an inner class. This class catches an event object
 * and extracts the mouse coordinates as a x, y pair. Then it calls the methods
 * to update the GUI.
 * 
 * @author Clive Leddy
 * @version 1.1
 */

public class MyStatusBar extends JPanel {
	private static final long serialVersionUID = -1185694110633572898L;

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

	private static Color STATUS_BAR_BACKGROUND_COLOUR = Color.LIGHT_GRAY;

	/**
	 * An instance of the mouse coordinate object that displays the mouse
	 * coordinates in the status bar.
	 */
	private MyMouseCoordinates myMouseCoordinates = new MyMouseCoordinates(
			getText(SBTextEnum.MOUSE_COORDINATES_TITLE) + getText(SBTextEnum.END));
	/**
	 * An instance of the shape colour object that displays the colour selected from
	 * the tool bar and displays the choice in the status bar.
	 */
	private MyShapeColour myShapeColour = new MyShapeColour(
			getText(SBTextEnum.SELECTED_COLOUR) + getText(SBTextEnum.END));

	private IDrawingAreaListener<MyDrawingAreaEvent<Point>> locationOfMouse;

	private IDrawingAreaListener<MyDrawingAreaEvent<Color>> toolbarSelectedColour;

	public MyStatusBar() {
		super();
		locationOfMouse = new CoordinatesOfMouse();
		toolbarSelectedColour = new SelectedColourFromToolbar();

		setLayout(new BorderLayout());

		// add a mouse coordinates class
		add(myMouseCoordinates, BorderLayout.LINE_START);
		// add a selected colour class
		add(myShapeColour, BorderLayout.LINE_END);
		setBackground(STATUS_BAR_BACKGROUND_COLOUR);
	}

	/**
	 * Get the reference to the instance of object myMouseCoordinates.
	 * 
	 * @return a reference to the object of myMouseCoordinates as type
	 *         MyMouseCoordinates.
	 */
	public MyMouseCoordinates getMyMouseCoordinates() {
		return myMouseCoordinates;
	}

	/**
	 * Get the reference to the instance of object MyShapeColour.
	 * 
	 * @return a reference to the object of myShapeColour as type
	 *         MyMouseCoordinates.
	 */
	public MyShapeColour getMyShapeColour() {
		return myShapeColour;
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
	 * Get the mouse location listener.
	 * 
	 * @return the class that is listening for an event.
	 */
	public IDrawingAreaListener<MyDrawingAreaEvent<Point>> getMouseLocationListener() {
		return locationOfMouse;
	}

	/**
	 * Get the mouse location listener.
	 * 
	 * @return the class that is listening for an event.
	 */
	public IDrawingAreaListener<MyDrawingAreaEvent<Color>> getColourSelectedListener() {
		return toolbarSelectedColour;
	}

	/**
	 * <h1>Implement the interface class IDrawingAreaListener that is used to catch
	 * an instance of the MyDrawingAreaEvent event.</h1> Then set the mouse
	 * coordinates as string pair.
	 * 
	 * @author Clive Leddy
	 * @version 1.0
	 */
	class CoordinatesOfMouse implements IDrawingAreaListener<MyDrawingAreaEvent<Point>> {

		/**
		 * 
		 * Query the event object and extract the coordinates of the mouse. Convert the
		 * mouse x and y coordinates to a String. Pass the string coordinates to the
		 * MyMouseCoordinates class.
		 * 
		 * @param event an event object of type MyDrawingAreaEvent.
		 * 
		 */
		@Override
		public void drawingAreaEventOccurred(MyDrawingAreaEvent<Point> event) {

			if (event.getData() != null) {
				getMyMouseCoordinates().setMouseCoordinates(Integer.toString(event.getData().x),
						Integer.toString(event.getData().y));
			} else {
				getMyMouseCoordinates().setMouseCoordinates(null, null);
			}
		}
	}

	/**
	 * <h1>Implement the interface class IDrawingAreaListener that is used to catch
	 * an instance of the MyDrawingAreaEvent event.</h1> Then set the shape colour
	 * to the colour selected from the tool bar.
	 * 
	 * @author Clive Leddy
	 * @version 1.0
	 */
	class SelectedColourFromToolbar implements IDrawingAreaListener<MyDrawingAreaEvent<Color>> {

		/**
		 * 
		 * Query the event object and extract the coordinates of the mouse. Convert the
		 * mouse x and y coordinates to a String. Pass the string coordinates to the
		 * MyMouseCoordinates class.
		 * 
		 * @param event an event object of type MyDrawingAreaEvent.
		 * 
		 */
		@Override
		public void drawingAreaEventOccurred(MyDrawingAreaEvent<Color> event) {

			if (event.getData() != null) {

				getMyShapeColour().setShapeBackgroundColour(event.getData());

			} else {
				getMyShapeColour().setShapeBackgroundColour(Color.WHITE);
			}
		}
	}

	/**
	 * <h1>Abstract class that set the layout type and opaque.</h1>
	 * 
	 * @author Clive Leddy
	 * @version 1.0
	 *
	 */
	abstract class MyStatusBarComponents extends JPanel {
		private static final long serialVersionUID = -586670725549199998L;

		public MyStatusBarComponents() {
			super();
			setLayout(new FlowLayout());
			setOpaque(false);
		}
	}

	/**
	 * </h1>Display the mouse coordinates as x,y.</h1>
	 * 
	 * @author Clive Leddy
	 * @version 1.0
	 *
	 */
	public class MyMouseCoordinates extends MyStatusBarComponents {
		private static final long serialVersionUID = 6077525360115010702L;

		private JLabel title;
		private JLabel coordinates;

		public MyMouseCoordinates(String title) {
			super();
			this.title = new JLabel(title);
			// display mouse coordinates.
			coordinates = new JLabel();
			// initialise the coordinates to null.
			setMouseCoordinates(null, null);
			add(this.title);
			add(coordinates);
		}

		/**
		 * Set the mouse coordinates in the status field. Both coordinates have to non
		 * null or the coordinates are displayed as a blank String.
		 * 
		 * @param xCoordinate x coordinate as a String.
		 * @param yCoordinate y coordinate as a String.
		 */
		public void setMouseCoordinates(String xCoordinate, String yCoordinate) {
			if ((xCoordinate != null) && (yCoordinate != null)) {
				coordinates.setText(xCoordinate + "," + yCoordinate);
			} else {
				coordinates.setText("");
			}
		}

	}

	/**
	 * <h1>Show the selected colour.</h1>
	 * <p>
	 * Add the method that changes the label background colour.
	 * 
	 * @author Clive Leddy
	 * @version 1.1
	 *
	 */
	class MyShapeColour extends MyStatusBarComponents {
		private static final long serialVersionUID = 7172717267359201923L;
		private JLabel imageLabel;

		public MyShapeColour(String title) {
			super();
			add(new JLabel(title));

			imageLabel = new JLabel();
			imageLabel.setOpaque(true);
			imageLabel.setBackground(null);
			imageLabel.setPreferredSize(new Dimension(20, 20));
			setShapeBackgroundColour(STATUS_BAR_BACKGROUND_COLOUR);
			// add(new JLabel("image"));
			add(imageLabel);
		}

		/**
		 * Set the background colour of the imageLabel.
		 * 
		 * @param background colour c as type Color.
		 */
		public void setShapeBackgroundColour(Color c) {
			imageLabel.setBackground(c);
		}
	}
}
