package de.m3y3r.nbeep.profile.channel.mgmt.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.persistence.oxm.MediaType;

import de.m3y3r.nbeep.CallbackChannel;
import de.m3y3r.nbeep.CallbackMessage;
import de.m3y3r.nbeep.Message;
import de.m3y3r.nbeep.Profile;
import de.m3y3r.nbeep.ProfileCodec;
import de.m3y3r.nbeep.api.Channel;
import de.m3y3r.nbeep.netty.model.frame.data.DataFrame;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

public class DefaultBeepMessage implements Message {

	private final int messageNo;
	private final Channel channel;
	private final Message reply;

	private final List<DataFrame> frames;
	private boolean isComplete = false;

	private Object payload;

	public DefaultBeepMessage(Channel channel, int messageNo, boolean isReply, String payload) {
		this.channel = channel;
		this.messageNo = messageNo;
		this.frames = new ArrayList<>();
		if(!isReply) {
			this.reply = new DefaultBeepMessage(channel, messageNo, true);
		} else {
			this.reply = null;
		}

		this.payload = payload;
	}

	public DefaultBeepMessage(Channel channel, int messageNo, boolean isReply) {
		this(channel, messageNo, isReply, null);
	}

	public DefaultBeepMessage(Channel channel, int messageNo) {
		this(channel, messageNo, false, null);
	}

	@Override
	public void addCallback(CallbackMessage callback) {
		// TODO Auto-generated method stub
	}

	@Override
	public MediaType getMediaType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPayload() {
		return payload;
	}

	@Override
	public Object sendReply(Object payload, MediaType mediaType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object sendError(Object payload, MediaType mediaType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object sendAnswer(Object payload, MediaType mediaType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message getReply() {
		return reply;
	}

	@Override
	public boolean isComplete() {
		return isComplete;
	}

	@Override
	public void addDataFrame(DataFrame dataFrame) {
		if(isComplete) {
			throw new IllegalStateException();
		}

		frames.add(dataFrame);
		isComplete = !dataFrame.getHeader().getMore();

		//TODO: testing only
		if(isComplete) {
			try {
				doCallback(frames);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void doCallback(List<DataFrame> frames) throws Exception {
		CompositeByteBuf buf = Unpooled.compositeBuffer(frames.size());
		for(DataFrame f: frames) {
			buf.addComponent(true, f.getPayload());
		}

		Profile profile = channel.getProfile();

		CallbackChannel channelCallback = null;
		// process dataFrame into profile specific model
		ProfileCodec msgCodec = profile.getProfileCodec(channel.getSession());
		Object profileModel = msgCodec.decode(buf);
		System.out.println(profileModel);

//		switch(dataFrame.getHeader().getType()) {
//		case "MSG": channelCallback.onMessage(channel, profileModel); break;
//		case "RPY": channelCallback.onReply(channel, message, profileModel); break;
//		case "ANS": channelCallback.onAnswer(channel, message, profileModel); break;
//		case "ERR": channelCallback.onError(channel, message, profileModel); break;
//		case "NUL": channelCallback.onNull(channel, message, profileModel); break;
//		}
	}
}
