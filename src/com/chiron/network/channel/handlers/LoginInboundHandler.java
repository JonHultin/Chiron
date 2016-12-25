package com.chiron.network.channel.handlers;

import java.security.SecureRandom;
import java.util.List;

import com.chiron.game.GameWorld;
import com.chiron.game.model.actor.player.Player;
import com.chiron.network.NetworkConstants;
import com.chiron.network.security.SecureCipher;
import com.chiron.util.Longs;
import com.chiron.util.Strings;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public final class LoginInboundHandler extends ByteToMessageDecoder {

	protected enum State {
		SERVICE,
		UPDATE,
		LOGIN;
	}
	
	private final GameWorld world;
	
	private State state;

	public LoginInboundHandler(GameWorld world) {
		this.world = world;
		this.state = State.SERVICE;
	}
	
	private void handleService(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
		int opcode = in.readUnsignedByte();
		switch (opcode) {
		case 14:
			in.skipBytes(1);
			ctx.writeAndFlush(ctx.alloc().buffer().writeByte(0).writeLong(new SecureRandom().nextLong()));
			state = State.LOGIN;
			break;
			
		case 15: 
			if (in.readInt() == 508) {
				ctx.writeAndFlush(ctx.alloc().buffer().writeByte(0));
				state = State.UPDATE;
				return;
			}
			ctx.writeAndFlush(ctx.alloc().buffer().writeByte(6)).addListener(ChannelFutureListener.CLOSE);
			break;
		}
	}
	
	private void handleUpdate(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
		in.skipBytes(4);
		ByteBuf message = ctx.alloc().buffer();
		NetworkConstants.CACHE_KEYS.forEach($it -> message.writeByte($it));
		ctx.writeAndFlush(message).addListener(ChannelFutureListener.CLOSE);
	}
	
	private void handleLogin(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
		int opcode = in.readUnsignedByte();
		if (opcode != 16 && opcode != 18) {
			System.out.println("Invalid Opcode");
			return;
		}
		int length = in.readUnsignedShort();
		if (length > in.readableBytes()) {
			System.out.println("Length exceeds readable bytes");
			return;
		}
		int version = in.readInt();
		if (version != 508) {
			System.out.println("Invalid Client");
			return;
		}
		in.skipBytes(29);
		Strings.readString(in);
		in.skipBytes(116);
		//boolean hd = true;
		int rsaOpcode = in.readUnsignedByte();
		if (rsaOpcode != 10) {
			rsaOpcode = in.readUnsignedByte();
			//hd = false;
		}
		if (rsaOpcode != 10) {
			System.out.println("Invalid rsa opcode");
			return;
		}
		long clientSeed = in.readLong();
		long serverSeed = in.readLong();
		final int seed [] = { (int) (clientSeed >> 32), (int) clientSeed, (int) (serverSeed >> 32), (int) serverSeed };
		SecureCipher secureRead = new SecureCipher(seed);
		for (int i = 0; i < seed.length; i++) {
			seed[i] += 50;
		}
		SecureCipher secureWrite = new SecureCipher(seed);
		String username = Longs.toString(in.readLong());
		String password = Strings.readString(in);
		ctx.pipeline().remove(this);
		ctx.pipeline().addLast("outbound_handler", new MessageOutboundHandler(secureWrite));
		ctx.pipeline().addLast("inbound_handler", new MessageInboundHandler(secureRead));
		world.queue(new Player(world, ctx.channel(), username, password));
	}
	
	@Override protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		switch (state) {
		
		case SERVICE:
			handleService(ctx, in, out);
			break;
			
		case UPDATE:
			handleUpdate(ctx, in, out);
			break;
			
		case LOGIN:
			handleLogin(ctx, in, out);
			break;
			
		}
	}
	
}
