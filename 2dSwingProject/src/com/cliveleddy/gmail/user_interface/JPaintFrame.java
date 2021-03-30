package com.cliveleddy.gmail.user_interface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
 * <h2>Step 6</h2> The class MyDrawingArea under went some structural changes.
 * Thus the status bar listener had to be registered in a different method in
 * the drawing area.
 * 
 * @author Clive Leddy
 * @version 2.1
 *
 */
public class JPaintFrame extends JFrame implements IDrawingAreaListener<MyDrawingAreaEvent<Drawing>> {
	private static final long serialVersionUID = -3289281159035541953L;
	private final int FRAME_WIDTH = 800;
	private final int FRAME_HEIGHT = 800;
	private final int FRAME_MIN_WIDTH = 400;
	private final int FRAME_MIN_HEIGHT = 300;

	/**
	 * The key values to get a text description.
	 */
	public static enum UITextEnum {
		TITLE
	};

	/**
	 * A dictionary that is indexed by a key.
	 */
	public static Map<UITextEnum, String> Text = Map.ofEntries(Map.entry(UITextEnum.TITLE, "JPaint"));

	public JPaintFrame() {
		super();
	}

	/**
	 * Get a text description.
	 * 
	 * @param key to index the container that holds the text description as Enum.
	 * @return a String.
	 */
	public static String getText(UITextEnum key) {
		return Text.get(key);
	}

	/**
	 * Set up and initialise the application window.
	 */
	public void Initialise() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SetFrameIcon();
		SetLookAndFeel();
		SetWindowSize();
		SetFrameTitle(getText(UITextEnum.TITLE));
		SetLayout();
		setVisible(true);
	}

	/**
	 * Add an icon to the top left of the JFrame
	 */
	public void SetFrameIcon() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(getClass().getResource("../resource/favicon-32x32.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		setIconImage(img);
	}

	private void SetLayout() {
		MyStatusBar sb = new MyStatusBar();
		MyDrawingArea da = new MyDrawingArea();
		MyMenuBar mb = new MyMenuBar(this);

		// add listeners for the different UI events
		da.getMyDrawingPanel().addMouseLocationListener(sb.getMouseLocationListener());
		da.addToolbarColourSelectedListener(sb.getColourSelectedListener());
		mb.addMenuBarDrawingListener(this);

		setLayout(new BorderLayout());
		// add the menu bar to the layout
		add(mb, BorderLayout.PAGE_START);
		// add the drawing area to the layout.
		add(da, BorderLayout.CENTER);
		// add the status bar to the layout
		add(sb, BorderLayout.PAGE_END);
	}

	private void SetFrameTitle(String frameTitle) {
		// setTitle(getText(UITextEnum.TITLE));
		setTitle(frameTitle);
	}

	private void SetLookAndFeel() {
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

	private void SetWindowSize() {
		// set the size of the window
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		// set the minimum window size
		setMinimumSize(new Dimension(FRAME_MIN_WIDTH, FRAME_MIN_HEIGHT));
		// this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		// setBounds(POSITION_X, POSITION_Y, FRAME_WIDTH, FRAME_HEIGHT);

		// centre the window on screen
		setLocationRelativeTo(null);
		// the window is not re-sizable
		// setResizable(true);

	}

	/**
	 * 
	 * Query the event object and extract the title and author of the drawing.
	 * Construct the title and author in the object drawingAreasEvent to a String.
	 * This string should be amended to the application title.
	 * 
	 * @param event an event object of type MyDrawingAreaEvent.
	 * 
	 */
	@Override
	public void drawingAreaEventOccurred(MyDrawingAreaEvent<Drawing> drawingAreaEvent) {
		Drawing d = null;

		if (drawingAreaEvent != null) {
			d = new Drawing();
			d = drawingAreaEvent.getData();
			if (d != null) {
				updateFrameTitle(d.getName(), d.getAuthor());
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
	 * @param title  the title of the artwork as a String.
	 * @param author the authors name of the artwork as a String.
	 */
	private void updateFrameTitle(String title, String author) {
		String str = getText(UITextEnum.TITLE);

		if (!title.isBlank() && !author.isBlank()) {
			str += " - " + title + " by " + author;
		}
		if (!title.isBlank() && author.isBlank()) {
			str += " - " + title;
		}
		if (title.isBlank() && !author.isBlank()) {
			str += " - " + author;
		}

		SetFrameTitle(str);
	}
}
