package com.cliveleddy.gmail.model;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <h1>Class Drawing</h1> This class will manage a collection of Shape objects.
 * It implements the class interface <code>IDrawable</code>. In addition,
 * implement the class methods that are defined in the given main class.
 * <p>
 * <h2>Updates for Custom Exception</h2> Added a try catch to methods
 * getTotalCircumference and getTotalArea.
 * <p>
 * <h2>Added getters.</h2>Added a getter for both the name and author fields.
 * 
 * @author Clive Leddy
 * @version 1.2
 */
public class Drawing implements IDrawable, Iterator<Shape> {

	private String name;

	private String author;

	private List<Shape> shapes;// collection of shapes, programming to the base type.

	private int index = 0;

	/**
	 * Drawing constructor that initialises the class variables.
	 */
	public Drawing() {

		this.name = "";

		this.author = "";

		shapes = new ArrayList<Shape>();
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

		for (Shape shape : shapes) {

			shape.draw(g);
		}

	}

	/**
	 * Set the name of the artwork.
	 * 
	 * @param name artwork's title as string.
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * Get the title name of the artwork.
	 * 
	 * @return the title as a String.
	 */
	public String getName() {

		return name;
	}

	/**
	 * Set the name of the author.
	 * 
	 * @param author name as a string.
	 */
	public void setAuthor(String author) {

		this.author = author;

	}

	/**
	 * get the authors name of the artwork.
	 * 
	 * @return the authors name as a String.
	 */
	public String getAuthor() {

		return author;
	}

	/**
	 * Add a shape to a collection of shapes.
	 * 
	 * @param s a non null object as data type shape.
	 */
	public void addShape(Shape s) {

		if (s != null) {

			shapes.add(s);
		}
	}

	/**
	 * Get the number of shape objects in the collection of shapes.
	 * 
	 * @return number of objects as an integer.
	 */
	public int getSize() {

		return shapes.size();
	}

	/**
	 * Find the total circumference of each shape in the collection of shapes. Add
	 * each shapes total circumference together to calculate the all of the shapes
	 * circumference as a total.
	 * 
	 * @return the total circumference of all shapes as a double.
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
	 * @return the total area of all shapes as a double.
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
