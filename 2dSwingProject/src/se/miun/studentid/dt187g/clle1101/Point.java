/**
 * 
 */
package se.miun.studentid.dt187g.clle1101;

/**
 * <h1>Class Point</h1>
 * This class represents a geometric point in a coordinate system.
 * Point(x,y)
 * <p>
 * Example: "Point (1,0,1,0)"
 * 
 * @author Clive Leddy (clle1101)
 * @version 1.0
 */
public class Point {

	private double x, y;

	/**
	 *Create a default point at Origo. 
	 */
	Point()
	{
		x = 0;
		y = 0;
	}
	
	/**
	 *Create a point at given coordinates. 
	 */
	Point (double x, double y){
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
	 * Get the value for point y.
	 */
	public double get_y() {
		return y;
	}

	/**
	 * Set the value for point x.
	 */
	public void set_x(double x) {
		this.x = x;	
	}
	
	/**
	 * Set the value for point y.
	 */
	public void set_y(double y) {
		this.y = y;	
	}
	
	/**
	 * Display the current coordinates.
	 */
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
}
