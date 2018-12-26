package com.neuedu.shop.test;

import com.neuedu.shop.entity.Category;
import com.neuedu.shop.entity.Product;
import com.neuedu.shop.entity.User;
import com.neuedu.shop.mapper.ICategoryMapper;
import com.neuedu.shop.mapper.IProductMapper;
import com.neuedu.shop.mapper.IUserMapper;
import com.neuedu.shop.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 杜晓鹏
 * @create 2018-12-10 19:25
 */
public class TestMybatis {
    @Test
    public void testpaging(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);

        List<User> page = mapper.findByPage(1,10);
        System.out.println(mapper.count());
        System.out.println(page);
    }
    @Test
    public void testpaging1(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);
        Map<String,Object> map = new HashMap<>();
        map.put("startIndex",1);
        map.put("pageSize",10);
        List<User> page = mapper.findAll(map);
        System.out.println(mapper.count());
        System.out.println(page);
    }
    @Test
    public void testproduct(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        IProductMapper mapper = sqlSession.getMapper(IProductMapper.class);
        List<Product> products = mapper.findAll();
        System.out.println(products);
    }
    @Test
    public void testaddproduct(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        IProductMapper mapper = sqlSession.getMapper(IProductMapper.class);
        ICategoryMapper cmapper = sqlSession.getMapper(ICategoryMapper.class);
        Category category = cmapper.findById(13);
        mapper.add(new Product("测试","hao",2000.0,category));
        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void deletepro(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        IProductMapper mapper = sqlSession.getMapper(IProductMapper.class);
        mapper.delete(1005);
        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void updatepro(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        IProductMapper mapper = sqlSession.getMapper(IProductMapper.class);
        Product product = mapper.findById(1003);
System.out.println(product);
        /*product.setName("enenen");
        product.setNormalprice(300.0);
        product.setDescr("yeyeye");
        CategoryDAOImpt dao = new CategoryDAOImpt();
        Category category = dao.findById(13);
        product.setCategory(category);
        mapper.update(product);
        sqlSession.commit();*/
        sqlSession.close();
    }
    @Test
    public void testSearchS(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        IProductMapper mapper = sqlSession.getMapper(IProductMapper.class);
        List<Product> products = mapper.findByKeywords("测试商品10", 0, 10);
        System.out.println(products);
    }
    @Test
    public void testCSearch(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        IProductMapper mapper = sqlSession.getMapper(IProductMapper.class);
        Map<String,Object> map = new HashMap<>();
        map.put("categoryid",5);
        map.put("keywords","10");
        map.put("lownormalprice",1);
        map.put("highnormalprice",2147483647);
        map.put("lowmemberprice",1);
        map.put("highmemberprice",2147483647);
        map.put("startdate","2018-07-03");
        map.put("enddate","2018-12-03");
        map.put("startIndex",0);
        map.put("pagesize",10);
        List<Product> products = mapper.complexSearchs(map);
        System.out.println(products.size());
        System.out.println(products);
    }
    @Test
    public void test001(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        IProductMapper mapper = sqlSession.getMapper(IProductMapper.class);
        Map<String,Object> map = new HashMap<>();
        map.put("startIndex",0);
        map.put("pagesize",10);
        List<Product> lastByPagings = mapper.findLastByPagings(map);
        System.out.println(lastByPagings);
    }
    @Test
    public void testcount(){
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        IProductMapper mapper = sqlSession.getMapper(IProductMapper.class);
        int i = mapper.countAll();
        System.out.println(i);
        int i1 = mapper.countSimple("10");
        System.out.println(i1);
        Map<String,Object> map = new HashMap<>();
        map.put("categoryid",5);
        map.put("keywords","10");
        map.put("lownormalprice",1);
        map.put("highnormalprice",2147483647);
        map.put("lowmemberprice",1);
        map.put("highmemberprice",2147483647);
        map.put("startdate","2018-07-03");
        map.put("enddate","2018-12-03");
        map.put("startIndex",0);
        map.put("pagesize",10);
        int i2 = mapper.countComp(map);
        System.out.println(i2);
    }


}
