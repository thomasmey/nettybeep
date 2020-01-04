package de.m3y3r.nbeep.profile.channel.mgmt;

import de.m3y3r.nbeep.Profile;
import de.m3y3r.nbeep.ProfileCodec;
import de.m3y3r.nbeep.api.Session;
import de.m3y3r.nbeep.profile.channel.mgmt.impl.DefaultChannelManagement;
import de.m3y3r.nbeep.profile.channel.mgmt.netty.ChannelManagementCodec;

public class ChannelManagementProfile implements Profile<ChannelManagement> {

	@Override
	public String getUri() {
		return null;
	}

	@Override
	public String getName() {
		return "BEEP Channel Management";
	}

	@Override
	public ChannelManagement getApi(Session session) {
		return new DefaultChannelManagement(session);
	}

	@Override
	public ProfileCodec<Object> getProfileCodec(Session session) {
		return new ChannelManagementCodec(session);
	}
}
