
package com.cliveleddy.gmail.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * <h1>Class Rectangle</h1> This class represents a Circle. It extends the
 * abstract class Shape.
 * <p>
 * <h2>Updates for Collection</h2> Change the call to variable points from and
 * array of Point to an ArrayList of type Point.
 * <p>
 * <h2>Updates for Custom Exception</h2> Added a throwable to methods
 * getCircumference and getArea. In addition, the toString method has been
 * amended to reflect the changes to the getArea method.
 * <h2>Added a copy and clone constructors</h2> *
 * <p>
 * <h2>Step 9</h2> Made use of the lambda functions from the abstract class
 * Shape.
 * 
 * @author Clive Leddy
 * @version 1.3
 */
public class Circle extends Shape {

	private static final double pi = 3.14;

	/**
	 * Create a circle.
	 * 
	 * @param startx x coordinate of the starting point of a circle.
	 * @param endy   y coordinate of the starting point of a circle.
	 * @param color  the colour of the circle.
	 */
	public Circle(double startx, double endy, String color) {

		super(startx, endy, color);
	}

	/**
	 * Copy constructor.
	 * 
	 * @param circle and Object of type Circle.
	 */
	public Circle(Circle circle) {
		super(circle);
	}

	/**
	 * Clone this Object.
	 */
	public Shape clone() {
		return new Circle(this);

	}

	/**
	 * Calculate the radius of the circle.
	 * 
	 * @return the radius of a circle as an int.
	 * @throws ShapeException the second Point is undefined.
	 */
	public double getRadius() throws ShapeException {

		if (secondPoint.get() != null) {

			return calculateRadius(startX.get(), startY.get(), secondX.get(), secondY.get());
		}

		throw new ShapeException(END_POINT_ERROR_MESSAGE);
	}

	/**
	 * Calculate the radius of the circle from the given coordinates. Use the
	 * equation r^2 = (x-h)^2 + (y-k)^2
	 * 
	 * @param h the value on the x-axis of a circles centre point as a double.
	 * @param k the value on the y-axis of a circles centre point as a double.
	 * @param x the coordinate of a point on the on a circle as a double.
	 * @param y the coordinate of a point on the on a circle as a double.
	 * @return radius as a double.
	 * 
	 */
	private double calculateRadius(Double h, Double k, Double x, Double y) {

		// find the differences between origo and a point on the circle
		double Dx = x - h;

		double Dy = y - k;

		// calculate Dx^2 and Dy^2
		double xx = Math.pow(Dx, 2);

		double yy = Math.pow(Dy, 2);

		return Math.sqrt(xx + yy);

	}

	/**
	 * Render the circle to standard out.
	 */
	@Override
	public void draw() {

		System.out.print(toString());
	}

	@Override
	public void draw(Graphics g) {

		Double width = secondX.get() - startX.get();

		Double height = secondY.get() - startY.get();

		// anti-aliasing
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Graphics2D g2 = (Graphics2D) g.create();

		g2.setRenderingHints(rh);

		g2.setColor(Color.decode(getColor()));

		g2.fillOval(startX.get().intValue(), startY.get().intValue(), width.intValue(), height.intValue());
	}

	/**
	 * Calculate the circumference of a circle using the equation C = 2pi * r
	 * 
	 * @param the circumference of a circle as a double.
	 * @throws ShapeException on error.
	 */
	@Override
	public double getCircumference() throws ShapeException {

		double res = -1;

		try {

			if (getRadius() != -1) {

				res = 2 * getPi() * getRadius();
			}

		} catch (ShapeException e) {

			throw new ShapeException(e);
		}

		return res;
	}

	/**
	 * Calculate the area of a circle, A = pi * r^2.
	 * 
	 * @return area of a circle otherwise.
	 * @throws ShapeException on error.
	 */
	@Override
	public double getArea() throws ShapeException {

		double res = -1;

		try {

			if (getRadius() != -1) {

				res = Math.pow(getRadius(), 2) * getPi();
			}

		} catch (ShapeException e) {

			throw new ShapeException(e);
		}

		return res;
	}

	/**
	 * Get the value for pi.
	 * 
	 * @return 3.14 as double.
	 */
	public static double getPi() {

		return pi;
	}

	public String toString() {

		String res = "Circle[";

		String del = String.format("; ");

		String na = String.format("N/A");

		String startP_NA = String.format("start=%s", na);

		String endP_NA = String.format("end=%s", na);

		String radius_NA = String.format("Radius=%s", na);

		double radius;

		try {

			radius = getRadius();
		} catch (ShapeException e) {

			radius = 0.0;
		}

		// start point
		res += startPoint.get() != null ? String.format("start=%.1f, %.1f", startX.get(), startY.get()) : startP_NA;

		res += del;

		// end point
		res += secondPoint.get() != null ? String.format("end=%.1f, %.1f", secondX.get(), secondY.get()) : endP_NA;

		res += del;

		// get radius
		res += radius != 0 ? String.format("radius=%.1f", radius) : radius_NA;

		res += del;

		// get colour
		res += String.format("color=%s", getColor());

		res += "]\n";

		return res;
	}
}
