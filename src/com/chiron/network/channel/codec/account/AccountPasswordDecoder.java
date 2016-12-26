package com.chiron.network.channel.codec.account;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import com.chiron.util.Longs;
import com.chiron.util.Strings;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public final class AccountPasswordDecoder extends ByteToMessageDecoder {

	private static final Logger LOGGER = Logger.getLogger(AccountPasswordDecoder.class.getName());
	
	@Override protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int responseCode = 2;
		in.skipBytes(2);
		while (in.readByte() != 1) {
			in.skipBytes(1);
		}
		in.readerIndex(in.readerIndex() - 1);
		int version = in.readShort();
		String username = Longs.toString(in.readLong());
		String password = Strings.readString(in.skipBytes(4));
		int day = in.skipBytes(6).readByte();
		int month = in.readByte();
		int year = in.skipBytes(4).readShort();
		int country = in.readShort();
		in.skipBytes(4);		
		if (version != 508) {
			responseCode = 37;
		}
		if (password.length() < 4) {
			responseCode = 32;
		}
		if (password.equals(username)) {
			responseCode = 34;
		}
		if (password.equals(null) || password.equals("") || password.equals(" ")) {
			responseCode = 30;
		}
		File file = new File("./data/json/accounts/" + username + ".json");
		if (file.exists()) {
			responseCode = 20;
		}
		ctx.writeAndFlush(new AccountPasswordResponse(username, password, day, month, year, country, responseCode)).addListener(ChannelFutureListener.CLOSE);
		LOGGER.info("Account Creation -> Account = [username = " + username + ", password = " + password + ", response = " + responseCode + ", version = " + version + "]");
	}

}
