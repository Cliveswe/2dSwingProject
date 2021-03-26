package com.cliveleddy.gmail.user_interface;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Create the applications menu bar and add items to each menu. It extends the
 * JMenuBar swing.
 * 
 * @author Clive Leddy
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MyMenuBar extends JMenuBar {

	/**
	 * The key values to get a text description.
	 */
	public static enum MBTextEnum {
		FILE, NEW, SAVE_AS, LOAD, EXIT, EDIT, UNDO, NAME, AUTHOR, DOT
	};

	/**
	 * A dictionary that is indexed by a key.
	 */
	public static Map<MBTextEnum, String> Text = Map.ofEntries(Map.entry(MBTextEnum.FILE, "File"),
			Map.entry(MBTextEnum.NEW, "New"), Map.entry(MBTextEnum.SAVE_AS, "Save as"),
			Map.entry(MBTextEnum.LOAD, "Load"), Map.entry(MBTextEnum.EXIT, "Exit"), Map.entry(MBTextEnum.EDIT, "Edit"),
			Map.entry(MBTextEnum.UNDO, "Undo"), Map.entry(MBTextEnum.NAME, "Name"),
			Map.entry(MBTextEnum.AUTHOR, "Author"), Map.entry(MBTextEnum.DOT, "..."));

	public MyMenuBar() {
		super();
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

		// mFile.add(new JMenuItem(getText(MBTextEnum.NEW) + getText(MBTextEnum.DOT)));
		newMenuItem(mFile);
		// mFile.add(new JMenuItem(getText(MBTextEnum.SAVE_AS) +
		// getText(MBTextEnum.DOT)));
		saveAsMenuItem(mFile);
		// mFile.add(new JMenuItem(getText(MBTextEnum.LOAD) + getText(MBTextEnum.DOT)));
		loadMenuItem(mFile);
		mFile.addSeparator();
		exitMenuItem(mFile);
		// mFile.add(new myJMenuItem(getText(MBTextEnum.EXIT)));

		return mFile;
	}

	private void newMenuItem(JMenu mFile) {
		mFile.add(new JMenuItem(new AbstractAction(getText(MBTextEnum.NEW) + getText(MBTextEnum.DOT)) {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO add instructions
			}
		}));
	}

	private void saveAsMenuItem(JMenu mFile) {
		mFile.add(new JMenuItem(new AbstractAction(getText(MBTextEnum.SAVE_AS) + getText(MBTextEnum.DOT)) {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO add instructions
			}
		}));
	}

	private void loadMenuItem(JMenu mFile) {
		mFile.add(new JMenuItem(new AbstractAction(getText(MBTextEnum.LOAD) + getText(MBTextEnum.DOT)) {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO add instructions
			}
		}));
	}

	private void exitMenuItem(JMenu mFile) {
		mFile.add(new JMenuItem(new AbstractAction(getText(MBTextEnum.EXIT)) {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}));
	}

	/**
	 * Add the menu items for a Edit.
	 * 
	 * @param mEdit file menu as JMenu.
	 */
	private JMenu initialiseEditMenu(JMenu mEdit) {
		mEdit.add(new JMenuItem(getText(MBTextEnum.UNDO) + getText(MBTextEnum.DOT)));
		mEdit.add(new JMenuItem(getText(MBTextEnum.NAME) + getText(MBTextEnum.DOT)));
		mEdit.add(new JMenuItem(getText(MBTextEnum.AUTHOR) + getText(MBTextEnum.DOT)));

		return mEdit;
	}

	class myJMenuItem extends AbstractAction {
		private String mTitle;

		public myJMenuItem(String title) {
			super(title);

			mTitle = title;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Text.containsValue(mTitle);
			switch (mTitle) {
			case "Exit":
				// System.exit(0);
				break;
			default:
				// TODO
			}

		}
	}

}
