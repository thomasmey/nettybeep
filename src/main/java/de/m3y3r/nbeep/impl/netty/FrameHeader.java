package de.m3y3r.nbeep.impl.netty;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import io.netty.buffer.ByteBuf;

public class FrameHeader {

	// max header length derived from max header in rfc3080, as no explicit value is stated
	//                                    typ channel    msgno      m seqno      size       ansno
	public static final int MAX_LENGTH = "ANS 2147483647 2147483647 . 4294967295 2147483647 2147483647\r\n".length();
	private static final int MIN_LENGTH = "NUL 0 0 . 0 0\r\n".length();

	private final String type;
	private final String channel;
	private final String msgNo;
	private final String more;
	private final String seqNo;
	private final String size;
	private final String ansNo;

	/**
	 * parses an BEEP frame header
	 * @param header the ByteBuf with CRLF
	 * @return
	 */
	public static FrameHeader parse(ByteBuf header) {
		String type = null;
		String channel = null;
		String msgNo = null;
		String more = null;
		String seqNo = null;
		String size = null;
		String ansNo = null;

		StringTokenizer st = new StringTokenizer(header.readCharSequence(header.readableBytes() - 2, StandardCharsets.US_ASCII).toString(), " ");
		for(int i = 0; st.hasMoreElements(); i++) {
			switch(i) {
			case 0: type = st.nextToken(); break;
			case 1: channel = st.nextToken(); break;
			case 2: msgNo = st.nextToken(); break;
			case 3: more = st.nextToken(); break;
			case 4: seqNo = st.nextToken(); break;
			case 5: size = st.nextToken(); break;
			case 6: ansNo = st.nextToken(); break;
			default: throw new IllegalArgumentException("too many separators!");
			}
		}

		return new FrameHeader(type, channel, msgNo, more, seqNo, size, ansNo);
	}

	private FrameHeader(String type, String channel, String msgNo, String more, String seqNo,
			String size, String ansNo) {
		this.type = type;
		this.channel = channel;
		this.msgNo = msgNo;
		this.more = more;
		this.seqNo = seqNo;
		this.size = size;
		this.ansNo = ansNo;

		validate();
	}

	public String getType() {
		return type;
	}

	public String getChannel() {
		return channel;
	}

	public String getMsgNo() {
		return msgNo;
	}

	public String getMore() {
		return more;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public String getSize() {
		return size;
	}

	public String getAnsNo() {
		return ansNo;
	}

	private static List<String> VALID_TYPES = Collections.unmodifiableList(Arrays.asList("MSG", "RPY", "ANS", "ERR", "NUL"));

	public void validate() {
		if(!VALID_TYPES.contains(type))
			throw new IllegalArgumentException("frame.type");
		isValid(Integer.parseInt(channel), 0, Integer.MAX_VALUE, "frame.channel");
		isValid(Integer.parseInt(msgNo), 0, Integer.MAX_VALUE, "frame.msgNo");
		//TODO: more
		isValid(Long.parseLong(seqNo), 0,  0xFFFFFFFFl, "frame.seqNo");
		isValid(Integer.parseInt(size), 0, Integer.MAX_VALUE, "frame.size");

		if("ANS".equals(type)) {
			isValid(Integer.parseInt(ansNo), 0, Integer.MAX_VALUE, "frame.ansNo");
		}
	}

	private void isValid(long l, int min, long max, String err) {
		if(l >= min && l <= max) return;
		throw new IllegalArgumentException(err);
	}
}
