package com.chiron.network.message.encoders;

import com.chiron.game.model.actor.player.Player;
import com.chiron.network.message.MessageEncoder;
import com.chiron.network.message.MessageWriter;

public final class GameInterfaceEncoder extends MessageEncoder {

	private final int id;
	
	public GameInterfaceEncoder(int id) {
		this.id = id;
	}

	@Override protected MessageWriter encode(Player player) {
		MessageWriter writer = new MessageWriter(239);
		writer.writeShort(id);
		writer.writeAddend(0);
		return writer;
	}
}
