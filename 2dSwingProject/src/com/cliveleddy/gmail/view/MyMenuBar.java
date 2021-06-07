package com.cliveleddy.gmail.view;

import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.event.EventListenerList;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.cliveleddy.gmail.controller.CommandBroker;
import com.cliveleddy.gmail.controller.FileHandler;
import com.cliveleddy.gmail.controller.FileNameCreator;
import com.cliveleddy.gmail.controller.MenuBarItemEnum;
import com.cliveleddy.gmail.model.Drawing;
import com.cliveleddy.gmail.model.ShapeException;

/**
 * <h1>Class MyMenuBar</h1>
 * <h2>Step 4</h2> Create the applications menu bar and add items to each menu.
 * It extends the JMenuBar swing.
 * <p>
 * <h2>Step 5</h2> Add the action listeners to each of the menu items. These
 * Listeners will in turn perform tasks.
 * <p>
 * <h2>Step 6</h2>Added additional logic to the menu items. In addition,
 * replacing both the MBTextEnum and the Dictionary Text with a Enum class
 * called MenuBarItemEnum. This makes for a cleaner code style. Also the Enum
 * class allows for additional logic at a later date. In the actionPerfromed
 * method all the if logic has been replaced with a
 * <h3>Command Design Pattern.</h3> This pattern allows for additional logic to
 * be added to classes that implement the different commands from the menu.
 * <p>
 * <h2>Step 8</h2> The classes Broker, ICommand and MenyBarItemEnum have been
 * relocated to the controller package.
 * <p>
 * <h2>Step 9</h2> Moved the interface ILabled to a file. The commands that are
 * stored in the Broker collection have been replaced with lambda expressions
 * that implement {@code interface ICommand} class. The broker is used in the
 * method {@code public void actionPerformed(ActionEvent e)} Replaced and
 * removed the following internal classes with lambda expressions:
 * {@code class Load implements ICommand}.
 * {@code class SaveAs implements ICommand} Save is a statement block that also
 * calls a Functional interface lambda expression.
 * {@code class Exit implements ICommand}.
 * {@code class Undo implements ICommand}.
 * {@code class Info implements ICommand}.
 * {@code class UpdateName implements ICommand}.
 * {@code class UpdateAuthor implements ICommand}.
 * {@code class NewNameAuthor implements ICommand}.
 * 
 * Replacing classes with lambda functions solve the vertical problem caused by
 * internal classes.
 * 
 * Move the broker initialisation and population to a separate method
 * {@code initialiseBroker()}. This change also improves the responsive of the
 * application.
 * <p>
 * Added a lambda function that implements the functional interface IMyMenuItem.
 * The functional interface IMyMenuItem replaces the inner class MyMenuItem. The
 * inner class MyMenuItem has now been deleted.
 * <p>
 * Add a new menu that filters shapes to be shown. Updated the broker with the
 * lambda function for filtering shape objects.
 * <p>
 * 
 * 
 * @author Clive Leddy
 * @version 2.3
 */
public class MyMenuBar extends JMenuBar implements ILabeled {
	private static final long serialVersionUID = -1336076929035881984L;

	/**
	 * 
	 * <h1>Functional Interface</h1> This is a Java functional interface. It is
	 * implemented by lambda functions.
	 * 
	 * @param <R> return type R.
	 * @param <T> method parameter type T.
	 * 
	 * @author Clive Leddy
	 * @version 1.0
	 *
	 */
	public interface IMyFunctionalInterface<R, T> {
		/**
		 * Functional interface has an abstract method that takes an argument of T and
		 * returns result of type R
		 * 
		 * @param t of type T.
		 * @return type R.
		 */
		public R execute(T t);
	}

	/**
	 * <h1>Interface IMyMenuItem</h1> Create a JMenuItem and give it a title.
	 * 
	 * @author Clive Leddy
	 * @version 1.0
	 *
	 */
	public interface IMyMenuItem {
		/**
		 * Create a JMenuItem with a title.
		 * 
		 * @param title menu items title as type {@code String}
		 * @return a {@code JMenuItem}.
		 */
		public JMenuItem createItem(String title);
	}

