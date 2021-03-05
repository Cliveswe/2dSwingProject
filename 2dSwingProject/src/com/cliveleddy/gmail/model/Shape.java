package com.cliveleddy.gmail.model;

import java.util.ArrayList;

/**
 * <h1>Class Shape</h1>
 * This is a base class that is used to describe different geometric figures.
 * It implements the interface class IDrawable.
 * <p>
 * Example: "Create a circle or rectangle."
 * 
 * <h2>Updates for Custom Exception</h2>
 * Change the variable points from and array of Point to an ArrayList of type Point.
 * Remember to re-factor the constructors and the overloaded methods addPoint.
 * <p>
 * <h2>Updates for Step 3</h2>
 * Added a throwable to methods getCircumference and getArea.
 * 
 * @author Clive Leddy
 * @version 2.1
 */
public abstract class Shape implements IDrawable {
	protected String END_POINT_ERROR_MESSAGE = "end point is missing!";
	//class variables
	protected String color;//shape colour.
	//a list of coordinates to draw the shape.
	protected ArrayList<Point> points;

	/**
	 * Point p is the starting point for a shape of colour color.
	 * @param p a shapes coordinates as a Point object.
	 * @param color colour of the shape as a string.
	 */
	public Shape(Point p, String color) {
		super();
		this.color = color;
		//create an ArrayList object
		points = new ArrayList<Point>();
		points.add(0, p);
		points.add(1, null);
	}

	/**
	 * Both x and y are the values for the starting coordinates of the shape
	 * with colour color. 
	 * @param x coordinate of a shape as a double.
	 * @param y coordinate of a shape as a double.
	 * @param color colour colour of the shape as a string
	 */
	public Shape(double x, double y, String color) {
		super();
		this.color = color;
		points = new ArrayList<Point>();//create an ArrayList object
		points.add(0, new Point(x,y));
		points.add(1, null);
		
	}

	/**
	 * Get the colour of the shape object.
	 * @return shape colour as a string.
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Set the colour to the shape.
	 * @param color the shapes colour as a string.
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Get the circumference of the shape. 
	 * @return the circumference as a double.
	 * @throws ShapeException on error.
	 */
	public abstract double getCircumference() throws ShapeException;

	/**
	 * Get the area of the shape. 
	 * @return the area as a double
	 * @throws ShapeException on error
	 */
	public abstract double getArea() throws ShapeException;

	/**
	 * Add the end point to the array of points.
	 * @param p object point.
	 */
	public void addPoint(Point p) {
		//this.points[1] = p;
		this.points.set(1, p);
	}
	
	/**
	 * Add the end point to the array of points with the given coordinates.
	 * @param x coordinate of a point as a double.
	 * @param y coordinate of a point as a double.
	 */
	public void addPoint(double x, double y) {
		//this.points[1] = new Point(x, y);
		this.points.set(1, new Point(x,y));
	}
	
}
