package com.chiron.network.channel.codec.login;

import com.chiron.network.channel.codec.CodecResponse;
import com.chiron.network.security.SecureCipher;

public final class LoginResponse implements CodecResponse {

	private final String username;
	
	private final String password;
	
	private final SecureCipher secureRead;
	
	private final SecureCipher secureWrite;
	
	private final int responseCode;
	
	public LoginResponse(String username, String password, SecureCipher secureRead, SecureCipher secureWrite, int responseCode) {
		this.username = username;
		this.password = password;
		this.secureRead = secureRead;
		this.secureWrite = secureWrite;
		this.responseCode = responseCode;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public SecureCipher getSecureRead() {
		return secureRead;
	}

	public SecureCipher getSecureWrite() {
		return secureWrite;
	}

	@Override public int getResponseCode() {
		return responseCode;
	}

}
