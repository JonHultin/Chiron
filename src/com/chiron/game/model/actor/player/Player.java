package com.chiron.game.model.actor.player;

import com.chiron.game.model.actor.Actor;
import com.chiron.game.model.actor.ActorEventListener;

public final class Player extends Actor {

	private final ActorEventListener<Player> eventHandler = new PlayerEventHandler(this);
	
	@Override public ActorEventListener<?> getEventHandler() {
		return eventHandler;
	}

}
