package com.jFlip.inc;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Settings {
	private static boolean debug = false;
	private static Map<String, Boolean> Booleans = new HashMap<String, Boolean>() {
		{
			put("showLoader", true);
			put("showFlipping", true);
		}
	};

	private static Map<String, String> guiTitles = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;

		{
			// GUI
			String name = "Client";
			String title = "jFlip (v".concat(
					Versions.GUI.concat(Versions.GUI_STATE)).concat(")");
			put(name, title);

			// Flipping Panel
			name = "flippingPanel";
			title = "Flipping panel ";
			put(name, title);
		}
	};

	private static Point lastGlocation;
	private static Dimension lastGsize;
	private static long mili = Calendar.getInstance().getTimeInMillis();

	private static String status = "";

	public static String getGuiTitle(String Name) {
		return guiTitles.get(Name);
	}

	public static Point getLastGlocation() {
		return lastGlocation;
	}

	public static Dimension getLastGsize() {
		return lastGsize;
	}

	public static long getMili() {
		return mili;
	}

	public static String getStatus() {
		return status;
	}

	public static boolean isDebug() {
		return debug;
	}

	public static void setDebug(boolean debug) {
		Settings.debug = debug;
	}

	public static void setGuiTitles(Map<String, String> guiTitles) {
		Settings.guiTitles = guiTitles;
	}

	public static void setLastGlocation(Point lastGlocation) {
		Settings.lastGlocation = lastGlocation;
	}

	public static void setLastGsize(Dimension lastGsize) {
		Settings.lastGsize = lastGsize;
	}

	public static void setMili(long mili) {
		Settings.mili = mili;
	}

	public static void setStatus(String status) {
		Settings.status = status;
	}

	public static Boolean getBoolean(String Name) {
		return Booleans.get(Name);
	}

	public static void setBooleans(Map<String, Boolean> booleans) {
		Booleans = booleans;
	}

	public static void setBoolean(String str, boolean bool) {
		Booleans.put(str, bool);
	}

}
