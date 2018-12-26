package com.neuedu.shop.test;

import com.neuedu.shop.entity.*;
import com.neuedu.shop.mapper.IOrderDAO;
import com.neuedu.shop.mapper.IProductMapper;
import com.neuedu.shop.mapper.IUserMapper;
import com.neuedu.shop.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author 杜晓鹏
 * @create 2018-12-13 8:32
 */
public class TestMybatisOrder {
    SqlSession sqlSession = MybatisUtil.getSqlSession();
    IOrderDAO omapper = sqlSession.getMapper(IOrderDAO.class);
    IUserMapper umapper = sqlSession.getMapper(IUserMapper.class);
    IProductMapper pmapper = sqlSession.getMapper(IProductMapper.class);
    @Test
    public void testAdd(){
        User user = umapper.findById(4);
        //向购物车中添加购物项
        Cart cart = new Cart();
        Product p1 = pmapper.findById(10);//买哪件商品
        Product p2 = pmapper.findById(3);
        Product p3 = pmapper.findById(4);
        //将这3个商品添加到购物项当中
        CartItem ci1 = new CartItem();
        ci1.setProduct(p1);
        ci1.setQty(10);
        CartItem ci2 = new CartItem();
        ci2.setProduct(p2);
        ci2.setQty(30);
        CartItem ci3 = new CartItem();
        ci3.setProduct(p3);
        ci3.setQty(10);
        //将这3个购物项添加到购物车当中
        cart.add(ci1);
        cart.add(ci2);
        cart.add(ci3);

        Salesorder so = new Salesorder(user, 0, cart);
        omapper.add(so);
        System.out.println(so.getId());
        omapper.addItem(cart.getcItems(),so.getId());
        sqlSession.commit();
        sqlSession.close();

    }
    @Test
    public void testSelect(){
        List<Salesorder> salesorders = omapper.findByPage(0, 10);
        for (int i = 0; i < salesorders.size() ; i++){
            System.out.println(salesorders.get(i));
        }
    }
    @Test
    public void testFindById(){
        Salesorder salesorder = omapper.findById(44);
        System.out.println(salesorder);
    }
    @Test
    public void testUpdate(){
        Salesorder salesorder = omapper.findById(44);
        System.out.println(salesorder);
        salesorder.setStatus(1);
        omapper.update(salesorder);
        System.out.println(salesorder);
    }

    @Test
    public void test001(){
        int a = 5;
        int d = a ++;
        int c = a++ ;//+ ++a + d++ + ++d;
        c = c + ++a;
        c = c + d++;
        c = c + ++d;
    }

}
