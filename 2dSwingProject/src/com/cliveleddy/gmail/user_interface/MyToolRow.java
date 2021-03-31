package com.cliveleddy.gmail.user_interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

/**
 * <h1>Tool bar.</h1> The tool bar contains controls to select a colour and the
 * shape to draw.
 * <p>
 * <h2>Step 6</h2> A big change has occurred. This class, was an inner class,
 * has been move from the class MyDrawingArea. The move was motivated by the
 * need to add additional logic.
 * 
 * @author Clive Leddy
 * @version 2.0
 *
 */
class MyToolRow extends JPanel {
	private static final long serialVersionUID = -7271714906549483725L;
	// names of the drawing tools.
	public static String drawingTools[] = { "Circle", "Rectangle" };

	// keys to get colours from Color.
	public static enum coloursEnum {
		RED, GREEN, BLUE, BLACK, YELLOW, CYAN
	};

	// colours used in the tool bar.
	public static Map<coloursEnum, Color> Colours = new HashMap<>() {
		private static final long serialVersionUID = -1344271642148893155L;

		{
			put(coloursEnum.RED, Color.RED);
			put(coloursEnum.GREEN, Color.GREEN);
			put(coloursEnum.BLUE, Color.BLUE);
			put(coloursEnum.BLACK, Color.BLACK);
			put(coloursEnum.YELLOW, Color.YELLOW);
			put(coloursEnum.CYAN, Color.CYAN);
		}
	};

	public static int TOOL_BAR_MIN_HEIGHT = 35;

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

		drawingTool = new JComboBox<>(drawingTools);
		// display 3 rows
		drawingTool.setMaximumRowCount(3);
		// don't preselect an combo box item
		drawingTool.setSelectedIndex(0);
		return drawingTool;
	}

	/**
	 * The mouse has moved inform all the listeners.
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
		private Map<coloursEnum, JLabel> ColourTool = new HashMap<>();

		public ColourChoices() {
			super();
			// add a grid layout to the panel
			setLayout(new GridLayout(rowNumber, Colours.size()));
			// for each colour index, k and colour Color, v.
			Colours.forEach((k, v) -> {
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
				ColourTool.put(k, colourSelectorLabel);
				// add the label to the grid.
				add(ColourTool.get(k));
			});
		}
	}

}