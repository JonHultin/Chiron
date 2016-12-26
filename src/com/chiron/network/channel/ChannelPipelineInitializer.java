package com.chiron.network.channel;

import com.chiron.game.GameWorld;
import com.chiron.network.NetworkConstants;
import com.chiron.network.channel.codec.handshake.HandshakeDecoder;
import com.chiron.network.channel.codec.handshake.HandshakeEncoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public final class ChannelPipelineInitializer extends ChannelInitializer<SocketChannel> {

	private final GameWorld world;
	
	public ChannelPipelineInitializer(GameWorld world) {
		this.world = world;
	}

	@Override protected void initChannel(SocketChannel ch) throws Exception {
		ch.attr(NetworkConstants.GAME_WORLD_KEY).setIfAbsent(world);
		
		ch.pipeline().addLast("encoder", new HandshakeEncoder());
		ch.pipeline().addLast("decoder", new HandshakeDecoder());
	}

}
