package com.chiron.network.channel.codec.handshake;

import java.util.logging.Logger;

import com.chiron.network.channel.codec.login.LoginDecoder;
import com.chiron.network.channel.codec.login.LoginEncoder;
import com.chiron.network.channel.codec.update.UpdateDecoder;
import com.chiron.network.channel.codec.update.UpdateEncoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public final class HandshakeEncoder extends MessageToByteEncoder<HandshakeResponse> {

	private static final Logger LOGGER = Logger.getLogger(HandshakeEncoder.class.getName());
	
	private static final int RESPONSE_OK = 0;
	
	private static final int RESPONSE_OUT_OF_DATE = 6;
	
	@Override protected void encode(ChannelHandlerContext ctx, HandshakeResponse msg, ByteBuf out) throws Exception {
		if (msg.getResponseCode() == RESPONSE_OUT_OF_DATE) {
			out.writeByte(RESPONSE_OUT_OF_DATE);
			ctx.close();
			LOGGER.info("Client out of date.");
			return;
		}
		out.writeByte(RESPONSE_OK);
		if (msg instanceof HandshakeUpdateResponse) {
			ctx.pipeline().replace("encoder", "encoder", new UpdateEncoder());
			ctx.pipeline().replace("decoder", "decoder", new UpdateDecoder());
		}
		if (msg instanceof HandshakeLoginResponse) {
			out.writeLong(((HandshakeLoginResponse) msg).getServerSeed());
			ctx.pipeline().replace("encoder", "encoder", new LoginEncoder());
			ctx.pipeline().replace("decoder", "decoder", new LoginDecoder());
		}
	}

}
