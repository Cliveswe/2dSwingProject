package com.cliveleddy.gmail.view;

import javax.swing.JMenuItem;

/**
 * <h1>Interface IMyMenuItem</h1> Create a JMenuItem and give it a title.
 * 
 * @author Clive Leddy
 * @version 1.0
 *
 */
@FunctionalInterface
public interface IMyMenuItem {
	/**
	 * Create a JMenuItem with a title.
	 * 
	 * @param title menu items title as type {@code String}
	 * @return a {@code JMenuItem}.
	 */
	public JMenuItem createItem(String title);
}
