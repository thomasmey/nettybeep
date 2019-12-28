package de.m3y3r.nbeep;

public interface Channel {

	/* the bound Profile, must not be null */
	Profile getProfile();
	int getChannelNo();
}
