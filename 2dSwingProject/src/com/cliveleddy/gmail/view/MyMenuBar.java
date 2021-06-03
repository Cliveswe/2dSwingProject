package com.cliveleddy.gmail.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.EventListenerList;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.cliveleddy.gmail.controller.CommandBroker;
import com.cliveleddy.gmail.controller.FileHandler;
import com.cliveleddy.gmail.controller.FileNameCreator;
import com.cliveleddy.gmail.controller.ICommand;
import com.cliveleddy.gmail.controller.MenuBarItemEnum;
import com.cliveleddy.gmail.model.Drawing;
import com.cliveleddy.gmail.model.ShapeException;

/**
 * <h1>Step 4</h1> Create the applications menu bar and add items to each menu.
 * It extends the JMenuBar swing.
 * <p>
 * <h1>Step 5</h1> Add the action listeners to each of the menu items. These
 * Listeners will in turn perform tasks.
 * <h1>Step 6</h1>Added additional logic to the menu items. In addition,
 * replacing both the MBTextEnum and the Dictionary Text with a Enum class
 * called MenuBarItemEnum. This makes for a cleaner code style. Also the Enum
 * class allows for additional logic at a later date. In the actionPerfromed
 * method all the if logic has been replaced with a
 * <h2>Command Design Pattern</h2>. This pattern allows for additional logic to
 * be added to classes that implement the different commands from the menu.
 * <h1>Step 8</h1> The classes Broker, ICommand and MenyBarItemEnum have been
 * relocated to the controller package.
 * 
 * @author Clive Leddy
 * @version 1.4
 */
public class MyMenuBar extends JMenuBar {
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
	 * <h1>Interface class ILabeled.</h1> This is used to get a Enum as a string.
	 * 
	 * @author Clive Leddy
	 * @version 1.0
	 *
	 */
	public interface ILabeled {
		String label();
	}

	// reference to the main JFrame
	private JPaintFrame jPaintFrame;

	// a reference to a drawing d
	// private Drawing d;

	// LISTENER LISTS
	// a list of listeners for the menu bar drawing object.
	private EventListenerList menuBarDrawingListenerList = new EventListenerList();

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

		// File menu
		add(initialiseFileMenu(new JMenu(MenuBarItemEnum.FILE.label())));

