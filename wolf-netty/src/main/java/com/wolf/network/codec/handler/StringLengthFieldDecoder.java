package com.wolf.network.codec.handler;

import java.nio.charset.Charset;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StringLengthFieldDecoder extends MessageToMessageDecoder<ByteBuf> {

	private int lengthHeadOffset;
	private Charset charset;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		String string = msg.toString(charset);
		out.add(string.substring(lengthHeadOffset));
	}
}
