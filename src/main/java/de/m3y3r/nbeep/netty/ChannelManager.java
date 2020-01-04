package de.m3y3r.nbeep.netty;

import de.m3y3r.nbeep.api.Channel;
import de.m3y3r.nbeep.api.Session;

public interface ChannelManager {

	Channel getChannel(int channel);
}
