package com.chiron.game;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Logger;

import com.chiron.game.model.actor.ActorSet;
import com.chiron.game.model.actor.player.Player;
import com.chiron.game.task.TaskScheduler;

import com.google.common.collect.ImmutableSet;

public final class GameWorld {

	private final GameWorldSynchronizer synchronizer = new GameWorldSynchronizer(this);
	
	private final GameService service = new GameService(this);
	
	private final Logger logger = Logger.getLogger(GameWorld.class.getName());
	
	private final ActorSet<Player> players = new ActorSet<>(2000);
	
	private final Queue<Player> loginQueue = new ArrayDeque<>(2000);
	
	private final TaskScheduler scheduler = new TaskScheduler();
	
	public void queue(Player player) {
		loginQueue.add(player);
	}
	
	public void deque() {
		while (!loginQueue.isEmpty()) {
			Player player = loginQueue.poll();
			player.getEventHandler().register();
		}
	}
	
	public void register(Player player) {
		players.add(player);
		logger.info("Game World -> Register = [username = " + player.getUsername() + " index = " + player.getIndex() + "]");
	}
	
	public void unregister(Player player) {
		players.remove(player);
	}

	public final GameWorldSynchronizer getSynchronizer() {
		return synchronizer;
	}

	public final GameService getService() {
		return service;
	}

	public final Queue<Player> getLoginQueue() {
		return loginQueue;
	}

	public Set<Player> getPlayers() {
		return ImmutableSet.copyOf(players.getSet());
	}
	
	public final TaskScheduler getScheduler() {
		return scheduler;
	}
}
