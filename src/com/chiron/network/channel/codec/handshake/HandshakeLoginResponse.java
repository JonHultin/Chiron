package com.chiron.network.channel.codec.handshake;

public final class HandshakeLoginResponse implements HandshakeResponse {

	private final long serverSeed;

	private final int responseCode;
	
	public HandshakeLoginResponse(long serverSeed, int responseCode) {
		this.serverSeed = serverSeed;
		this.responseCode = responseCode;
	}

	public long getServerSeed() {
		return serverSeed;
	}

	@Override public int getResponseCode() {
		return responseCode;
	}
	
}
