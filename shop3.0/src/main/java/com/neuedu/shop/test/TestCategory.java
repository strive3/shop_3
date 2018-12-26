package com.neuedu.shop.test;

import com.neuedu.shop.entity.Category;
import com.neuedu.shop.mapper.ICategoryMapper;
import com.neuedu.shop.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 杜晓鹏
 * @create 2018-12-11 19:31
 */
public class TestCategory {
    SqlSession sqlSession = MybatisUtil.getSqlSession();
    ICategoryMapper mapper = sqlSession.getMapper(ICategoryMapper.class);
    @Test
    public void testCount(){
        int count = mapper.count();
        System.out.println(count);

    }
    @Test
    public void testFindToTree(){
        List<Category> categories = mapper.findToTree(0);
//        System.out.println(categories);
//        for(int i = 0; i < categories.size() ; i ++){
//            System.out.println(categories.get(i));
//        }
        for (int j = 0; j < categories.size(); j++){
            list.add(categories.get(j));
            recursionAddCategory(categories.get(j));
        }
        //list = list.subList(10,18);
        for (int i = 0; i < list.size(); i++){
            System.out.println(list.get(i));
        }
        System.out.println(list.size());
    }
    @Test
    public void testFindById(){
        Category category = mapper.findById(12);
        System.out.println(category);
    }
    @Test
    public void testAdd(){
        int pid = 30;
        try {
            Category category = mapper.findFather(pid);
            if (category == null){
                mapper.add("strive4","strive4",pid, 1);
            }else {
                mapper.add("strive4","strive4",pid,category.getGrade() + 1);
                System.out.println(category);
            }
            mapper.updateLeafTo0(pid);
            sqlSession.commit();

        }catch (Exception e){
            sqlSession.rollback();
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }


    }
    @Test
    public void testUpdate(){
        Category category = mapper.findById(27);
        category.setName("striv");
        category.setDescr("strive");
        mapper.update(category);
        sqlSession.commit();
        sqlSession.close();
    }

    Set<Integer> set = new HashSet<>();
    @Test
    public void  testDelete(){
        try {
            Category category = mapper.findForDelete(29);
            d(category );
            System.out.println(set);
            mapper.delete(set);
            if (mapper.findCountByPid(category.getPid()) <= 0) {
                mapper.updateLeafTo1(category.getPid());
            }
            sqlSession.commit();
        }catch (Exception e){
            sqlSession.rollback();
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }

    }
    public void d(Category category ){

        for (int i = 0; i < category.getCategoryList().size();i++){
            set.add(category.getCategoryList().get(i).getId());
            //如果集合中没有category时跳出
            if (category.getCategoryList().get(i) == null){
                return;
            }
            d(category.getCategoryList().get(i));
        }
        set.add(category.getId());
    }
    List<Category> list = new ArrayList<>();
    public void recursionAddCategory(Category category){
        //如果传过来的category中没有子类别就跳出循环
        if (category.getCategoryList().size() == 0){
            return;
        }
        for (int i = 0; i < category.getCategoryList().size();i++){
            //添加的是传来category的下一层子类别
            list.add(category.getCategoryList().get(i));
            //再将下一层子类别递归
            recursionAddCategory(category.getCategoryList().get(i));
        }
    }
    @Test
    public void test(){
        Category category = mapper.findForDelete(30);
        d(category );
        System.out.println(set);
        int countByPid = mapper.findCountByPid(29);
        System.out.println(countByPid);
    }
    @Test

    public void test01(){
        Set<Integer>  set1 = new HashSet<>();
        set1.add(28);
        mapper.delete(set1);
        sqlSession.commit();
        sqlSession.close();

    }
    @Test
    public void testFindAll(){
        List<Category> categories = mapper.findAll();
        System.out.println(categories);
        System.out.println(categories.size());
    }
}