package com.cliveleddy.gmail.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * <h1>Class Shape</h1> This is a base class that is used to describe different
 * geometric figures. It implements the interface class IDrawable.
 * <p>
 * Example: "Create a circle or rectangle."
 * 
 * <h2>Updates for Custom Exception</h2> Change the variable points from and
 * array of Point to an ArrayList of type Point. Remember to re-factor the
 * constructors and the overloaded methods addPoint.
 * <p>
 * <h2>Updates for Step 3</h2> Added a throwable to methods getCircumference and
 * getArea.
 * <p>
 * <h2>Step 6</h2> Added a copy and clone constructors. Also included is an
 * isValid method that checks if the shape has a colour and that all points are
 * not null. Two new overloaded methods have been added to set the starting
 * point of the shape.
 * <p>
 * <h2>Step 9</h2> To further enhance encapsulation in this abstract class we
 * add lambda expressions. The details on how to retrieve different Point
 * coordinates are encapsulated using Java Functional interface with lambda
 * expressions. The consequences are that the detail of indexing the list of
 * points remains in the abstract class as well as any changes that may be made
 * to the list.
 * 
 * @author Clive Leddy
 * @version 2.3
 */
public abstract class Shape implements IDrawable, Cloneable {

	protected String END_POINT_ERROR_MESSAGE = "end point is missing!";

	// class variables
	protected String color;// shape colour.

	// a list of coordinates to draw the shape.
	protected ArrayList<Point> points;

	/**
	 * Functional interface with lambda expressions.
	 */

	/**
	 * Get the value of point x. {@code Function<Point, Double> pointX}
	 */
	protected Function<Point, Double> pointX = p -> {
		return p.get_x();
	};

	/**
	 * Get the value of point y. {@code Function<Point, Double> pointY}
	 */
	protected Function<Point, Double> pointY = p -> {
		return p.get_y();
	};

	/**
	 * Get the x coordinate of the starting point. {@code Supplier<Double> startX}
	 */
	protected Supplier<Double> startX = () -> {

		return pointX.apply(points.get(0));
	};

	/**
	 * Get the y coordinate of the starting point. {@code Supplier<Double> startY}
	 */
	protected Supplier<Double> startY = () -> {

		return pointY.apply(points.get(0));
	};

	/**
	 * Get the x coordinate of the second point. {@code Supplier<Double> secondX}
	 */
	protected Supplier<Double> secondX = () -> {
		return pointX.apply(points.get(1));
	};

	/**
	 * Get the y coordinate of the second point. {@code Supplier<Double> secondY}
	 */
	protected Supplier<Double> secondY = () -> {
		return pointY.apply(points.get(1));
	};

	/**
	 * Get the Point object of the starting point.
	 * {@code Supplier<Point> startPoint}
	 */
	protected Supplier<Point> startPoint = () -> {
		return points.get(0);
	};

	/**
	 * Get the Point object of the second point. {@code Supplier<Point> secondPoint}
	 */
	protected Supplier<Point> secondPoint = () -> {
		return points.get(1);
	};

	/**
	 * Point p is the starting point for a shape of colour color.
	 * 
	 * @param p     a shapes coordinates as a Point object.
	 * @param color colour of the shape as a string.
	 */
	public Shape(Point p, String color) {

		super();

		this.color = color;

		// create an ArrayList object
		points = new ArrayList<Point>();

		points.add(0, p);

		points.add(1, null);
	}

	/**
	 * Both x and y are the values for the starting coordinates of the shape with
	 * colour color.
	 * 
	 * @param x     coordinate of a shape as a double.
	 * @param y     coordinate of a shape as a double.
	 * @param color colour colour of the shape as a string
	 */
	public Shape(double x, double y, String color) {

		super();

		this.color = color;

		points = new ArrayList<Point>();// create an ArrayList object

		points.add(0, new Point(x, y));

		points.add(1, null);

	}

	/**
	 * Copy constructor.
	 * 
	 * @param shape an Object of type Shape.
	 */
	public Shape(Shape shape) {
		color = shape.color;
		points = new ArrayList<>();

		Iterator<Point> iterator = shape.points.iterator();

		while (iterator.hasNext()) {
			points.add(iterator.next());
		}
	}

	/**
	 * Clone a shape.
	 */
	public abstract Shape clone();

	/**
	 * Get the colour of the shape object.
	 * 
	 * @return shape colour as a string.
	 */
	public String getColor() {

		return color;
	}

	/**
	 * Set the colour to the shape.
	 * 
	 * @param color the shapes colour as a string.
	 */
	public void setColor(String color) {

		this.color = color;
	}

	/**
	 * Get the circumference of the shape.
	 * 
	 * @return the circumference as a double.
	 * @throws ShapeException on error.
	 */
	public abstract double getCircumference() throws ShapeException;

	/**
	 * Get the area of the shape.
	 * 
	 * @return the area as a double
	 * @throws ShapeException on error
	 */
	public abstract double getArea() throws ShapeException;

	/**
	 * Add the end point to the array of points.
	 * 
	 * @param p object point.
	 */
	public void addPoint(Point p) {

		this.points.set(1, p);
	}

	/**
	 * Add the end point to the array of points with the given coordinates.
	 * 
	 * @param x coordinate of a point as a double.
	 * @param y coordinate of a point as a double.
	 */
	public void addPoint(double x, double y) {

		this.points.set(1, new Point(x, y));
	}

	/**
	 * Get the second point.
	 * 
	 * @return the second point at type Point.
	 */
	public Point getPoint() {
		return points.get(1);
	}

	/**
	 * Add a starting point.
	 * 
	 * @param p object point.
	 */
	public void setStartPoint(Point p) {
		points.set(0, p);
	}

	/**
	 * Add a starting point.
	 * 
	 * @param x coordinate of a point as a double.
	 * @param y coordinate of a point as a double.
	 */
	public void setStartPoint(double x, double y) {
		points.set(0, new Point(x, y));
	}

	/**
	 * Get the start point.
	 * 
	 * @return the start point at type Point.
	 */
	public Point getStartPoint() {
		return points.get(0);
	}

	/**
	 * Does the shape have a colour and all points are not null.
	 * 
	 * @return true if valid other wise false.
	 */
	public boolean isValid() {
		if (color == null) {
			return false;
		}

		for (Point p : points) {
			if (p == null) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Clear the points of the shape. This method acts as a class points reset to
	 * Origo.
	 */
	public void clear() {
		points.add(0, null);
		points.add(1, null);
	}

}