	/**
	 * <h1>Interface IMyMenuRadioButtonItem</h1> Create a
	 * {@code JRadioButtonMenuItem} and give it a title.
	 * 
	 * @author Clive Leddy
	 * @version 1.0
	 *
	 */
	public interface IMyMenuRadioButtonItem {
		/**
		 * Create a {@code JRadioButtonMenuItem} with a title.
		 * 
		 * @param title menu items title as type {@code String}
		 * @return a {@code JMenuItem}.
		 */
		public JRadioButtonMenuItem createItem(String title);
	}

	// reference to the main JFrame
	private JPaintFrame jPaintFrame;

	// a reference to a drawing d
	// private Drawing d;

	// LISTENER LISTS
	// a list of listeners for the menu bar drawing object.
	private EventListenerList menuBarDrawingListenerList = new EventListenerList();
	private CommandBroker broker;// Broker of commands.

	/*
	 * Lambda functions.
	 */
	/**
	 * Lambda function that implements the functional interface IMyMenuItem.
	 *
	 * @param id is the title of the menu item as type {@code String}
	 * @return the menu item {@code JMenuItem}
	 */
	private IMyMenuItem myMenuItem = (id) -> {
		JMenuItem jmi = new JMenuItem();// A new menu item.

		// Set the title of the menu item. A menu item is a button!
		jmi.setText(id);

		// Add a command from the broker to the menu item.
		jmi.addActionListener(e -> broker.runCommand(e.getActionCommand()));

		return jmi;
	};

	/**
	 * Lambda function that implements the functional interface
	 * {@code IMyMenuRadioButtonItem}.
	 *
	 * @param id is the title of the radio button item as type {@code String}
	 * @return the menu item {@code IMyMenuRadioButtonItem}
	 * 
	 */
	private IMyMenuRadioButtonItem myMenuRadioButtonItem = (id) -> {
		JRadioButtonMenuItem rdmi;
		// All shapes
		rdmi = new JRadioButtonMenuItem(id);
		rdmi.addActionListener(e -> broker.runCommand(e.getActionCommand()));

		return rdmi;
	};

	/**
	 * Class constructor.
	 * 
	 * @param jPaintFrame application container window.
	 */
	public MyMenuBar(JPaintFrame jPaintFrame) {

		super();

		this.jPaintFrame = jPaintFrame;

		initialiseMenuBar();

	}

	/**
	 * Create the menu bars functions, File and Edit.
	 */
	private void initialiseMenuBar() {
		// Broker
		initialiseBroker();

		// File menu
		add(initialiseFileMenu(new JMenu(MenuBarItemEnum.FILE.label())));

		// Edit menu
		add(initialiseEditMenu(new JMenu(MenuBarItemEnum.EDIT.label())));

		// Filter menu
		add(initialiseFilterMenu(new JMenu(MenuBarItemEnum.SHAPE_FILTER.label())));
	}

