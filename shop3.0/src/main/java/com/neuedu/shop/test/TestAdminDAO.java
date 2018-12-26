package com.neuedu.shop.test;

import com.neuedu.shop.util.ServletUtil;

public class TestAdminDAO {
	public static void main(String[] args) {
		/*AdminDAOImpl daoImpl = new AdminDAOImpl();
		List<Admin> admins = daoImpl.findAll();
		
		for (Admin admin : admins) {
			System.out.println(admin);
		}*/
		
		System.out.println(ServletUtil.getMD5("111111"));
	}
}
