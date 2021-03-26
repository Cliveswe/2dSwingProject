package com.cliveleddy.gmail.user_interface;

import java.awt.Point;
import java.util.EventObject;

/**
 * <h1>An event object that contains the coordinates of mouse location as a type
 * Point.</h1>
 * 
 * @author Clive Leddy
 * @version 1.0
 *
 */
public class MouseLocationEvent extends EventObject {
	private static final long serialVersionUID = 9118907373388612418L;
	// mouse coordinates as a Point object
	private Point mouseLocation = new Point();

	public MouseLocationEvent(Object source, Point p) {
		super(source);
		mouseLocation = p;
	}

	/**
	 * Get the mouse location.
	 * 
	 * @return mouse Location as a type Point.
	 */
	public Point getLocation() {
		return mouseLocation;
	}
}
