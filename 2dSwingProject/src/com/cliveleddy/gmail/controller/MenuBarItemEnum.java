package com.cliveleddy.gmail.controller;

/**
 * <h1>Enum class MenuBarItemEnum</h1> A Enum class that contains constants as
 * names of menu items. This class has additional logic for looking up an enum
 * class constant and returning it as a String.
 * 
 * @author Clive Leddy
 * @version 1.0
 *
 */
public enum MenuBarItemEnum implements ILabeled {
	FILE("File"), NEW("New..."), SAVE_AS("Save as..."), LOAD("Load"), EXIT("Exit"), EDIT("Edit"), UNDO("Undo"),
	NAME("Name..."), AUTHOR("Author..."), SHAPE_FILTER("Filter"), ALL_SHAPES("Show Circle and Rectangle."),
	SHOW_CIRCLE("Show Circle."), SHOW_RECTANGLE("Show Rectangle."),
	NEW_DRAWING_NAME_PROMPT("Enter new name of the drawing:"),
	NEW_DRAWING_AUTHOR_PROMPT("Enter new author of the drawing:"), SAVE_AS_PROMPT("Save drawing to:"),
	LOAD_PROMPT("Load drawing from:"), SHAPE("shape"), DOT_SHAPE(".shape"),
	DRAWING_NAME("Enter the name of the drawing:"), DRAWING_AUTHOR("Enter the autor of the drawing:"), INFO("Info"),
	CIRCLE("Circle"), RECTANGLE("Rectangle"), DRAWING_FILTER("Drawing filter");

	private final String label;

	private MenuBarItemEnum(String label) {
		this.label = label;
	}

	/**
	 * Get the text version of the constant from the Enum class.
	 * 
	 * @return a label as type String.
	 */
	@Override
	public String label() {

		return label;
	}
}
