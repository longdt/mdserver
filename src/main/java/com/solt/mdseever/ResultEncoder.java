package com.solt.mdseever;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.solt.mdseever.protocol.Result;

@Sharable
public class ResultEncoder extends MessageToByteEncoder<Result> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Result msg, ByteBuf out)
			throws Exception {
		out.writeByte(msg.getStatus());
		int length = msg.getLength();
		if (length != 0) {
			out.writeInt(length);
			out.writeBytes(msg.getData(), msg.getOffset(), length);
		}
	}

}
