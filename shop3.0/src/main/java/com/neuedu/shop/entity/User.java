package com.neuedu.shop.entity;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class User {
	private Integer id;
	private String username;
	private String password;
	private String phone;
	private String addr;
	private Timestamp rdate;
	public User() {
		super();
	}
	public User(String username, String password, String phone, String addr) {
		super();
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.addr = addr;
	}
	public User(Integer id, String username,  String password,String phone, String addr, Timestamp rDate) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.addr = addr;
		this.rdate = rdate;
	}

	
}
