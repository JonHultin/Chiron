package com.chiron.network.channel.codec.handshake;

import java.security.SecureRandom;
import java.util.List;
import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public final class HandshakeDecoder extends ByteToMessageDecoder {

	private static final Logger LOGGER = Logger.getLogger(HandshakeDecoder.class.getName());
	
	private static final int LOGIN_OPCODE = 14;
	
	private static final int UPDATE_OPCODE = 15;
	
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
			
		default:
			throw new UnsupportedOperationException("Invalid handshake operation.");
		}
	}

}
