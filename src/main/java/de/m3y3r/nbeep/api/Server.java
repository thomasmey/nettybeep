package de.m3y3r.nbeep.api;

import java.util.Properties;

/* models an BEEP server */
public interface Server {
	void start(Properties config);
//	void start(Properties config, CallbackSession callbackSession);
//	void stop();
}
