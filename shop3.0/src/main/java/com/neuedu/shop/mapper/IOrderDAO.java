package com.neuedu.shop.mapper;

import java.util.List;

import com.neuedu.shop.entity.CartItem;
import com.neuedu.shop.entity.Paging;
import com.neuedu.shop.entity.Salesitem;
import com.neuedu.shop.entity.Salesorder;
import org.apache.ibatis.annotations.Param;

public interface IOrderDAO {
	List<Salesorder> findByPage(@Param("startIndex") int rownum , @Param("pagesize") int pagesize);

	void delete(Integer id);
	
	void add(Salesorder so);
	void addItem(@Param("list") List<CartItem> cartItems, @Param("key") int key);

	Salesorder findById(Integer id);

	int count();

	void update(Salesorder salesorder);
}
