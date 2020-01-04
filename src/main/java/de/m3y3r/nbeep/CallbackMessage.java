package de.m3y3r.nbeep;

public interface CallbackMessage {
	
	/**
	 * a callback for a reply for a send message
	 * once a reply for the message is received this callback is triggered
	 * 
	 * @param msg the original message this reply is for
	 * @param reply the reply that was received
	 */
	//TODO: type reply as Reply, error as Error, etc. instead of Object
	void onReply(Message msg, Object reply);
	void onError(Message msg, Object error);
	void onAnswer(Message msg, Object answer);
}
