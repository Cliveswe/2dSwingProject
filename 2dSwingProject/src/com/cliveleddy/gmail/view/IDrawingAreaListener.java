package com.cliveleddy.gmail.view;

import java.util.EventListener;

/**
 * <h1>Interface IDrawingAreaListener</h1> Specifies what class should listen to
 * the event object.
 * 
 * @author Clive Leddy
 * @version 1.0
 *
 */
@FunctionalInterface
public interface IDrawingAreaListener<T> extends EventListener {

	/**
	 * Method that is used to retrieve data from the event object.
	 * 
	 * @param drawingAreaEvent object that describes the generic data in the event
	 *                         as type {@code MyDrawingAreaEvent}.
	 */
	public void drawingAreaEventOccurred(T drawingAreaEvent);
}
