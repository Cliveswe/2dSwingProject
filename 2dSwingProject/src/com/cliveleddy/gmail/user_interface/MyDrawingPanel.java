package com.cliveleddy.gmail.user_interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

/**
 * <h1>The drawing area for a shape.</h1> Added a MouseMotionAdapter to the
 * JPanel MouseMotionListerner. I wanted to test the Mouse motion adapter and to
 * use this class as a tutorial at a later date.
 * <p>
 * <h2>Step 6</h2> A big change has occurred. This class, was an inner class,
 * has been move from the class MyDrawingArea. The move was motivated by the
 * need to add additional logic.
 * 
 * @author Clive Leddy
 * @version 2.0
 *
 */
class MyDrawingPanel extends JPanel {
	private static final long serialVersionUID = 1223323159512490642L;

	// a list of listeners for the mouse location.
	private EventListenerList mouseLocationListenerList = new EventListenerList();

	public MyDrawingPanel() {
		super();
		setMouseMotionListener();

		// Temporary code to set the background colour for testing.
		setBackground(Color.WHITE);
		setMinimumSize(new Dimension(200, 200));
		// TODO at a later stage add a free hand drawing area.
	}

	/**
	 * Add a mouse motion listener to the JPanel. When the mouse moves into, around
	 * and out of the JPanel an MouseEvent is fired.
	 */
	private void setMouseMotionListener() {
		// set the mouse location within the drawing area
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent me) {
				// setMousePointerLocation to the event object me Point;
				// setMousePointerLocation(me.getPoint());
				// Set the current location of the mouse pointer from the even object.
				firedMouseLocationEvent(new MyDrawingAreaEvent<Point>(this, me.getPoint()));
			}
		});
		// set the mouse location to null when the mouse exits the drawing area
		addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent me) {
				// setMousePointerLocation to a null;
				// setMousePointerLocation(null);
				// Set the current location of the mouse pointer to null.
				firedMouseLocationEvent(new MyDrawingAreaEvent<Point>(this, null));
			}
		});
	}

	/**
	 * The mouse has moved inform all the listeners.
	 * 
	 * @param <T>
	 * 
	 * @param event mle object as MyDrawingAreaEvent
	 */
	@SuppressWarnings("unchecked")
	private void firedMouseLocationEvent(MyDrawingAreaEvent<Point> mle) {
		Object[] listerners = mouseLocationListenerList.getListenerList();

		for (int index = 0; index < listerners.length; index += 2) {
			if (listerners[index] == IDrawingAreaListener.class) {
				((IDrawingAreaListener<MyDrawingAreaEvent<Point>>) listerners[index + 1]).drawingAreaEventOccurred(mle);
			}
		}
	}

	/**
	 * Add a listener that is listening for a change in the mouse location.
	 * 
	 * @param a reference to a listener of type IDrawingAreaListener.
	 */
	public void addMouseLocationListener(IDrawingAreaListener<MyDrawingAreaEvent<Point>> listener) {
		mouseLocationListenerList.add(IDrawingAreaListener.class, listener);
	}

	/**
	 * Remove a listener that is listening for a change in the mouse location.
	 * 
	 * @param a reference to a listener of type IDrawingAreaListener.
	 */
	public void removeIMouseLocationListener(IDrawingAreaListener<Point> listener) {
		mouseLocationListenerList.remove(IDrawingAreaListener.class, listener);
	}

}