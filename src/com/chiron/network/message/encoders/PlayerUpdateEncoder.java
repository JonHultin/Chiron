package com.chiron.network.message.encoders;

import com.chiron.game.model.actor.player.Player;
import com.chiron.network.message.MessageDefinition;
import com.chiron.network.message.MessageEncoder;
import com.chiron.network.message.MessageWriter;

public final class PlayerUpdateEncoder extends MessageEncoder {

	@Override protected MessageWriter encode(Player player) {
		MessageWriter writer = new MessageWriter(MessageDefinition.SHORT, 216);
		writer.initializeBitMode();
		writer.writeBits(1, 1);
		writer.writeBits(2, 3);
		writer.writeBits(7, player.getPosition().getLocalX());
		writer.writeBits(1, 1);
		writer.writeBits(2, player.getPosition().getPlane());
		writer.writeBits(1, 1);
		writer.writeBits(7, player.getPosition().getLocalY());		
		writer.writeBits(8, 0);		
		writer.writeBits(11, 2047);		
		writer.initializeByteMode();
		return writer;
	}

}
