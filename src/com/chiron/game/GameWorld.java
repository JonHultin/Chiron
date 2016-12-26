package com.chiron.game;

import java.util.List;
import java.util.logging.Logger;

import com.chiron.game.model.actor.ActorList;
import com.chiron.game.model.actor.player.Player;
import com.chiron.game.task.TaskScheduler;

public final class GameWorld {

	private final GameWorldSynchronizer synchronizer = new GameWorldSynchronizer(this);
	
	private final GameService service = new GameService(this);
	
	private final Logger logger = Logger.getLogger(GameWorld.class.getName());
	
	private final ActorList<Player> players = new ActorList<>(2000);
	
	private final TaskScheduler scheduler = new TaskScheduler();
	
	public void register(Player player) {
		players.add(player);
		player.getEventHandler().register();
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

	public List<Player> getPlayers() {
		return players.getList();
	}
	
	public final TaskScheduler getScheduler() {
		return scheduler;
	}
	
}
