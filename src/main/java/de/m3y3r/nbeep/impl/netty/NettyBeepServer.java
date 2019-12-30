package de.m3y3r.nbeep.impl.netty;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.m3y3r.nbeep.Server;
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
						ch.pipeline().addLast("frameDecoder", new BeepFrameDecoder());
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
