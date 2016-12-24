package com.chiron.game;

import java.util.Collections;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

import com.chiron.game.model.actor.player.Player;

public class GameWorldSynchronizer {

	private static final int DEFAULT_PARTIES = 1;
	
	private final ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
	private final Phaser phaser = new Phaser(DEFAULT_PARTIES);
	
	private final GameWorld world;
	
	public GameWorldSynchronizer(GameWorld world) {
		this.world = world;
	}
	
	public void preSync() {
		for (Enumeration<Player> $enum = Collections.enumeration(world.getPlayers()); $enum.hasMoreElements();) {
			Player player = $enum.nextElement();
			player.getEventHandler().updateMovement();
		}
		phaser.bulkRegister(world.getPlayers().size());
	}
	
	public void onSync() {
		for (Enumeration<Player> $enum = Collections.enumeration(world.getPlayers()); $enum.hasMoreElements();) {
			try {
			Player player = $enum.nextElement();
			service.execute(() -> player.getEventHandler().update());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				phaser.arriveAndDeregister();
			}
		}
		phaser.arriveAndAwaitAdvance();
	}
	
	public void postSync() {
		for (Enumeration<Player> $enum = Collections.enumeration(world.getPlayers()); $enum.hasMoreElements();) {
			Player player = $enum.nextElement();
			player.getUpdateFlags().clear();
		}
	}
}
