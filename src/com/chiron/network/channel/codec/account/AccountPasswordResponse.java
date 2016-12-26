package com.chiron.network.channel.codec.account;

public final class AccountPasswordResponse {

	private final String username;
	
	private final String password;
	
	private final int day;
	
	private final int month;
	
	private final int year;
	
	private final int country;
	
	private final int responseCode;

	public AccountPasswordResponse(String username, String password, int day, int month, int year, int country, int responseCode) {
		this.username = username;
		this.password = password;
		this.day = day;
		this.month = month;
		this.year = year;
		this.country = country;
		this.responseCode = responseCode;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	public int getCountry() {
		return country;
	}

	public int getResponseCode() {
		return responseCode;
	}
	
}
