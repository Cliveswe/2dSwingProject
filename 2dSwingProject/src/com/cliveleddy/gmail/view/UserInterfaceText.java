package com.cliveleddy.gmail.view;

import java.util.Map;

/**
 * A loosely coupled class that holds a container of text that is identified by
 * a key. The text is used to label and describe the different components of the
 * application.
 * 
 * @author Clive Leddy
 * @version 1.0
 *
 */
public class UserInterfaceText {

	/**
	 * The key values to get a text description.
	 */
	public static enum UITextEnum {
		TITLE, FILE, EDIT
	};

	/**
	 * A dictionary that is indexed by a key.
	 */
	public static Map<UITextEnum, String> Text = Map.ofEntries(Map.entry(UITextEnum.TITLE, "My GUI"),
			Map.entry(UITextEnum.FILE, "File"), Map.entry(UITextEnum.EDIT, "Edit"));

	/**
	 * Get a text description.
	 * 
	 * @param key to index the container that holds the text description as Enum.
	 * @return a String.
	 */
	public static String getText(UITextEnum key) {
		return UserInterfaceText.Text.get(key);
	}

}
