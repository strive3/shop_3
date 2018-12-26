package com.neuedu.shop.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Salesorder {
	private Integer id; 	//主键
	private User user;		
	private Timestamp odate;	//下单时间
	private Integer status;		//0：未付款  1：已付款
	private Cart cart;
	private List<Salesitem> salesitems;  //存储当前订单的所有订单项
	public Salesorder() {
		super();
	}

	public Salesorder(Integer id, User user, Timestamp odate, Integer status, Cart cart) {
		super();
		this.id = id;
		this.user = user;
		this.odate = odate;
		this.status = status;
		this.cart = cart;
	}

	public Salesorder(User user, Integer status, Cart cart) {
		super();
		this.user = user;
		this.status = status;
		this.cart = cart;
	}


	
	
	
}
