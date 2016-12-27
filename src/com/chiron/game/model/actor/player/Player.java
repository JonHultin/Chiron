package com.chiron.game.model.actor.player;

import com.chiron.game.GameWorld;
import com.chiron.game.model.Position;
import com.chiron.game.model.actor.Actor;
import com.chiron.game.model.actor.ActorEventListener;
import com.chiron.network.message.MessageEncoder;

import io.netty.channel.Channel;

public final class Player extends Actor {

	private final ActorEventListener<Player> eventHandler = new PlayerEventHandler(this);
	
	private final Channel channel;
	
	private final String username;
	
	private final String password;
	
	public Player(GameWorld world, Channel channel, String username, String password) {
		super(world, new Position(3222, 3222));
		this.channel = channel;
		this.username = username;
		this.password = password;
	}
	
	public void write(MessageEncoder encoder) {
		channel.writeAndFlush(encoder.handle(this), channel.voidPromise());
	}
	
	public Channel getChannel() {
		return channel;
	}
	
	public final String getUsername() {
		return username;
	}

	public final String getPassword() {
		return password;
	}

	@Override public ActorEventListener<?> getEventHandler() {
		return eventHandler;
	}

}
