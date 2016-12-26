package com.chiron.network.channel.codec.update;

import java.util.logging.Logger;

import com.google.common.collect.ImmutableList;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public final class UpdateEncoder extends MessageToByteEncoder<ImmutableList<Integer>> {

	private static final Logger LOGGER = Logger.getLogger(UpdateEncoder.class.getName());
	
	@Override protected void encode(ChannelHandlerContext ctx, ImmutableList<Integer> msg, ByteBuf out) throws Exception {
		msg.forEach(out::writeByte);
		LOGGER.info("Cache validation keys sent.");
	}

}