	/**
	 * Create a broker and add commands to the broker.
	 */
	private void initialiseBroker() {

		broker = new CommandBroker();

		// New
		broker.addCommand(MenuBarItemEnum.NEW.label(), () -> {
			InputTextDialog inputTextDialogName = new InputTextDialog(MenuBarItemEnum.NEW_DRAWING_NAME_PROMPT.label(),
					jPaintFrame);

			InputTextDialog inputTextDialogAuthor = new InputTextDialog(
					MenuBarItemEnum.NEW_DRAWING_AUTHOR_PROMPT.label(), jPaintFrame);

			inputTextDialogName.newName();

			inputTextDialogAuthor.newAuthor();

			inputTextDialogName.getDrawing().setAuthor(inputTextDialogAuthor.getDrawing().getAuthor());

			// re-set the drawing object.
			setDrawingObject(inputTextDialogName.getDrawing(), MenuBarItemEnum.NEW.label());
		});

		// Update author
		broker.addCommand(MenuBarItemEnum.AUTHOR.label(), () -> {

			InputTextDialog inputTextDialog = new InputTextDialog(MenuBarItemEnum.DRAWING_AUTHOR.label(), jPaintFrame);

			inputTextDialog.updateAuthor();

			setDrawingObject(inputTextDialog.getDrawing(), MenuBarItemEnum.AUTHOR.label());
		});

		// Update name
		broker.addCommand(MenuBarItemEnum.NAME.label(), () -> {

			InputTextDialog inputTextDialog = new InputTextDialog(MenuBarItemEnum.DRAWING_NAME.label(), jPaintFrame);

			inputTextDialog.updateName();

			setDrawingObject(inputTextDialog.getDrawing(), MenuBarItemEnum.NAME.label());
		});

		// SaveAs
		broker.addCommand(MenuBarItemEnum.SAVE_AS.label(), () -> {
			/**
			 * Function interface implementation.
			 */
			IMyFunctionalInterface<String, Drawing> mf = (draw) -> {

				String str = "";
				str = FileNameCreator.createFileName(draw);

				return str += MenuBarItemEnum.DOT_SHAPE.label();
			};

			new InputTextDialog(MenuBarItemEnum.DRAWING_NAME.label(), jPaintFrame)
					.saveAs(mf.execute(jPaintFrame.getDrawingPanel().getDrawing()));
		});

		// Exit
		broker.addCommand(MenuBarItemEnum.EXIT.label(), () -> System.exit(0));

		// Undo
		broker.addCommand(MenuBarItemEnum.UNDO.label(),
				() -> setDrawingObject(new Drawing(), MenuBarItemEnum.UNDO.label()));

		// Load
		broker.addCommand(MenuBarItemEnum.LOAD.label(),
				() -> new InputTextDialog(MenuBarItemEnum.LOAD_PROMPT.label(), jPaintFrame).load());

		// Info
		broker.addCommand(MenuBarItemEnum.INFO.label(), () -> {

			Drawing d = jPaintFrame.getDrawingPanel().getDrawing();

			if ((d != null) && !d.isEmpty()) {

				String str = "";

				if (!d.isEmpty()) {

					str = String.format(d.getName() + " by " + d.getAuthor());
				}
				if (d.getSize() > 0) {

					str += String.format("\nNumber of shapes: %d", d.getSize());

					str += String.format("\nTotal area: %.1f", d.getTotalArea());

					str += String.format("\nTotal circumference: %f", d.getTotalCircumference());
				}

				JOptionPane.showMessageDialog(jPaintFrame, str);
			}
		});

		// Show all shapes.
		broker.addCommand(MenuBarItemEnum.ALL_SHAPES.label(), () -> {

			// Get the drawing.
			Drawing d = jPaintFrame.getDrawingPanel().getDrawing();

			d.resetFilter();
			setDrawingObject(d, MenuBarItemEnum.ALL_SHAPES.label());
		});

		// Show Circle shapes.
		broker.addCommand(MenuBarItemEnum.SHOW_CIRCLE.label(), () -> {

			// Get the drawing.
			Drawing d = jPaintFrame.getDrawingPanel().getDrawing();

			// Set a filter to show shapes of type circle using lambda function.
			d.setFilter(e -> {
				String circle = MenuBarItemEnum.CIRCLE.label().toLowerCase();
				String eShape = e.getClass().getSimpleName().toLowerCase();
				if (circle.compareTo(eShape) == 0) {
					return true;
				}
				return false;
			});

			setDrawingObject(d, MenuBarItemEnum.SHOW_CIRCLE.label());
		});

		// Show Rectangle shapes.
		broker.addCommand(MenuBarItemEnum.SHOW_RECTANGLE.label(), () -> {

			// Get the drawing.
			Drawing d = jPaintFrame.getDrawingPanel().getDrawing();

			// Set a filter to show shapes of type rectangle using lambda function.
			d.setFilter(e -> {

				String rectangle = MenuBarItemEnum.RECTANGLE.label().toLowerCase();
				String eShape = e.getClass().getSimpleName().toLowerCase();

				if (rectangle.compareTo(eShape) == 0) {
					return true;
				}
				return false;
			});

			setDrawingObject(d, MenuBarItemEnum.SHOW_RECTANGLE.label());

		});

	}

