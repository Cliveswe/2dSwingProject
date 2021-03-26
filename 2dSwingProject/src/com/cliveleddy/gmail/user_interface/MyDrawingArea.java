package com.cliveleddy.gmail.user_interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

/**
 * <h1>Create a drawing panel the contains a tool bar and a drawing area.</h1>
 * The tool bar contains controls for drawing shapes with a selected colour. The
 * drawing ares is where the shape is drawn.
 * 
 * @author Clive Leddy
 * @version 1.0
 *
 */

public class MyDrawingArea extends JPanel {
	private static final long serialVersionUID = -6320295967491559522L;
	private JPanel drawingPanel, toolRow;
	public static int TOOL_BAR_MIN_HEIGHT = 35;

	// keys to get colours from Color.
	public static enum coloursEnum {
		RED, GREEN, BLUE, BLACK, YELLOW, CYAN
	};

	// colours used in the tool bar.
	public static HashMap<coloursEnum, Color> Colours = new HashMap<>() {
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
	// names of the drawing tools.
	public static String drawingTools[] = { "Circle", "Rectangle" };

	// a list of listeners for the mouse location.
	private EventListenerList mouseLocationListenerList = new EventListenerList();

	public MyDrawingArea() {
		super();

		drawingPanel = new MyDrawingPanel();
		toolRow = new MyToolRow();

		setLayout(new BorderLayout());
		// add(new MyToolRow(), BorderLayout.PAGE_START);
		add(toolRow, BorderLayout.PAGE_START);
		// add(new MyDrawingPanel(), BorderLayout.CENTER);
		add(drawingPanel, BorderLayout.CENTER);

	}

	/**
	 * The mouse has moved inform all the listeners.
	 * 
	 * @param event mle object as MouseLocationEvent
	 */
	public void firedMouseLocationEvent(MouseLocationEvent mle) {
		Object[] listerners = mouseLocationListenerList.getListenerList();

		for (int index = 0; index < listerners.length; index += 2) {
			if (listerners[index] == IMouseLocationListener.class) {
				((IMouseLocationListener) listerners[index + 1]).mouseLocationEventOccurred(mle);
			}
		}
	}

	/**
	 * Add a listener that is listening for a change in the mouse location.
	 * 
	 * @param a reference to a listener of type IMouseLocationListener.
	 */
	public void addMouseLocationListener(IMouseLocationListener listener) {
		mouseLocationListenerList.add(IMouseLocationListener.class, listener);
	}

	/**
	 * Remove a listener that is listening for a change in the mouse location.
	 * 
	 * @param a reference to a listener of type IMouseLocationListener.
	 */
	public void removeIMouseLocationListener(IMouseLocationListener listener) {
		mouseLocationListenerList.remove(IMouseLocationListener.class, listener);
	}

	/**
	 * Set the current location of the mouse pointer.
	 * 
	 * @param locationPoint as a Point
	 */
	public void setMousePointerLocation(Point locationPoint) {
		firedMouseLocationEvent(new MouseLocationEvent(this, locationPoint));
	}

	/**
	 * <h1>Inner class that creates a tool bar.</h1> The tool bar contains controls
	 * to select a colour and the shape to draw.
	 * 
	 * @author Clive Leddy
	 * @version 1.0
	 *
	 */
	class MyToolRow extends JPanel {
		private static final long serialVersionUID = -7271714906549483725L;

		public MyToolRow() {
			super();
			setPreferredSize(new Dimension(0, TOOL_BAR_MIN_HEIGHT));
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
			private HashMap<coloursEnum, JLabel> ColourTool = new HashMap<>();

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
							System.out.println(colourSelectorLabel.getBackground().toString());
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

			/**
			 * Number of rows.
			 * 
			 * @return row number as an integer.
			 */
			public int getRowNumber() {
				return rowNumber;
			}

		}

	}

	/**
	 * <h1>The drawing area for a shape.</h1>
	 * <p>
	 * Added a MouseMotionAdapter to the JPanel MouseMotionListerner. I wanted to
	 * test the Mouse motion adapter and to use this class as a tutorial at a later
	 * date.
	 * 
	 * @author Clive Leddy
	 * @version 1.1
	 *
	 */
	class MyDrawingPanel extends JPanel {
		private static final long serialVersionUID = 1223323159512490642L;

		public MyDrawingPanel() {
			super();
			// set the mouse location within the drawing area
			addMouseMotionListener(new MouseMotionAdapter() {
				public void mouseMoved(MouseEvent me) {
					// setMousePointerLocation to the event object me Point;
					setMousePointerLocation(me.getPoint());
				}
			});
			// set the mouse location to null when the mouse exits the drawing area
			addMouseListener(new MouseAdapter() {
				public void mouseExited(MouseEvent me) {
					// setMousePointerLocation to a null;
					setMousePointerLocation(null);
				}
			});

			// Temporary code to set the background colour for testing.
			setBackground(Color.WHITE);
			setMinimumSize(new Dimension(200, 200));
			// TODO at a later stage add a free hand drawing area.
		}
	}
}
