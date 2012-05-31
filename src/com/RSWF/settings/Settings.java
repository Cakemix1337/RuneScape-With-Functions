package com.RSWF.settings;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.RSWF.methods.functions.Log;
import com.RSWF.methods.languages.classes.Languages;

public class Settings {
	private static boolean debug = Boolean.FALSE;
	private static Map<String, Boolean> Booleans = new HashMap<String, Boolean>() {
		private static final long serialVersionUID = 1L;

		{
			put("foundUpdate", Boolean.FALSE);
			put("forceUpdate", Boolean.FALSE);
			put("showLoader", Boolean.TRUE);
			put("showFlipping", Boolean.TRUE);
			put("check_New_Version", Boolean.TRUE);
			put("Debug", Boolean.TRUE);
		}
	};

	private static Map<String, String> GUI_Titles = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;

		{
			char SPACE = ' '; //Fancy fancy!
			
			// GUI
			StringBuilder title = new StringBuilder();
			title.append(Languages.getString("Settings.Title_Client"));
			title.append(SPACE);
			title.append("(v");
			title.append(Versions.VERSION);
			title.append('_');
			title.append(new String(Versions.getVERSION_TYPE(Versions.VERSION_STAGE)).toLowerCase());
			title.append(")");
			put("Client", title.toString());
			title.delete(0, title.length());

			// Flipping Panel
			title.append(Languages.getString("Settings.Title_Flipping_panel"));
			title.append(SPACE);
			title.append("(v");
			title.append(Versions.FLIPPINGPANEL);
			title.append('_');
			title.append(new String(Versions.getVERSION_TYPE(Versions.FLIPPINGPANEL_STAGE)).toLowerCase());
			title.append(")");

			put("flippingPanel", title.toString());
		}
	};

	private static Point lastGlocation;
	private static Dimension lastGsize;
	private static long mili = Calendar.getInstance().getTimeInMillis();
	private static String downloadLink = "";
	private static String Note = "";
	private static String status = "Starting up.";

	public static String getGuiTitle(String Name) {
		return GUI_Titles.get(Name);
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
		Settings.GUI_Titles = guiTitles;
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
		Log.debug(Name + " - " + Booleans.get(Name), Boolean.FALSE);
		return Booleans.get(Name);
	}

	public static void setBoolean(String str, boolean bool) {
		Log.debug(str + " - " + bool, Boolean.FALSE);
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