	/**
	 * Add the menu items for a File.
	 * 
	 * @param mFile file menu as {@code JMenu}.
	 * @return {@code JMenu}.
	 */
	private JMenu initialiseFileMenu(JMenu mFile) {

		// new Menu Item
		mFile.add(myMenuItem.createItem(MenuBarItemEnum.NEW.label()));

		// save as Menu Item
		mFile.add(myMenuItem.createItem(MenuBarItemEnum.SAVE_AS.label()));

		// load Menu Item
		mFile.add(myMenuItem.createItem(MenuBarItemEnum.LOAD.label()));

		// info Menu Item
		mFile.add(myMenuItem.createItem(MenuBarItemEnum.INFO.label()));

		mFile.addSeparator();

		// exit Menu Item
		mFile.add(myMenuItem.createItem(MenuBarItemEnum.EXIT.label()));

		return mFile;
	}

	/**
	 * Add the menu items for a Edit.
	 * 
	 * @param mEdit file menu as {@code JMenu}.
	 * @return {@code JMenu}.
	 */
	private JMenu initialiseEditMenu(JMenu mEdit) {

		// Undo
		mEdit.add(myMenuItem.createItem(MenuBarItemEnum.UNDO.label()));

		// Drawing title
		mEdit.add(myMenuItem.createItem(MenuBarItemEnum.NAME.label()));

		// Drawing author
		mEdit.add(myMenuItem.createItem(MenuBarItemEnum.AUTHOR.label()));

		return mEdit;
	}

	/**
	 * Add radio button items for Filter.
	 * 
	 * @param mFilter filter menu as {@code JMenu}.
	 * @return {@code JMenu}.
	 */
	private JMenu initialiseFilterMenu(JMenu mFilter) {
		JRadioButtonMenuItem rdmi;

		// A group of radio button menu items.
		ButtonGroup bg = new ButtonGroup();

		// All shapes.
		rdmi = myMenuRadioButtonItem.createItem(MenuBarItemEnum.ALL_SHAPES.label());
		rdmi.setSelected(true);// Default selected.
		bg.add(rdmi);// Add to button group.
		mFilter.add(rdmi);

		// All circle shapes
		rdmi = myMenuRadioButtonItem.createItem(MenuBarItemEnum.SHOW_CIRCLE.label());
		bg.add(rdmi);// Add to button group.
		mFilter.add(rdmi);

		// All rectangle shapes
		rdmi = myMenuRadioButtonItem.createItem(MenuBarItemEnum.SHOW_RECTANGLE.label());
		bg.add(rdmi);// Add to button group.
		mFilter.add(rdmi);

		return mFilter;
	}

	/**
	 * Set the drawing object.
	 * 
	 * @param d is a drawing instance of type {@code Drawing}.
	 */
	public void setDrawingObject(Drawing d) {

		firedDrawingObjectEvent(new MyDrawingAreaEvent<Drawing>(this, d));
	}

	/**
	 * Set the drawing object.
	 * 
	 * @param d  is a drawing instance of type {@code Drawing}.
	 * @param id menu item id from {@code Map<MBTextEnum, String> Text}.
	 */
	public void setDrawingObject(Drawing d, String id) {
		// create new source event object
		MyDrawingAreaEvent<Drawing> dae = new MyDrawingAreaEvent<Drawing>(this, d);

		if (id != null) {

			dae.setId(id);

			firedDrawingObjectEvent(dae);

		} else {

			setDrawingObject(d);
		}
	}

	/**
	 * The Drawing has either been created or modified, inform all the listeners.
	 * 
	 * @param <T>    a generic type as type Drawing
	 * 
	 * @param {@code event mle} object as {@code MyDrawingAreaEvent}.
	 */
	@SuppressWarnings("unchecked")
	public void firedDrawingObjectEvent(MyDrawingAreaEvent<Drawing> mle) {

		Object[] listerners = menuBarDrawingListenerList.getListenerList();

		for (int index = 0; index < listerners.length; index += 2) {

			if (listerners[index] == IDrawingAreaListener.class) {

				((IDrawingAreaListener<MyDrawingAreaEvent<Drawing>>) listerners[index + 1])
						.drawingAreaEventOccurred(mle);
			}
		}
	}

