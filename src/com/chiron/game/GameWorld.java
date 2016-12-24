package com.chiron.game;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

import com.chiron.game.model.actor.ActorSet;
import com.chiron.game.model.actor.player.Player;
import com.chiron.game.task.TaskScheduler;

import com.google.common.collect.ImmutableSet;

public final class GameWorld {

	private final ActorSet<Player> players = new ActorSet<>(2000);
	
	private final Queue<Player> loginQueue = new ArrayDeque<>(2000);
	
	private final TaskScheduler scheduler = new TaskScheduler();
	
	public void queue(Player player) {
		loginQueue.add(player);
	}
	
	public void deque() {
		for (Player player = loginQueue.poll(); Objects.nonNull(player);) {
			player.getEventHandler().register();
		}
	}
	
	public void register(Player player) {
		players.add(player);
	}
	
	public void unregister(Player player) {
		players.remove(player);
	}

	public ImmutableSet<Player> getPlayers() {
		return ImmutableSet.copyOf(players.getSet());
	}
	
	public final TaskScheduler getScheduler() {
		return scheduler;
	}
}
