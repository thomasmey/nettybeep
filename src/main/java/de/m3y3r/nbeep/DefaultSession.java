package de.m3y3r.nbeep;

import de.m3y3r.nbeep.api.Session;
import de.m3y3r.nbeep.netty.ChannelManager;
import io.netty.channel.socket.SocketChannel;

public class DefaultSession implements Session {

	private ChannelManager channelManager;

	public DefaultSession(SocketChannel ch) {
	}

	@Override
	public boolean isListeningRole() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInitiatingRole() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addChannelCallback(int channelNo, CallbackChannel channelCallback) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	public ChannelManager getChannelManager() {
		return channelManager;
	}
	public void setChannelManager(ChannelManager channelManager) {
		this.channelManager = channelManager;
	}

}
