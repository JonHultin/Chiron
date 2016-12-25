package com.chiron;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.chiron.game.GameWorld;
import com.chiron.game.region.RegionElements;
import com.chiron.network.NetworkConstants;
import com.chiron.network.channel.ChannelPipelineInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public final class Chiron {

	public static void main(String[] args) {
		try {
			Chiron chiron = new Chiron();
			chiron.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
	private final ServerBootstrap bootstrap = new ServerBootstrap();
	
	private final GameWorld world = new GameWorld();
	
	private Chiron() { }
	
	public void init() {
		service.execute(() -> new RegionElements().parse());
		
		world.getService().startAsync();
		
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.childHandler(new ChannelPipelineInitializer(world));
		bootstrap.group(new NioEventLoopGroup());
		bootstrap.bind(NetworkConstants.NETWORK_PORT).syncUninterruptibly();
	}
	
}
