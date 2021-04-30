package com.cliveleddy.gmail.view;

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
	
	// generic data package
	private T data;
	
	private String id;

	public MyDrawingAreaEvent(Object source, T p) {
	
		super(source);
		
		id = null;
		
		data = p;
	}

	public MyDrawingAreaEvent(Object source, String id, T p) {
		
		super(source);
		
		setId(id);
		
		data = p;
	}

	/**
	 * Get the generic data package.
	 * 
	 * @return data package as a generic type.
	 */
	public T getData() {
		
		return data;
	}

	public String getId() {
		
		return id;
	}

	public void setId(String id) {
		
		this.id = id;
	}
}
