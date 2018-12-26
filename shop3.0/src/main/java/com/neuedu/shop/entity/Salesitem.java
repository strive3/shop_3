package com.neuedu.shop.entity;

import lombok.Data;

@Data
public class Salesitem {
	private Integer id;
	private Integer productid;
	private Double unitprice;
	private Integer pcount;
	private Integer orderid;
	
	
	public Salesitem() {
		super();
	}
	public Salesitem(Integer productid, Double unitprice, Integer pcount, Integer orderid) {
		super();
		this.productid = productid;
		this.unitprice = unitprice;
		this.pcount = pcount;
		this.orderid = orderid;
	}
	public Salesitem(Integer id, Integer productid, Double unitprice, Integer pcount, Integer orderid) {
		this(productid, unitprice, pcount, orderid);
		this.id = id;
	}

	
}
