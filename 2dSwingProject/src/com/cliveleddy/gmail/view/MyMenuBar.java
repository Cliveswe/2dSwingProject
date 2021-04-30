package com.cliveleddy.gmail.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.EventListenerList;

import com.cliveleddy.gmail.model.Circle;
import com.cliveleddy.gmail.model.Drawing;
import com.cliveleddy.gmail.model.Rectangle;
import com.cliveleddy.gmail.model.Shape;

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
 * 
 * @author Clive Leddy
 * @version 1.3
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

	private interface ICommand {
		void execute();
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

	/**
	 * A Enum class that contains the names of the menu items. This class has
	 * additional logic for looking up an class constant and returning it as a
	 * String.
	 * 
	 * @author Clive Leddy
	 * @version 1.0
	 *
	 */
	public static enum MenuBarItemEnum implements ILabeled {
		FILE("File"), NEW("New..."), SAVE_AS("Save as..."), LOAD("Load"), EXIT("Exit"), EDIT("Edit"), UNDO("Undo"),
		NAME("Name..."), AUTHOR("Author..."), NEW_DRAWING_NAME_PROMPT("Enter new name of the drawing:"),
		NEW_DRAWING_AUTHOR_PROMPT("Enter new author of the drawing:"), SAVE_AS_PROMPT("Save drawing to:"),
		LOAD_PROMPT("Load drawing from:"), SHAPE(".shape"), DRAWING_NAME("Enter the name of the drawing:"),
		DRAWING_AUTHOR("Enter the autor of the drawing:"), INFO("Info");

		private final String label;

		private MenuBarItemEnum(String label) {
			this.label = label;
		}

		/**
		 * Get the text version from the Enum class.
		 * 
		 * @return a label as type String.
		 */
		@Override
		public String label() {

			return label;
		}
	}

	// reference to the main JFrame
	private JPaintFrame jPaintFrame;

	// a reference to a drawing d
	// private Drawing d;

	// LISTENER LISTS
	// a list of listeners for the menu bar drawing object.
	private EventListenerList menuBarDrawingListenerList = new EventListenerList();

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
		add(initialiseFileMenu(new JMenu(MenuBarItemEnum.FILE.label)));

		// Edit menu
		add(initialiseEditMenu(new JMenu(MenuBarItemEnum.EDIT.label)));
	}

	/**
	 * Add the menu items for a File.
	 * 
	 * @param mFile file menu as JMenu.
	 */
	private JMenu initialiseFileMenu(JMenu mFile) {

		// new Menu Item
		mFile.add(new myJMenuItem(MenuBarItemEnum.NEW.label));

		// save as Menu Item
		mFile.add(new myJMenuItem(MenuBarItemEnum.SAVE_AS.label));

		// load Menu Item
		mFile.add(new myJMenuItem(MenuBarItemEnum.LOAD.label));

		// info Menu Item
		mFile.add(new myJMenuItem(MenuBarItemEnum.INFO.label));

		mFile.addSeparator();

		// exit Menu Item
		mFile.add(new myJMenuItem(MenuBarItemEnum.EXIT.label));

		return mFile;
	}

	/**
	 * Add the menu items for a Edit.
	 * 
	 * @param mEdit file menu as JMenu.
	 */
	private JMenu initialiseEditMenu(JMenu mEdit) {

		// Undo
		mEdit.add(new myJMenuItem(MenuBarItemEnum.UNDO.label));

		// Drawing title
		mEdit.add(new myJMenuItem(MenuBarItemEnum.NAME.label));

		// Drawing author
		mEdit.add(new myJMenuItem(MenuBarItemEnum.AUTHOR.label));

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

			Broker broker = new Broker();

			broker.addCommand(MenuBarItemEnum.NEW.label,
					new NewNameAuthorInput(MenuBarItemEnum.NEW_DRAWING_NAME_PROMPT.label,
							MenuBarItemEnum.NEW_DRAWING_AUTHOR_PROMPT.label, jPaintFrame));
			broker.addCommand(MenuBarItemEnum.AUTHOR.label,
					new UpdateAuthorInput(MenuBarItemEnum.DRAWING_AUTHOR.label, jPaintFrame));

			broker.addCommand(MenuBarItemEnum.NAME.label,
					new UpdateNameInput(MenuBarItemEnum.DRAWING_NAME.label, jPaintFrame));

			broker.addCommand(MenuBarItemEnum.SAVE_AS.label,
					new SaveAs(MenuBarItemEnum.DRAWING_NAME.label, jPaintFrame));

			broker.addCommand(MenuBarItemEnum.EXIT.label, new Exit());

			broker.addCommand(MenuBarItemEnum.UNDO.label, new Undo());

			broker.addCommand(MenuBarItemEnum.LOAD.label, new Load(MenuBarItemEnum.LOAD_PROMPT.label, jPaintFrame));

			broker.addCommand(MenuBarItemEnum.INFO.label, new Info(jPaintFrame));

			broker.runCommand(itemName);
		}
	}

	/**
	 * Command design pattern Request class.
	 * 
	 * @author Clive Leddy
	 * @version 1.0
	 *
	 */
	public class InputTextDialog {

		private Drawing d;

		private String inputText;

		private String outText;

		private JPaintFrame jPaintFrame;

		InputTextDialog(String outText, JPaintFrame jPaintFrame) {

			d = new Drawing();

			this.outText = outText;

			this.jPaintFrame = jPaintFrame;
		}

		private String getInputDialog() {

			return JOptionPane.showInputDialog(jPaintFrame, outText, null);
		}

		private String getInputDialog(Object initialSelectionValue) {

			return JOptionPane.showInputDialog(jPaintFrame, outText, initialSelectionValue);
		}

		public boolean isInputText() {

			if (inputText != null) {

				if (!inputText.isBlank() && (d != null)) {

					return true;
				}
			}

			return false;
		}

		public void updateName() {

			inputText = getInputDialog();

			if (isInputText()) {

				d.setName(inputText);
			}
		}

		public void updateAuthor() {

			inputText = getInputDialog();
			if (isInputText()) {

				d.setAuthor(inputText);
			}
		}

		public Drawing getDrawing() {

			return d;
		}

		public void newName() {

			inputText = getInputDialog();

			if (inputText == null) {

				inputText = "";
			}

			d.setName(inputText);
		}

		public void newAuthor() {

			inputText = getInputDialog();

			if (inputText == null) {

				inputText = "";
			}

			d.setAuthor(inputText);
		}

		public void saveAs(Object initialSelectionValue) {
			inputText = getInputDialog(initialSelectionValue);
		}

		public void Load() {

			inputText = getInputDialog();

			/*
			 * Temporary code for testing
			 * 
			 */
			Drawing d1 = new Drawing();

			System.out.println(d1);

			// Set name and author.
			System.out.println("\nSetting name and author...");

			d1.setName("Mona Lisa");
			d1.setAuthor("L. da Vincis");

			System.out.println(d1);

			// Add five shapes with no end points
			System.out.println("\nAdding 5 shapes with no end points...");

			Shape face = new Circle(30, 35, "#ffe0bd"); // RGB(255,224,189)
			Shape leftEye = new Circle(72, 75, "#0000ff"); // RGB(0, 0, 255)
			Shape rightEye = new Circle(122, 75, "#0000ff"); // RGB(0, 0, 255)
			Shape nose = new Rectangle(96, 100, "#000000"); // RGB(0, 0, 0)
			Shape mouth = new Rectangle(60, 130, "#ff0000"); // RGB(255, 0, 0)

			d1.addShape(face);
			d1.addShape(leftEye);
			d1.addShape(rightEye);
			d1.addShape(nose);
			d1.addShape(mouth);

			// Add end point to all shapes
			System.out.println("\nAdding end point to all shapes...");
			face.addPoint(175, 180);
			leftEye.addPoint(82, 85);
			rightEye.addPoint(132, 85);
			nose.addPoint(110, 120);
			mouth.addPoint(144, 140);
			System.out.println("Total circumference is: " + d1.getTotalCircumference());
			System.out.println("Total area is: " + d1.getTotalArea());

			System.out.println(d1);

			setDrawingObject(d1, MenuBarItemEnum.LOAD.label);
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

		public UpdateNameInput(String outText, JPaintFrame jPaintFrame) {

			inputTextDialog = new InputTextDialog(outText, jPaintFrame);
		}

		@Override
		public void execute() {

			inputTextDialog.updateName();

			// re-set the drawing object.
			setDrawingObject(inputTextDialog.getDrawing(), MenuBarItemEnum.NAME.label);
		}
	}

	public class UpdateAuthorInput implements ICommand {

		private InputTextDialog inputTextDialog;

		public UpdateAuthorInput(String outText, JPaintFrame jPaintFrame) {

			inputTextDialog = new InputTextDialog(outText, jPaintFrame);
		}

		@Override
		public void execute() {

			inputTextDialog.updateAuthor();

			// re-set the drawing object.
			setDrawingObject(inputTextDialog.getDrawing(), MenuBarItemEnum.AUTHOR.label);
		}
	}

	public class NewNameAuthorInput implements ICommand {

		private InputTextDialog inputTextDialogName;

		private InputTextDialog inputTextDialogAuthor;

		public NewNameAuthorInput(String outTextName, String outTextAuthor, JPaintFrame jPaintFrame) {

			inputTextDialogName = new InputTextDialog(outTextName, jPaintFrame);

			inputTextDialogAuthor = new InputTextDialog(outTextAuthor, jPaintFrame);
		}

		@Override
		public void execute() {

			inputTextDialogName.newName();

			inputTextDialogAuthor.newAuthor();

			inputTextDialogName.getDrawing().setAuthor(inputTextDialogAuthor.getDrawing().getAuthor());

			// re-set the drawing object.
			setDrawingObject(inputTextDialogName.getDrawing(), MenuBarItemEnum.NEW.label);
		}
	}

	public class SaveAs implements ICommand {

		private InputTextDialog inputTextDialogSaveAs;

		public SaveAs(String outTextSaveAs, JPaintFrame jPaintFrame) {

			inputTextDialogSaveAs = new InputTextDialog(outTextSaveAs, jPaintFrame);
		}

		@Override
		public void execute() {

			/**
			 * Function interface implementation.
			 */
			IMyFunctionalInterface<String, Drawing> mf = (draw) -> {

				String str = "";

				if (draw != null) {

					if (!draw.getName().isBlank() && !draw.getAuthor().isBlank()) {

						str += draw.getName() + " by " + draw.getAuthor();
					}

					if (!draw.getName().isBlank() && draw.getAuthor().isBlank()) {

						str += draw.getName();
					}

					if (draw.getName().isBlank() && !draw.getAuthor().isBlank()) {

						str += draw.getAuthor();
					}
				}

				return str += MenuBarItemEnum.SHAPE.label;
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

			setDrawingObject(new Drawing(), MenuBarItemEnum.UNDO.label);
		}
	}

	public class Load implements ICommand {

		private InputTextDialog inputTextDialog;

		public Load(String outText, JPaintFrame jPaintFrame) {

			inputTextDialog = new InputTextDialog(outText, jPaintFrame);
		}

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

		@Override
		public void execute() {

			Drawing d = jPaintFrame.getDrawingPanel().getDrawing();

			if ((d != null) && !d.isEmpty()) {

				JOptionPane.showMessageDialog(jPaintFrame, generateMessage(d));
			}
		}
	}

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

}
