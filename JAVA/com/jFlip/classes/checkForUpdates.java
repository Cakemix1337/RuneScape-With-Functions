package com.jFlip.classes;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.jFlip.inc.Versions;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class checkForUpdates {

	private String response;
	private URL url;

	public checkForUpdates() {
		try {
			url = new URL("http://site.com/api.php?v="
					+ Versions.VERSION);
			response = Functions.getPageSource(url);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		if (response == null)
			return;

		JSONObject json = (JSONObject) JSONSerializer.toJSON(response);
		String current_Version = json.getString("current_Version");
		System.out.println(current_Version);
	}

}
