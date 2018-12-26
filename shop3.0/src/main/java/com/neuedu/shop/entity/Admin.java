package com.neuedu.shop.entity;

import lombok.Data;

@Data
public class Admin {
	private Integer id;
	private String aname;
	private String apwd;
	
	public Admin() {

	}

	public Admin(String sname, String apwd) {
		super();
		this.aname = sname;
		this.apwd = apwd;
	}
	public Admin(Integer id, String sname, String apwd) {
		this(sname, apwd);
		this.id = id;
	}


}
