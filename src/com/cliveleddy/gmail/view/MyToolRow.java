package com.cliveleddy.gmail.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import com.cliveleddy.gmail.model.Circle;
import com.cliveleddy.gmail.model.Rectangle;
import com.cliveleddy.gmail.model.Shape;

/**
 * <h1>Tool bar.</h1> The tool bar contains controls to select a colour and the
 * shape to draw.
 * <p>
 * <h2>Step 6</h2> A big change has occurred. This class, was an inner class,
 * has been move from the class MyDrawingArea. The move was motivated by the
 * need to add additional logic. Replaced both coloursEnum enum and colours
 * HashMap with the enum class ToolRowEnum. ToolRowEnum has additional logic and
 * is now more compact and easier to maintain. Replaced the drawingTools array
 * with the DrawingToolsEnum Enum class. This will make the class more usable.
 * Replaced the array drawingTools with a enum class DrawingToolsEnum. Added
 * additional logic to the JComboBox drawingToolComboBox. When a selection is
 * made all the listeners to this source will be informed that a selection has
 * been made. A new empty shape is created and sent to the listeners.
 * 
 * @author Clive Leddy
 * @version 2.2
 *
 */
class MyToolRow extends JPanel implements ActionListener {
	private static final long serialVersionUID = -7271714906549483725L;

	/**
	 * A Enum class that contains the names of the drawing tools. This class has
	 * additional logic for looking up an class constant and returning it as a
	 * String. In addition, this class can return all the constants as an array of
	 * Strings.
	 * 
	 * @author Clive Leddy
	 * @version 1.0
	 *
	 */
	public static enum DrawingToolsEnum {
		SELECT_SHAPE("> Select shape <"), CIRCLE("Circle."), RECTANGLE("Rectangle.");

		private final String label;

		private static final ArrayList<String> drawingTools = new ArrayList<String>();

		private DrawingToolsEnum(String label) {
			this.label = label;
		}

		public String label() {
			return label;
		}

		static {
			for (DrawingToolsEnum e : DrawingToolsEnum.values()) {
				drawingTools.add(e.label);
			}
		}

		public static String[] getDrawingToolsArray() {
			return drawingTools.toArray(new String[drawingTools.size()]);
		}
	}

	/**
	 * A collection of colours of type Color.
	 * 
	 * @author Clive leddy
	 * @version 1.0
	 *
	 */
	public static enum ToolRowEnum {
		RED(Color.RED), GREEN(Color.GREEN), BLUE(Color.BLUE), BLACK(Color.BLACK), YELLOW(Color.YELLOW),
		CYAN(Color.CYAN);

		private final Color label;

		/**
		 * Enum class lookup function.
		 */
		public static final Map<ToolRowEnum, Color> sorted = new TreeMap<ToolRowEnum, Color>();

		static {
			for (ToolRowEnum e : ToolRowEnum.values()) {
				sorted.put(e, e.label);
			}
		}

		private ToolRowEnum(Color label) {
			this.label = label;
		}

		public Color label() {
			return label;
		}

		public static final int length() {
			return ToolRowEnum.values().length;
		}
	}

	public static int TOOL_BAR_MIN_HEIGHT = 35;

	// a list of listeners for the tool bar shape selection
	private EventListenerList toolbarShapeSelectListenerList = new EventListenerList();

	// a list of listeners for the tool bar colour selection
	private EventListenerList toolbarColourSelectListenerList = new EventListenerList();

	public MyToolRow() {

		super();

		// setLayout(new FlowLayout());
		setPreferredSize(new Dimension(TOOL_BAR_MIN_HEIGHT, 35));

		setLayout(new BorderLayout());

		// use minWidth for the JComboBox!!
		add(drawingToolComboBox(), BorderLayout.LINE_END);

		add(new ColourChoices(), BorderLayout.CENTER);
	}

	/**
	 * Create a combo box that contains the names of the tools for drawing shapes.
	 * 
	 * @return a list of tool names as a JComboBox
	 */
	private JComboBox<String> drawingToolComboBox() {

		JComboBox<String> drawingTool;

		// drawingTool = new JComboBox<>(drawingTools);
		drawingTool = new JComboBox<>(DrawingToolsEnum.getDrawingToolsArray());

		// display 3 rows
		drawingTool.setMaximumRowCount(DrawingToolsEnum.getDrawingToolsArray().length);

		// don't preselect an combo box item
		drawingTool.setSelectedIndex(0);

		// listen for a combo box selection.
		drawingTool.addActionListener(this);

		return drawingTool;
	}

	/**
	 * A shape has been selected inform all the listeners.
	 * 
	 * @param <T>   generic type parameter of type Shape.
	 * 
	 * @param event mle object as MyDrawingAreaEvent
	 */
	@SuppressWarnings("unchecked")
	private void firedShapeSelectedEvent(MyDrawingAreaEvent<Shape> mle) {

		Object[] listerners = toolbarShapeSelectListenerList.getListenerList();

		for (int index = 0; index < listerners.length; index += 2) {

			if (listerners[index] == IDrawingAreaListener.class) {

				((IDrawingAreaListener<MyDrawingAreaEvent<Shape>>) listerners[index + 1]).drawingAreaEventOccurred(mle);
			}
		}
	}