		// Edit menu
		add(initialiseEditMenu(new JMenu(MenuBarItemEnum.EDIT.label())));
	}

	/**
	 * Add the menu items for a File.
	 * 
	 * @param mFile file menu as JMenu.
	 */
	private JMenu initialiseFileMenu(JMenu mFile) {

		// new Menu Item
		mFile.add(new myJMenuItem(MenuBarItemEnum.NEW.label()));

		// save as Menu Item
		mFile.add(new myJMenuItem(MenuBarItemEnum.SAVE_AS.label()));

		// load Menu Item
		mFile.add(new myJMenuItem(MenuBarItemEnum.LOAD.label()));

		// info Menu Item
		mFile.add(new myJMenuItem(MenuBarItemEnum.INFO.label()));

		mFile.addSeparator();

		// exit Menu Item
		mFile.add(new myJMenuItem(MenuBarItemEnum.EXIT.label()));

		return mFile;
	}

	/**
	 * Add the menu items for a Edit.
	 * 
	 * @param mEdit file menu as JMenu.
	 */
	private JMenu initialiseEditMenu(JMenu mEdit) {

		// Undo
		mEdit.add(new myJMenuItem(MenuBarItemEnum.UNDO.label()));

		// Drawing title
		mEdit.add(new myJMenuItem(MenuBarItemEnum.NAME.label()));

		// Drawing author
		mEdit.add(new myJMenuItem(MenuBarItemEnum.AUTHOR.label()));

		return mEdit;
	}

	/**
	 * Set the drawing object.
	 * 
	 * @param d is a drawing instance of type Drawing.
	 */
	public void setDrawingObject(Drawing d) {

		firedDrawingObjectEvent(new MyDrawingAreaEvent<Drawing>(this, d));
	}

	/**
	 * Set the drawing object.
	 * 
	 * @param d  is a drawing instance of type Drawing.
	 * @param id menu item id from Map<MBTextEnum, String> Text
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
	 * @param <T>   a generic type as type Drawing
	 * 
	 * @param event mle object as MyDrawingAreaEvent
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
	 * @param a reference to a listener of type IDrawingAreaListener
	 */
	public void addMenuBarDrawingListener(IDrawingAreaListener<MyDrawingAreaEvent<Drawing>> listener) {

		menuBarDrawingListenerList.add(IDrawingAreaListener.class, listener);
	}

	/**
	 * Remove a listener that is listening for a colour selection from the tool bar.
	 * 
	 * @param a reference to a listener of type IDrawingAreaListener.
	 */
	public void removeMenuBarDrawingListener(IDrawingAreaListener<MyDrawingAreaEvent<Drawing>> listener) {

		menuBarDrawingListenerList.remove(IDrawingAreaListener.class, listener);
	}

	/**
	 * <h1>Class myJMenuItem</h1> This class both extends the JMenuItem and
	 * implements the ActionListener class. Extending the JMenuItem means that we
	 * can add a menu item to the menu bar. Then when a menu item is selected the
	 * actionPerfromed from the ActionListener is triggered.
	 * 
	 * @author Clive Leddy
	 * @version 1.0
	 */
	class myJMenuItem extends JMenuItem implements ActionListener {

		private static final long serialVersionUID = -3898605973624196788L;

		public myJMenuItem(String title) {

			super(title);

			addActionListener(this);
		}

		/**
		 * Examining the action event e we can determine what menu item fired this even
		 * action. Then depending on the menu item this method performs and action.
		 * 
		 * @param e the even that occurred as type ActionEven.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String itemName = e.getActionCommand();

			CommandBroker broker = new CommandBroker();

			broker.addCommand(MenuBarItemEnum.NEW.label(),
					new NewNameAuthorInput(MenuBarItemEnum.NEW_DRAWING_NAME_PROMPT.label(),
							MenuBarItemEnum.NEW_DRAWING_AUTHOR_PROMPT.label(), jPaintFrame));
			broker.addCommand(MenuBarItemEnum.AUTHOR.label(),
					new UpdateAuthorInput(MenuBarItemEnum.DRAWING_AUTHOR.label(), jPaintFrame));

			broker.addCommand(MenuBarItemEnum.NAME.label(),
					new UpdateNameInput(MenuBarItemEnum.DRAWING_NAME.label(), jPaintFrame));

			broker.addCommand(MenuBarItemEnum.SAVE_AS.label(),
					new SaveAs(MenuBarItemEnum.DRAWING_NAME.label(), jPaintFrame));

			broker.addCommand(MenuBarItemEnum.EXIT.label(), new Exit());

			broker.addCommand(MenuBarItemEnum.UNDO.label(), new Undo());

			broker.addCommand(MenuBarItemEnum.LOAD.label(), new Load(MenuBarItemEnum.LOAD_PROMPT.label(), jPaintFrame));

			broker.addCommand(MenuBarItemEnum.INFO.label(), new Info(jPaintFrame));

			broker.runCommand(itemName);
		}
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
		 * @return the text entered by the user as type String.
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
		 * @return the current drawing as type Drawing.
		 */
		public Drawing getDrawing() {

			return d;
		}

		/**
		 * Set the name of the art piece.
		 */
		public void newName() {

			inputText = getInputDialog();

			if (inputText == null) {

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
		 *                              String.
		 * @param initialSelectionValue is a dummy file name, as type String.
		 * @return a file handler of type FileHandler.
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
		 * @param initialSelectionValue a file name as type String.
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
		 * @param initialSelectionValue a file name as type String.
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
		 * @param initialSelectionValue file name to save the drawing to as type Object.
		 */
		public void saveAs(Object initialSelectionValue) {
			// inputText = getInputDialog(initialSelectionValue);
			getSaveFileChooserDialog((String) initialSelectionValue);
		}

		/**
		 * Menu item method that activates the logic for loading a drawing object from a
		 * file.
		 */
		public void Load() {

			setDrawingObject(getLoadFileChooserDialog(MenuBarItemEnum.SHAPE.label()), MenuBarItemEnum.LOAD.label());
		}
	}

	/**
	 * Command design pattern UpdateNameInput.
	 * 
	 * @author Clive Leddy
	 * @version 1.0
	 *
	 */
	public class UpdateNameInput implements ICommand {

		private InputTextDialog inputTextDialog;

		/**
		 * Class constructor .
		 * 
		 * @param outText     text to be displayed in a pop up dialog.
		 * @param jPaintFrame application container window.
		 */
		public UpdateNameInput(String outText, JPaintFrame jPaintFrame) {

			inputTextDialog = new InputTextDialog(outText, jPaintFrame);
		}

		/**
		 * Implementation of the ICommand method. Use a pop up to get the name of the
		 * art piece.
		 */
		@Override
		public void execute() {

			inputTextDialog.updateName();

			// re-set the drawing object.
			setDrawingObject(inputTextDialog.getDrawing(), MenuBarItemEnum.NAME.label());
		}
	}

	public class UpdateAuthorInput implements ICommand {

		private InputTextDialog inputTextDialog;

		/**
		 * Class constructor .
		 * 
		 * @param outText     text to be displayed in a pop up dialog.
		 * @param jPaintFrame application container window.
		 */
		public UpdateAuthorInput(String outText, JPaintFrame jPaintFrame) {

			inputTextDialog = new InputTextDialog(outText, jPaintFrame);
		}

		/**
		 * Implementation of the ICommand method. Use a pop up to get the name of the
		 * author.
		 */
		@Override
		public void execute() {

			inputTextDialog.updateAuthor();

			// re-set the drawing object.
			setDrawingObject(inputTextDialog.getDrawing(), MenuBarItemEnum.AUTHOR.label());
		}
	}

	public class NewNameAuthorInput implements ICommand {

		private InputTextDialog inputTextDialogName;

		private InputTextDialog inputTextDialogAuthor;

		/**
		 * Class constructor.
		 * 
		 * @param outTextName   name of the art piece as text to be displayed in a pop
		 *                      up dialog.
		 * @param outTextAuthor name of the author as text to be displayed in a pop up
		 *                      dialog.
		 * @param jPaintFrame   application container window.
		 */
		public NewNameAuthorInput(String outTextName, String outTextAuthor, JPaintFrame jPaintFrame) {

			inputTextDialogName = new InputTextDialog(outTextName, jPaintFrame);

			inputTextDialogAuthor = new InputTextDialog(outTextAuthor, jPaintFrame);
		}

		/**
		 * Implementation of the ICommand method. Use a pop up to get the name of the
		 * art piece and the name of the author.
		 */
		@Override
		public void execute() {

			inputTextDialogName.newName();

			inputTextDialogAuthor.newAuthor();

			inputTextDialogName.getDrawing().setAuthor(inputTextDialogAuthor.getDrawing().getAuthor());

			// re-set the drawing object.
			setDrawingObject(inputTextDialogName.getDrawing(), MenuBarItemEnum.NEW.label());
		}
	}

	public class SaveAs implements ICommand {

		private InputTextDialog inputTextDialogSaveAs;

		/**
		 * Class constructor.
		 * 
		 * @param outTextSaveAs text to be display in the pop up dialog, type String.
		 * @param jPaintFrame   application container window.
		 */
		public SaveAs(String outTextSaveAs, JPaintFrame jPaintFrame) {

			inputTextDialogSaveAs = new InputTextDialog(outTextSaveAs, jPaintFrame);
		}

		/**
		 * Implementation of the ICommand method. Use a pop up to get the file name used
		 * to save a drawing to a file.
		 */
		@Override
		public void execute() {

			/**
			 * Function interface implementation.
			 */
			IMyFunctionalInterface<String, Drawing> mf = (draw) -> {

				String str = "";
				str = FileNameCreator.createFileName(draw);

				return str += MenuBarItemEnum.DOT_SHAPE.label();
			};

			inputTextDialogSaveAs.saveAs(mf.execute(jPaintFrame.getDrawingPanel().getDrawing()));
		}
	}

	public class Exit implements ICommand {

		public Exit() {
		}

		@Override
		public void execute() {

			System.exit(0);
		}
	}

	public class Undo implements ICommand {

		public Undo() {
		}

		@Override
		public void execute() {

			setDrawingObject(new Drawing(), MenuBarItemEnum.UNDO.label());
		}
	}

	public class Load implements ICommand {

		private InputTextDialog inputTextDialog;

		/**
		 * Class constructor.
		 *
		 * @param outText     text to be display in the pop up dialog, type String.
		 * @param jPaintFrame application container window.
		 */
		public Load(String outText, JPaintFrame jPaintFrame) {

			inputTextDialog = new InputTextDialog(outText, jPaintFrame);
		}

		/**
		 * Implementation of the ICommand method. Use a pop up to get the file name used
		 * to load a drawing from a file.
		 */
		@Override
		public void execute() {

			inputTextDialog.Load();
		}
	}

	public class Info implements ICommand {

		// default title and icon
		private JPaintFrame jPaintFrame;

		public Info(JPaintFrame jPaintFrame) {

			this.jPaintFrame = jPaintFrame;
		}

		/**
		 * Create an info message from a drawing.
		 * 
		 * @param d a drawing of type Drawing.
		 * @return a message as a String.
		 */
		private String generateMessage(Drawing d) {

			String str = "";

			if (!d.isEmpty()) {

				str = String.format(d.getName() + " by " + d.getAuthor());
			}
			if (d.getSize() > 0) {

				str += String.format("\nNumber of shapes: %d", d.getSize());

				str += String.format("\nTotal area: %.1f", d.getTotalArea());

				str += String.format("\nTotal circumference: %f", d.getTotalCircumference());
			}

			return str;
		}

		/**
		 * Implementation of the ICommand method. Use a pop up to display the drawing
		 * info.
		 */
		@Override
		public void execute() {

			Drawing d = jPaintFrame.getDrawingPanel().getDrawing();

			if ((d != null) && !d.isEmpty()) {

				JOptionPane.showMessageDialog(jPaintFrame, generateMessage(d));
			}
		}
	}

}
