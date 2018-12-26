package com.neuedu.shop.mapper;
/**
 * 对Admin操作对应的crud
 * @author 杜晓鹏
 *
 */

import java.util.List;
import java.util.Map;

import com.neuedu.shop.entity.Paging;
import com.neuedu.shop.entity.User;
import org.apache.ibatis.annotations.Param;

public interface IUserMapper {
	// 查询操作
	List<User> findByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pagesize);
	List<User> findAll(Map<String,Object> map);
	// 添加操作
	void add(User user);

	// 删除
	void delete(Integer id);

	// 更新
	void update(User user);

	// 根据id查找管理员
	User findById(Integer id);

	//根据name查找管理员
	User findByName(String name);

	int count();
}
