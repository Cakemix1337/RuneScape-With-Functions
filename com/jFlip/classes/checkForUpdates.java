package com.jFlip.classes;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class checkForUpdates {

	private URL url;
	private String response;

	public String getPageSource(URL url) throws IOException,
			InterruptedException {
		URLConnection uc = url.openConnection();
		uc.setConnectTimeout(300);
		DataInputStream di = new DataInputStream(uc.getInputStream());
		byte[] tmp = new byte[uc.getContentLength()];
		di.readFully(tmp);
		di.close();
		return new String(tmp);
	}

	@SuppressWarnings("deprecation")
	public checkForUpdates() {
		try {
		//	url = new URL("http://site.com/api.php?v="+URLEncoder.encode(Versions.PROGRAM, x-www-form-urlencoded ));
			response = getPageSource(url);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		if (response == null)
			return;
		
		
	}
}
