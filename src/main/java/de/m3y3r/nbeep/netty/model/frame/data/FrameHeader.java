package de.m3y3r.nbeep.netty.model.frame.data;

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
	private final int channel;
	private final int msgNo;
	private final boolean more;
	private final long seqNo;
	private final int size;
	// only for type ANS
	private final Integer ansNo;

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
			default: throw new PoorlyFormedFrameException("frame.header.separators");
			}
		}

		return new FrameHeader(type, channel, msgNo, more, seqNo, size, ansNo);
	}

	public FrameHeader(String type, String channel, String msgNo, String more, String seqNo, String size, String ansNo) {

		validate(type, channel, msgNo, more, seqNo, size, ansNo);

		this.type = type;
		this.channel = Integer.parseInt(channel);
		this.msgNo = Integer.parseInt(msgNo);
		this.more = "*".equals(more);
		this.seqNo = Long.parseLong(seqNo);
		this.size = Integer.parseInt(size);

		if("ANS".equals(type)) {
			this.ansNo = Integer.valueOf(ansNo);
		} else {
			this.ansNo = null;
		}
	}

	public String getType() {
		return type;
	}

	public int getChannel() {
		return channel;
	}

	public int getMsgNo() {
		return msgNo;
	}

	public boolean getMore() {
		return more;
	}

	public long getSeqNo() {
		return seqNo;
	}

	public int getSize() {
		return size;
	}

	public Integer getAnsNo() {
		return ansNo;
	}

	private static List<String> VALID_TYPES = Collections.unmodifiableList(Arrays.asList("MSG", "RPY", "ANS", "ERR", "NUL"));
	private static List<String> VALID_MORE = Collections.unmodifiableList(Arrays.asList(".", "*"));

	public static void validate(String type, String channel, String msgNo, String more, String seqNo, String size, String ansNo) {
		if(!VALID_TYPES.contains(type)) {
			throw new PoorlyFormedFrameException("frame.header.type", type);
		}

		isValid(Integer.parseInt(channel), 0, Integer.MAX_VALUE, "frame.header.channel");
		isValid(Integer.parseInt(msgNo), 0, Integer.MAX_VALUE, "frame.header.msgNo");
		if(!VALID_MORE.contains(more)) {
			throw new PoorlyFormedFrameException("frame.header.more", more);
		}
		isValid(Long.parseLong(seqNo), 0,  0xFFFFFFFFl, "frame.header.seqNo");
		isValid(Integer.parseInt(size), 0, Integer.MAX_VALUE, "frame.header.size");

		if("ANS".equals(type)) {
			isValid(Integer.parseInt(ansNo), 0, Integer.MAX_VALUE, "frame.header.ansNo");
		} else {
			if(ansNo != null) {
				throw new PoorlyFormedFrameException("frame.header.ansNo", ansNo);
			}
		}
	}

	private static void isValid(long value, int min, long max, String attribute) {
		if(value >= min && value <= max) return;
		throw new PoorlyFormedFrameException(attribute, String.valueOf(value));
	}
}
