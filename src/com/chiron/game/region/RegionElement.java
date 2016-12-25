package com.chiron.game.region;

public final class RegionElement {
	
	private final int id;
	
	private final int[] keys;

	public RegionElement(int id, int[] keys) {
		this.id = id;
		this.keys = keys;
	}

	public final int getId() {
		return id;
	}

	public final int[] getKeys() {
		return keys;
	}
	
}
