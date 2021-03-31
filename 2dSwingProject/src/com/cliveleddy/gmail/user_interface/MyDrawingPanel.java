package com.cliveleddy.gmail.user_interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import com.cliveleddy.gmail.model.Drawing;

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

	private Drawing drawing;

	// a list of listeners for the mouse location.
	private EventListenerList mouseLocationListenerList = new EventListenerList();

	public MyDrawingPanel() {
		super();
		drawing = new Drawing();
		initialise();
	}

	public MyDrawingPanel(Drawing d) {
		super();
		drawing = d;
		initialise();
	}

	/**
	 * Initialise the class.
	 */
	private void initialise() {
		setLayout(new FlowLayout());
		setMouseMotionListener();
		setBackground(Color.WHITE);
		setMinimumSize(new Dimension(200, 200));
	}

	/**
	 * Set the class field to a new drawing then render it.
	 * 
	 * @param d an object containing a drawing of type Drawing.
	 */
	public void setDrawing(Drawing d) {
		if (d != null) {
			drawing = d;
			// TODO render drawing d.
		}
	}

	/**
	 * Get the current drawing.
	 * 
	 * @return a drawing of type Drawing.
	 */
	public Drawing getDrawing() {
		return drawing;
	}

	/**
	 * Add a mouse motion listener to the JPanel. When the mouse moves into, around
	 * and out of the JPanel an MouseEvent is fired.
	 */
	private void setMouseMotionListener() {
		// set the mouse location within the drawing area
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent me) {
				// Set the current location of the mouse pointer from the even object me Point.
				firedMouseLocationEvent(new MyDrawingAreaEvent<Point>(this, me.getPoint()));
			}
		});
		// set the mouse location to null when the mouse exits the drawing area
		addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent me) {
				// Set the current location of the mouse pointer to null.
				firedMouseLocationEvent(new MyDrawingAreaEvent<Point>(this, null));
			}
		});
	}

	/**
	 * The mouse has moved inform all the listeners.
	 * 
	 * @param <T>   generic type parameter of type Point.
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