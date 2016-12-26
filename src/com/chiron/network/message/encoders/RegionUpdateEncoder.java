package com.chiron.network.message.encoders;

import java.util.function.Predicate;

import com.chiron.game.model.actor.player.Player;
import com.chiron.game.region.RegionElement;
import com.chiron.game.region.RegionElements;
import com.chiron.network.message.MessageDefinition;
import com.chiron.network.message.MessageEncoder;
import com.chiron.network.message.MessageWriter;

public class RegionUpdateEncoder extends MessageEncoder {

	@Override protected MessageWriter encode(Player player) {
		MessageWriter writer = new MessageWriter(MessageDefinition.SHORT, 142);
		boolean forceSend = true;
		if((((player.getPosition().getRegionX() / 8) == 48) || ((player.getPosition().getRegionX() / 8) == 49)) && ((player.getPosition().getRegionY() / 8) == 48)) {
			forceSend = false;
		}
		if(((player.getPosition().getRegionX() / 8) == 48) && ((player.getPosition().getRegionY() / 8) == 148)) {
			forceSend = false;
		}
		writer.writeAddendShort(player.getPosition().getRegionX());
		writer.writeAddendShortLE(player.getPosition().getLocalY());
		writer.writeAddendShort(player.getPosition().getLocalX());
		for(int xCalc = (player.getPosition().getRegionX() - 6) / 8; xCalc <= ((player.getPosition().getRegionX() + 6) / 8); xCalc++) {

			for(int yCalc = (player.getPosition().getRegionY() - 6) / 8; yCalc <= ((player.getPosition().getRegionY() + 6) / 8); yCalc++) {

				int region = yCalc + (xCalc << 8); 

				if(forceSend || ((yCalc != 49) && (yCalc != 149) && (yCalc != 147) && (xCalc != 50) && ((xCalc != 49) || (yCalc != 47)))) {
					Predicate<RegionElement> filter = $it -> $it.getId() == region;
					RegionElement element = RegionElements.getRegionElements().stream().filter(filter).findFirst().get();
					if(element == null) {
						element = new RegionElement(0, new int[] { 0, 0, 0, 0});
					}
					writer.writeInteger(element.getKeys()[0]);
					writer.writeInteger(element.getKeys()[1]);
					writer.writeInteger(element.getKeys()[2]);
					writer.writeInteger(element.getKeys()[3]);
				}
			}
		}
		writer.writeOpposite(player.getPosition().getPlane());
		writer.writeShort(player.getPosition().getRegionY());
		return writer;
	}

}
