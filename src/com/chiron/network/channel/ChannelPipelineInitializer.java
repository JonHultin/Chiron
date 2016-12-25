package com.chiron.network.channel;

import com.chiron.game.GameWorld;
import com.chiron.network.channel.handlers.LoginInboundHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public final class ChannelPipelineInitializer extends ChannelInitializer<SocketChannel> {

	private final GameWorld world;
	
	public ChannelPipelineInitializer(GameWorld world) {
		this.world = world;
	}
	
	@Override protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast("inbound_handler", new LoginInboundHandler(world));
	}

}
