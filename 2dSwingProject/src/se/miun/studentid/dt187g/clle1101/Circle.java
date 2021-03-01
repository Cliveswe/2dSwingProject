
package se.miun.studentid.dt187g.clle1101;

import java.awt.Graphics;

/**
 * <h1>Class Rectangle</h1>
 * This class represents a Circle. It extends the abstract class Shape.
 * 
 * @author Clive Leddy (clle1101)
 * @version 1.0
 */
public class Circle extends Shape{

	private static final double pi = 3.14;

	/**
	 * Create a circle.
	 * @param startx x coordinate of the starting point of a circle.
	 * @param endy y coordinate of the starting point of a circle.
	 * @param color the colour of the circle.
	 */
	public Circle(double startx, double endy, String color) {
		super(startx, endy, color);
	}

	public double getRadius() {
		if(points[1] != null) {
			return calculateRadius(points[0].get_x(), points[0].get_y(), points[1].get_x(), points[1].get_y());
		}
		return -1;
	}

	/**
	 * Calculate the radius of the circle from the given coordinates.
	 * Use the equation r^2 = (x-h)^2 + (y-k)^2
	 * @param h the value on the x-axis of a circles centre point as a double. 
	 * @param k the value on the y-axis of a circles centre point as a double.
	 * @param x the coordinate of a point on the on a circle as a double.
	 * @param y the coordinate of a point on the on a circle as a double.
	 * @return radius as a double.
	 * 
	 */
	private double calculateRadius(double h, double k, double x, double y) {

		//find the differences between origo and a point on the circle 
		double	Dx = x - h;
		double	Dy = y - k;

		//calculate Dx^2 and Dy^2
		double	xx = Math.pow(Dx, 2);
		double	yy = Math.pow(Dy, 2);;

		return Math.sqrt(xx + yy);

	}

	@Override
	public void draw() {
		System.out.print(toString());
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

	/**
	 * Calculate the circumference of a circle using the equation C = 2pi * r
	 * @param the circumference of a circle as a double otherwise -1 on error.
	 */
	@Override
	public double getCircumference() {

		if(getRadius() != -1) {
			return 2 * getPi() * getRadius();
		}

		return -1;
	}

	/**
	 * Calculate the area of a circle, A = pi * r^2.
	 * 
	 * @return area of a circle otherwise -1 on error.
	 */
	@Override
	public double getArea() {

		if(getRadius() != -1) {
			return Math.pow(getRadius(), 2) * getPi();
		}
		return -1;
	}

	public static double getPi() {
		return pi;
	}

	public String toString() {
		String res = "Circle[";		

		String del = String.format("; ");
		String na = String.format("N/A");

		String startP_NA = String.format("start=%s",na);
		String endP_NA = String.format("end=%s",na);
		String radius_NA = String.format("Radius=%s", na);

		//start point
		res += points[0] != null ? String.format("start=%.1f, %.1f", points[0].get_x(), points[0].get_y()) : startP_NA;
		res += del;
		//end point
		res += points[1] != null ? String.format("end=%.1f, %.1f", points[1].get_x(), points[1].get_y()) : endP_NA;
		res += del;
		//get radius
		res += getRadius() != -1 ?  String.format("radius=%.1f", getRadius()): radius_NA;
		res += del;
		//get colour
		res += String.format("color=%s", getColor());
		res += "]\n";

		return res;
	}

}
