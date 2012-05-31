package com.RSWF;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.UIManager;

import com.RSWF.methods.functions.Client;
import com.RSWF.methods.functions.Log;
import com.RSWF.methods.functions.Updater;
import com.RSWF.methods.languages.classes.Languages;
import com.RSWF.settings.Settings;

public class Setup {
	public static void main(String[] args) throws MalformedURLException, IOException, InterruptedException {
		new Setup();
	}

	public Setup() throws MalformedURLException, IOException, InterruptedException {
		new Updater();

		if (Settings.getBoolean("Debug")) Log.setDebug(true);

		if (Settings.getBoolean("forceUpdate")) {
			Updater.Update();
			return;
		}

		Log.info(Languages.getString("startClient.Starting_up_the_client")); //$NON-NLS-1$

		UIManager.put("swing.boldMetal", Boolean.FALSE);
		
		new Client();
	}
}
