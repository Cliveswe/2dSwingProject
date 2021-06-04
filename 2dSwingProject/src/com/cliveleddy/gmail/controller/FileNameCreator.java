package com.cliveleddy.gmail.controller;

import com.cliveleddy.gmail.model.Drawing;

/**
 * <h1>Class FileNameCreator.</h1> Create a file name from the drawings title
 * and author.
 * 
 * @author Clive Leddy
 * @version 1.0
 */
public class FileNameCreator {

	/**
	 * Create a file name from the name of the art piece and the name of the author.
	 * If both the art piece name and author are given then the file name will be
	 * "Title" by "Author". If either the title or author are missing then the "by"
	 * is not added to the name of the file.
	 * 
	 * @param drawing a drawing of type {@code Drawing}.
	 * @return a file name as type {@code String}.
	 */
	public static String createFileName(Drawing drawing) {
		String fileName = "";

		if (drawing != null) {

			if (!drawing.getName().isBlank() && !drawing.getAuthor().isBlank()) {

				fileName = drawing.getName() + " by " + drawing.getAuthor();
			}

			if (!drawing.getName().isBlank() && drawing.getAuthor().isBlank()) {

				fileName = drawing.getName();
			}

			if (drawing.getName().isBlank() && !drawing.getAuthor().isBlank()) {

				fileName = drawing.getAuthor();
			}
		}
		return fileName;
	}

}
