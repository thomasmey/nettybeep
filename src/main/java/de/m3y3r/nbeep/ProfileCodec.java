package de.m3y3r.nbeep;

import io.netty.buffer.ByteBuf;

public interface ProfileCodec<PM> {

	PM decode(ByteBuf data) throws Exception;
	ByteBuf encode(PM model) throws Exception;
}
