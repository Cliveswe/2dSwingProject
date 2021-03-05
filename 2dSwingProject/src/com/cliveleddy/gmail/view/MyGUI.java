package com.cliveleddy.gmail.view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

@SuppressWarnings("serial")
public class MyGUI extends JFrame {

	private final int FRAME_WIDTH = 400;
	private final int FRAME_HEIGHT = 800;

	public MyGUI() {
		super();
	}

	public void Initialise() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SetLookAndFeel();
		SetFrameSize();
		SetFrameTitle();

		this.setVisible(true);
	}

	private void SetFrameTitle() {
		this.setTitle(UserInterfaceText.getText(UserInterfaceText.UITextEnum.TITLE));
	}

	private void SetLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void SetFrameSize() {
		this.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

	}
}
