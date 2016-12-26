package com.chiron.network.channel.codec.account;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public final class AccountPasswordEncoder extends MessageToByteEncoder<AccountPasswordResponse> {

	@Override protected void encode(ChannelHandlerContext ctx, AccountPasswordResponse msg, ByteBuf out) throws Exception {
		ctx.writeAndFlush(msg.getResponseCode());
	}

}
