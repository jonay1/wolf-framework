package com.wolf.network.server;

import com.wolf.network.server.channel.HttpServerChannelInitailaizer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class HttpServer extends SocketServer {

	public HttpServer() {
		super(7000);
	}

	public HttpServer(int port) {
		super(port);
	}

	@Override
	protected ChannelInitializer<SocketChannel> childHandler() {
		return new HttpServerChannelInitailaizer(this);
	}
}
