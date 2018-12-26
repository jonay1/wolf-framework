package com.wolf.network;

import com.wolf.network.base.Server;
import com.wolf.network.server.SocketServer;

public class Bootstrap {

	public static void main(String[] args) {
//		Server server = new HttpServer();
//		server.start();

		Server server = new SocketServer();
		server.start();
	}
}
