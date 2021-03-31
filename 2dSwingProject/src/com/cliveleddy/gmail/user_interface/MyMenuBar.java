package com.cliveleddy.gmail.user_interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.EventListenerList;

import com.cliveleddy.gmail.model.Drawing;

/**
 * <h1>Step 4</h1> Create the applications menu bar and add items to each menu.
 * It extends the JMenuBar swing.
 * <p>
 * <h1>Step 5</h1> Add the action listeners to each of the menu items. These
 * Listeners will in turn perform tasks.
 * 
 * @author Clive Leddy
 * @version 1.1
 */
public class MyMenuBar extends JMenuBar {
	private static final long serialVersionUID = -1336076929035881984L;

	/**
	 * 
	 * <h1>Functional Interface</h1> This is a Java functional interface. It is
	 * implemented by lambda functions.
	 * 
	 * @param <R> return type R.
	 * @param <T> method parameter type T.
	 * 
	 * @author Clive Leddy
	 * @version 1.0
	 *
	 */
	public interface IMyFunctionalInterface<R, T> {
		/**
		 * Functional interface has an abstract method that takes an argument of T and
		 * returns result of type R
		 * 
		 * @param t of type T.
		 * @return type R.
		 */
		public R execute(T t);
	}

	/**
	 * The key values to get a text description.
	 */
	public static enum MBTextEnum {
		FILE, NEW, SAVE_AS, LOAD, EXIT, EDIT, UNDO, NAME, AUTHOR, DOT, NEW_DRAWING_TITLE_PROMPT, NEW_AUTHOR_NAME_PROMPT,
		SAVE_AS_PROMPT, LOAD_PROMPT, COLON, SHAPE, DRAWING_TITLE, DRAWING_AUTHOR
	};

	/**
	 * A dictionary that is indexed by a key.
	 */
	public static Map<MBTextEnum, String> Text = Map.ofEntries(Map.entry(MBTextEnum.FILE, "File"),
			Map.entry(MBTextEnum.NEW, "New"), Map.entry(MBTextEnum.SAVE_AS, "Save as"),
			Map.entry(MBTextEnum.LOAD, "Load"), Map.entry(MBTextEnum.EXIT, "Exit"), Map.entry(MBTextEnum.EDIT, "Edit"),
			Map.entry(MBTextEnum.UNDO, "Undo"), Map.entry(MBTextEnum.NAME, "Name"),
			Map.entry(MBTextEnum.AUTHOR, "Author"), Map.entry(MBTextEnum.DOT, "..."),
			Map.entry(MBTextEnum.NEW_DRAWING_TITLE_PROMPT, "Enter new title of the drawing"),
			Map.entry(MBTextEnum.NEW_AUTHOR_NAME_PROMPT, "Enter new author of the drawing"),
			Map.entry(MBTextEnum.SAVE_AS_PROMPT, "Save drawing to"),
			Map.entry(MBTextEnum.LOAD_PROMPT, "Load drawing from"),
			Map.entry(MBTextEnum.DRAWING_TITLE, "Enter the title of the drawing"),
			Map.entry(MBTextEnum.DRAWING_AUTHOR, "Enter the autor of the drawing"), Map.entry(MBTextEnum.COLON, ":"),
			Map.entry(MBTextEnum.SHAPE, ".shape"));

	// reference to the main JFrame
	protected JPaintFrame jPaintFrame;

	// a reference to a drawing d
	private Drawing d;

	// a list of listeners for the menu bar drawing object.
	private EventListenerList menuBarDrawingListenerList = new EventListenerList();

	public MyMenuBar(JPaintFrame jPaintFrame) {
		super();
		this.jPaintFrame = jPaintFrame;
		d = null;
		initialiseMenuBar();
	}

	/**
	 * Get a text description.
	 * 
	 * @param key to index the container that holds the text description as Enum.
	 * @return a String.
	 */
	private static String getText(MBTextEnum key) {
		return Text.get(key);
	}

	/**
	 * Create the menu bars functions, File and Edit.
	 */
	private void initialiseMenuBar() {
		// File menu
		add(initialiseFileMenu(new JMenu(getText(MBTextEnum.FILE))));
		// Edit menu
		add(initialiseEditMenu(new JMenu(getText(MBTextEnum.EDIT))));
	}

