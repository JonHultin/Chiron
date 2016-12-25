package com.chiron.network.channel.handlers;

import com.chiron.network.message.Message;
import com.chiron.network.message.MessageDefinition;
import com.chiron.network.security.SecureCipher;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public final class MessageOutboundHandler extends MessageToByteEncoder<Message> {

	private final SecureCipher secureWrite;
	
	public MessageOutboundHandler(SecureCipher secureWrite) {
		this.secureWrite = secureWrite;
	}
	
	@Override protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
		System.out.println(msg.getOpcode() + " " + msg.getLength());
		if (msg.getDefinition() == MessageDefinition.RAW) {
			out.writeBytes(msg.getContents());
			return;
		}
		out.writeByte(msg.getOpcode() + secureWrite.nextValue());
		if (msg.getDefinition() == MessageDefinition.BYTE) {
			out.writeByte(msg.getLength());
		} else if (msg.getDefinition() == MessageDefinition.SHORT) {
			out.writeShort(msg.getLength());
		}
		out.writeBytes(msg.getContents());
	}

}