	/**
	 * A colour has been selected inform all the listeners.
	 * 
	 * @param <T>   generic type parameter of type Color.
	 * 
	 * @param event mle object as MyDrawingAreaEvent
	 */
	@SuppressWarnings("unchecked")
	private void firedColourSelectedEvent(MyDrawingAreaEvent<Color> mle) {

		Object[] listerners = toolbarColourSelectListenerList.getListenerList();

		for (int index = 0; index < listerners.length; index += 2) {

			if (listerners[index] == IDrawingAreaListener.class) {

				((IDrawingAreaListener<MyDrawingAreaEvent<Color>>) listerners[index + 1]).drawingAreaEventOccurred(mle);
			}
		}
	}

	/**
	 * Listen for an event from the source object.
	 * 
	 * @param e is the event from the source event as type ActionEvent.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// Get the source of the event.
		JComboBox<?> jb = (JComboBox<?>) e.getSource();

		// A broker that determines a shape to use.
		Broker broker = new Broker();

		broker.addShape(DrawingToolsEnum.CIRCLE.label, new Circle(0, 0, null));

		broker.addShape(DrawingToolsEnum.RECTANGLE.label, new Rectangle(0, 0, null));

		// Set the state of this class to use shape from the broker given the event
		// source.
		if (broker.getShape((String) jb.getSelectedItem()) != null) {

			firedShapeSelectedEvent(
					new MyDrawingAreaEvent<Shape>(this, broker.getShape((String) jb.getSelectedItem())));
		} else {
			firedShapeSelectedEvent(new MyDrawingAreaEvent<Shape>(this, null));
		}
	}

	/**
	 * Add a listener that is listening for a shape selection from the tool bar.
	 * 
	 * @param a reference to a listener of type IDrawingAreaListener
	 */
	public void addToolbarShapeSelectedListener(IDrawingAreaListener<MyDrawingAreaEvent<Shape>> listener) {

		toolbarShapeSelectListenerList.add(IDrawingAreaListener.class, listener);
	}

	/**
	 * Remove a listener that is listening for a shape selection from the tool bar.
	 * 
	 * @param a reference to a listener of type IDrawingAreaListener.
	 */
	public void removeToolbarShapeSelectedListener(IDrawingAreaListener<MyDrawingAreaEvent<Shape>> listener) {

		toolbarShapeSelectListenerList.remove(IDrawingAreaListener.class, listener);
	}

	/**
	 * Add a listener that is listening for a colour selection from the tool bar.
	 * 
	 * @param a reference to a listener of type IDrawingAreaListener
	 */
	public void addToolbarColourSelectedListener(IDrawingAreaListener<MyDrawingAreaEvent<Color>> listener) {

		toolbarColourSelectListenerList.add(IDrawingAreaListener.class, listener);
	}

	/**
	 * Remove a listener that is listening for a colour selection from the tool bar.
	 * 
	 * @param a reference to a listener of type IDrawingAreaListener.
	 */
	public void removeToolbarColourSelectedListener(IDrawingAreaListener<MyDrawingAreaEvent<Color>> listener) {

		toolbarColourSelectListenerList.remove(IDrawingAreaListener.class, listener);
	}

	/**
	 * A container that holds shapes as values and can be indexed with a key.
	 * 
	 * @author Clive Leddy
	 * @version 1.0
	 */
	public class Broker {
		private Map<String, Shape> selectShapes = new HashMap<String, Shape>();

		/**
		 * Add a shape to the container with a key that identifies it.
		 * 
		 * @param key   as an identifier of type String.
		 * @param shape of type Shape.
		 */
		public void addShape(String key, Shape shape) {
			selectShapes.put(key, shape);
		}

		/**
		 * Get a Shape.
		 * 
		 * @param key the identifier of type String.
		 * @return shape of type Shape.
		 */
		public Shape getShape(String key) {
			if (selectShapes.containsKey(key)) {
				return selectShapes.get(key);
			}
			return null;
		}

	}

	/**
	 * <h1>Inner class that populates the tool bar with a list of buttons with
	 * different background colours.</h1>
	 * <p>
	 * Added a mouse adapter to listen for a click event on a JLabel. This click
	 * event look for the background colour of the JLabel.
	 * 
	 * @author Clive Leddy
	 * @version 1.1
	 *
	 */
	class ColourChoices extends JPanel {

		private static final long serialVersionUID = 2937968855860466342L;

		private int rowNumber = 1;

		// private HashMap<coloursEnum, JButton> ColourTool = new HashMap<>();
		private Map<ToolRowEnum, JLabel> colourTool = new HashMap<>();

		public ColourChoices() {

			super();

			// add a grid layout to the panel
			setLayout(new GridLayout(rowNumber, ToolRowEnum.length()));

			// for each colour index, k and colour Color, v.
			ToolRowEnum.sorted.forEach((k, v) -> {

				// create a label with the background colour defined by v.
				JLabel colourSelectorLabel = new JLabel();

				// add a mouse adapter to listen for a click event.
				colourSelectorLabel.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {

						firedColourSelectedEvent(
								new MyDrawingAreaEvent<Color>(this, colourSelectorLabel.getBackground()));
					}
				});

				// set the label to opaque and to a background colour
				colourSelectorLabel.setOpaque(true);

				colourSelectorLabel.setBackground(v);

				// add the label to the list of labels using the index, k.
				colourTool.put(k, colourSelectorLabel);

				// add the label to the grid.
				add(colourTool.get(k));
			});
		}
	}

}