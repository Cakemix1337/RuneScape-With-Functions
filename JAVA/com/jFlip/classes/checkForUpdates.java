package com.jFlip.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.json.simple.parser.ParseException;

import argo.jdom.JdomParser;
import argo.jdom.JsonRootNode;
import argo.saj.InvalidSyntaxException;

import com.jFlip.inc.Settings;
import com.jFlip.inc.Versions;

public class checkForUpdates {

	private String response;
	private URL url;
	private Double current_Version;
	private Double minimal_Version;
	private String new_Version_Link;
	private String lines = "";

	public static void main(String[] args) {
		try {
			new checkForUpdates();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Update() {

	}

	@SuppressWarnings("unused")
	public checkForUpdates() throws ParseException {

		try {
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
					// No updates found
					Log.info(Languages.getString("checkForUpdates.No_Updates_Found")); //$NON-NLS-1$
					return;
				}

				if (parse.getStringValue("Update").equalsIgnoreCase("SOFTUPDATE")) {
					Log.info(Languages.getString("checkForUpdates.Soft_Update_Found")); //$NON-NLS-1$
					Settings.setBoolean("foundUpdate", Boolean.FALSE);
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
