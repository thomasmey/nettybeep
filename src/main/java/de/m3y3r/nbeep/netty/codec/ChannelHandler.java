package de.m3y3r.nbeep.netty.codec;

import de.m3y3r.nbeep.Message;
import de.m3y3r.nbeep.api.Channel;
import de.m3y3r.nbeep.api.Session;
import de.m3y3r.nbeep.netty.model.frame.data.DataFrame;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/* dispatch frames into a given message of a channel */
public class ChannelHandler extends ChannelInboundHandlerAdapter {

	private Session session;

	public ChannelHandler(Session session) {
		this.session = session;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object objectMessage) throws Exception {
		DataFrame dataFrame = (DataFrame) objectMessage;
		Channel channel = session.getChannelManager().getChannel(dataFrame.getHeader().getChannel());

		// validate sequence number of channel
		validateSequenceNumber(channel, dataFrame);

		// get belonging original profileModel object
		Message message = channel.getMessage(dataFrame.getHeader().getMsgNo());
		if("MSG".equals(dataFrame.getHeader().getType())) {
			message.addDataFrame(dataFrame);
		} else {
			message.getReply().addDataFrame(dataFrame);
		}
	}

	private void validateSequenceNumber(Channel channel, DataFrame dataFrame) {
	}
}
