package com.cliveleddy.gmail.user_interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * <h1>Step 4</h1> Create the applications menu bar and add items to each menu.
 * It extends the JMenuBar swing.
 * <p>
 * <h1>Step 5</h1> Add the action listeners to each of the menu items.
 * 
 * @author Clive Leddy
 * @version 1.1
 */
public class MyMenuBar extends JMenuBar {
	private static final long serialVersionUID = -1336076929035881984L;

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

	protected JFrame jPaintFrame;

	public MyMenuBar(JFrame jPaintFrame) {
		super();
		this.jPaintFrame = jPaintFrame;
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

		// Component comp = null;
		// JFrame f = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, comp);

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
			// New
			if (itemName.equals(MyMenuBar.getText(MBTextEnum.NEW) + MyMenuBar.getText(MBTextEnum.DOT))) {

				JOptionPane.showInputDialog(jPaintFrame,
						MyMenuBar.getText(MyMenuBar.MBTextEnum.NEW_DRAWING_TITLE_PROMPT)
								+ MyMenuBar.getText(MyMenuBar.MBTextEnum.COLON),
						null);
				JOptionPane.showInputDialog(jPaintFrame, MyMenuBar.getText(MyMenuBar.MBTextEnum.NEW_AUTHOR_NAME_PROMPT)
						+ MyMenuBar.getText(MyMenuBar.MBTextEnum.COLON), null);
			}
			// Save As
			if (itemName.equals(MyMenuBar.getText(MBTextEnum.SAVE_AS) + MyMenuBar.getText(MBTextEnum.DOT))) {
				JOptionPane.showInputDialog(jPaintFrame,
						MyMenuBar.getText(MyMenuBar.MBTextEnum.SAVE_AS_PROMPT)
								+ MyMenuBar.getText(MyMenuBar.MBTextEnum.COLON),
						MyMenuBar.getText(MyMenuBar.MBTextEnum.SHAPE));
			}
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
			// Name
			if (itemName.equals(MyMenuBar.getText(MBTextEnum.NAME) + MyMenuBar.getText(MBTextEnum.DOT))) {
				JOptionPane.showInputDialog(jPaintFrame, MyMenuBar.getText(MyMenuBar.MBTextEnum.DRAWING_TITLE)
						+ MyMenuBar.getText(MyMenuBar.MBTextEnum.COLON), null);
			}
			// Author
			if (itemName.equals(MyMenuBar.getText(MBTextEnum.AUTHOR) + MyMenuBar.getText(MBTextEnum.DOT))) {
				JOptionPane.showInputDialog(jPaintFrame, MyMenuBar.getText(MyMenuBar.MBTextEnum.DRAWING_AUTHOR)
						+ MyMenuBar.getText(MyMenuBar.MBTextEnum.COLON), null);
			}

		}

	}

}
