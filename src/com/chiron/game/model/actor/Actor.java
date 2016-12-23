package com.chiron.game.model.actor;

import com.chiron.game.model.actor.update.UpdateFlagSet;

public abstract class Actor {
	
	private final UpdateFlagSet updateFlags = new UpdateFlagSet();
	
	public abstract ActorEventListener<?> getEventHandler();
}
