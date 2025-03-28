package com.cliveleddy.gmail.model;

/**
 * <h1>Class ShapeException</h1> This is a custom Exception that wraps the
 * standard Java Exception and adds an Error code for additional expansion.
 * Throwable is also added so that the origin cause from the current exception
 * is caught.
 * <p>
 * The benefit of the Throwable is adding a higher abstraction layer for the
 * exception. However, the original exception must be include in order to
 * preserve the cause. This will keep trace when debugging the program when the
 * exception is raised.
 * 
 * @author Clive Leddy
 * @version 1.0
 */
public class ShapeException extends Exception {
	private String msg;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3879073651253019757L;

	public ShapeException() {

		super();
	}

	/**
	 * 
	 * @param message text message describing the error as String.
	 */
	public ShapeException(String message) {

		super(message);

		msg = message;
	}

	/**
	 * Used when re-throwing a exception.
	 * 
	 * @param cause origin description of the cause of the error as Throwable.
	 */
	public ShapeException(Throwable cause) {

		super(cause);

		if (cause.toString() != null) {

			msg = cause.toString();
		}
	}

	/**
	 * Used when re-throwing a exception.
	 * 
	 * @param message text message describing the error as String.
	 * @param cause   origin description of the cause of the error as Throwable.
	 */
	public ShapeException(String message, Throwable cause) {

		super(message, cause);

		if (cause.toString() != null) {

			msg = message + cause.toString();
		} else {

			msg = message;
		}
	}

	public String toString() {

		return msg;
	}
}
