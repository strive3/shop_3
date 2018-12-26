package com.neuedu.shop.mapper;

import java.util.List;
import java.util.Set;

import com.neuedu.shop.entity.Category;
import com.neuedu.shop.entity.Paging;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableInterceptor.INACTIVE;

public interface ICategoryMapper {
    int count();

    // 查询操作
    List<Category> findAll();

    List<Category> findToTree(int pid);

    // 根据id查找管理员
    Category findById(Integer id);

    //添加操作的时候调用以下3个方法
    Category findFather(int pid);

    void add(@Param("name") String name, @Param("descr") String descr, @Param("pid") int pid, @Param("grade") int grade);

    void updateLeafTo0(int pid);

    // 更新
    void update(Category category);

    // 删除时调用以下4个方法
    Category findForDelete(int id);

    void delete(@Param("set") Set<Integer> set);

    int findCountByPid(int pid);

    void updateLeafTo1(int pid);


}
