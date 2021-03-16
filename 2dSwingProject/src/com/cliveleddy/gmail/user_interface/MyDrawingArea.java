package com.cliveleddy.gmail.user_interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Create a drawing panel the contains a tool bar and a drawing area. The tool
 * bar contains controls for drawing shapes with a selected colour. The drawing
 * ares is where the shape is drawn.
 * 
 * @author Clive Leddy
 *
 */
@SuppressWarnings("serial")
public class MyDrawingArea extends JPanel {
	public static int TOOL_BAR_MIN_HEIGHT = 35;

	// keys to get colours from Color.
	public static enum coloursEnum {
		RED, GREEN, BLUE, BLACK, YELLOW, CYAN
	}

	// colours used in the tool bar.
	public static HashMap<coloursEnum, Color> Colours = new HashMap<>() {
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

	public MyDrawingArea(JFrame myGUIContainer) {
		super();
		setLayout(new BorderLayout());
		add(new MyToolRow(), BorderLayout.PAGE_START);
		add(new MyDrawingPanel(), BorderLayout.CENTER);
	}

	/**
	 * Inner class that creates a tool bar. The tool bar contains controls to select
	 * a colour and the shape to draw.
	 * 
	 * @author Clive Leddy
	 *
	 */
	class MyToolRow extends JPanel {

		/**
		 * Inner class that populates the tool bar a list of buttons with different
		 * background colours.
		 * 
		 * @author Clive Leddy
		 *
		 */
		class ColourChoices extends JPanel {
			private int rowNumber = 1;
			// private HashMap<coloursEnum, JButton> ColourTool = new HashMap<>();
			private HashMap<coloursEnum, JLabel> ColourTool = new HashMap<>();

			public ColourChoices() {
				super();

				// add a grid layout to the panel
				setLayout(new GridLayout(rowNumber, Colours.size()));
				// for each colour index, k and colour Color, v.
				MyDrawingArea.Colours.forEach((k, v) -> {
					// create a label with the background colour defined by v.
					JLabel colourSelectorLabel = new JLabel();
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
			 * @return int.
			 */
			public int getRowNumber() {
				return rowNumber;
			}

		}

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
			drawingTool.setSelectedIndex(-1);
			return drawingTool;
		}
	}

	/**
	 * The drawing area for a shape.
	 * 
	 * @author Clive Leddy
	 *
	 */
	class MyDrawingPanel extends JPanel {
		public MyDrawingPanel() {
			super();
			// Temporary code to set the background colour for testing.
			setBackground(Color.WHITE);
			setMinimumSize(new Dimension(200, 200));
			// TODO at a later stage add a free hand drawing area.
		}
	}
}
