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

	public void setDocumentBase(URL documentBase) {
		this.documentbase = documentBase;
	}

	public void setCodeBase(URL codeBase) {
		this.codebase = codeBase;
	}

	public boolean isActive() {
		return isActive();
	}

	public URL getDocumentBase() {
		return documentbase;
	}

	public URL getCodeBase() {
		return codebase;
	}

	public String getParameter(String name) {
		return params.get(name);
	}

	public AppletContext getAppletContext() {
		return null;
	}

	public void appletResize(int width, int height) {

	}

	public AudioClip getAudioClip(final URL url) {
		System.out.println("NOT YET IMPLEMENTED getAudioClip=" + url);
		return null;
	}

	public Applet getApplet() {
		return applet;
	}

	private Applet applet;
	public boolean active = false;
	private Map<String, String> params = new HashMap<String, String>();

	private URL documentbase;
	private URL codebase;

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

}
