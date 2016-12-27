package com.chiron.network.channel.codec.login;

import java.util.List;

import com.chiron.network.security.SecureCipher;
import com.chiron.util.Longs;
import com.chiron.util.Strings;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public final class LoginDecoder extends ByteToMessageDecoder {

	@Override protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
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
		ctx.writeAndFlush(new LoginResponse(username, password, secureRead, secureWrite, 2));
	}

}
