package de.m3y3r.nbeep.profile.tls;

import de.m3y3r.nbeep.Profile;
import de.m3y3r.nbeep.ProfileCodec;
import de.m3y3r.nbeep.api.Session;

public class TlsProfile implements Profile {

	@Override
	public String getUri() {
		return "http://iana.org/beep/TLS";
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public Object getApi(Session session) {
		return null;
	}

	@Override
	public ProfileCodec getProfileCodec(Session session) {
		return null;
	}
}
