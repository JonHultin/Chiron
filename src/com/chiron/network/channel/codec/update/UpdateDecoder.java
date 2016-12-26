package com.chiron.network.channel.codec.update;

import java.util.List;

import com.chiron.network.NetworkConstants;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public final class UpdateDecoder extends ByteToMessageDecoder {

	@Override protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		in.skipBytes(4);
		ctx.writeAndFlush(NetworkConstants.CACHE_KEYS).addListener(ChannelFutureListener.CLOSE);
	}

}
