package com.chiron.network.message;

import com.chiron.game.model.actor.player.Player;

public abstract class MessageEncoder {

	public final Message handle(Player player) {
		MessageWriter writer = encode(player);
		return new Message(writer.getDefinition(), writer.content(), writer.getOpcode());
	}
	
	protected abstract MessageWriter encode(Player player);
}
