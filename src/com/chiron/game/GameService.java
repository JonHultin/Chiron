package com.chiron.game;

import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.AbstractScheduledService;

public final class GameService extends AbstractScheduledService {

	private final GameWorld world;
	
	public GameService(GameWorld world) {
		this.world = world;
	}
	
	@Override protected void runOneIteration() throws Exception {
		world.getScheduler().process();
		world.deque();
	}

	@Override protected Scheduler scheduler() {
		return Scheduler.newFixedRateSchedule(600, 600, TimeUnit.MILLISECONDS);
	}

}
