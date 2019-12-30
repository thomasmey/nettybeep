package de.m3y3r.nbeep.profile.channel.mgmt;

import java.util.List;

import de.m3y3r.nbeep.Channel;
import de.m3y3r.nbeep.Profile;

public interface ChannelManagement {
	Channel startChannel(int channelNo, List<Profile> profiles, Object msg);

	/**
	 * closes a given channel
	 * @param channelNo
	 */
	void stopChannel(int channelNo);
}
