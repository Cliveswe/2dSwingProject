package com.cliveleddy.gmail;

import javax.swing.SwingUtilities;

import com.cliveleddy.gmail.user_interface.JPaintFrame;

/**
 * <h1>Step 1</h1> This application creates different shapes and calls various
 * methods to print circumference, print area and draw the shapes to the
 * standard output.
 * <p>
 * Giving proper comments in your program makes it more user friendly and it is
 * assumed as a high quality code.
 * <p>
 * <h2>Updates for Custom Exception</h2> Modified the method printArea to catch
 * the exception ShapeException. if the exception is caught then a message
 * informing the user that shape area could not be calculated. Otherwise, the
 * shapes area is shown.
 * <p>
 * <h1>Step 4</h1> This class is the starting point for the drawing application.
 * It creates our JFrame and makes it visible.
 * 
 * @author Clive Leddy
 * @version 1.0
 */

public class Step5 {

	public static void main(String[] args) {
		// Make sure GUI is created on the event dispatching thread.
		// This will be explained in Java III (the lesson about threads).
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//
				new JPaintFrame().Initialise();
			}
		});

	}
}