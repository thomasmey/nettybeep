package de.m3y3r.nbeep;

import java.util.Set;

/* models a profile registration */
public interface Profile {
	String getName();
	String getUri();

	//TODO: provide default impl for below
//	Set<Class> getMessagesDuringChannelCreation();
//	Set<Class> getMessagesStaringOneToOne();
//	Set<Class> getMessagesInPositiveReplies();
//	Set<Class> getMessagesInNegativeReplies();
//	Set<Class> getMessagesInOneToManyExchanges();
}
