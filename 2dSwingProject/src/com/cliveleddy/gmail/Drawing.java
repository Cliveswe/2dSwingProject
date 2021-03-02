package com.cliveleddy.gmail;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <h1>Class Drawing</h1>
 * This class will manage a collection of Shape objects. It implements the class interface
 * <code>IDrawable</code>. In addition, implement the class methods that are defined in the 
 * given main class.
 *  <p>
 * <h2>Updates for Custom Exception</h2>
 * Added a try catch to methods getTotalCircumference and getTotalArea.
 * 
 * @author Clive Leddy
 * @version 1.1
 */
public class Drawing implements IDrawable {

	private String name;
	private String author;
	private Collection<Shape> shapes;//collection of shapes, programming to the base type.

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
	 * @param name shapes name as a string.
	 * @param author the authors name that created the shape as a string.
	 */
	Drawing(String name, String author){
		this.name = name;
		this.author = author;
		shapes = new ArrayList<Shape>();
	}

	/**
	 * Render the name of the artwork, the author. Display the size of the collection of
	 * shapes. For each shape display its circumference and area. In addition render each
	 * shape in the collection of shapes that make up the artwork.
	 */
	@Override
	public void draw() {
		System.out.println(this.toString());

		System.out.println("A drawing  by " + this.author + " called " + this.name);
		//render each of the shape object in the collection of shapes.
		for(Shape s: shapes) {
			s.draw();//render the shape.
		}
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

	/**
	 * Set the name of the artwork.
	 * @param name artwork's title as string.
	 */
	public void setName(String name) {
		this.name = name;

	}

	/**
	 * Set the name of the author.
	 * @param author name as a string.
	 */
	public void setAuthor(String author) {
		this.author = author;

	}

	/**
	 * Add a shape to a collection of shapes.
	 * @param s a non null object as data type shape.
	 */
	public void addShape(Shape s) {
		if(s != null) {
			shapes.add(s);
		}
	}

	/**
	 * Get the number of shape objects in the collection of shapes.	
	 * @return number of objects as an int.
	 */
	public int getSize() {

		return shapes.size();
	}

	/**
	 * Find the total circumference of each shape in the collection of shapes. Add each shapes total circumference
	 * together to calculate the all of the shapes circumference as a total.   
	 * @return the total circumference of all shapes as a double.
	 */
	public double getTotalCircumference() {
		double c = 0;

		for(Shape s: shapes) {
			try {
				c += s.getCircumference();
			} 
			catch (ShapeException e) {

			}
		}
		return c;
	}

	/**
	 * Find the total area of each shape in the collection of shapes. Add each shapes total area
	 * together to calculate the all of the shapes area as a total.   
	 * @return the total area of all shapes as a double.
	 */
	public double getTotalArea() {
		double a = 0;

		for(Shape s: shapes) {
			try {
				a += s.getArea();
			}
			catch (ShapeException e) {

			}
		}
		return a;
	}

	public String toString() {
		String res = "Drawing[";		

		String del = String.format("; ");

		//name and author
		res += String.format("name=%s%s author=%s%s", this.name, del, this.author, del);
		//get size
		res += String.format("size=%d%s", this.getSize(), del);
		//get circumference
		res += String.format("circumference=%.1f%s", this.getTotalCircumference(), del);
		//get area
		res += String.format("area=%.1f", this.getTotalArea());

		res += "]\n";

		return res;
	}

}
