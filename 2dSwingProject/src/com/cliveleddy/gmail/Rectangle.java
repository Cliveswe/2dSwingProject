package com.cliveleddy.gmail;

import java.awt.Graphics;

/**
 * <h1>Class Rectangle</h1>
 * This class represents a rectangle. It extends the abstract class Shape.
 * 
 * <h2>Updates for Collection</h2>
 * Change the call to variable points from and array of Point to an ArrayList of type Point.

 * @author Clive Leddy
 * @version 2.0
 */
public class Rectangle extends Shape {
	/**
	 * Create a rectangle.
	 * @param startPoint upper left point as an object Point.
	 */
	public Rectangle(Point startPoint, String color) {
		super(startPoint, color);
	}

	/**
	 * Create a rectangle.
	 * @param startx upper left point x as a double.
	 * @param starty upper left point y as a double.
	 */
	public Rectangle (double startx, double starty, String color) {
		super(startx, starty, color);
	}

	/**
	 * Get the height of the rectangle.
	 * @return height as a double, -1 if the second Point is not defined.
	 */
	double getHeight() {
		if(points.get(1) != null) {
			return Math.abs(calculateHeight());
		}
		return -1;
	}

	/**
	 * Calculate the height of the rectangle in the coordinate system.
	 *
	 * @return the height as a double.
	 */
	private double calculateHeight() {
		//transpose the y coordinates to the positive quadrant if the y value
		//of the end point is negative.		
		if(points.get(1).get_y() < 0) {
			return Math.abs(points.get(1).get_y()) + points.get(0).get_y();
		}

		return points.get(1).get_y()-points.get(0).get_y();
	}

	/**
	 * Get the weight of the rectangle.
	 * @return weight as a double, -1 if the second point is not defined.
	 */
	double getWidth() {

		if(points.get(1) != null) {
			return Math.abs(calculateWidth());
		}
		return -1;
	}

	/**
	 * Calculate the width of the rectangle in the coordinate system.
	 *
	 * @return the height as a double.
	 */
	private double calculateWidth() {
		//transpose the x coordinates to the positive quadrant if the x value
		//of the start point is negative.  
		if(points.get(0).get_x() < 0) {
			return Math.abs(points.get(0).get_x()) + points.get(1).get_x();
		}
		return points.get(1).get_x() - points.get(0).get_x();
	}


	/**
	 * Calculate the circumference of the rectangle.
	 * @return the circumference as a double, -1 on error.
	 */
	@Override
	public double getCircumference() {
		if(checkBoundaries()) {
			return 2 * getHeight() + 2 * getWidth();	
		}

		return -1;
	}

	/**
	 * Calculate the area of the rectangle.
	 * @return the area as a double, -1 on error.
	 */
	@Override
	public double getArea() {
		if(checkBoundaries()) {
			return getHeight() * getWidth();	
		}

		return -1;
	}

	/**
	 * Check the rectangles boundaries.
	 * @return -1 if either the width of height of the rectangle cannot be calculated, otherwise 0.
	 */
	private boolean checkBoundaries() {
		if((getWidth() == -1) || (getHeight() == -1)) {
			return false;
		}
		return true;
	}

	/**
	 * Render the triangle to standard out.
	 */
	@Override
	public void draw() {
		System.out.print(toString());
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

	/**
	 * Print as a string the start and end coordinates of the rectangle. In addition  
	 * print the area and circumference of the rectangle as well as its colour. 
	 */
	public String toString() {
		String res = "Rectangle [";

		String del = String.format("; ");
		String na = String.format("N/A");

		String startP_NA = String.format("start=%s",na);
		String endP_NA = String.format("end=%s",na);
		String width_NA = String.format("width=%s",na);
		String height_NA = String.format("height=%s",na);

		//start point
		res += points.get(0) != null ?String.format("start=%.1f, %.1f", points.get(0).get_x(), points.get(0).get_y()): startP_NA;
		res += del;
		//end point
		res += points.get(1) != null? String.format("end=%.1f, %.1f", points.get(1).get_x(), points.get(1).get_y()): endP_NA;
		res += del;
		//get width
		res += getWidth() != -1? String.format("width=%.1f", getWidth()): width_NA;
		res += del;
		//get height
		res += getHeight() != -1? String.format("height=%.1f", getHeight()): height_NA;
		res += del;
		//get colour
		res += String.format("color=%s", getColor());
		res += "]\n";

		return res;
	}
}
