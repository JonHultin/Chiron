package com.chiron.game.model.actor;

/**
 * An abstract class designed to provide specific protocol event 
 * handling for {@link Actor} objects.
 *  
 * @author Jon Hultin
 *
 * @param <T> The actor type being boxed.
 */
public abstract class ActorEventListener<T extends Actor> {

	/**
	 * The actor using this event listener.
	 */
	private final T actor;
	
	/**
	 * Creates a new {@link ActorEventListener} for a specific {@link Actor}.
	 * 
	 * @param actor the actor using this event listener.
	 */
	public ActorEventListener(T actor) {
		this.actor = actor;
	}
	
	/**
	 * Updates the current state of the {@link Actor} using this event listener.
	 */
	public abstract void update();
	
	/**
	 * Registers the {@link Actor} using this event listener into the game world.
	 */
	public abstract void register();
	
	/**
	 * Unregisters the {@link Actor} using this event listener from the game world.
	 */
	public abstract void unregister();
	
	/**
	 * Returns the {@link Actor} using this event listener.
	 * 
	 * @return the actor.
	 */
	public T getActor() {
		return actor;
	}
	
}
