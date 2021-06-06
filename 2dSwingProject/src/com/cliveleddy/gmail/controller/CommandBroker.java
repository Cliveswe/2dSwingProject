package com.cliveleddy.gmail.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>Class Broker.</h1> This is a container holing a directory of commands
 * accessed by a item key.
 * 
 * @author Clive Leddy
 * @version 1.0
 */
public class CommandBroker {

	private Map<String, ICommand> menuCommands = new HashMap<String, ICommand>();

	/**
	 * Add a command to the container of commands.
	 * 
	 * @param item       item a item key as type {@code String}.
	 * @param menuCommand the command to be stored in the container as a type
	 *                   {@code ICommand}.
	 */
	public void addCommand(String item, ICommand menuCommand) {

		menuCommands.put(item, menuCommand);
	}

	/**
	 * Run a requested command.
	 * 
	 * @param item a item key as type {@code String}.
	 */
	public void runCommand(String item) {

		if (menuCommands.containsKey(item)) {

			menuCommands.get(item).execute();
		}
	}

}