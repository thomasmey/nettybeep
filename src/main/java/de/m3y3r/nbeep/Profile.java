package de.m3y3r.nbeep;

/* models a profile registration */
public interface Profile<T> {

	String getName();
	String getUri();

	/**
	 * returns the associated API for this profile
	 * @return
	 */
	T getApi(Session session);

	//TODO: provide default impl for below
//	Set<Class> getMessagesDuringChannelCreation();
//	Set<Class> getMessagesStaringOneToOne();
//	Set<Class> getMessagesInPositiveReplies();
//	Set<Class> getMessagesInNegativeReplies();
//	Set<Class> getMessagesInOneToManyExchanges();
}
