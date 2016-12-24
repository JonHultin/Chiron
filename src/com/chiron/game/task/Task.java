package com.chiron.game.task;

public abstract class Task {

	private int delay;
	
	private int ticks;
	
	private boolean canceled;
	
	public Task(int delay, boolean push) {
		this.delay = delay;
		this.ticks = push ? 0 : delay;
	}

	protected abstract void execute();
	
	protected void tick() {
		if (!isCanceled() && ticks <= 0) {
			execute();
			ticks = delay;
		}
	}
	
	public final int getDelay() {
		return delay;
	}

	public final void setDelay(int delay) {
		this.delay = delay;
	}

	public final boolean isCanceled() {
		return canceled;
	}

	public final void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
	
}
