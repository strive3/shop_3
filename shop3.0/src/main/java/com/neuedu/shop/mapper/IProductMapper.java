package com.neuedu.shop.mapper;
/**
 * 对Admin操作对应的crud
 *
 * @author 杜晓鹏
 */

import java.util.List;
import java.util.Map;

import com.neuedu.shop.entity.Paging;
import com.neuedu.shop.entity.Product;
import org.apache.ibatis.annotations.Param;

public interface IProductMapper {
    /**
     * 查询操作
     *
     * @return products
     */
    List<Product> findAll();

    /**
     * 添加商品
     *
     * @param product
     */
    void add(Product product);

    // 删除
    void delete(Integer id);

    // 更新
    void update(Product product);

    /**
     * 通过id找到商品
     * @param id
     * @return
     */
    Product findById(Integer id);

    /**
     * 简单搜索 按照关键字搜索
     */
    //Paging<Product> findByKeywords(String keywords, int rownum, int pagesize);
    List<Product> findByKeywords(@Param("keywords") String keywords, @Param("startIndex") int rownum, @Param("pagesize") int pagesize);

    /**
     * 复杂查询
     */
    List<Product> complexSearchs(Map<String, Object> map);

    List<Product> findLastByPagings(Map<String, Object> map);


    /**
     * 查询一共多少条数据
     */
    int count(String sql);

    int countAll();

    int countSimple(String keywords);

    int countComp(Map<String, Object> map);
}
