package com.cliveleddy.gmail.controller;

/**
 * <h1>Class ICommand</h1> A interface for a command to execute.
 * <h2>Step 9</h2> Added {@code FunctionalInterface} so that the compiler
 * observes that the class is implemented correctly.
 * 
 * @author Clive Leddy
 * @version 1.0
 *
 */
@FunctionalInterface
public interface ICommand {

	void execute();
}
