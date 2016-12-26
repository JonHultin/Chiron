package com.chiron.network.channel.codec.account;

import com.chiron.network.channel.codec.CodecResponse;

public final class AccountUsernameResponse implements CodecResponse {

	private final int responseCode;
	
	public AccountUsernameResponse(int responseCode) {
		this.responseCode = responseCode;
	}

	@Override public int getResponseCode() {
		return responseCode;
	}
	
}
