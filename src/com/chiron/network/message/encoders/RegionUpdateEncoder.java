package com.chiron.network.message.encoders;

import java.util.Arrays;

import com.chiron.game.model.actor.player.Player;
import com.chiron.game.region.RegionElement;
import com.chiron.game.region.RegionElements;
import com.chiron.network.message.MessageDefinition;
import com.chiron.network.message.MessageEncoder;
import com.chiron.network.message.MessageWriter;

public class RegionUpdateEncoder extends MessageEncoder {

	@Override protected MessageWriter encode(Player player) {
		MessageWriter writer = new MessageWriter(MessageDefinition.SHORT, 142);
		boolean force = true;
		
		if ((player.getPosition().getRegionX() / 8) == 48 || (player.getPosition().getRegionX() / 8) == 49 && (player.getPosition().getRegionY() / 8) == 48) {
			force = false;
		}
		
		if ((player.getPosition().getRegionX() / 8) == 48 || (player.getPosition().getRegionY() / 8) == 148) {
			force = false;
		}
		writer.writeAddendShort(player.getPosition().getRegionX());
		writer.writeAddendShortLE(player.getPosition().getLocalY());
		writer.writeAddendShort(player.getPosition().getLocalX());
		for (int xCalc = (player.getPosition().getRegionX() - 6) / 8; xCalc <= ((player.getPosition().getRegionX() + 6) / 8); xCalc++) {
			for (int yCalc = (player.getPosition().getRegionY() - 6) / 8; yCalc <= ((player.getPosition().getRegionY() + 6) / 8); yCalc++) {
				int regionId = yCalc + (xCalc << 8);
				if (force || ((yCalc != 49) && (yCalc != 149) && (yCalc != 147) && (xCalc != 50) && ((xCalc != 49) || (yCalc != 47)))) {
					RegionElement element = RegionElements.getRegionElements().stream().filter($it -> $it.getId() == regionId).findFirst().get();
					int[] keys = element.getKeys();
					System.out.println("Id: " + element.getId() + "Keys: " + Arrays.toString(keys));
					if (keys == null) {
						keys = new int[4];
						for (int i = 0; i < keys.length; i++) {
							keys[i] = 0;
						}
					}
					for (int key : keys) {
						writer.writeInteger(key);
					}
				}
			}
		}
		writer.writeOpposite(player.getPosition().getPlane());
		writer.writeShort(player.getPosition().getRegionY());
		return writer;
	}

}
