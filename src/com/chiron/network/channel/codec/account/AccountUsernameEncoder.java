package com.chiron.network.channel.codec.account;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public final class AccountUsernameEncoder extends MessageToByteEncoder<AccountUsernameResponse> {

	@Override protected void encode(ChannelHandlerContext ctx, AccountUsernameResponse msg, ByteBuf out) throws Exception {
		out.writeByte(msg.getResponseCode());
	}

}
