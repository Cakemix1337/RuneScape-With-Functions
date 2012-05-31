package com.RSWF.methods.functions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import argo.jdom.JdomParser;
import argo.jdom.JsonRootNode;
import argo.saj.InvalidSyntaxException;

import com.RSWF.methods.languages.classes.Languages;
import com.RSWF.settings.Settings;
import com.RSWF.settings.Versions;

public class Updater {

	public static void main(String[] args) {
		new Updater();
	}

	public static void Update() {

	}

	public Updater() {
		String lines = "";
		URL url;
		try { //TODO: Make this a method.
			url = new URL(Languages.getString("checkForUpdates.version_url") + Versions.VERSION); //$NON-NLS-1$
			URLConnection yc = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				lines += inputLine;
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (lines == null || lines.indexOf("}") <= 0) return;

		try {
			JsonRootNode parse = new JdomParser().parse(lines);

			if (parse.getStringValue("Update") != null) {
				if (parse.getStringValue("Update").equalsIgnoreCase("NOUPDATE")) {
					Log.info(Languages.getString("checkForUpdates.No_Updates_Found")); //$NON-NLS-1$
					return;
				}

				if (parse.getStringValue("Update").equalsIgnoreCase("SOFTUPDATE")) {
					Log.info(Languages.getString("checkForUpdates.Soft_Update_Found")); //$NON-NLS-1$
					Settings.setBoolean("foundUpdate", Boolean.TRUE);
					Settings.setDownloadLink(parse.getStringValue("Download"));
					Settings.setNote(parse.getStringValue("Note"));
					return;
				}

				if (parse.getStringValue("Update").equalsIgnoreCase("FORCEUPDATE")) {
					Log.info(Languages.getString("checkForUpdates.Force_Update_Found")); //$NON-NLS-1$
					Settings.setBoolean("forceUpdate", Boolean.TRUE); // Make the client not start because the update is needed.
					Settings.setDownloadLink(parse.getStringValue("Download"));
					Settings.setNote(parse.getStringValue("Note"));
					return;
				}
			} else {
				Log.info(Languages.getString("checkForUpdates.Could_Not_Check"));
			}

		} catch (InvalidSyntaxException e) {
			Log.error(Languages.getString("checkForUpdates.Could_Not_Check"));
		}

	}

}
