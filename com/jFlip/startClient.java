package com.jFlip;

import com.jFlip.classes.Log;
import com.jFlip.classes.checkForUpdates;
import com.jFlip.gui.Client;
import com.jFlip.inc.Settings;

public class startClient {
	public static void main(String[] args) throws Exception {

	//	new checkForUpdates();

		Log.init();

		Log.info("Starting up the client.");

		new Client();
	}

}
