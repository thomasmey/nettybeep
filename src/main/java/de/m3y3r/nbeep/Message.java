package de.m3y3r.nbeep;

import org.eclipse.persistence.oxm.MediaType;

import de.m3y3r.nbeep.netty.model.frame.data.DataFrame;

/* models an BEEP message */
public interface Message {

	void addCallback(CallbackMessage callback);

	MediaType getMediaType();
	Object getPayload();

	/* 2.1.1 exchange styles */
	Object sendReply(Object payload, MediaType mediaType);
	Object sendError(Object payload, MediaType mediaType);
	Object sendAnswer(Object payload, MediaType mediaType);

	Message getReply();

	boolean isComplete();

	void addDataFrame(DataFrame dataFrame);

}
