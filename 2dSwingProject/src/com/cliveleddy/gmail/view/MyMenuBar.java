package com.cliveleddy.gmail.view;

import java.util.Map;

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
	 * Create the menu bars functions, File and Edit.
	 */
	private void initialiseMenuBar() {
		// File menu
		add(initialiseFileMenu(new JMenu(Text.get(MBTextEnum.FILE))));
		// Edit menu
		add(initialiseEditMenu(new JMenu(Text.get(MBTextEnum.EDIT))));
	}

	/**
	 * Add the menu items for a File.
	 * 
	 * @param mFile file menu as JMenu.
	 */
	private JMenu initialiseFileMenu(JMenu mFile) {
		mFile.add(new JMenuItem(Text.get(MBTextEnum.NEW) + Text.get(MBTextEnum.DOT)));
		mFile.add(new JMenuItem(Text.get(MBTextEnum.SAVE_AS) + Text.get(MBTextEnum.DOT)));
		mFile.add(new JMenuItem(Text.get(MBTextEnum.LOAD) + Text.get(MBTextEnum.DOT)));
		mFile.addSeparator();
		mFile.add(new JMenuItem(Text.get(MBTextEnum.EXIT)));

		return mFile;
	}

	/**
	 * Add the menu items for a Edit.
	 * 
	 * @param mEdit file menu as JMenu.
	 */
	private JMenu initialiseEditMenu(JMenu mEdit) {
		mEdit.add(new JMenuItem(Text.get(MBTextEnum.UNDO) + Text.get(MBTextEnum.DOT)));
		mEdit.add(new JMenuItem(Text.get(MBTextEnum.NAME) + Text.get(MBTextEnum.DOT)));
		mEdit.add(new JMenuItem(Text.get(MBTextEnum.AUTHOR) + Text.get(MBTextEnum.DOT)));

		return mEdit;
	}
}
