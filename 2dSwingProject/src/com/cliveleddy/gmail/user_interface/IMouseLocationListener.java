package com.cliveleddy.gmail.user_interface;

import java.util.EventListener;

/**
 * <h1>Interface that specifies what class should listen to the event
 * object.</h1>
 * 
 * @author Clive Leddy
 * @version 1.0
 *
 */
public interface IMouseLocationListener extends EventListener {
	/**
	 * Method that is used to retrieve data from the event object.
	 * 
	 * @param event object that describes the data in the event as type
	 *              MouseLocationEvent.
	 */
	public void mouseLocationEventOccurred(MouseLocationEvent event);
}
