package com.jFlip.inc;

import java.util.HashMap;

import com.jFlip.classes.Languages;

public class Versions {
	public static String FLIPPINGPANEL = "0.5.4";
	public static String GUI = "0.0.8"; 
	public static Double VERSION = 0.34;

	public static HashMap<Integer, String> VERSION_TYPE = new HashMap<Integer, String>() {
		private static final long serialVersionUID = 8441341419449037289L;
		{
			put(size(), Languages.getString("Versions.UNDEFINED")); //$NON-NLS-1$
			put(size(), Languages.getString("Versions.PRE-ALPHA")); //$NON-NLS-1$
			put(size(), Languages.getString("Versions.ALPHA")); //$NON-NLS-1$
			put(size(), Languages.getString("Versions.BETA")); //$NON-NLS-1$
			put(size(), Languages.getString("Versions.RELEASE-CANDIDATE")); //$NON-NLS-1$
			put(size(), Languages.getString("Versions.RELEASED")); //$NON-NLS-1$
		}
	};

}
