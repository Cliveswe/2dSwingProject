
package com.cliveleddy.gmail.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.cliveleddy.gmail.model.Drawing;
import com.cliveleddy.gmail.model.Shape;
import com.cliveleddy.gmail.model.ShapeException;

/**
 * <h1>Class FileHandler</h1> This class save a drawing object and saves it to a
 * file with an ".shape" extension. In addition, the class opens a file with the
 * ".shape" extension reads its contents and creates a drawing object.
 * 
 * @author Clive Leddy
 * @version 1.0
 */
public class FileHandler extends File {
	private static final long serialVersionUID = 1L;
	private final String fileExtension;
	private final String fileDescription;

	/**
	 * Class constructor.
	 * 
	 * @param description a text description of what file to filter for, type
	 *                    {@code String}.
	 * @param extension   the file extension that is allowed in this class, type
	 *                    {@code String}.
	 */
	public FileHandler(String fileDescription, String fileExtension) {

		super("default.shape");
		this.fileDescription = fileDescription;
		this.fileExtension = fileExtension;
	}

	/**
	 * Get a text description of the file to filter.
	 * 
	 * @return description of the file to filter for, type {@code String}.
	 */
	public String getFileExtension() {

		return fileExtension;
	}

	/**
	 * Get the file extension that is allowed in this class.
	 * 
	 * @return file extension that is allowed in this class, type {@code String}.
	 */
	public String getFileDescription() {

		return fileDescription;
	}

	/**
	 * Write the art piece title and the authors name to the file.
	 * 
	 * @param d       a drawing of type {@code Drawing}.
	 * @param fileOut file descriptor that writes data to a file of type
	 *                BufferedWriter.
	 * @throws IOException re-throws an exception that was thrown by BufferedWriter.
	 */
	private void setTitleAuthor(Drawing d, BufferedWriter fileOut) throws IOException {

		try {

			// Write the name of the art piece.
			fileOut.write(d.getName());
			fileOut.newLine();

			// Write the author of the art piece.
			fileOut.write(d.getAuthor());
			fileOut.newLine();

		} catch (IOException e) {

			throw e;
		}
	}

	/**
	 * Write the shapes to the file.
	 * 
	 * @param d       a drawing of type {@code Drawing}.
	 * @param fileOut file descriptor that writes data to a file of type
	 *                BufferedWriter.
	 * @throws IOException re-throws an exception that was thrown by
	 *                     {@code BufferedWriter}.
	 */
	private void writeShapes(Drawing drawing, BufferedWriter fileOut) throws IOException {

		try {

			while (drawing.hasNext()) {

				// Get the next shape to save.
				Shape s = drawing.next();

				// Write the shapes components to the file. The components are formatted.
				fileOut.write(s.getClass().getSimpleName().toString().toLowerCase());
				fileOut.write("," + s.getStartPoint().getX());
				fileOut.write("," + s.getStartPoint().getY());
				fileOut.write("," + s.getPoint().getX());
				fileOut.write("," + s.getPoint().getY());
				fileOut.write("," + s.getColor());
				fileOut.newLine();
			}

		} catch (IOException e) {

			throw e;
		}
	}

	/**
	 * Save a drawing to a given file name.
	 * 
	 * @param drawing  a drawing of type {@code Drawing}.
	 * @param fileName the name of the file as type {@code String}.
	 * @throws ShapeException, when an exception has been thrown this method creates
	 *                         a custom exception of type {@code ShapeException}.
	 */
	public void save(Drawing drawing, String fileName) throws ShapeException {
		FileWriter fw;
		BufferedWriter fileOut;

		fileName = fileName.strip();

		// The file name has to have a name.
		if (fileName.isEmpty() || fileName.isBlank()) {

			throw new ShapeException("Error: the file name >>" + fileName + "<< contains an error.");
		}

		// Add the correct extension to the name of the file.
		if (!isValid(fileName)) {

			fileName += "." + getFileExtension();
		}

		try {

			fw = new FileWriter(fileName);
			fileOut = new BufferedWriter(fw);

			// Write the header to the file.
			setTitleAuthor(drawing, fileOut);

			// Write the shape data to the file.
			writeShapes(drawing, fileOut);

			// Clean up.
			fileOut.flush();
			fileOut.close();
			fw.close();

		} catch (IOException e) {

			String msg = "Class FileHandler encountered a IO error while writing to a file.";
			System.err.println(msg);
			e.printStackTrace();

			throw new ShapeException(msg + " " + fileName);
		}
	}

	/**
	 * Save a drawing.
	 * 
	 * @param drawing a drawing of type {@code Drawing}.
	 * @throws ShapeException, when an exception has been thrown this method creates
	 *                         a custom exception of type {@code ShapeException}.
	 */
	public void save(Drawing drawing) throws ShapeException {

		save(drawing, FileNameCreator.createFileName(drawing));
	}

