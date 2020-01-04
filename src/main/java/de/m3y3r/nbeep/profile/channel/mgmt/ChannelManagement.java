package de.m3y3r.nbeep.profile.channel.mgmt;

import java.util.List;

import org.eclipse.persistence.oxm.MediaType;

import de.m3y3r.nbeep.Profile;
import de.m3y3r.nbeep.api.Channel;

public interface ChannelManagement {
	Channel startChannel(int channelNo, List<Profile> profiles, Object payload, MediaType mediaType);

	/**
	 * closes a given channel
	 * @param channelNo
	 */
	void stopChannel(int channelNo);
}
