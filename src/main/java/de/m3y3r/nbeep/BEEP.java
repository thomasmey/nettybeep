package de.m3y3r.nbeep;

import de.m3y3r.nbeep.api.Server;
import de.m3y3r.nbeep.api.Session;
import de.m3y3r.nbeep.netty.NettyBeepServer;
import de.m3y3r.nbeep.profile.channel.mgmt.impl.DefaultChannelManagement;
import io.netty.channel.socket.SocketChannel;

public class BEEP {

	public static Server getServer() {
		return new NettyBeepServer();
	}

	public static Session newSession(SocketChannel ch) {
		DefaultSession session = new DefaultSession(ch);
		DefaultChannelManagement channelManagement = new DefaultChannelManagement(session);
		session.setChannelManager(channelManagement);
		return session;
	}
}
