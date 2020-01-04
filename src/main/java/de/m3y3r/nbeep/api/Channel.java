package de.m3y3r.nbeep.api;

import org.eclipse.persistence.oxm.MediaType;

import de.m3y3r.nbeep.CallbackMessage;
import de.m3y3r.nbeep.Message;
import de.m3y3r.nbeep.Profile;

public interface Channel {

	/**
	 * get's the BEEP session this channel belongs to
	 * @return
	 */
	Session getSession();

	/* the bound Profile, must not be null */
	Profile getProfile();
	int getChannelNo();

	/**
	 * sends a new message to the given channel
	 * @param payload the payload to send
	 * @param mediaType the mediatype of the payload for automatic conversion
	 * @param callback the callbacks for this message
	 * @return a 
	 */
	//TODO: return a Message or a DataFrame object here?
	Message sendMessage(Object payload, MediaType mediaType, CallbackMessage callback);
	Message getMessage(int msgNo);

	int getMessageNumber();
	int getFrameCount();
}
