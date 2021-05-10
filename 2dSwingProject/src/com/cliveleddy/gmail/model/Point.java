/**
 * 
 */
package com.cliveleddy.gmail.model;

/**
 * <h1>Class Point</h1> This class represents a geometric point in a coordinate
 * system. Point(x,y)
 * <p>
 * Example: "Point (1,0,1,0)"
 * 
 * @author Clive Leddy
 * @version 1.0
 */
public class Point {

	private double x, y;

	/**
	 * Create a default point at Origo.
	 */
	Point() {

		x = 0;

		y = 0;
	}

	/**
	 * Create a point at given coordinates.
	 */
	Point(double x, double y) {

		this.x = x;

		this.y = y;
	}

	/**
	 * Get the value for point x.
	 */
	public double get_x() {

		return x;
	}

	/**
	 * Get the value of x coordinate in a Point.
	 * 
	 * @return value of x as type String.
	 */
	public String getX() {
		return Double.toString(x);
	}

	/**
	 * Get the value of y coordinate in a Point.
	 * 
	 * @return value of y as type Double.
	 */
	public double get_y() {

		return y;
	}

	/**
	 * Get the value of y coordinate in a Point.
	 * 
	 * @return value of y as type String.
	 */
	public String getY() {
		return Double.toString(y);
	}

	/**
	 * Set the value of the x coordinate in a Point.
	 * 
	 * @param x coordinate of a point, type double.
	 */
	public void set_x(double x) {

		this.x = x;
	}

	/**
	 * Set the value of the y coordinate in a Point.
	 * 
	 * @param y coordinate of a point, type double.
	 */
	public void set_y(double y) {

		this.y = y;
	}

	/**
	 * Get the current coordinates.
	 */
	public String toString() {

		return "(" + this.x + ", " + this.y + ")";
	}
}
