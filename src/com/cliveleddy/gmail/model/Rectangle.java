package com.cliveleddy.gmail.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * <h1>Class Rectangle</h1> This class represents a rectangle. It extends the
 * abstract class Shape.
 * 
 * <h2>Updates for Collection</h2> Change the call to variable points from and
 * array of Point to an ArrayList of type Point.
 * <p>
 * <h2>Updated for Custom Exception</h2> Added throwable code to the following
 * methods. getHeight, getWidght, getCircumference and getArea. In addition, the
 * toString method has been amended to reflect the changes to the getWidth and
 * getHeight methods.
 * 
 * <h2>Added a copy and clone constructors</h2>
 * <p>
 * <h2>Step 9</h2> Made use of the lambda functions from the abstract class
 * Shape.
 * 
 * @author Clive Leddy
 * @version 2.3
 */
public class Rectangle extends Shape {

	/**
	 * Create a rectangle.
	 * 
	 * @param startPoint upper left point as an object Point.
	 */
	public Rectangle(Point startPoint, String color) {

		super(startPoint, color);
	}

	/**
	 * Create a rectangle.
	 * 
	 * @param startx upper left point x as a double.
	 * @param starty upper left point y as a double.
	 */
	public Rectangle(double startx, double starty, String color) {

		super(startx, starty, color);
	}

	/**
	 * Copy constructor.
	 * 
	 * @param rectangle an object type Rectangle.
	 */
	public Rectangle(Rectangle rectangle) {
		super(rectangle);
	}

	/**
	 * Clone this Object.
	 */
	public Rectangle clone() {
		return new Rectangle(this);

	}

	/**
	 * Get the height of the rectangle.
	 * 
	 * @return height as a double.
	 * @throws ShapeException the second Point is undefined.
	 */
	Double getHeight() throws ShapeException {

		if (startPoint.get() != null) {

			return Math.abs(calculateHeight());
		}

		throw new ShapeException(END_POINT_ERROR_MESSAGE);
	}

	/**
	 * Calculate the height of the rectangle in the coordinate system.
	 *
	 * @return the height as a double.
	 */
	private double calculateHeight() {

		// transpose the y coordinates to the positive quadrant if the y value
		// of the end point is negative.
		if (secondY.get() < 0) {

			return Math.abs(secondY.get()) + startY.get();
		}

		return secondY.get() - startY.get();
	}

	/**
	 * Get the width of the rectangle.
	 * 
	 * @return width as a double.
	 * @throws ShapeException the second Point is undefined.
	 */
	Double getWidth() throws ShapeException {

		if (secondPoint.get() != null) {

			return Math.abs(calculateWidth());
		}

		throw new ShapeException(END_POINT_ERROR_MESSAGE);
	}

	/**
	 * Calculate the width of the rectangle in the coordinate system.
	 *
	 * @return the height as a double.
	 */
	private double calculateWidth() {
		// transpose the x coordinates to the positive quadrant if the x value
		// of the start point is negative.
		if (startX.get() < 0) {

			return Math.abs(startX.get()) + secondX.get();
		}

		return secondX.get() - startX.get();
	}

	/**
	 * Calculate the circumference of the rectangle.
	 * 
	 * @return the circumference as a double.
	 * @throws ShapeException on error.
	 */
	@Override
	public double getCircumference() throws ShapeException {

		double res = -1;

		try {

			if (checkBoundaries()) {

				res = 2 * getHeight() + 2 * getWidth();
			}

		} catch (ShapeException e) {

			throw new ShapeException(e);
		}

		return res;
	}

	/**
	 * Calculate the area of the rectangle.
	 * 
	 * @return the area as a double.
	 * @throws ShapeException on error.
	 */
	@Override
	public double getArea() throws ShapeException {

		double res = -1;

		try {

			if (checkBoundaries()) {

				res = getHeight() * getWidth();
			}

		} catch (ShapeException e) {

			throw new ShapeException(e);
		}

		return res;
	}

	/**
	 * Check the rectangles boundaries.
	 * 
	 * @return true if the boundaries are valid.
	 * @throws ShapeException if either the width of height of the rectangle cannot
	 *                        be calculated.
	 */
	private boolean checkBoundaries() throws ShapeException {

		boolean ans = false;

		try {

			getHeight();

			getWidth();

			ans = true;
		} catch (ShapeException e) {

			throw new ShapeException(e);
		}

		return ans;
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

		int height = 0;

		try {

			height = getHeight().intValue();

		} catch (ShapeException e) {

			System.err.println("Converting height to int failed!");

			e.printStackTrace();
		}

		int width = 0;

		try {

			width = getWidth().intValue();

		} catch (ShapeException e) {

			System.err.println("Converting width to int failed!");

			e.printStackTrace();
		}

		Graphics2D g2 = (Graphics2D) g.create();

		g2.setColor(Color.decode(getColor()));

		g2.fillRect(startX.get().intValue(), startY.get().intValue(), width, height);

	}

	/**
	 * Print as a string the start and end coordinates of the rectangle. In addition
	 * print the area and circumference of the rectangle as well as its colour.
	 */
	public String toString() {

		String res = "Rectangle [";

		String del = String.format("; ");

		String na = String.format("N/A");

		String startP_NA = String.format("start=%s", na);

		String endP_NA = String.format("end=%s", na);

		String width_NA = String.format("width=%s", na);

		String height_NA = String.format("height=%s", na);

		double height, width;

		try {

			height = getHeight();
		} catch (ShapeException e) {

			height = 0.0;

		}

		try {

			width = getWidth();
		} catch (ShapeException e) {

			width = 0.0;
		}

		// start point
		res += startPoint.get() != null ? String.format("start=%.1f, %.1f", startX.get(), startY.get()) : startP_NA;
		res += del;
		// end point
		res += secondPoint.get() != null ? String.format("end=%.1f, %.1f", secondX.get(), secondY.get()) : endP_NA;
		res += del;

		// get width
		res += width != 0 ? String.format("width=%.1f", width) : width_NA;

		res += del;

		// get height
		res += height != 0 ? String.format("height=%.1f", height) : height_NA;

		res += del;

		// get colour
		res += String.format("color=%s", getColor());

		res += "]\n";

		return res;
	}
}
