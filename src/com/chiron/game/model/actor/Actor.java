package com.chiron.game.model.actor;

import com.chiron.game.GameWorld;
import com.chiron.game.model.Position;
import com.chiron.game.model.actor.update.UpdateFlagSet;

public abstract class Actor {
	
	private final UpdateFlagSet updateFlags = new UpdateFlagSet();
	
	private final GameWorld world;
	
	private final Position position;
	
	private int index;
	
	public Actor(GameWorld world, Position position) {
		this.world = world;
		this.position = position;
	}
	
	public final int getIndex() {
		return index;
	}

	public final void setIndex(int index) {
		this.index = index;
	}

	public final UpdateFlagSet getUpdateFlags() {
		return updateFlags;
	}

	public final GameWorld getWorld() {
		return world;
	}

	public final Position getPosition() {
		return position;
	}

	public abstract ActorEventListener<?> getEventHandler();
}
