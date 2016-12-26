package com.chiron.game.model.actor.player;

import com.chiron.game.model.actor.ActorEventListener;
import com.chiron.network.message.encoders.GameInterfaceEncoder;
import com.chiron.network.message.encoders.RegionUpdateEncoder;

public final class PlayerEventHandler extends ActorEventListener<Player> {

	public PlayerEventHandler(Player actor) {
		super(actor);
	}

	@Override public void update() {

	}

	@Override public void updateMovement() {
		
	}
	
	@Override public void register() {
		getActor().write(new RegionUpdateEncoder());
		getActor().write(new GameInterfaceEncoder(549));
	}

	@Override public void unregister() {

	}

}
