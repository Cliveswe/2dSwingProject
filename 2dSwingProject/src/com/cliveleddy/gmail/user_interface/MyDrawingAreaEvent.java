package com.cliveleddy.gmail.user_interface;

import java.util.EventObject;

/**
 * <h1>An event object that contains the a generic data package.</h1>
 * 
 * @author Clive Leddy
 * @version 1.0
 *
 */
public class MyDrawingAreaEvent<T> extends EventObject {
	private static final long serialVersionUID = 9118907373388612418L;
	// mouse coordinates as a Point object
	// private Point data = new Point();
	private T data;

	// public MyDrawingAreaEvent(Object source, Point p) {
	public MyDrawingAreaEvent(Object source, T p) {
		super(source);
		data = p;
	}

	/**
	 * Get the generic data package.
	 * 
	 * @return data package as a generic type.
	 */
	// public Point getLocation() {
	public T getData() {
		return data;
	}
}
