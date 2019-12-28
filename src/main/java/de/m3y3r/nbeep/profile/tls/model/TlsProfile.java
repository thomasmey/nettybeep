package de.m3y3r.nbeep.profile.tls.model;

import de.m3y3r.nbeep.Profile;

public class TlsProfile implements Profile {

	@Override
	public String getUri() {
		return "http://iana.org/beep/TLS";
	}

	@Override
	public String getName() {
		return null;
	}
}
