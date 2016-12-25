package com.chiron.game.model.actor.player;

import com.chiron.game.model.actor.ActorEventListener;
import com.chiron.network.message.encoders.RegionUpdateEncoder;

import io.netty.buffer.ByteBuf;

public final class PlayerEventHandler extends ActorEventListener<Player> {

	public PlayerEventHandler(Player actor) {
		super(actor);
	}

	@Override public void update() {

	}

	@Override public void updateMovement() {
		
	}
	
	@Override public void register() {
		ByteBuf message = getActor().getChannel().alloc().buffer();
		message.writeByte(2);
		message.writeByte(0);
		message.writeByte(0);
		message.writeByte(0);
		message.writeByte(0);
		message.writeByte(1);
		message.writeShort(getActor().getIndex());
		message.writeByte(0);
		getActor().getChannel().writeAndFlush(message);
		getActor().write(new RegionUpdateEncoder());
		getActor().getWorld().register(getActor());	
	}

	@Override public void unregister() {

	}

}
