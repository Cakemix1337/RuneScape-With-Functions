package com.jFlip.inc;


import java.awt.Dimension;
import java.awt.Point;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Settings {
	private static boolean debug = false;
	private static long mili = Calendar.getInstance().getTimeInMillis();
	private static Point lastGlocation;
	private static Dimension lastGsize;
	private static boolean showFlipping;
	private static String status = "";
	private static boolean showLoader = true;

	private static Map<String, String> guiTitles = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;

		{
			// GUI
			String name = "Client";
			String title = "jFlip (v".concat(Versions.GUI).concat(")");
			put(name, title);

			// Flipping Panel
			name = "flippingPanel"; 
			title = "Flipping panel ";
			put(name, title);
		}
	};

	public static Point getLastGlocation() {
		return lastGlocation;
	}

	public static Dimension getLastGsize() {
		return lastGsize;
	}

	public static boolean isShowFlipping() {
		return showFlipping;
	}

	public static boolean isShowLoader() {
		return showLoader;
	}

	public static void setLastGlocation(Point lastGlocation) {
		Settings.lastGlocation = lastGlocation;
	}

	public static void setLastGsize(Dimension lastGsize) {
		Settings.lastGsize = lastGsize;
	}

	public static void setShowFlipping(boolean showFlipping) {
		Settings.showFlipping = showFlipping;
	}

	public static void setShowLoader(boolean showLoader) {
		Settings.showLoader = showLoader;
	}


	public static String getGuiTitle(String Name) {
		return guiTitles.get(Name);
	}

	public static void setGuiTitles(Map<String, String> guiTitles) {
		Settings.guiTitles = guiTitles;
	}

	/**
	 * @return the status
	 */
	public static String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public static void setStatus(String status) {
		Settings.status = status;
	}

	/**
	 * @return the mili
	 */
	public static long getMili() {
		return mili;
	}

	/**
	 * @param mili the mili to set
	 */
	public static void setMili(long mili) {
		Settings.mili = mili;
	}

	public static boolean isDebug() {
		return debug;
	}

	public static void setDebug(boolean debug) {
		Settings.debug = debug;
	}


}
