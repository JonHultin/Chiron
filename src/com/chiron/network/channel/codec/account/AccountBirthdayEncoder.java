package com.chiron.network.channel.codec.account;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public final class AccountBirthdayEncoder extends MessageToByteEncoder<AccountBirthdayResponse> {

	private static final int RESPONSE_OK = 2;
	
	@Override protected void encode(ChannelHandlerContext ctx, AccountBirthdayResponse msg, ByteBuf out) throws Exception {
		out.writeByte(RESPONSE_OK);
	}

}
