package com.cliveleddy.gmail.user_interface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import com.cliveleddy.gmail.model.Drawing;
import com.cliveleddy.gmail.model.IDrawable;
import com.cliveleddy.gmail.model.Shape;

/**
 * <h1>The drawing area for a shape.</h1> Added a MouseMotionAdapter to the
 * JPanel MouseMotionListerner. I wanted to test the Mouse motion adapter and to
 * use this class as a tutorial at a later date.
 * <p>
 * <h2>Step 6</h2> A big change has occurred. This class, was an inner class,
 * has been move from the class MyDrawingArea. The move was motivated by the
 * need to add additional logic. New additional mouse motion events have been
 * added. Logic has been added to a mouse motion when either a button or not is
 * pressed. Additional logic has also been added when a mouse button is either
 * pushed or released.
 * 
 * @author Clive Leddy
 * @version 2.1
 *
 */
class DrawingPanel extends JPanel {
	private static final long serialVersionUID = 1223323159512490642L;

	private Drawing drawing;

	// a list of listeners for the mouse location.
	private EventListenerList mouseLocationListenerList = new EventListenerList();

	private IDrawingAreaListener<MyDrawingAreaEvent<Color>> toolbarSelectedColour;

	private Color colour;

	private IDrawingAreaListener<MyDrawingAreaEvent<Shape>> toolbarSelectedShape;
	private Shape shape;

	/**
	 * This is an collection of Objects.
	 */
	private List<Object> allShapes = new ArrayList<>();
	/**
	 * Reverse iterator for the ArrayList allShapes.
	 */
	private ListIterator<Object> allShapesIterator = allShapes.listIterator();

	public DrawingPanel() {

		super();

		drawing = new Drawing();

		initialise();
	}

	public DrawingPanel(Drawing d) {

		super();

		drawing = d;

		initialise();
	}

	/**
	 * Initialise the class.
	 */
	private void initialise() {
		toolbarSelectedColour = new SelectedColourFromToolbar();

		colour = null;

		toolbarSelectedShape = new SelectedShapeFromToolbar();

		shape = null;

		setLayout(new FlowLayout());

		setOpaque(false);

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

		}

		// trigger the graphics painting
		repaint();
	}

	/**
	 * Add a drawing to the class drawing instance.
	 * 
	 * @param d a drawing of type Drawing.
	 */
	public void addDrawing(Drawing d) {

		if (d != null) {

			if (drawing != null) {

				while (d.hasNext()) {

					drawing.addShape(d.next());
				}

				setDrawing(drawing);
			}
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

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		if (drawing.getSize() > 0) {

			drawing.draw(g);
		}

		if ((colour != null) && (shape != null)) {

			if (shape.isValid()) {

				shape.draw(g);

			}

			while (allShapesIterator.hasPrevious()) {

				Object s = allShapesIterator.previous();

				if (((Component) s).isValid()) {
					((IDrawable) s).draw(g);
				}
			}
		}

		repaint();
	}

	/**
	 * Add a mouse motion listener to the JPanel. When the mouse moves into, around
	 * and out of the JPanel an MouseEvent is fired.
	 */
	private void setMouseMotionListener() {

		/*
		 * Set the mouse location within the drawing area.
		 */
		addMouseMotionListener(new MouseMotionAdapter() {

			/**
			 * Invoked when the mouse cursor has been moved onto a component but no buttons
			 * have been pushed.
			 */
			public void mouseMoved(MouseEvent me) {

				// Set the current location of the mouse pointer from the even object me Point.
				firedMouseLocationEvent(new MyDrawingAreaEvent<Point>(this, me.getPoint()));
			}

			/**
			 * Invoked when a mouse button is pressed on a component and then dragged.
			 */
			public void mouseDragged(MouseEvent e) {

				// Set the current location of the mouse pointer from the even object me Point.
				firedMouseLocationEvent(new MyDrawingAreaEvent<Point>(this, e.getPoint()));

				if ((colour != null) && (shape != null)) {

					shape.addPoint(e.getX(), e.getY());
				}
			}

		});

		/**
		 * Set the mouse location to null when the mouse exits the drawing area.
		 */
		addMouseListener(new MouseAdapter() {

			/**
			 * Invoked when the mouse exits a component.
			 */
			public void mouseExited(MouseEvent me) {

				// Set the current location of the mouse pointer to null.
				firedMouseLocationEvent(new MyDrawingAreaEvent<Point>(this, null));
			}

			/**
			 * Invoked when a mouse button has been pressed on a component.
			 */
			public void mousePressed(MouseEvent e) {

				if ((colour != null) && (shape != null)) {

					shape.setStartPoint(e.getX(), e.getY());

					shape.addPoint(e.getX(), e.getY());

					shape.setColor("#" + Integer.toHexString(colour.getRGB()).substring(2));
				}
			}

			/**
			 * Invoked when a mouse button has been released on a component.
			 */
			public void mouseReleased(MouseEvent e) {
				if ((colour != null) && (shape != null)) {

					shape.addPoint(e.getX(), e.getY());

					if (drawing != null) {

						drawing.addShape(shape.clone());

					} else {

						allShapes.add(shape.clone());
					}
				}
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

	/**
	 * <h1>Implement the interface class IDrawingAreaListener that is used to catch
	 * an instance of the MyToolRowEvent event.</h1> Then set the shape colour to
	 * the colour selected from the tool bar.
	 * 
	 * @author Clive Leddy
	 * @version 1.0
	 */
	class SelectedColourFromToolbar implements IDrawingAreaListener<MyDrawingAreaEvent<Color>> {

		/**
		 * 
		 * Query the event object and extract the selected color.
		 * 
		 * @param event an event object of type MyDrawingAreaEvent.
		 * 
		 */
		@Override
		public void drawingAreaEventOccurred(MyDrawingAreaEvent<Color> event) {

			if (event.getData() != null) {

				colour = event.getData();
			}
		}
	}

	/**
	 * Get the selected colour listener.
	 * 
	 * @return the class that is listening for an event.
	 */
	public IDrawingAreaListener<MyDrawingAreaEvent<Color>> getColourSelectedListener() {

		return toolbarSelectedColour;
	}

	/**
	 * <h1>Implement the interface class IDrawingAreaListener that is used to catch
	 * an instance of the MyToolRowEvent event.</h1> Then set the shape colour to
	 * the colour selected from the tool bar.
	 * 
	 * @author Clive Leddy
	 * @version 1.0
	 */
	class SelectedShapeFromToolbar implements IDrawingAreaListener<MyDrawingAreaEvent<Shape>> {

		/**
		 * 
		 * Query the event object and extract the shape selected. Convert the
		 * 
		 * @param event an event object of type MyDrawingAreaEvent.
		 * 
		 */
		@Override
		public void drawingAreaEventOccurred(MyDrawingAreaEvent<Shape> event) {

			if (event.getData() != null) {

				shape = event.getData();
			} else {
				shape = null;
			}
		}
	}

	/**
	 * Get the selected shape listener.
	 * 
	 * @return the class that is listening for an event.
	 */
	public IDrawingAreaListener<MyDrawingAreaEvent<Shape>> getShapeSelectedListener() {

		return toolbarSelectedShape;
	}
}