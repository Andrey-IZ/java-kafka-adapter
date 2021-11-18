package com.sber.javaschool.finalproject.json2http.service.http.version;

import java.text.MessageFormat;
import java.util.Objects;

public class ProtocolVersion implements Comparable<ProtocolVersion> {
	private final String protocolName;
	private final int major;
	private final int minor;

	public ProtocolVersion(String protocolName, int major, int minor) {
		this.protocolName = protocolName;
		this.major = major;
		this.minor = minor;
	}

	public String getProtocolName() {
		return protocolName;
	}

	public int getMinor() {
		return minor;
	}

	public int getMajor() {
		return major;
	}

	@Override
	public int compareTo(ProtocolVersion that) {
		int delta = this.getMajor() - that.getMajor();
		if (delta == 0) {
			delta = this.getMinor() - that.getMinor();
		}

		return delta;
	}

	public final boolean greaterThan(ProtocolVersion that) {
		return this.isComparable(that) && this.compareTo(that) >= 0;
	}

	private boolean isComparable(ProtocolVersion that) {
		return that != null && this.protocolName.equals(that.protocolName);
	}

	public final boolean lessThan(ProtocolVersion version) {
		return this.isComparable(version) && this.compareTo(version) <= 0;
	}

	public String asString() {
		return MessageFormat.format("{0}.{1}", getMajor(), getMinor());
	}

	public String toRequestFormat() {
		return getProtocolName() +
				'/' +
				getMajor() +
				'.' +
				getMinor();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ProtocolVersion that = (ProtocolVersion) o;
		return major == that.major && minor == that.minor && protocolName.equals(that.protocolName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(protocolName, major, minor);
	}
}
