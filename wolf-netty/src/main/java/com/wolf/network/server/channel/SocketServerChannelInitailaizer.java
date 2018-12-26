package com.wolf.network.server.channel;

import java.nio.charset.Charset;

import com.wolf.network.codec.handler.StringLengthFieldDecoder;
import com.wolf.network.codec.handler.StringLengthFieldEncoder;
import com.wolf.network.server.SocketServer;
import com.wolf.network.server.handler.SocketServerHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class SocketServerChannelInitailaizer extends ChannelInitializer<SocketChannel> {

	private SocketServer server;

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		int length = server.length();
		Charset charset = server.charset();
		pipeline.addLast(new LoggingHandler());
		pipeline.addLast(new StringLengthFieldDecoder(length, charset));
		pipeline.addLast(new SocketServerHandler());
		pipeline.addLast(new StringLengthFieldEncoder(length, charset));
	}

}