	/**
	 * Add a listener that is listening for a colour selection from the tool bar.
	 * 
	 * @param a reference to a listener of type {@code IDrawingAreaListener}.
	 */
	public void addMenuBarDrawingListener(IDrawingAreaListener<MyDrawingAreaEvent<Drawing>> listener) {

		menuBarDrawingListenerList.add(IDrawingAreaListener.class, listener);
	}

	/**
	 * Remove a listener that is listening for a colour selection from the tool bar.
	 * 
	 * @param a reference to a listener of type {@code IDrawingAreaListener}.
	 */
	public void removeMenuBarDrawingListener(IDrawingAreaListener<MyDrawingAreaEvent<Drawing>> listener) {

		menuBarDrawingListenerList.remove(IDrawingAreaListener.class, listener);
	}

	/**
	 * <h1>Class InputTextDialog.</h1> The logic used when loading a saved drawing
	 * of type Drawing. In addition, the logic used when saving a drawing to a file.
	 * 
	 * @author Clive Leddy
	 * @version 1.0
	 *
	 */
	public class InputTextDialog extends JFileChooser {

		private static final long serialVersionUID = 1L;

		private Drawing d;

		private String inputText;

		private String outText;

		private JPaintFrame jPaintFrame;

		/**
		 * Class constructor.
		 * 
		 * @param outText     text to be displayed in the dialog box.
		 * @param jPaintFrame application container window.
		 */
		InputTextDialog(String outText, JPaintFrame jPaintFrame) {

			d = new Drawing();

			this.outText = outText;

			this.jPaintFrame = jPaintFrame;

		}

		/**
		 * Display a pop up input dialog box used for input.
		 * 
		 * @return the text entered by the user as type {@code String}.
		 */
		private String getInputDialog() {

			return JOptionPane.showInputDialog(jPaintFrame, outText, null);
		}

		/**
		 * Is there any input text.
		 * 
		 * @return true if there is input text, otherwise false.
		 */
		public boolean isInputText() {

			if (inputText != null) {

				if (!inputText.isBlank() && (d != null)) {

					return true;
				}
			}

			return false;
		}

		/**
		 * Get the name of the art piece from the input dialog pop up.
		 */
		public void updateName() {

			// Get the name of the art piece from the input dialog pop up.
			inputText = getInputDialog();

			if (isInputText()) {

				d.setName(inputText);
			}
		}

		/**
		 * Get the name of the author from the input dialog pop up.
		 */
		public void updateAuthor() {

			inputText = getInputDialog();

			if (isInputText()) {

				d.setAuthor(inputText);
			}
		}

		/**
		 * Get the current drawing.
		 * 
		 * @return the current drawing as type {@code Drawing}.
		 */
		public Drawing getDrawing() {

			return d;
		}

		/**
		 * Set the name of the art piece.
		 */
		public void newName() {

			inputText = getInputDialog();

			if ((inputText == null) || inputText.isBlank()) {

				inputText = "";
			}

			d.setName(inputText);
		}

		/**
		 * Set the authors name of the art piece.
		 */
		public void newAuthor() {

			inputText = getInputDialog();

			if (inputText == null) {

				inputText = "";
			}

			d.setAuthor(inputText);
		}

