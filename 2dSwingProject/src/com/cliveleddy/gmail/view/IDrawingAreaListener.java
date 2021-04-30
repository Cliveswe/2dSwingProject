package com.cliveleddy.gmail.view;

import java.util.EventListener;

/**
 * <h1>Interface that specifies what class should listen to the event
 * object.</h1>
 * 
 * @author Clive Leddy
 * @version 1.0
 *
 */
public interface IDrawingAreaListener<T> extends EventListener {

	/**
	 * Method that is used to retrieve data from the event object.
	 * 
	 * @param drawingAreaEvent object that describes the generic data in the event
	 *                         as type MyDrawingAreaEvent.
	 */
	public void drawingAreaEventOccurred(T drawingAreaEvent);
}
