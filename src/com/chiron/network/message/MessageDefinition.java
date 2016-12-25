package com.chiron.network.message;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.OptionalInt;

public enum MessageDefinition {
	RAW,
	STANDARD,
	BYTE(-1),
	SHORT(-2);
	
	private final OptionalInt value;
	
	private MessageDefinition(int value) {
		this.value = OptionalInt.of(value);
	}
	
	private MessageDefinition() {
		this.value = OptionalInt.empty();
	}
	
	public static EnumSet<MessageDefinition> toSet() {
		return EnumSet.copyOf(Arrays.asList(values()));
	}
	
	public static MessageDefinition valueOf(int value) {
		if (value == -1) {
			return BYTE;
		} else if (value == -2) {
			return SHORT;
		} else {
			return STANDARD;
		}
	}

	public final OptionalInt getValue() {
		return value;
	}
	
}
