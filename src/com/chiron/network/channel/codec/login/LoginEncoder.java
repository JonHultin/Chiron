package com.chiron.network.channel.codec.login;

import com.chiron.game.GameWorld;
import com.chiron.game.model.actor.player.Player;
import com.chiron.network.NetworkConstants;
import com.chiron.network.channel.codec.game.GameDecoder;
import com.chiron.network.channel.codec.game.GameEncoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public final class LoginEncoder extends MessageToByteEncoder<LoginResponse> {
	
	@Override protected void encode(ChannelHandlerContext ctx, LoginResponse msg, ByteBuf out) throws Exception {
		out.writeByte(getResponseCode(msg));
		out.writeByte(0);
		out.writeByte(0);
		out.writeByte(0);
		out.writeByte(0);
		out.writeByte(1);
		out.writeShort(1);
		out.writeByte(0);
		ctx.pipeline().replace("encoder", "encoder", new GameEncoder(msg.getSecureWrite()));
		ctx.pipeline().replace("decoder", "decoder", new GameDecoder(msg.getSecureRead()));
		GameWorld world = ctx.channel().attr(NetworkConstants.GAME_WORLD_KEY).get();
		world.register(new Player(world, ctx.channel(), msg.getUsername(), msg.getPassword()));
	}

	public int getResponseCode(LoginResponse response) {
		return 2;
	}
}
