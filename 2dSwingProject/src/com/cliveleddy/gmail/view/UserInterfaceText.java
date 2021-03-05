package com.cliveleddy.gmail.view;

import java.util.Map;

public class UserInterfaceText {

	public static enum UITextEnum {
		TITLE, FILE, EDIT
	};

	public static Map<UITextEnum, String> Text = Map.ofEntries(Map.entry(UITextEnum.TITLE, "My GUI"),
			Map.entry(UITextEnum.FILE, "File"), Map.entry(UITextEnum.EDIT, "Edit"));

	public static String getText(UITextEnum key) {
		return UserInterfaceText.Text.get(key);
	}

}
