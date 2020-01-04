package de.m3y3r.nbeep;

import de.m3y3r.nbeep.api.Channel;
import de.m3y3r.nbeep.model.Reply;

public interface CallbackChannel {

	void onReply(Channel channel, Object message, Object reply);
}
