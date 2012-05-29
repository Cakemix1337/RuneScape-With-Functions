package com.jFlip.classes;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jodd.io.FileUtil;

public class Log {

	/**
	 * @author Cakemix
	 * @since 0.3
	 */

	public static void init() {
		if (!new File("Logs/").exists())
			new File("Logs/").mkdir();

		logUID = "Logs/" + "log-";
		logUID += new SimpleDateFormat("MM-dd-yyyy").format(new Date());
		logUID += ".txt";

		log("---- "
				+ new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
						.format(new Date()) + " ----");
	}

	private static void log(String message) {
		log(message, false);
	}

	private static void log(String message, Boolean Error) {
		if (Error)
			System.err.println(message);
		else
			System.out.println(message);

		message += "\n";
		try {
			FileUtil.appendString(new File(logUID), message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void log(String message, String caller) {
		log(caller + " - " + message);
	}

	public static void debug(String message) {
		if (!isDebug())
			return;

		final StackTraceElement methodCaller = new Throwable().getStackTrace()[1];

		String m = methodCaller.getClassName().substring(
				methodCaller.getClassName().lastIndexOf(".") + 1);
		m += " - ";
		m += methodCaller.getMethodName();
		m += " [";
		m += methodCaller.getFileName();
		m += ":";
		m += methodCaller.getLineNumber();
		m += "]";

		log(message, m);
	}

	public static void error(String message) {
		log(message);
	}

	public static void fatal(String message) {
		log(message);
	}

	public static void info(String message) {
		log(message);
	}

	public static boolean isDebug() {
		return Debug;
	}

	public static void setDebug(boolean debug) {
		Debug = debug;
	}

	private static boolean Debug;
	private static String logUID = null;
}