	/**
	 * Add the menu items for a File.
	 * 
	 * @param mFile file menu as JMenu.
	 */
	private JMenu initialiseFileMenu(JMenu mFile) {
		// new Menu Item
		mFile.add(new myJMenuItem(getText(MBTextEnum.NEW) + getText(MBTextEnum.DOT)));
		// save as Menu Item
		mFile.add(new myJMenuItem(getText(MBTextEnum.SAVE_AS) + getText(MBTextEnum.DOT)));
		// load Menu Item
		mFile.add(new myJMenuItem(getText(MBTextEnum.LOAD)));
		mFile.addSeparator();
		// exitMenuItem(mFile);
		mFile.add(new myJMenuItem(getText(MBTextEnum.EXIT)));

		return mFile;
	}

	/**
	 * Add the menu items for a Edit.
	 * 
	 * @param mEdit file menu as JMenu.
	 */
	private JMenu initialiseEditMenu(JMenu mEdit) {
		// Undo
		mEdit.add(new myJMenuItem(getText(MBTextEnum.UNDO)));
		// Drawing title
		mEdit.add(new myJMenuItem(getText(MBTextEnum.NAME) + getText(MBTextEnum.DOT)));
		// Drawing author
		mEdit.add(new myJMenuItem(getText(MBTextEnum.AUTHOR) + getText(MBTextEnum.DOT)));

		return mEdit;
	}

	/**
	 * Set the drawing object.
	 * 
	 * @param d is a drawing instance of type Drawing.
	 */
	public void setDrawingObject(Drawing d) {

		firedDrawingObjectEvent(new MyDrawingAreaEvent<Drawing>(this, d));
	}

	/**
	 * The Drawing has either been created or modified, inform all the listeners.
	 * 
	 * @param <T>   a generic type as type Drawing
	 * 
	 * @param event mle object as MyDrawingAreaEvent
	 */
	@SuppressWarnings("unchecked")
	public void firedDrawingObjectEvent(MyDrawingAreaEvent<Drawing> mle) {
		Object[] listerners = menuBarDrawingListenerList.getListenerList();

		for (int index = 0; index < listerners.length; index += 2) {
			if (listerners[index] == IDrawingAreaListener.class) {
				((IDrawingAreaListener<MyDrawingAreaEvent<Drawing>>) listerners[index + 1])
						.drawingAreaEventOccurred(mle);
			}
		}
	}

	/**
	 * Add a listener that is listening for a colour selection from the tool bar.
	 * 
	 * @param a reference to a listener of type IDrawingAreaListener
	 */
	public void addMenuBarDrawingListener(IDrawingAreaListener<MyDrawingAreaEvent<Drawing>> listener) {
		menuBarDrawingListenerList.add(IDrawingAreaListener.class, listener);

	}

	/**
	 * Remove a listener that is listening for a colour selection from the tool bar.
	 * 
	 * @param a reference to a listener of type IDrawingAreaListener.
	 */
	public void removeMenuBarDrawingListener(IDrawingAreaListener<MyDrawingAreaEvent<Drawing>> listener) {
		menuBarDrawingListenerList.remove(IDrawingAreaListener.class, listener);

	}

	/**
	 * <h1>Class myJMenuItem</h1> This class both extends the JMenuItem and
	 * implements the ActionListener class. Extending the JMenuItem means that we
	 * can add a menu item to the menu bar. Then when a menu item is selected the
	 * actionPerfromed from the ActionListener is triggered.
	 * 
	 * @author Clive Leddy
	 * @version 1.0
	 */
	class myJMenuItem extends JMenuItem implements ActionListener {
		private static final long serialVersionUID = -3898605973624196788L;

		public myJMenuItem(String title) {
			super(title);
			addActionListener(this);
		}

		/**
		 * Examining the action event e we can determine what menu item fired this even
		 * action. Then depending on the menu item this method performs and action.
		 * 
		 * @param e the even that occurred as type ActionEven.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String itemName = e.getActionCommand();
			// New drawing
			newDrawing(itemName);
			// Save drawing as
			saveDrawingAs(itemName);
			// Exit
			if (itemName.equals(MyMenuBar.getText(MBTextEnum.EXIT))) {
				System.exit(0);
			}
			// Load
			if (itemName.equals(MyMenuBar.getText(MBTextEnum.LOAD))) {
				JOptionPane.showInputDialog(jPaintFrame, MyMenuBar.getText(MyMenuBar.MBTextEnum.LOAD_PROMPT)
						+ MyMenuBar.getText(MyMenuBar.MBTextEnum.COLON), null);
			}
			// Undo
			if (itemName.equals(MyMenuBar.getText(MBTextEnum.UNDO))) {
				// Do nothing
			}
			// New drawing title
			newDrawingName(itemName);
			// New drawing author
			newDrawingAuthor(itemName);

		}

		/**
		 * Add a new author to the artwork this is triggered by a menu item. Get a new
		 * author for a drawing and update the drawing object.
		 * 
		 * @param itemName is a key that was extracted from the source event.
		 */
		private void newDrawingAuthor(String itemName) {
			if (itemName.equals(MyMenuBar.getText(MBTextEnum.AUTHOR) + MyMenuBar.getText(MBTextEnum.DOT))) {
				String author;
				author = JOptionPane.showInputDialog(jPaintFrame, MyMenuBar.getText(MyMenuBar.MBTextEnum.DRAWING_AUTHOR)
						+ MyMenuBar.getText(MyMenuBar.MBTextEnum.COLON), null);

				if (author != null) {
					if (!author.isBlank() && (d != null)) {
						// update the drawings author
						d.setAuthor(author);
						// re-set the drawing object.
						setDrawingObject(d);
					}
				}
			}
		}

