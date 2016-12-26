package com.chiron.network.channel.codec.handshake;

import java.security.SecureRandom;
import java.util.List;
import java.util.logging.Logger;

import com.chiron.network.channel.codec.account.AccountBirthdayDecoder;
import com.chiron.network.channel.codec.account.AccountBirthdayEncoder;
import com.chiron.network.channel.codec.account.AccountPasswordDecoder;
import com.chiron.network.channel.codec.account.AccountPasswordEncoder;
import com.chiron.network.channel.codec.account.AccountUsernameDecoder;
import com.chiron.network.channel.codec.account.AccountUsernameEncoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public final class HandshakeDecoder extends ByteToMessageDecoder {

	private static final Logger LOGGER = Logger.getLogger(HandshakeDecoder.class.getName());
	
	private static final int LOGIN_OPCODE = 14;
	
	private static final int UPDATE_OPCODE = 15;
	
	private static final int ACCOUNT_PASSWORD_OPCODE = 48;
	
	private static final int ACCOUNT_BIRTHDAY_OPCODE = 85;
	
	private static final int ACCOUNT_USERNAME_OPCODE = 118;
	
	@Override protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int opcode = in.readUnsignedByte();
		switch (opcode) {
		case UPDATE_OPCODE:
			ctx.writeAndFlush(new HandshakeUpdateResponse(in.readInt() == 508 ? 0 : 6));
			LOGGER.info("Initializing update protocol.");
			break;
		
		case LOGIN_OPCODE:
			in.skipBytes(1);
			ctx.writeAndFlush(new HandshakeLoginResponse(new SecureRandom().nextLong(), 0));
			LOGGER.info("Initializing login protocol.");
			break;
		
		case ACCOUNT_BIRTHDAY_OPCODE:
			ctx.pipeline().replace("encoder", "encoder", new AccountBirthdayEncoder());
			ctx.pipeline().replace("decoder", "decoder", new AccountBirthdayDecoder());
			break;
			
		case ACCOUNT_USERNAME_OPCODE:
			ctx.pipeline().replace("encoder", "encoder", new AccountUsernameEncoder());
			ctx.pipeline().replace("decoder", "decoder", new AccountUsernameDecoder());
			break;
		
		case ACCOUNT_PASSWORD_OPCODE:
			ctx.pipeline().replace("encoder", "encoder", new AccountPasswordEncoder());
			ctx.pipeline().replace("decoder", "decoder", new AccountPasswordDecoder());
			break;
			
		default:
			throw new UnsupportedOperationException("Invalid handshake operation.");
		}
	}

}
