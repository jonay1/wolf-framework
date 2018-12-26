package com.wolf.network.server.channel;

import com.wolf.network.server.HttpServer;
import com.wolf.network.server.handler.HttpServerHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LoggingHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HttpServerChannelInitailaizer extends ChannelInitializer<SocketChannel> {
	
	private HttpServer server;

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new HttpServerCodec(), new HttpObjectAggregator(65536));
		pipeline.addLast(new LoggingHandler());
		pipeline.addLast(new HttpServerHandler());
	}

}