		/**
		 * Add a new title to the artwork this is triggered by a menu item. Get a new
		 * title for a drawing and update the drawing object.
		 * 
		 * @param itemName is a key that was extracted from the source event.
		 */
		private void newDrawingName(String itemName) {
			if (itemName.equals(MyMenuBar.getText(MBTextEnum.NAME) + MyMenuBar.getText(MBTextEnum.DOT))) {
				String title = "";
				title = JOptionPane.showInputDialog(jPaintFrame, MyMenuBar.getText(MyMenuBar.MBTextEnum.DRAWING_TITLE)
						+ MyMenuBar.getText(MyMenuBar.MBTextEnum.COLON), null);

				if (title != null) {
					if (!title.isBlank() && (d != null)) {
						// update the drawings title
						d.setName(title);
						// re-set the drawing object.
						setDrawingObject(d);
					}
				}
			}
		}

		/**
		 * New drawing method that is triggered by a menu item. This method creates 2
		 * input dialogs. The first input dialog request the artwork title. The second
		 * input dialog requests the artwork author's name. Then create a new drawing
		 * with the title and author of the artwork.
		 * 
		 * @param itemName is a key that was extracted from the source event.
		 */
		private void newDrawing(String itemName) {
			if (itemName.equals(MyMenuBar.getText(MBTextEnum.NEW) + MyMenuBar.getText(MBTextEnum.DOT))) {
				String title = null;
				String name = null;

				title = JOptionPane.showInputDialog(jPaintFrame,
						MyMenuBar.getText(MyMenuBar.MBTextEnum.NEW_DRAWING_TITLE_PROMPT)
								+ MyMenuBar.getText(MyMenuBar.MBTextEnum.COLON),
						null);

				name = JOptionPane.showInputDialog(jPaintFrame,
						MyMenuBar.getText(MyMenuBar.MBTextEnum.NEW_AUTHOR_NAME_PROMPT)
								+ MyMenuBar.getText(MyMenuBar.MBTextEnum.COLON),
						null);
				setNewDrawingTitleAuthor(title, name);
			}
		}

		/**
		 * Save as method that is triggered by a menu item. This method creates a input
		 * dialog and displays default file as a suggestion for a file name.
		 * 
		 * @param itemName is a key that was extracted from the source event.
		 */
		private void saveDrawingAs(String itemName) {
			if (itemName.equals(MyMenuBar.getText(MBTextEnum.SAVE_AS) + MyMenuBar.getText(MBTextEnum.DOT))) {

				/**
				 * Function interface implementation.
				 */
				IMyFunctionalInterface<String, Drawing> mf = (draw) -> {
					String str = "";
					if (draw != null) {
						if (!draw.getName().isBlank() && !draw.getAuthor().isBlank()) {
							str += draw.getName() + " by " + draw.getAuthor();
						}
						if (!draw.getName().isBlank() && draw.getAuthor().isBlank()) {
							str += draw.getName();
						}
						if (draw.getName().isBlank() && !draw.getAuthor().isBlank()) {
							str += draw.getAuthor();
						}
					}

					return str += MyMenuBar.getText(MyMenuBar.MBTextEnum.SHAPE);
				};

				JOptionPane.showInputDialog(jPaintFrame, MyMenuBar.getText(MyMenuBar.MBTextEnum.SAVE_AS_PROMPT)
						+ MyMenuBar.getText(MyMenuBar.MBTextEnum.COLON), mf.execute(d));
			}
		}

		/**
		 * Given a title or author create a new drawing. Add either the non null title
		 * or author or both to a drawing object.
		 * 
		 * @param title  the name of the art work as a String.
		 * @param author the name of the author of the art work as a String.
		 */
		private void setNewDrawingTitleAuthor(String title, String author) {

			if (!title.isBlank() || !author.isBlank()) {
				d = new Drawing(title, author);

			} else {
				d = null;
			}

			setDrawingObject(d);
		}

	}

}
