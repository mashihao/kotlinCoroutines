package com.msh.flow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 马世豪
 * time : 2021/9/24 14
 * email : ma_shihao@yeah.net
 * des :
 */
public class Test {
    @org.junit.Test
    public void test() {
        List<Integer> listA = new ArrayList<>();
        listA.add(1);
        listA.add(2);
        listA.add(3);
        listA.add(4);
        listA.add(5);
        List<Integer> listA_new = new ArrayList<>();
        for (int i = 0; i < listA.size(); i++) {
            if (listA.get(i) > 2) {
                listA_new.add(listA.get(i));
            }
        }
        List<String> listB = new ArrayList<>();

        for (int i = 0; i < listA_new.size(); i++) {
            listB.add("listb---->"+listA_new.get(i));
        }
        System.out.println(listB);
    }
}
