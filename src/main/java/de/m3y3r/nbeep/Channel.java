package de.m3y3r.nbeep;

import de.m3y3r.nbeep.frame.data.MessageFrame;

public interface Channel {

	/* the bound Profile, must not be null */
	Profile getProfile();
	int getChannelNo();
	void sendMessage(MessageFrame msg);
}
