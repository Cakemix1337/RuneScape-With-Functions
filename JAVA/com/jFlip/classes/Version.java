package com.jFlip.classes;

import com.jFlip.inc.Versions;

public class Version {

	private double version;
	private double major;
	private double minor;
	private int state;

	public Version(double Version, int State) {
		this.setVersion(Version);
		this.state = State;

	}

	public Version(double Minor, double Major) {
		this.setMinor(Minor);
		this.setMajor(Major);

	}

	public String getState() {
		if (this.state >= Versions.VERSION_TYPE.size() == false)
			return Versions.VERSION_TYPE.get(this.state);
		return Versions.VERSION_TYPE.get(0);
	}

	public Boolean checkVersion() {
		return false;
	}

	public double getMajor() {
		return major;
	}

	public void setMajor(double major) {
		this.major = major;
	}

	public double getMinor() {
		return minor;
	}

	public void setMinor(double minor) {
		this.minor = minor;
	}

	public double getVersion() {
		return version;
	}

	public void setVersion(double version) {
		this.version = version;
	}

}
