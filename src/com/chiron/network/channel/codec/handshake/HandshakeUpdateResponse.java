package com.chiron.network.channel.codec.handshake;

public final class HandshakeUpdateResponse implements HandshakeResponse {
	
	private final int responseCode;
	
	public HandshakeUpdateResponse(int responseCode) {
		this.responseCode = responseCode;
	}

	@Override public int getResponseCode() {
		return responseCode;
	}

}
