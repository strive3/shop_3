package com.neuedu.shop.test;

import com.neuedu.shop.entity.Cart;
import com.neuedu.shop.entity.CartItem;
import com.neuedu.shop.mapper.IProductMapper;
import com.neuedu.shop.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * ≤‚ ‘π∫ŒÔ≥µ
 * @author ∂≈œ˛≈Ù
 *
 */
public class TestCart {
	public static void main(String[] args) {
		SqlSession sqlSession = MybatisUtil.getSqlSession();
		IProductMapper mapper = sqlSession.getMapper(IProductMapper.class);
		Cart cart = new Cart();
		List<CartItem> cItems = cart.getcItems();
		
		CartItem ci = new CartItem();
		ci.setProduct(mapper.findById(1000));
		ci.setQty(8);
		cItems.add(ci);
		
System.out.println(cItems);
		System.out.println(cart.getTotals());
	}
}
