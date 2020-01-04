package de.m3y3r.nbeep.netty.codec;

import java.util.Arrays;

import de.m3y3r.nbeep.Message;
import de.m3y3r.nbeep.api.Channel;
import de.m3y3r.nbeep.api.Session;
import de.m3y3r.nbeep.netty.model.frame.data.DataFrame;
import de.m3y3r.nbeep.netty.model.frame.data.FrameHeader;
import de.m3y3r.nbeep.netty.model.frame.data.PoorlyFormedFrameException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * handles enforcement of rules describes in rfc3080
 * 2.2.1.1 "poorly-formed frames"
 */
public class PoorlyFormedFrameHandler extends ChannelInboundHandlerAdapter {

	private Session session;

	public PoorlyFormedFrameHandler(Session session) {
		this.session = session;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		DataFrame dataFrame = (DataFrame) msg;
		handle(dataFrame.getHeader());
		ctx.fireChannelRead(msg);
	}

	/*
	 * handles enforcement of rules describes in rfc3080
	 * 2.2.1.1 "poorly-formed frames"
	 */
	private void handle(FrameHeader header) {
		// rule: "if the header doesn't start with "MSG", "RPY", "ERR", "ANS", or "NUL";
		// rule: "if any of the parameters in the header cannot be determined or are invalid (i.e., syntactically incorrect)"
		// -> enforced by FrameHeader object creation

		// rule: "if the value of the channel number doesn't refer to an existing channel"
		validateChannelNo(header);
		
		// rule: "if the header starts with "MSG", and the message number refers to a "MSG" message that has been completely received but for which a reply has not been completely sent"
		validateMessageReplyInProgress(header);
		
		// rule: "if the header doesn't start with "MSG", and refers to a message number for which a reply has already been completely received"
		validateReplyAlreadyReceived(header);
		
		// rule: "if the header doesn't start with "MSG", and refers to a message number that has never been sent (except during session establishment, c.f., Section 2.3.1.1);"
		validateReplyUnknownMessage(header);

		/* rule: "if the header starts with "MSG", "RPY", "ERR", or "ANS", and
	      refers to a message number for which at least one other frame has
	      been received, and the three-character keyword starting this frame
	      and the immediately-previous received frame for this message
	      number are not identical"
	     */
		validateMsgNoOrder(header);

		/* rule: "if the header starts with "NUL", and refers to a message number
      	 for which at least one other frame has been received, and the
      	 keyword of of the immediately-previous received frame for this
      	 reply isn't "ANS"
      	 */
		validateAnswerClosing(header);

		/* rule: "if the continuation indicator of the previous frame received on
      	 the same channel was intermediate ("*"), and its message number
      	 isn't identical to this frame's message number
      	 */
		validateMoreIndicatorMsgNo(header);

		/* rule: "if the value of the sequence number doesn't correspond to the
      	 expected value for the associated channel (c.f., Section 2.2.1.2);
      	 */
		validateSequenceNumber(header);

		/* rule: "if the header starts with "NUL", and the continuation indicator is
      	 intermediate ("*") or the payload size is non-zero"
      	 */
		validateMoreIndicatorNullMessage(header);
	}

	private void validateMoreIndicatorNullMessage(FrameHeader header) {
	}

	private void validateSequenceNumber(FrameHeader header) {
	}

	private void validateMoreIndicatorMsgNo(FrameHeader header) {
	}

	private void validateAnswerClosing(FrameHeader header) {
	}

	/* rule: "if the header starts with "MSG", "RPY", "ERR", or "ANS", and
       refers to a message number for which at least one other frame has
       been received, and the three-character keyword starting this frame
       and the immediately-previous received frame for this message
       number are not identical"
     */
	private static String[] ALL_TYPES_WITHOUT_NUL = new String[] { "ANS", "ERR", "MSG", "RPY" };
	private void validateMsgNoOrder(FrameHeader header) {
//		session.getChannel(frame.getHeader().getChannel()).getPreviousFrame().getMsgNo != frame.getHeader().getMsgNo();
		int channelNo = header.getChannel();
		Channel channel = session.getChannelManager().getChannel(channelNo);
		if(Arrays.binarySearch(ALL_TYPES_WITHOUT_NUL, header.getType()) >= 0) {
			Message msg = channel.getMessage(header.getMsgNo());
			
		}
	}

	// rule: "if the header doesn't start with "MSG", and refers to a message number that has never been sent (except during session establishment, c.f., Section 2.3.1.1);"
	private void validateReplyUnknownMessage(FrameHeader header) {
		int channelNo = header.getChannel();
		Channel channel = session.getChannelManager().getChannel(channelNo);
		if(!"MSG".equals(header.getType())) {
			Message message = session.getChannelManager().getChannel(channelNo).getMessage(header.getMsgNo());
			if(channel.getFrameCount() > 0 && message == null) {
				throw new PoorlyFormedFrameException("msg.reply.unknown.msg", String.valueOf(channelNo));
			}
		}
	}

	// rule: "if the header doesn't start with "MSG", and refers to a message number for which a reply has already been completely received"
	private void validateReplyAlreadyReceived(FrameHeader header) {
		int channelNo = header.getChannel();
		if(!"MSG".equals(header.getType())) {
			Message message = session.getChannelManager().getChannel(channelNo).getMessage(header.getMsgNo());
			if(message != null && message.isComplete() && message.getReply().isComplete()) {
				throw new PoorlyFormedFrameException("msg.reply.already.received", String.valueOf(channelNo));
			}
		}
	}

	// rule: "if the header starts with "MSG", and the message number refers to a "MSG" message that has been completely received but for which a reply has not been completely sent"
	private void validateMessageReplyInProgress(FrameHeader header) {
		int channelNo = header.getChannel();
		if("MSG".equals(header.getType())) {
			Message message = session.getChannelManager().getChannel(channelNo).getMessage(header.getMsgNo());
			if(message != null && message.isComplete() && !message.getReply().isComplete()) {
				throw new PoorlyFormedFrameException("msg.reply.in.progress", String.valueOf(channelNo));
			}
		}
	}

	// rule: "if the value of the channel number doesn't refer to an existing channel"
	private void validateChannelNo(FrameHeader header) {
		int channelNo = header.getChannel();
		Channel channel = session.getChannelManager().getChannel(channelNo);
		if(channel == null) {
			throw new PoorlyFormedFrameException("frame.header.channel", String.valueOf(channelNo));
		}
	}
}
