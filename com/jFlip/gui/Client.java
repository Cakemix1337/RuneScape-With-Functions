package com.jFlip.gui;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;

import javax.swing.JFrame;

import com.jFlip.classes.Log;
import com.jFlip.inc.Settings;
import com.jFlip.loader.RSApplet;

public class Client extends JFrame {
	private static final long serialVersionUID = 1L;
	private clientMethods methods;

	public Client() throws MalformedURLException, IOException,
			InterruptedException {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		methods = new clientMethods();

		methods.initLoader(Client.class.getSimpleName());

		methods.initLoaderProcess();

		methods.pack();

		methods.getFrame().setVisible(true);

		try {
			methods.setApplet(new RSApplet().getApplet());
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Settings.setShowLoader(false);

		methods.initApplet();

		methods.initClient();
		
		methods.pack();

		Log.info("Client took "
				+ ((Calendar.getInstance().getTimeInMillis() - Settings
						.getMili()) / 1000) + " seconds to start up.");
	}

}
