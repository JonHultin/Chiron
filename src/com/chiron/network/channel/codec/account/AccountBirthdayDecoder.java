package com.chiron.network.channel.codec.account;

import java.time.Month;
import java.util.List;
import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class AccountBirthdayDecoder extends ByteToMessageDecoder {

	private static final Logger LOGGER = Logger.getLogger(AccountBirthdayDecoder.class.getName());
	
	@Override protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int responseCode = 2;
		int day = in.readByte();
		int month = in.readByte();
		int year = in.readShort();
		int country = in.readShort();
		ctx.writeAndFlush(new AccountBirthdayResponse(responseCode)).addListener(ChannelFutureListener.CLOSE);
		LOGGER.info("Account Creation -> Birthday = [date = " + Month.of(month + 1).toString().toLowerCase() + " " + day + ", " + year + " country = " + country + "]");
	}

}
