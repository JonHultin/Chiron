package com.chiron.game.model.actor;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Set;
import java.util.stream.IntStream;

import com.google.common.collect.Sets;

/**
 * A thread-safe set of {@link Actor} objects.
 * 
 * <p>
 * This collection doesn't allow null values.
 * </p>
 * 
 * @author Jon Hultin
 *
 * @param <T> The actor type being boxed.
 */
public final class ActorSet<T extends Actor> {

	/**
	 * The set of {@link Actor} objects.
	 */
	private final Set<T> actors;
	
	/**
	 * A queue of {@link Integer} values, which are used from index purposes.
	 */
	private final Queue<Integer> indices;
	
	/**
	 * Creates a new {@link ActorSet} with a specified capacity.
	 * 
	 * @param capacity the capacity of this set.
	 */
	public ActorSet(int capacity) {
		this.actors = Sets.newConcurrentHashSet();
		this.indices = new ArrayDeque<>(capacity);
		
		IntStream.rangeClosed(1, capacity).forEach(indices::add);
	}
	
	/**
	 * Adds the specified {@link Actor} to the set if not already present.
	 * 
	 * @param actor the actor to be added to this set.
	 */
	public void add(T actor) {
		actor.setIndex(indices.poll());
		actors.add(actor);
	}
	
	/**
	 * Removes the specified {@link Actor} from this set if present.
	 * 
	 * @param actor the actor to be removed from this set, if present.
	 */
	public void remove(T actor) {
		indices.add(actor.getIndex());
		actors.remove(actor);
	}
	
	/**
	 * Returns the internal set of {@link Actor} objects.
	 * 
	 * @return the set of actors.
	 */
	public Set<T> getSet() {
		return actors;
	}
}
