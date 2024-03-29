package com.RSWF.methods.loader;

import java.applet.Applet;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.RSWF.methods.functions.Functions;
import com.RSWF.methods.languages.classes.Languages;
import com.RSWF.settings.Settings;

public class RSApplet {

	private Applet applet;

	public String appletClass, archive, frameSource, GAMEPACK_JAR = Languages.getString("RSApplet.GAMEPACK_SAVE"); //$NON-NLS-1$

	public Pattern PARAMETER_PATTERN = Pattern.compile(Languages.getString("RSApplet.PARAMETER_PATTERN")), //$NON-NLS-1$
			CODE_PATTERN = Pattern.compile(Languages.getString("RSApplet.CODE_PATTERN")), //$NON-NLS-1$
			ARCHIVE_PATTERN = Pattern.compile(Languages.getString("RSApplet.ARCHIVE_PATTERN")), //$NON-NLS-1$
			SOURCE_PATTERN = Pattern.compile(Languages.getString("RSApplet.SOURCE_PATTERN")); //$NON-NLS-1$

	public ClientStub stub;

	public URL world;

	public RSApplet() throws MalformedURLException, IOException, InterruptedException {
		String source = Functions.getPageSource(new URL(Languages.getString("RSApplet.DOWNLOAD_URL"))); //$NON-NLS-1$
		Settings.setStatus(Languages.getString("RSApplet.STRING_STATUS_Parsing_the_info.")); //$NON-NLS-1$

		if (source == null) {
			Settings.setStatus(Languages.getString("RSApplet.STRING_STATUS_Error_Could_Not_download.")); //$NON-NLS-1$
			return;
		}

		Matcher matcher = SOURCE_PATTERN.matcher(source);

		if (matcher.find()) {
			String match = matcher.group(1);
			frameSource = Functions.getPageSource(new URL(match));
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

					Settings.setStatus(Languages.getString("RSApplet.STRING_STATUS_Downloading_The_Gamepack")); //$NON-NLS-1$

					Functions.Download(world.toString() + "/" + archive, GAMEPACK_JAR);

					Settings.setStatus(Languages.getString("RSApplet.STRING_STATUS_Download_finished_parsing_applet...")); //$NON-NLS-1$

					{
						/** Applet stuff **/
						setApplet((Applet) new URLClassLoader(new URL[] { new File(GAMEPACK_JAR).toURI().toURL() }).loadClass(appletClass.replaceAll(".class", "")).newInstance());
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

	/**
	 * @return the applet
	 */
	public Applet getApplet() {
		return applet;
	}

	private void setApplet(Applet Applet) {
		this.applet = Applet;
	}

}
