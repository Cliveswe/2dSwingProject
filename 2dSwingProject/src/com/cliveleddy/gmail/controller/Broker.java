package com.cliveleddy.gmail.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>Class Broker.</h1>
 * 
 * @author Clive Leddy
 * @version 1.0
 */
public class Broker {

	private Map<String, ICommand> menuCommands = new HashMap<String, ICommand>();

	public void addCommand(String item, ICommand menCommand) {

		menuCommands.put(item, menCommand);
	}

	public void runCommand(String item) {

		if (menuCommands.containsKey(item)) {

			menuCommands.get(item).execute();
		}
	}

}