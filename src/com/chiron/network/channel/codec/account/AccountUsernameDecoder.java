package com.chiron.network.channel.codec.account;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import com.chiron.util.Longs;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public final class AccountUsernameDecoder extends ByteToMessageDecoder {

	private static final Logger LOGGER = Logger.getLogger(AccountUsernameDecoder.class.getName());
	
	@Override protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int responseCode = 2;
		String username = Longs.toString(in.readLong()).replace("_", " ").toLowerCase().trim();
		File file = new File("./data/json/accounts/" + username + ".json");
		if (file.exists()) {
			responseCode = 20;
		}
		ctx.writeAndFlush(new AccountUsernameResponse(responseCode)).addListener(ChannelFutureListener.CLOSE);
		LOGGER.info("Account Creation -> Username = [username = " + username + "]");
	}

}
