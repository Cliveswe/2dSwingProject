package se.miun.studentid.dt187g.clle1101;

/**
 * <h1>Class Shape</h1>
 * This is a base class that is used to describe different geometric figures.
 * It implements the interface class IDrawable.
 * <p>
 * Example: "Create a circle or rectangle."
 * 
 * @author Clive Leddy (clle1101)
 * @version 1.0
 */
public abstract class Shape implements IDrawable {

	//class variables
	protected String color;//shape colour.
	protected Point[] points;//a list of coordinates to draw the shape.

	/**
	 * Point p is the starting point for a shape of colour color.
	 * @param p a shapes coordinates as a Point object.
	 * @param color colour of the shape as a string.
	 */
	public Shape(Point p, String color) {
		super();
		this.color = color;
		points = new Point[2];
		points[0] = p;
		points[1] = null;
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
		points = new Point[2];
		points[0] = new Point(x,y);
		points[1] = null;
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
	 * @return the circumference as a double
	 */
	public abstract double getCircumference();

	/**
	 * Get the area of the shape. 
	 * @return the area as a double
	 */
	public abstract double getArea();

	/**
	 * Add the end point to the array of points.
	 * @param p object point.
	 */
	public void addPoint(Point p) {
		this.points[1] = p;
	}
	
	/**
	 * Add the end point to the array of points with the given coordinates.
	 * @param x coordinate of a point as a double.
	 * @param y coordinate of a point as a double.
	 */
	public void addPoint(double x, double y) {
		this.points[1] = new Point(x, y);
	}
	
}
