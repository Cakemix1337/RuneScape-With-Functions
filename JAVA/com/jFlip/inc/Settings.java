package com.jFlip.inc;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.jFlip.classes.Languages;
import com.jFlip.classes.Log;

public class Settings {
	private static boolean debug = false;
	private static Map<String, Boolean> Booleans = new HashMap<String, Boolean>() {
		private static final long serialVersionUID = 1L;

		{
			put("foundUpdate", false); 
			put("forceUpdate", false); 
			put("showLoader", true); 
			put("showFlipping", true);
			put("check_New_Version", true);
			put("Debug", true); 
		}
	};

	private static Map<String, String> guiTitles = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;

		{
			// GUI
			String name = "Client"; //$NON-NLS-1$
			String title = Languages.getString("Settings.Title_jFlip").concat(" (v )"); //$NON-NLS-1$ //$NON-NLS-2$
			put(name, title);

			// Flipping Panel
			name = "flippingPanel"; //$NON-NLS-1$
			title = Languages.getString("Settings.Title_Flipping_panel"); //$NON-NLS-1$
			put(name, title);
		}
	};

	private static Point lastGlocation;
	private static Dimension lastGsize;
	private static long mili = Calendar.getInstance().getTimeInMillis();
	private static String downloadLink = ""; 
	private static String Note = ""; 
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
		Log.debug(Name + " - " + Booleans.get(Name), false); 
		return Booleans.get(Name);
	}

	public static void setBoolean(String str, boolean bool) {
		Log.debug(str + " - " + bool, false); 
		Booleans.put(str, bool);
	}

	public static void setBooleans(Map<String, Boolean> booleans) {
		Booleans = booleans;
	}

	public static String getDownloadLink() {
		return downloadLink;
	}

	public static void setDownloadLink(String downloadLink) {
		Settings.downloadLink = downloadLink;
	}

	public static String getNote() {
		return Note;
	}

	public static void setNote(String note) {
		Note = note;
	}

}
