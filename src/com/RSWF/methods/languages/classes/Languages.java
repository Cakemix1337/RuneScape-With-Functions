package com.RSWF.methods.languages.classes;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Languages {

	private static final String ENGLISH = "com.RSWF.methods.languages.files.English"; 

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(ENGLISH);

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return "Could not find:";
		}
	}
}
