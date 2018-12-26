package com.wolf.network.server;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wolf.network.base.Server;
import com.wolf.network.server.channel.SocketServerChannelInitailaizer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class SocketServer implements Server {
	private Logger log = LoggerFactory.getLogger(getClass());

	protected int port = 6000;
	private int length = 4;
	protected Charset charset = StandardCharsets.UTF_8;

	private EventLoopGroup bossGroup = new NioEventLoopGroup();
	private EventLoopGroup workerGroup = new NioEventLoopGroup();

	public SocketServer() {

	}

	public SocketServer(int port) {
		this.port = port;
	}

	@Override
	public void start() {
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);
			b.childHandler(childHandler());
			ChannelFuture f = b.bind(port).sync();
			log.info("Listening on {}.", port);
			f.channel().closeFuture().sync();
			log.info("Listening on {} stopped.", port);
		} catch (InterruptedException e) {
			log.error("Interrupt", e);
		} finally {
			shutdown();
		}
	}

	protected ChannelInitializer<SocketChannel> childHandler() {
		return new SocketServerChannelInitailaizer(this);
	}

	@Override
	public void shutdown() {
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
	}

	public SocketServer port(int port) {
		this.port = port;
		return this;
	}

	public SocketServer length(int length) {
		this.length = length;
		return this;
	}

	public SocketServer charset(Charset charset) {
		this.charset = charset;
		return this;
	}

	public int port() {
		return port;
	}

	public int length() {
		return length;
	}

	public Charset charset() {
		return charset;
	}

}