	/**
	 * Convert a source to a type Double.
	 * 
	 * @param source is a regular expression as type Scanner.
	 * @return a parsed string as type Double.
	 */
	private double strToDouble(Scanner source) {
		String str = source.next();

		return Double.parseDouble(str);
	}

	/**
	 * Create a shape.
	 * 
	 * @param line contain the data for creating a shape of type Shape.
	 * @return a new shape of type Shape.
	 */
	private Shape createShape(String line) {
		Shape s = null;
		Scanner scanner = new Scanner(line);// Scan the line.

		// Get the Shape components from the line.
		while (scanner.hasNext()) {

			// Get shape from a shape factory.
			s = new ShapeFactory().getShape(scanner.next());
			// Set the start point.
			s.setStartPoint(strToDouble(scanner), strToDouble(scanner));
			// Add the second point.
			s.addPoint(strToDouble(scanner), strToDouble(scanner));
			// Set the shape colour.
			s.setColor(scanner.next());
		}

		scanner.close();

		return s;
	}

	/**
	 * Get the name of the art piece and author.
	 * 
	 * @param d      a drawing to update as type {@code Drawing}.
	 * @param fileIn file descriptor to retrieve data, type {@code BufferedReader}.
	 * @return an updated drawing of type {@code Drawing}.
	 * @throws IOException re-throw and exception that was thrown by
	 *                     {@code BufferedReader}.
	 */
	private Drawing getTitleAuthor(Drawing d, BufferedReader fileIn) throws IOException {

		try {

			d.setName(fileIn.readLine());// Name of the art piece.
			d.setAuthor(fileIn.readLine());// Name of the author.

		} catch (IOException e) {

			throw e;
		}

		return d;
	}

	/**
	 * Get all the shapes the make up the art piece.
	 * 
	 * @param d      a drawing to update as type {@code Drawing}.
	 * @param fileIn file descriptor to retrieve data, type {@code BufferedReader}.
	 * @return an updated drawing of type Drawing.
	 * @throws IOException re-throw and exception that was thrown by
	 *                     {@code BufferedReader}.
	 */
	private Drawing getShapes(Drawing d, BufferedReader fileIn) throws IOException {

		try {

			String line = fileIn.readLine();// Read a line from the file.

			// Get the different shapes components that make up the art piece.
			while (line != null) {

				// Format the line to read a shapes components.
				line = line.replace(",", " ");

				// Add the shape to the drawing;
				d.addShape(createShape(line));

				// Get the next shape.
				line = fileIn.readLine();
			}

		} catch (IOException e) {

			throw e;
		}

		return d;
	}

	/**
	 * Open a file and read its contents.
	 * 
	 * @param fileName the name of the file as type {@code String}.
	 * @return an updated drawing of type {@code Drawing}.
	 * @throws ShapeException, when an exception has been thrown this method creates
	 *                         a custom exception of type {@code ShapeException}.
	 */
	public Drawing load(String fileName) throws ShapeException {
		FileReader fr;
		BufferedReader fileIn;
		Drawing d = null;

		if (!isValid(fileName)) {

			throw new ShapeException(fileName + " extension is not reconised!");
		}

		// Load the file.
		try {

			// Setup for reading the contents of a file.
			fr = new FileReader(fileName);
			fileIn = new BufferedReader(fr);
			d = new Drawing();// Create a new empty drawing.

			// Get the art piece title and the author name.
			d = getTitleAuthor(d, fileIn);

			// Get the different shapes components that make up the art piece.
			d = getShapes(d, fileIn);

			// Clean up.
			fileIn.close();
			fr.close();

		} catch (FileNotFoundException e) {

			String msg = "Error with loading a file. Could not open the file for reading.";
			System.err.println(msg);
			e.printStackTrace();

			throw new ShapeException(msg + " " + fileName);

		} catch (IOException e) {

			String msg = "Error with reading from a file. An I/O error occurred.";
			System.err.println(msg);
			e.printStackTrace();

			throw new ShapeException(msg + " " + fileName);
		}

		return d;
	}

	/**
	 * Validate if the file name given is valid. The file name must end with the
	 * extension given in this class.
	 * 
	 * @param fileName file name as type {@code String}.
	 * @return {@code true} if the file name is valid, otherwise {@code false}.
	 */
	public boolean isValid(String fileName) {

		// If fileName does not contain "." or has a "." towards the end of the file
		// name then it's not a valid file name.
		if (fileName.contains(".") && (fileName.lastIndexOf(".") != 0)) {

			// Does the file contain the correct extension.
			if (fileName.contains(getFileExtension())) {
				return true;
			}
		}
		return false;
	}
}
