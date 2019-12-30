package de.m3y3r.nbeep.profile.channel.mgmt.impl;

import java.util.List;

import de.m3y3r.nbeep.Channel;
import de.m3y3r.nbeep.Profile;
import de.m3y3r.nbeep.Session;
import de.m3y3r.nbeep.profile.channel.mgmt.ChannelManagement;

public class DefaultChannelManagement implements ChannelManagement {

	private final Session session;

	public DefaultChannelManagement(Session session) {
		this.session = session;
	}

	@Override
	public Channel startChannel(int channelNo, List<Profile> profiles, Object msg) {
		return null;
	}

	@Override
	public void stopChannel(int channelNo) {
	}

}
