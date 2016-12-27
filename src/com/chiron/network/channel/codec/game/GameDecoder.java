package com.chiron.network.channel.codec.game;

import java.util.List;

import com.chiron.network.NetworkConstants;
import com.chiron.network.message.MessageDefinition;
import com.chiron.network.security.SecureCipher;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public final class GameDecoder extends ByteToMessageDecoder {

	private final SecureCipher secureRead;
	
	public GameDecoder(SecureCipher secureRead) {
		this.secureRead = secureRead;
	}
	
	@Override protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int opcode = (in.readUnsignedByte() - secureRead.nextValue()) & 0xFF;
		int length = NetworkConstants.INBOUND_MESSAGE_LENGTH.get(opcode);
		
		MessageDefinition definition = MessageDefinition.valueOf(length);
		if (definition == MessageDefinition.BYTE) {
			length = in.readUnsignedByte();
		}
		
		if (length > in.readableBytes()) {
			return;
		}
		System.out.println("Message In = [opcode = " + opcode + " length = " + length + " definition = " + definition + "]");
	}

}
