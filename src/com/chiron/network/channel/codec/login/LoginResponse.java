package com.chiron.network.channel.codec.login;

import com.chiron.network.security.SecureCipher;

public final class LoginResponse {

	private final String username;
	
	private final String password;
	
	private final SecureCipher secureRead;
	
	private final SecureCipher secureWrite;
	
	public LoginResponse(String username, String password, SecureCipher secureRead, SecureCipher secureWrite) {
		this.username = username;
		this.password = password;
		this.secureRead = secureRead;
		this.secureWrite = secureWrite;
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

}
