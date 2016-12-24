package com.chiron.game.task;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class TaskScheduler {

	private final Queue<Task> pending = new ArrayDeque<>();
	
	private final List<Task> active = new LinkedList<>();
	
	public void process() {
		for (Task task = pending.poll(); task != null;) {
			active.add(task);
		}
		for (Iterator<Task> $it = active.iterator(); $it.hasNext();) {
			Task task = $it.next();
			task.tick();
			if (task.isCanceled()) {
				$it.remove();
			}
		}
	}
	
	public boolean schedule(Task task) {
		return pending.add(task);
	}
}
