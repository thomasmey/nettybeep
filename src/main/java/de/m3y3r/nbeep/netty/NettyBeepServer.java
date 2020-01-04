package de.m3y3r.nbeep.netty;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.m3y3r.nbeep.BEEP;
import de.m3y3r.nbeep.api.Server;
import de.m3y3r.nbeep.api.Session;
import de.m3y3r.nbeep.netty.codec.BeepFrameDecoder;
import de.m3y3r.nbeep.netty.codec.ChannelHandler;
import de.m3y3r.nbeep.netty.codec.PoorlyFormedFrameHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyBeepServer implements Server {

	private static final Logger logger = LoggerFactory.getLogger(NettyBeepServer.class.getName());
	private EventLoopGroup eventLoopGroup;
	private Channel channel;
	private BEEP beep;

	@Override
	public void start(Properties config) {
		eventLoopGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			int port = Integer.parseInt(config.getProperty("port"));
			b.group(eventLoopGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						Session session = BEEP.newSession(ch);
						ch.pipeline().addLast("frameDecoder", new BeepFrameDecoder());
						ch.pipeline().addLast("poorlyFrameHandler", new PoorlyFormedFrameHandler(session));
						ch.pipeline().addLast("channelHandler", new ChannelHandler(session));
					}
				});
			ChannelFuture f = b.bind(port).sync();
		} catch (InterruptedException e) {
			logger.error("Int1", e);
		}
	}

	public void stop() {
		try {
			eventLoopGroup.shutdownGracefully().sync();
		} catch (InterruptedException e) {
			logger.error("Int2", e);
		}
	}
}
