package com.RSWF.methods.functions;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Functions {

	public static void centreWindow(Window frame) {
		Log.debug("Call method centreWindow(" + frame.getClass().getSimpleName() + ")");
		Dimension area = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((area.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((area.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);
	}

	public static void Download(String download, String output) throws Exception {
		new File(new File(output).getParent()).mkdir();
		if (new File(output).exists() && !new File(output).canWrite()) Log.error("Could not write the file " + output);
		URLConnection connection = new URL(download).openConnection();
		FileOutputStream out = new FileOutputStream(output);
		InputStream input = connection.getInputStream();
		byte[] tmp = new byte[1024];
		int read;
		while ((read = input.read(tmp)) != -1) {
			out.write(tmp, 0, read);
		}
	}

	public static String getPageSource(URL url) throws IOException, InterruptedException {
		URLConnection uc = url.openConnection();
		uc.addRequestProperty("Accept", "text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
		uc.addRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
		uc.addRequestProperty("Accept-Encoding", "gzip,deflate");
		uc.addRequestProperty("Accept-Language", "en-gb,en;q=0.5");
		uc.addRequestProperty("Connection", "keep-alive");
		uc.addRequestProperty("Host", "www.runescape.com");
		uc.addRequestProperty("Keep-Alive", "300");
		uc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-GB; rv:1.8.0.6) Gecko/20060728 Firefox/1.5.0.6");

		DataInputStream di = new DataInputStream(uc.getInputStream());
		byte[] tmp = new byte[uc.getContentLength()];
		di.readFully(tmp);
		di.close();
		return new String(tmp);
	}
}
