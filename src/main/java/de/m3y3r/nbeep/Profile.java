package de.m3y3r.nbeep;

import de.m3y3r.nbeep.api.Session;

/* models a profile registration */
public interface Profile<A> {

	String getName();
	String getUri();

	/**
	 * returns the associated user API for this profile
	 * @return
	 */
	A getApi(Session session);

	/**
	 * get the per Profile MessageToMessageCodec
	 * An optional codec that converts DataFrames into model objects and vice versa
	 * @param session
	 * @return
	 */
	ProfileCodec<Object> getProfileCodec(Session session);

	//TODO: provide default impl for below
//	Set<Class> getMessagesDuringChannelCreation();
//	Set<Class> getMessagesStaringOneToOne();
//	Set<Class> getMessagesInPositiveReplies();
//	Set<Class> getMessagesInNegativeReplies();
//	Set<Class> getMessagesInOneToManyExchanges();
}
