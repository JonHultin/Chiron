package com.chiron.util;

public final class Longs {

    private static final char VALID_CHARACTERS[] = { '_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+', '=', ':', ';', '.', '>', '<', ',', '"', '[', ']', '|', '?', '/', '`' };

	public static String toString(long value) {
		int i = 0;
		char ac[] = new char[12];
		while (value != 0L) {
			long l1 = value;
			value /= 37L;
			ac[11 - i++] = VALID_CHARACTERS[(int) (l1 - value * 37L)];
		}
		return new String(ac, 12 - i, i);
	}
	
	private Longs() { }
}
