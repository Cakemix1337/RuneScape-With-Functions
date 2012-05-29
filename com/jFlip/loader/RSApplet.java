package com.jFlip.loader;

import java.applet.Applet;
import java.awt.Dimension;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jFlip.inc.Settings;

public class RSApplet {

	private Applet applet;

	public Pattern PARAMETER_PATTERN = Pattern
			.compile("<param name=\"([^\\s]+)\"\\s+value=\"([^>]*)\">"),
			CODE_PATTERN = Pattern.compile("code=(.*) "),
			ARCHIVE_PATTERN = Pattern.compile("archive=(.*) "),
			SOURCE_PATTERN = Pattern.compile("src=\"(.*?)\" ");

	public String appletClass, archive, frameSource, GAMEPACK_JAR = "./gamepack.jar";

	public ClientStub stub;

	public URL world;

	public RSApplet() throws MalformedURLException, IOException,
			InterruptedException {
		String source = getPageSource(new URL(
				"http://www.runescape.com/game.ws?j=1"));
		com.jFlip.inc.Settings.setStatus("Parsing the info.");

		if (source == null) {
			Settings.setStatus("Error, could not download.");
			return;
		}

		Matcher matcher = SOURCE_PATTERN.matcher(source);

		if (matcher.find()) {
			String match = matcher.group(1);
			frameSource = getPageSource(new URL(match));
			matcher = ARCHIVE_PATTERN.matcher(frameSource);
			Matcher codeMatcher = CODE_PATTERN.matcher(frameSource);
			if (matcher.find() && codeMatcher.find()) {
				try {
					world = new URL(match.substring(0, match.indexOf("/,")));
					appletClass = codeMatcher.group(1);
					stub = new ClientStub();
					stub.parsePARAMS(PARAMETER_PATTERN, frameSource);
					stub.setCodeBase(world);
					stub.setDocumentBase(world);
					archive = matcher.group(1);
					
					Settings.setStatus("Downloading the<br /> gamepack.jar.");

					Download(world.toString() + "/", archive);

					Settings.setStatus("Download finished.<br />Parsing applet...");

					{
						/** Applet stuff **/
						setApplet((Applet) new URLClassLoader(
								new URL[] { new File(GAMEPACK_JAR).toURI()
										.toURL() }).loadClass(
								appletClass.replaceAll(".class", ""))
								.newInstance());
						getApplet().setStub(stub);
						getApplet().setSize(new Dimension(763, 508));
					}
					System.gc();
				} catch (Throwable e) {
					e.printStackTrace();
				}

			}
		}

	}

	private void setApplet(Applet Applet) {
		this.applet = Applet;
	}

	public String getPageSource(URL url) throws IOException,
			InterruptedException {
		URLConnection uc = url.openConnection();
		uc.addRequestProperty(
				"Accept",
				"text/xml,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
		uc.addRequestProperty("Accept-Charset",
				"ISO-8859-1,utf-8;q=0.7,*;q=0.7");
		uc.addRequestProperty("Accept-Encoding", "gzip,deflate");
		uc.addRequestProperty("Accept-Language", "en-gb,en;q=0.5");
		uc.addRequestProperty("Connection", "keep-alive");
		uc.addRequestProperty("Host", "www.runescape.com");
		uc.addRequestProperty("Keep-Alive", "300");
		uc.addRequestProperty(
				"User-Agent",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-GB; rv:1.8.0.6) Gecko/20060728 Firefox/1.5.0.6");

		DataInputStream di = new DataInputStream(uc.getInputStream());
		byte[] tmp = new byte[uc.getContentLength()];
		di.readFully(tmp);
		di.close();
		return new String(tmp);
	}

	public void Download(String world, String archive) throws Exception {
		URLConnection jarConnection = new URL(world + archive).openConnection();
		FileOutputStream out = new FileOutputStream(GAMEPACK_JAR);
		InputStream input = jarConnection.getInputStream();
		byte[] tmp = new byte[1024];
		int read;
		while ((read = input.read(tmp)) != -1) {
			out.write(tmp, 0, read);
		}
	}

	/**
	 * @return the applet
	 */
	public Applet getApplet() {
		return applet;
	}

}
