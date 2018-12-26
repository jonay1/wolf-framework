package com.wolf.network.codec.handler;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StringLengthFieldEncoder extends MessageToMessageEncoder<CharSequence> {

	private int lengthFieldOffset;
	private Charset charset;

	@Override
	protected void encode(ChannelHandlerContext ctx, CharSequence msg, List<Object> out) throws Exception {
		if (msg == null || msg.length() == 0) {
			return;
		}
		int length = msg.length();
		String head = StringUtils.leftPad(String.valueOf(length), lengthFieldOffset, '0');
		String string = head + msg;
		out.add(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(string), charset));
	}

}