		/**
		 * Configure the JFileChooser pop up to filter all files except the shape files.
		 * 
		 * @param dialogTitle           the title of the pop up file chooser, as type
		 *                              {@code String}.
		 * @param initialSelectionValue is a dummy file name, as type {@code String}.
		 * @return a file handler of type {@code FileHandler}.
		 */
		private FileHandler setFileChooser(String dialogTitle, String initialSelectionValue) {

			FileHandler fileHandler = new FileHandler(MenuBarItemEnum.DRAWING_FILTER.label(),
					MenuBarItemEnum.SHAPE.label());

			setAcceptAllFileFilterUsed(false);

			setFileFilter(
					new FileNameExtensionFilter(fileHandler.getFileDescription(), fileHandler.getFileExtension()));

			// Small trick. Show a preselected file by adding a dummy file.
			this.setSelectedFile(new File(initialSelectionValue));

			setDialogTitle(dialogTitle);// Set the file chooser dialog title.

			return fileHandler;
		}

		/**
		 * The pop up dialog file chooser. When activated and given a file name the
		 * contents of a drawing are saved to a shape file.
		 * 
		 * @param initialSelectionValue a file name as type {@code String}.
		 */
		private void getSaveFileChooserDialog(String initialSelectionValue) {

			FileHandler fh = setFileChooser(MenuBarItemEnum.SAVE_AS.label(), initialSelectionValue);

			int choice = showSaveDialog(jPaintFrame);// Pop up dialog chooser.

			if (choice == JFileChooser.APPROVE_OPTION) {

				// Get the user input name of the file.
				File file = getSelectedFile();

				// Get the drawing to be saved to a file.
				d = jPaintFrame.getDrawingPanel().getDrawing();

				try {

					// Save the drawing to a file.
					fh.save(d, file.toString());

				} catch (ShapeException e) {

					JOptionPane.showMessageDialog(jPaintFrame, e.getMessage(), MenuBarItemEnum.SAVE_AS.label(),
							JOptionPane.ERROR_MESSAGE);
				}

			} else {

				// The user did not choose to save the file.
				JOptionPane.showMessageDialog(jPaintFrame, "Nothing saved.", MenuBarItemEnum.SAVE_AS.label(),
						JOptionPane.INFORMATION_MESSAGE);
			}
		}

		/**
		 * The pop up dialog file chooser. When activated a selected shape file is
		 * opened and the contents of of the file are used to recreate a drawing object.
		 * 
		 * @param initialSelectionValue a file name as type {@code String}.
		 */
		private Drawing getLoadFileChooserDialog(String initialSelectionValue) {
			Drawing d = null, currentDrawing = null;
			FileHandler fh = setFileChooser(MenuBarItemEnum.LOAD.label(), initialSelectionValue);

			// Get the current drawing used in recovery.
			currentDrawing = jPaintFrame.getDrawingPanel().getDrawing();

			int choice = showOpenDialog(jPaintFrame);// Pop up dialog chooser.

			if (choice == JFileChooser.APPROVE_OPTION) {

				// The user's selected file.
				File file = getSelectedFile();

				try {

					// Load the saved drawing from a file.
					d = fh.load(file.toString());

				} catch (ShapeException e) {

					// Restore the current drawing.
					if (currentDrawing != null) {

						d = currentDrawing;
					}

					JOptionPane.showMessageDialog(jPaintFrame, e.getMessage(), MenuBarItemEnum.LOAD.label(),
							JOptionPane.ERROR_MESSAGE);
				}

			} else {

				// Restore the current drawing.
				if (currentDrawing != null) {
					d = currentDrawing;
				}

				// The user did not choose to save the file.
				JOptionPane.showMessageDialog(jPaintFrame, "Nothing loaded.", MenuBarItemEnum.LOAD.label(),
						JOptionPane.INFORMATION_MESSAGE);
			}

			return d;
		}

		/**
		 * Menu item method that activates the logic for saving a drawing object to a
		 * file.
		 * 
		 * @param initialSelectionValue file name to save the drawing to as type
		 *                              {@code Object}.
		 */
		public void saveAs(Object initialSelectionValue) {

			getSaveFileChooserDialog((String) initialSelectionValue);
		}

		/**
		 * Menu item method that activates the logic for loading a drawing object from a
		 * file.
		 */
		public void load() {

			setDrawingObject(getLoadFileChooserDialog(MenuBarItemEnum.SHAPE.label()), MenuBarItemEnum.LOAD.label());
		}

	}

}
