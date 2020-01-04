package de.m3y3r.nbeep.netty.codec;

import de.m3y3r.nbeep.netty.model.frame.data.FrameHeader;
import io.netty.buffer.ByteBuf;

public class FrameDecodeState {
	public static enum State {HEADER, PAYLOAD, TRAILER}

	private State state;
	private FrameHeader header;
	private ByteBuf payload;

	public FrameDecodeState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
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
