package com.chiron.game.model.actor;

import com.chiron.game.model.actor.update.UpdateFlagSet;

public abstract class Actor {
	
	private final UpdateFlagSet updateFlags = new UpdateFlagSet();
	
	private int index;
	
	
	public final int getIndex() {
		return index;
	}

	public final void setIndex(int index) {
		this.index = index;
	}

	public final UpdateFlagSet getUpdateFlags() {
		return updateFlags;
	}

	public abstract ActorEventListener<?> getEventHandler();
}
