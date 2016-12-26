package com.chiron;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

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

	private final Logger logger = Logger.getLogger(Chiron.class.getName());
	
	private final ExecutorService service = Executors.newCachedThreadPool();
	
	private final ServerBootstrap bootstrap = new ServerBootstrap();
	
	private final GameWorld world = new GameWorld();
	
	private Chiron() { }
	
	public void init() {
		logger.info("Allocating resources.");
		service.execute(() -> new RegionElements().parse());
		
		logger.info("Initializing game thread.");
		world.getService().startAsync();
		
		logger.info("Initializing network boostrap.");
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.childHandler(new ChannelPipelineInitializer(world));
		bootstrap.group(new NioEventLoopGroup());
		bootstrap.bind(NetworkConstants.NETWORK_PORT).syncUninterruptibly();
		
		logger.info("Chiron is now online.");
	}
	
}
