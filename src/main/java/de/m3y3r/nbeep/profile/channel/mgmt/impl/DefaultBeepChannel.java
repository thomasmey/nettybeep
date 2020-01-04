package de.m3y3r.nbeep.profile.channel.mgmt.impl;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.persistence.oxm.MediaType;

import de.m3y3r.nbeep.CallbackMessage;
import de.m3y3r.nbeep.Message;
import de.m3y3r.nbeep.Profile;
import de.m3y3r.nbeep.api.Channel;
import de.m3y3r.nbeep.api.Session;

public class DefaultBeepChannel implements Channel {

	private final Session session;
	private final Profile<?> profile;
	private final int channelNo;

	private final Map<Integer, Message> messages;

	private int currentMessageNumber;
	private int currentFrameCount;

	public DefaultBeepChannel(Session session, int channelNo, Profile<?> profile) {
		this.messages = new HashMap<>();
		this.session = session;
		this.channelNo = channelNo;
		this.profile = profile;

		//FIXME: Add fake message 0 for channel 0, as only an reply is send in that special case
		if(channelNo == 0) {
			messages.put(0, new DefaultBeepMessage(this, 0, false, "<greeting>"));
		}
	}

	@Override
	public Session getSession() {
		return session;
	}

	@Override
	public Profile getProfile() {
		return profile;
	}

	@Override
	public int getChannelNo() {
		return channelNo;
	}

	@Override
	public Message sendMessage(Object payload, MediaType mediaType, CallbackMessage callback) {
		return null;
	}

	@Override
	public Message getMessage(int msgNo) {
		return messages.get(msgNo);
	}

	@Override
	public int getMessageNumber() {
		return currentMessageNumber;
	}

	@Override
	public int getFrameCount() {
		return currentFrameCount;
	}

}
