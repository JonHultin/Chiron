package com.chiron.network.message;

import io.netty.buffer.ByteBuf;

public final class Message {

	private final MessageDefinition definition;
	
	private final ByteBuf contents;
	
	private final int opcode;

	public Message(MessageDefinition definition, ByteBuf contents, int opcode) {
		this.definition = definition;
		this.contents = contents;
		this.opcode = opcode;
	}

	public MessageDefinition getDefinition() {
		return definition;
	}

	public ByteBuf getContents() {
		return contents;
	}

	public int getOpcode() {
		return opcode;
	}
	
	public int getLength() {
		return contents.readableBytes();
	}
}
