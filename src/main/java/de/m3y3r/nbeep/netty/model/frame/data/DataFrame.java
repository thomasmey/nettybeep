package de.m3y3r.nbeep.netty.model.frame.data;

import io.netty.buffer.ByteBuf;

public class DataFrame {

	private FrameHeader header;
	private ByteBuf payload;

	public FrameHeader getHeader() {
		return header;
	}
	public void setHeader(FrameHeader header) {
		this.header = header;
	}

	public ByteBuf getPayload() {
		return payload;
	}
	public void setPayload(ByteBuf payload) {
		this.payload = payload;
	}
}
