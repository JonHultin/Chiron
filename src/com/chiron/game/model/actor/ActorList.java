package com.chiron.game.model.actor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;

public final class ActorList<T extends Actor> {

	private final List<T> actors;
	
	private final Queue<Integer> indices;
	
	public ActorList(int capacity) {
		this.actors = new ArrayList<>(capacity);
		this.indices = new ArrayDeque<>(capacity);
		
		IntStream.rangeClosed(1, capacity).forEach(indices::add);
	}

	public void add(T actor) {
		actor.setIndex(indices.poll());
		actors.add(actor);
	}

	public void remove(T actor) {
		indices.add(actor.getIndex());
		actors.remove(actor);
	}

	public List<T> getList() {
		return actors;
	}
	
}
