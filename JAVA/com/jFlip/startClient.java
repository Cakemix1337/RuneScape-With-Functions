package com.jFlip;

import javax.swing.UIManager;

import com.jFlip.classes.Languages;
import com.jFlip.classes.Log;
import com.jFlip.classes.checkForUpdates;
import com.jFlip.gui.Client;
import com.jFlip.inc.Settings;

public class startClient {
	public static void main(String[] args) throws Exception {

		new checkForUpdates();

		if (Settings.getBoolean("Debug")) Log.setDebug(true); 

		if (Settings.getBoolean("forceUpdate")) // Don't run the client if a force update is in. 
			return;

		Log.info(Languages.getString("startClient.Starting_up_the_client")); //$NON-NLS-1$

		UIManager.put("swing.boldMetal", Boolean.FALSE);
		new Client();
	}

}
