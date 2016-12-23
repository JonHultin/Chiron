package com.chiron.game.model.actor.update;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A set used to cache {@link UpdateFlag} objects, which pertain to specific
 * updates that need to occur on the next update cycle for {@link Actor}
 * objects.
 * 
 * @author Jon Hultin
 *
 */
public final class UpdateFlagSet {

	/**
	 * The internal set of {@link UpdateFlag}s.
	 */
	private final Set<UpdateFlag> updateFlags = new LinkedHashSet<>();

	/**
	 * Adds a specified {@link UpdateFlag} to the set if not already present.
	 * 
	 * @param updateFlag the update flag to be added to this set.
	 */
	public void add(UpdateFlag updateFlag) {
		updateFlags.add(updateFlag);
	}

	/**
	 * Removes all {@link UpdateFlag}s from this set.
	 */
	public void clear() {
		updateFlags.clear();
	}

	/**
	 * Returns {@code true} if the set contains the specified {@link UpdateFlag}.
	 * 
	 * @param updateFlag the update flag whose presence in this set is to be tested.
	 * 
	 * @return {@code true} if the set contains the specified update flag.
	 */
	public boolean contains(UpdateFlag updateFlag) {
		return updateFlags.contains(updateFlag);
	}

	/**
	 * Returns {@code true} if the set isn't empty, which means an update will be required.
	 * 
	 * @return {@code true} if an update is required.
	 */
	public boolean isUpdateRequired() {
		return !updateFlags.isEmpty();
	}
}
