package com.cliveleddy.gmail.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.cliveleddy.gmail.controller.MenuBarItemEnum;
import com.cliveleddy.gmail.model.Drawing;

/**
 * <h1>Class JPaintFrame</h1> This is the application container window. It
 * extends the JFrame swing and contains components used in this application.
 * <p>
 * <h1>Version 2</h1> The class has been renamed to JPaintFrame. New click
 * features will be added.
 * <p>
 * This class acts as a controller class wiring up listener event objects to
 * source event objects.
 * <p>
 * <h2>Added an event listener.</h2> Added an event listener listening for the
 * artwork's new or updated title and author. The title and author are used to
 * update the JFrame's title.
 * <p>
 * <h2>Step 6</h2> The class MyDrawingArea has been removed from the
 * application. It was broken down into 2 new classes, DrawingPanel and
 * MyToolRow. The motivation for this change is that the menu tool bars logic
 * will be developed. The wiring for event handling has been modified.
 * 
 * 
 * @author Clive Leddy
 * @version 2.2
 *
 */
public class JPaintFrame extends JFrame implements IDrawingAreaListener<MyDrawingAreaEvent<Drawing>> {

	private static final long serialVersionUID = -3289281159035541953L;

	private final int FRAME_WIDTH = 800;

	private final int FRAME_HEIGHT = 800;

	private final int FRAME_MIN_WIDTH = 400;

	private final int FRAME_MIN_HEIGHT = 300;

	// Drawing Panel
	private DrawingPanel dp;

	/**
	 * The key values to get a text description.
	 * 
	 * @author Clive leddy
	 * @version 1.0
	 *
	 */
	public static enum PaintFrameEnum {

		TITLE("JPaint");

		private final String label;

		private PaintFrameEnum(String label) {

			this.label = label;
		}

		public String label() {

			return label;
		}
	}

	/**
	 * Set up and initialise the application window.
	 */
	public void initialise() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setFrameIcon();

		setLookAndFeel();

		setWindowSize();

		setFrameTitle(PaintFrameEnum.TITLE.label);

		setLayout();

		setVisible(true);
	}

	/**
	 * Add an icon to the top left of the {@code JFrame}.
	 */
	public void setFrameIcon() {

		BufferedImage img = null;
		InputStream stream = null;

		try {

			stream = getClass().getResourceAsStream("../resource/favicon-32x32.png");

			img = ImageIO.read(stream);

		} catch (IOException e) {

			e.printStackTrace();
		}

		setIconImage(img);

		img.flush();

		try {

			stream.close();

		} catch (IOException e) {

			System.err.println("Could not close \"stream\"!");

			e.printStackTrace();
		}
	}

	private void setLayout() {

		MyStatusBar sb = new MyStatusBar();

		dp = new DrawingPanel();

		MyToolRow tr = new MyToolRow();

		MyMenuBar mb = new MyMenuBar(this);

		// add listeners for the different UI events
		dp.addMouseLocationListener(sb.getMouseLocationListener());
		tr.addToolbarColourSelectedListener(sb.getColourSelectedListener());
		tr.addToolbarColourSelectedListener(dp.getColourSelectedListener());
		tr.addToolbarShapeSelectedListener(dp.getShapeSelectedListener());
		mb.addMenuBarDrawingListener(this);

		setLayout(new BorderLayout());

		// add the menu bar to the layout
		add(mb, BorderLayout.PAGE_START);

		// add the drawing area to the layout.
		JPanel drawingArea = new JPanel();

		drawingArea.setLayout(new BorderLayout());

		tr.setPreferredSize(new Dimension(0, MyToolRow.TOOL_BAR_MIN_HEIGHT));

		drawingArea.add(tr, BorderLayout.NORTH);

		drawingArea.add(dp, BorderLayout.CENTER);

		add(drawingArea, BorderLayout.CENTER);

		// add the status bar to the layout
		add(sb, BorderLayout.PAGE_END);
	}

	private void setFrameTitle(String frameTitle) {

		setTitle(frameTitle);
	}

	private void setLookAndFeel() {

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

	private void setWindowSize() {

		// set the size of the window
		setSize(FRAME_WIDTH, FRAME_HEIGHT);

		// set the minimum window size
		setMinimumSize(new Dimension(FRAME_MIN_WIDTH, FRAME_MIN_HEIGHT));

		// centre the window on screen
		setLocationRelativeTo(null);

	}

	public DrawingPanel getDrawingPanel() {

		return dp;
	}

	/**
	 * 
	 * Query the event object and extract the title and author of the drawing.
	 * Construct the title and author in the object drawingAreasEvent to a String.
	 * This string should be amended to the application title.
	 * 
	 * @param event an event object of type {@code MyDrawingAreaEvent}.
	 * 
	 */
	public void drawingAreaEventOccurred(MyDrawingAreaEvent<Drawing> drawingAreaEvent) {

		if (drawingAreaEvent != null) {
			if (drawingAreaEvent.getData() != null) {

				// New Menu
				if (MenuBarItemEnum.NEW.label() == drawingAreaEvent.getId()) {

					dp.setDrawing(drawingAreaEvent.getData());

					updateFrameTitle(dp.getDrawing().getName(), dp.getDrawing().getAuthor());
				}
				// Load Menu
				if (MenuBarItemEnum.LOAD.label() == drawingAreaEvent.getId()) {

					dp.resetDrawingPanel();
					dp.setDrawing(drawingAreaEvent.getData());

					updateFrameTitle(dp.getDrawing().getName(), dp.getDrawing().getAuthor());
				}
				// Edit Name
				if (MenuBarItemEnum.NAME.label() == drawingAreaEvent.getId()) {

					dp.getDrawing().setName(drawingAreaEvent.getData().getName());

					updateFrameTitle(dp.getDrawing().getName(), dp.getDrawing().getAuthor());
				}
				// Edit Author
				if (MenuBarItemEnum.AUTHOR.label() == drawingAreaEvent.getId()) {

					dp.getDrawing().setAuthor(drawingAreaEvent.getData().getAuthor());

					updateFrameTitle(dp.getDrawing().getName(), dp.getDrawing().getAuthor());
				}
				// Edit Undo
				if (MenuBarItemEnum.UNDO.label() == drawingAreaEvent.getId()) {

					dp.getDrawing().removeLastShape();

					dp.setDrawing(dp.getDrawing());

					updateFrameTitle(dp.getDrawing().getName(), dp.getDrawing().getAuthor());
				}

			} else {

				updateFrameTitle("", "");
			}
		} else {

			System.out.println("Something went wrong in drawingAreaEventOccurred method");
		}
	}

	/**
	 * Create a new title for the JFrame title.
	 * 
	 * @param title  the title of the artwork as a {@code String}.
	 * @param author the authors name of the artwork as a {@code String}.
	 */
	private void updateFrameTitle(String title, String author) {

		String str = PaintFrameEnum.TITLE.label;

		if (!title.isBlank() && !author.isBlank()) {

			str += " - " + title + " by " + author;
		}

		if (!title.isBlank() && author.isBlank()) {

			str += " - " + title;
		}

		if (title.isBlank() && !author.isBlank()) {

			str += " - " + author;
		}

		setFrameTitle(str);
	}

}
