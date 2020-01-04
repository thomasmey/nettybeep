package de.m3y3r.nbeep.profile.channel.mgmt.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.persistence.oxm.MediaType;

import de.m3y3r.nbeep.CallbackChannel;
import de.m3y3r.nbeep.CallbackSession;
import de.m3y3r.nbeep.Profile;
import de.m3y3r.nbeep.api.Channel;
import de.m3y3r.nbeep.api.Session;
import de.m3y3r.nbeep.netty.ChannelManager;
import de.m3y3r.nbeep.netty.model.frame.data.DataFrame;
import de.m3y3r.nbeep.netty.model.frame.data.FrameHeader;
import de.m3y3r.nbeep.profile.channel.mgmt.ChannelManagement;
import de.m3y3r.nbeep.profile.channel.mgmt.ChannelManagementProfile;
import de.m3y3r.nbeep.profile.channel.mgmt.model.Greeting;

public class DefaultChannelManagement implements ChannelManagement, ChannelManager, CallbackSession, CallbackChannel {

	private final Session session;
	private final Map<Integer, Channel> channelNo2Channel;

	public DefaultChannelManagement(Session session) {
		this.session = session;
		this.channelNo2Channel = new HashMap<>();

		// add channel 0
		channelNo2Channel.put(0, new DefaultBeepChannel(session, 0, new ChannelManagementProfile()));
	}

	@Override
	public Channel startChannel(int channelNo, List<Profile> profiles, Object payload, MediaType mediaType) {
		return null;
	}

	@Override
	public void stopChannel(int channelNo) {
	}

	//	@Override
	public void onNewSession(Session session) {
		//send a raw data frame, as the first RPY is not coupled to a MSG
		DataFrame replyFrame = new DataFrame();
		Greeting greeting = new Greeting();
		List<de.m3y3r.nbeep.profile.channel.mgmt.model.Profile> profiles = new ArrayList<>();

		if(session.isListeningRole()) {
			//TODO: add all profiles we did register with
		}
		greeting.setProfiles(profiles);

		// send RPY on channel 0
		String size = "11";
		FrameHeader header = new FrameHeader("RPY", "0", "0", ".", "0", size, null);
		replyFrame.setHeader(header);
//		replyFrame.setPayload(greeting);

		Object provider = session.getProvider();
		io.netty.channel.Channel ch = (io.netty.channel.Channel) provider;
		ch.writeAndFlush(replyFrame);
	}

	@Override
	public void onReply(Channel channel, Object message, Object reply) {
	}

	@Override
	public Channel getChannel(int channel) {
		return channelNo2Channel.get(channel);
	}
}
