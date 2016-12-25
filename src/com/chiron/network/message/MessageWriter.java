package com.chiron.network.message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.buffer.PooledByteBufAllocator;

public final class MessageWriter extends DefaultByteBufHolder {

	private static final ByteBufAllocator ALLOC = PooledByteBufAllocator.DEFAULT;
	
	private static final int[] BIT_MASK_INDEX = new int[32];

	static {
		for (int i = 0; i < BIT_MASK_INDEX.length; i++) {
			BIT_MASK_INDEX[i] = (1 << i) - 1;
		}
	}

	private final MessageDefinition definition;

	private final int opcode;

	private int bitIndex;

	public MessageWriter(MessageDefinition definition, int opcode) {
		super(ALLOC.buffer());
		this.definition = definition;
		this.opcode = opcode;
	}
	
	public void writeBytes(ByteBuf src) {
		content().writeBytes(src);
	}
	
	public void writeReverseBytes(int[] src, int offset, int length) {
		for (int index = offset + length - 1; index >= offset; index--) {
			content().writeByte(src[index]);
		}
	}

	public void writeAddendReverseBytes(int[] src, int offset, int length) {
		for (int index = offset + length - 1; index >= offset; index--) {
			content().writeByte(128 + src[index]);
		}
	}
	
	public void writeByte(int value) {
		content().writeByte(value);
	}

	public void writeAddend(int value) {
		content().writeByte(128 + value);
	}

	public void writeOpposite(int value) {
		content().writeByte(-value);
	}

	public void writeSubtrahend(int value) {
		content().writeByte(128 - value);
	}

	public void writeShort(int value) {
		content().writeShort(value);
	}

	public void writeShortLE(int value) {
		content().writeShortLE(value);
	}

	public void writeAddendShort(int value) {
		content().writeByte(value >> 8);
		content().writeByte(value + 128);
	}

	public void writeAddendShortLE(int value) {
		content().writeByte(value + 128);
		content().writeByte(value >> 8);
	}

	public void writeInteger(int value) {
		content().writeInt(value);
	}

	public void writeIntegerME(int value) {
		content().writeByte(value >> 8);
		content().writeByte(value);
		content().writeByte(value >> 24);
		content().writeByte(value >> 16);
	}

	public void writeIntegerMIE(int value) {
		content().writeByte(value >> 16);
		content().writeByte(value >> 24);
		content().writeByte(value);
		content().writeByte(value >> 8);
	}

	public void writeLong(long value) {
		content().writeLong(value);
	}

	public void writeString(String value) {
		content().writeBytes(value.getBytes());
		content().writeByte(0);
	}

	public void writeBits(int amount, int value) {
		int bytePosition = bitIndex >> 3;
		int bitOffset = 8 - (bitIndex & 7);
		int requiredSpace = bytePosition - content().writerIndex() + 1;
		bitIndex += amount;
		requiredSpace += (amount + 7) / 8;
		content().ensureWritable(requiredSpace);
		for (; amount > bitOffset; bitOffset = 8) {
			int allocation = content().getByte(bytePosition);
			allocation &= ~BIT_MASK_INDEX[bitOffset];
			allocation |= value >> amount - bitOffset & BIT_MASK_INDEX[bitOffset];
			content().setByte(bytePosition++, allocation);
			amount -= bitOffset;
		}
		if (amount == bitOffset) {
			int allocation = content().getByte(bytePosition);
			allocation &= ~BIT_MASK_INDEX[bitOffset];
			allocation |= value & BIT_MASK_INDEX[bitOffset];
			content().setByte(bytePosition, allocation);
		} else {
			int allocation = content().getByte(bytePosition);
			allocation &= ~(BIT_MASK_INDEX[amount] << bitOffset - amount);
			allocation |= (value & BIT_MASK_INDEX[amount]) << bitOffset - amount;
			content().setByte(bytePosition, allocation);
		}
	}

	public void initializeBitMode() {
		bitIndex = content().writerIndex() * 8;
	}

	public void initializeByteMode() {
		content().writerIndex((bitIndex + 7) / 8);
	}

	public MessageDefinition getDefinition() {
		return definition;
	}

	public int getOpcode() {
		return opcode;
	} 
	
}
