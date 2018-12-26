package com.neuedu.shop.test;

import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author 杜晓鹏
 * @create 2018-12-15 9:42
 */
public class ViewTest {
    @Test
    public void test01(){
        Set<String> set = new HashSet<>();
        set.add("a");
        set.add("b");
        set.add("c");
        set.add("d");
        /*for(String str : set){
            System.out.println(str);
        }*/
        Iterator it = set.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }

    }
    @Test
    public void test02(){
        final StringBuffer str1 = new StringBuffer("abc");
        str1.append("def");
        System.out.println(str1);
    }
}
