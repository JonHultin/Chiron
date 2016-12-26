package com.chiron.game;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

import com.chiron.game.model.actor.player.Player;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class GameWorldSynchronizer {

	private static final int DEFAULT_PARTIES = 1;
	
    private final ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new ThreadFactoryBuilder().setNameFormat("Game World Sync Thread").build());
	
	private final Phaser phaser = new Phaser(DEFAULT_PARTIES);
	
	private final GameWorld world;
	
	public GameWorldSynchronizer(GameWorld world) {
		this.world = world;
	}
	
	public void sync() {
		preSync();
		onSync();
		postSync();
	}
	
	private void preSync() {
		for (Player player : world.getPlayers()) {
			player.getEventHandler().updateMovement();
		}
		phaser.bulkRegister(world.getPlayers().size());
	}
	
	private void onSync() {
		for (Player player : world.getPlayers()) {
			service.execute(() -> {
				synchronized (player) {
					try {
						player.getEventHandler().update();
					} catch (Exception e) { 
						e.printStackTrace();
					} finally {
						phaser.arriveAndDeregister();
					}
				}
			});
		}
		phaser.arriveAndAwaitAdvance();
	}
	
	private void postSync() {
		for (Player player : world.getPlayers()) {
			player.getChannel().flush();
			player.getUpdateFlags().clear();
		}
	}
}
