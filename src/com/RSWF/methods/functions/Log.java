package com.RSWF.methods.functions;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import jodd.io.FileUtil;

public class Log {

	private static boolean Debug;

	private static String logUID = null;

	private static String lastMessage = "";

	public static void debug(String message) {
		debug(message, true);
	}

	public static void debug(String message, Boolean printStack) {
		if (!isDebug())
			return;
		
		String m = message;
		
		if (printStack) {
			final StackTraceElement methodCaller = new Throwable()
					.getStackTrace()[1];

			m = methodCaller.getClassName().substring(
					methodCaller.getClassName().lastIndexOf(".") + 1);
			m += " - ";
			m += methodCaller.getMethodName();
			m += " [";
			m += methodCaller.getFileName();
			m += ":";
			m += methodCaller.getLineNumber();
			m += "]";
		}
		
		log(m);
	}

	public static void error(String message) {
		log(message);
	}

	public static void info(String message) {
		log(message);
	}
	
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

	public static boolean isDebug() {
		return Debug;
	}

	private static void log(String message) {
		log(message, false);
	}

	private static void log(String message, Boolean Error) {
		if (logUID == null)
			init();

		PrintStream k = Error ? System.err : System.out;
		if (!lastMessage.equalsIgnoreCase(new String(message))) {
			k.println();
			k.println(message);
			lastMessage = message;
		} else { // Hate getting spammed with lines makes the debugging harder
					// as you have to scroll up.
			k.print("+");
		}
		message += "\n";
		try {
			FileUtil.appendString(new File(logUID), message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void setDebug(boolean debug) {
		Debug = debug;
	}
}
