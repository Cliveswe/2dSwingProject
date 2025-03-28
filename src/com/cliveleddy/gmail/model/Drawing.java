package com.cliveleddy.gmail.model;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/**
 * <h1>Class Drawing</h1> This class will manage a collection of Shape objects.
 * It implements the class interface <code>IDrawable</code>. In addition,
 * implement the class methods that are defined in the given main class.
 * <p>
 * <h2>Updates for Custom Exception</h2> Added a try catch to methods
 * getTotalCircumference and getTotalArea.
 * <p>
 * <h2>Added getters.</h2>Added a getter for both the name and author fields.
 * <h2>Step 9</h2>Added a filter that when selected filter out all other shapes
 * except for the shape in the filter logic. Added a method
 * {@code resetFilter()} that resets the filter to show all shapes. In addition,
 * added a method {@code setFilter(Predicate<Shape> filter)} to set the logic
 * that show a particular type of shape.
 * <p>
 * Replaced the {@code foreach} loop with a stream and filter in the method
 * {@code draw(Graphics g)}.
 * 
 * @author Clive Leddy
 * @version 1.3
 */
public class Drawing implements IDrawable, Iterator<Shape> {

	private String name;

	private String author;

	private List<Shape> shapes;// collection of shapes, programming to the base type.

	private int index = 0;

	private Predicate<Shape> showShape;// A filter to show a type of shape.

	/**
	 * Drawing constructor that initialises the class variables.
	 */
	public Drawing() {
		this("", "");
	}

	/**
	 * Drawing constructor that initialises the class variables.
	 * 
	 * @param name   shapes name as a string.
	 * @param author the authors name that created the shape as a string.
	 */
	public Drawing(String name, String author) {

		this.name = name;

		this.author = author;

		shapes = new ArrayList<Shape>();

		resetFilter();

	}

	/**
	 * Set a filter to show a specific type of shape.
	 * 
	 * @param filter logic for showing a particular type of shape as type
	 *               {@code Predicate<Shape>}.
	 */
	public void setFilter(Predicate<Shape> filter) {
		this.showShape = filter;
	}

	/**
	 * Reset the filter to show all shapes.
	 */
	public void resetFilter() {
		setFilter(e -> true);
	}

	/**
	 * Render the name of the artwork, the author. Display the size of the
	 * collection of shapes. For each shape display its circumference and area. In
	 * addition render each shape in the collection of shapes that make up the
	 * artwork.
	 */
	@Override
	public void draw() {

		// render each of the shape object in the collection of shapes.
		for (Shape s : shapes) {

			s.draw();// render the shape.
		}
	}

	/**
	 * Render all the shape objects in the array list shapes.
	 * 
	 */
	@Override
	public void draw(Graphics g) {

		// Use stream with filter to draw shape object.
		shapes.stream().filter(showShape).forEach(e -> e.draw(g));
	}

	/**
	 * Set the name of the artwork.
	 * 
	 * @param name artwork's title as {@code String}.
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * Get the title name of the artwork.
	 * 
	 * @return the title as a {@code String}.
	 */
	public String getName() {

		return name;
	}

	/**
	 * Set the name of the author.
	 * 
	 * @param author name as a {@code String}.
	 */
	public void setAuthor(String author) {

		this.author = author;

	}

	/**
	 * get the authors name of the artwork.
	 * 
	 * @return the authors name as a {@code String}.
	 */
	public String getAuthor() {

		return author;
	}

	/**
	 * Add a shape to a collection of shapes.
	 * 
	 * @param s a non null object as data type {@code shape}.
	 */
	public void addShape(Shape s) {

		if (s != null) {

			shapes.add(s);
		}
	}

	/**
	 * Get the number of shape objects in the collection of shapes.
	 * 
	 * @return number of objects as an {@code int}.
	 */
	public int getSize() {

		return shapes.size();
	}

	/**
	 * Find the total circumference of each shape in the collection of shapes. Add
	 * each shapes total circumference together to calculate the all of the shapes
	 * circumference as a total.
	 * 
	 * @return the total circumference of all shapes as a {@code double}.
	 */
	public double getTotalCircumference() {

		double c = 0;

		for (Shape s : shapes) {

			try {

				c += s.getCircumference();

			} catch (ShapeException e) {

				System.err.println("Error in calculating the shape circumference: " + e.getMessage());
			}
		}

		return c;
	}

	/**
	 * Find the total area of each shape in the collection of shapes. Add each
	 * shapes total area together to calculate the all of the shapes area as a
	 * total.
	 * 
	 * @return the total area of all shapes as a {@code double}.
	 */
	public double getTotalArea() {

		double a = 0;

		for (Shape s : shapes) {

			try {

				a += s.getArea();

			} catch (ShapeException e) {

				System.err.println("Error in calculating the shape area: " + e.getMessage());

			}
		}

		return a;
	}

	/**
	 * Is there an additional shape object in the list of shapes.
	 * 
	 * @return true if the is another shape otherwise false.
	 */
	@Override
	public boolean hasNext() {

		if (index < shapes.size()) {

			return true;
		}

		index = 0;

		return false;
	}

	/**
	 * Get the next shape object in the list of shapes.
	 * 
	 * @return the next {@code Shape}.
	 */

	@Override
	public Shape next() {

		if (hasNext()) {

			index++;

			return shapes.get(index - 1);
		}

		index = 0;

		return null;
	}

	/**
	 * Remove the last shape added to the list of shapes.
	 */
	public void removeLastShape() {

		if (shapes.size() > 0) {

			shapes.remove(shapes.size() - 1);
		}
	}

	/**
	 * Is the drawing object empty? Contains no name, author and no list of shapes.
	 * 
	 * @return true if empty otherwise false.
	 */
	public boolean isEmpty() {

		if (shapes.size() > 0) {

			return false;
		}

		if (!name.isBlank() || !author.isBlank()) {

			return false;
		}

		return true;
	}

	/**
	 * Create a message for the drawing object.
	 */
	public String toString() {
		String res = "Drawing[";

		String del = String.format("; ");

		// name and author
		res += String.format("name=%s%s author=%s%s", this.name, del, this.author, del);

		// get size
		res += String.format("size=%d%s", this.getSize(), del);

		// get circumference
		res += String.format("circumference=%.1f%s", this.getTotalCircumference(), del);

		// get area
		res += String.format("area=%.1f", this.getTotalArea());

		res += "]\n";

		return res;
	}

}
