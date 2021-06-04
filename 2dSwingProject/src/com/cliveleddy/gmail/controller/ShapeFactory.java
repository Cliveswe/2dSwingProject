package com.cliveleddy.gmail.controller;

import com.cliveleddy.gmail.model.Circle;
import com.cliveleddy.gmail.model.Rectangle;
import com.cliveleddy.gmail.model.Shape;

/**
 * <h1>Class ShapeFactory.</h1> Shape factory that creates new shape objects
 * that are defined in the project model.
 * 
 * @author Clive Leddy
 * @version 1.0
 */
public class ShapeFactory {
	/**
	 * Create a new Shape object. The new object type is determinate by the
	 * parameter shape.
	 * 
	 * @param shape name of an object to be created as type String.
	 * @return a new object that is a base class type Shape.
	 */
	public Shape getShape(String shape) {
		Shape s = null;

		if (shape.equalsIgnoreCase(MenuBarItemEnum.CIRCLE.label())) {

			s = new Circle(0.0, 0.0, "");

		} else if (shape.equalsIgnoreCase(MenuBarItemEnum.RECTANGLE.label())) {

			s = new Rectangle(0.0, 0.0, "");
		}

		return s;

	}
}
