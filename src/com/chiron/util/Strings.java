package com.chiron.util;

import io.netty.buffer.ByteBuf;

public final class Strings {

	public static final String readString(ByteBuf src) {
		StringBuilder builder = new StringBuilder();
		byte value;
		while (src.isReadable() && (value = src.readByte()) != 0) {
			builder.append((char) value);
		}
		return builder.toString();
	}
	
	private Strings() { }
}
