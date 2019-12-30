package de.m3y3r.nbeep.impl.netty;

import java.nio.charset.StandardCharsets;
import java.util.List;

import de.m3y3r.nbeep.frame.data.AnswerFrame;
import de.m3y3r.nbeep.frame.data.DataFrame;
import de.m3y3r.nbeep.frame.data.ErrorFrame;
import de.m3y3r.nbeep.frame.data.MessageFrame;
import de.m3y3r.nbeep.frame.data.NullFrame;
import de.m3y3r.nbeep.frame.data.ReplyFrame;
import de.m3y3r.nbeep.impl.netty.FrameDecodeState.State;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.ReplayingDecoder;

public class BeepFrameDecoder extends ReplayingDecoder<FrameDecodeState> {

	private ByteBuf delimiter = Delimiters.lineDelimiter()[0];
	private ByteBuf trailer = Unpooled.wrappedBuffer("END\r\n".getBytes(StandardCharsets.US_ASCII));

	public BeepFrameDecoder() {
		super(new FrameDecodeState(State.HEADER));
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		switch(state().getState()) {
		// sadly we cannot reuse new DelimiterBasedFrameDecoder(FrameHeader.MAX_LENGTH, true, Delimiters.lineDelimiter()[0]);
		case HEADER:
			//try to find delimiter
			int i = indexOf(in, delimiter, FrameHeader.MAX_LENGTH);
			if(i < 0) { 		
				// no CRLF found, invalid frame
				invalidFrame("Invalid Header, no CRLF, after max header length");
				// throw new TooLongFrameException();
			}
			ByteBuf header = in.readSlice(i + 2); // also consume CRLF
			FrameHeader frameHeader = FrameHeader.parse(header);
			state().setHeader(frameHeader);
			state().setState(State.PAYLOAD);
			break;
		case PAYLOAD:
			state().setPayload(in.readRetainedSlice(Integer.parseInt(state().getHeader().getSize())));
			state().setState(State.TRAILER);
			break;
		case TRAILER:
			if(in.readSlice(trailer.readableBytes()).compareTo(trailer) != 0) {
				invalidFrame("wrong trailer");
			}
			frameHeader = state().getHeader();
			ByteBuf payload = state().getPayload();
			DataFrame dataFrame = null;
			switch(frameHeader.getType()) {
			case "MSG": dataFrame = new MessageFrame(); break;
			case "RPY": dataFrame = new ReplyFrame(); break;
			case "ANS":
				dataFrame = new AnswerFrame();
				((AnswerFrame)dataFrame).setAnsNo(Integer.parseInt(frameHeader.getAnsNo()));
				break;
			case "ERR": dataFrame = new ErrorFrame(); break;
			case "NUL": dataFrame = new NullFrame(); break;
			}
			dataFrame.setChannel(Integer.parseInt(frameHeader.getChannel()));
			dataFrame.setMsgNo(Integer.parseInt(frameHeader.getMsgNo()));
			dataFrame.setMore(frameHeader.getMore().equals("*"));
			dataFrame.setSeqNo(Long.parseLong(frameHeader.getSeqNo()));
			dataFrame.setSize(Integer.parseInt(frameHeader.getSize()));
			dataFrame.setPayload(payload /* .nioBuffer(); */ );
			out.add(dataFrame);
			state().setState(State.HEADER);
			break;
		}
	}

	//TODO: refine and align with RFC recommendation
	private void invalidFrame(String string) {
		// reset state
		state().setState(State.HEADER);
		throw new IllegalStateException(string);
	}

	/**
	 * Returns the number of bytes between the readerIndex of the haystack and
	 * the first needle found in the haystack.  -1 is returned if no needle is
	 * found in the haystack.
	 */
	private static int indexOf(ByteBuf haystack, ByteBuf needle, int maxLength) {
		for (int i = 0; i <= maxLength; i ++) {
			int haystackIndex = i;
			int needleIndex;
			for (needleIndex = 0; needleIndex < needle.capacity(); needleIndex ++) {
				if (haystack.getByte(haystackIndex) != needle.getByte(needleIndex)) {
					break;
				} else {
					haystackIndex ++;
					if (haystackIndex == haystack.writerIndex() &&
							needleIndex != needle.capacity() - 1) {
						return -1;
					}
				}
			}

			if (needleIndex == needle.capacity()) {
				// Found the needle from the haystack!
				return i - haystack.readerIndex();
			}
		}
		return -1;
	}
}
