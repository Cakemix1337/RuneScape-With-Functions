package com.jFlip.loader;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.applet.AudioClip;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientStub implements AppletStub {

	public boolean active = false;

	private Applet applet;

	private URL codebase;

	private URL documentbase;

	private Map<String, String> params = new HashMap<String, String>();

	public void appletResize(int width, int height) {

	}

	public Applet getApplet() {
		return applet;
	}

	public AppletContext getAppletContext() {
		return null;
	}

	public AudioClip getAudioClip(final URL url) {
		System.out.println("NOT YET IMPLEMENTED getAudioClip=" + url);
		return null;
	}

	public URL getCodeBase() {
		return codebase;
	}

	public URL getDocumentBase() {
		return documentbase;
	}
	public String getParameter(String name) {
		return params.get(name);
	}
	public boolean isActive() {
		return isActive();
	}

	public void parsePARAMS(Pattern parameterPattern, String frameSource) {

		Matcher param = parameterPattern.matcher(frameSource);
		while (param.find()) {
			String key = param.group(1);
			String value = param.group(2);
			if (key.equalsIgnoreCase("haveie6"))
				value = "false";
			params.put(key, value);
		}
	}
	public void setActive(boolean active) {
		this.active = active;
	}

	public void setCodeBase(URL codeBase) {
		this.codebase = codeBase;
	}

	public void setDocumentBase(URL documentBase) {
		this.documentbase = documentBase;
	}

}
