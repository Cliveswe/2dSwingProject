package com.cliveleddy.gmail.view;

import java.util.Map;

/**
 * 
 * @author Clive Leddy
 * @version 1.0
 */
public class MyStatusBar {

	/**
	 * The key values to get a text description.
	 */
	public static enum SBTextEnum {
		COORDINATES, SELECT_COLOUR, END
	};

	/**
	 * A dictionary that is indexed by a key.
	 */
	public static Map<SBTextEnum, String> Text = Map.ofEntries(Map.entry(SBTextEnum.COORDINATES, "Coordinates"),
			Map.entry(SBTextEnum.SELECT_COLOUR, "Select colour"), Map.entry(SBTextEnum.END, ":"));

	public MyStatusBar() {

	}

}
