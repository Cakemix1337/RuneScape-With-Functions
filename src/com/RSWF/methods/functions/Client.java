package com.RSWF.methods.functions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;

import javax.swing.JFrame;

import com.RSWF.gui.clientGUI;
import com.RSWF.methods.functions.Log;
import com.RSWF.methods.languages.classes.Languages;
import com.RSWF.methods.loader.RSApplet;
import com.RSWF.settings.Settings;

public class Client extends JFrame {
	private static final long serialVersionUID = 1L;
	private clientGUI gui;

	public Client() throws MalformedURLException, IOException, InterruptedException {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gui = new clientGUI(); // TODO: Make it so clientMethod isn't the only method.

		gui.initLoader(Client.class.getSimpleName());

		gui.initLoaderProcess();

		gui.pack();

		clientGUI.getFrame().setVisible(Boolean.TRUE);

		gui.setApplet(new RSApplet().getApplet());

		Settings.setBoolean("showLoader", Boolean.FALSE);

		gui.initApplet();

		gui.initClient();

		gui.pack();

		Log.info(Languages.getString("Client.Client_Took").replace("$1", String.valueOf((Calendar.getInstance().getTimeInMillis() - Settings.getMili()) / 1000))); //$NON-NLS-1$
	}
}
