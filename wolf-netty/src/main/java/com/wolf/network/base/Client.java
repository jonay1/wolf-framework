package com.wolf.network.base;

import java.util.function.Function;

public interface Client {
	
	String send(String message);

	void send(String message, Function<String, String> callback);
}
