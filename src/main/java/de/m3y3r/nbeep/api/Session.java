package de.m3y3r.nbeep.api;

import de.m3y3r.nbeep.CallbackChannel;
import de.m3y3r.nbeep.netty.ChannelManager;

/* models an BEEP session
 * which is a TCP connections between two peers
 */
public interface Session {

	/* 2.1 Role */
	boolean isListeningRole();
	boolean isInitiatingRole();

//	void addCallback(CallbackSession sessionCallback);

	void addChannelCallback(int channelNo, CallbackChannel channelCallback);

//	Channel startChannel(int channelNo, Object payload, MediaType mediaType);

	/**
	 * get Session provider, i.e. the underlying implementation, i.e. netty channel
	 */
	Object getProvider();

//	/**
//	 * get's the channel
//	 * @param channelNo
//	 * @return
//	 */
//	Channel getChannel(int channelNo);
	ChannelManager getChannelManager();
}
