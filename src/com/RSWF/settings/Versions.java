package com.RSWF.settings;

import java.util.HashMap;

import com.RSWF.methods.languages.classes.Languages;

public class Versions {
	public static Double FLIPPINGPANEL = 0.02;
	public static Double VERSION = 0.35;
	public static int VERSION_STAGE = 2;
	public static int FLIPPINGPANEL_STAGE = 1;

	private static HashMap<Integer, String> VERSION_TYPE = new HashMap<Integer, String>() {
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

	public static String getVERSION_TYPE(int Stage) {
		return VERSION_TYPE.get(Stage);
	}

}
